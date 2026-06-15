package com.lay.betterbadges.common.render.atlas.sources;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.util.texture.TextureHelper;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;

import java.util.List;
import java.util.Optional;

public class AnimationOverlayPermutations implements SpriteSource {

    public static MapCodec<AnimationOverlayPermutations> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceLocation.CODEC.listOf().fieldOf("textures").forGetter(animationOverlay -> animationOverlay.textures),
                    ResourceLocation.CODEC.fieldOf("animation_sprite").forGetter(animationOverlay -> animationOverlay.animationSprite),
                    Codec.INT.fieldOf("frames").forGetter(animationOverlay -> animationOverlay.frames)
            ).apply(instance, AnimationOverlayPermutations::new)
    );

    public static ResourceLocation createPath(ResourceLocation itemTexture, String animationSuffix, int index){
        return itemTexture.withSuffix("." + animationSuffix + "_frame" + index);
    }

    private final List<ResourceLocation> textures;
    private final ResourceLocation animationSprite;
    private final int frames;

    private AnimationOverlayPermutations(List<ResourceLocation> list, ResourceLocation animationSprite, int frames) {
        this.textures = list;
        this.animationSprite = animationSprite;
        this.frames = frames;
    }

    @Override
    public void run(ResourceManager resourceManager, Output output) {
        Optional<Resource> animationSpriteOptional = resourceManager.getResource(TextureHelper.png(animationSprite));
        LazyLoadedImage lazyAnimationSprite = new LazyLoadedImage(animationSprite, (Resource)animationSpriteOptional.get(), textures.size());
        String animSpriteId = this.animationSprite.getPath();
        animSpriteId = animSpriteId.substring(animSpriteId.lastIndexOf('/') + 1);
        for (ResourceLocation itemTexture : textures) {
            ResourceLocation path = TextureHelper.png(itemTexture);
            Optional<Resource> optional = resourceManager.getResource(path);

            if (optional.isEmpty()) {
                BetterBadges.LOGGER.warn("Unable to find texture {}", path);
            } else {
                for (int frame = 0; frame < this.frames; frame++) {
                    LazyLoadedImage lazyLoadedItemImage = new LazyLoadedImage(path, optional.get(), 1);

                    ResourceLocation frameLocation = createPath(itemTexture, animSpriteId, frame);

                    output.add(
                            frameLocation,
                            new ItemInstance(lazyLoadedItemImage, lazyAnimationSprite, frameLocation, frame)
                    );
                }
            }
        }
    }

    @Override
    public SpriteSourceType type() {
        return ModAtlasSources.ANIMATION_OVERLAY;
    }

    public static class ItemInstance implements SpriteSupplier {

        private final LazyLoadedImage image;
        private final LazyLoadedImage animation;
        private final int frameIndex;
        private final ResourceLocation resourceLocation;

        public ItemInstance(LazyLoadedImage image, LazyLoadedImage animation,
                            ResourceLocation resourceLocation, int frameIndex) {
            this.image = image;
            this.animation = animation;
            this.frameIndex = frameIndex;
            this.resourceLocation = resourceLocation;
        }

        @Override
        public SpriteContents apply(SpriteResourceLoader spriteResourceLoader) {
            try {
                NativeImage image = this.image.get();
                NativeImage animation = this.animation.get();

                NativeImage output = new NativeImage(16, 16, true);

                int frameYOffset = this.frameIndex * 16;

                for (int y = 0; y < 16; y++) {
                    for (int x = 0; x < 16; x++) {
                        int itemPixel = image.getPixelRGBA(x, y);

                        int shinePixel = animation.getPixelRGBA(
                                x,
                                y + frameYOffset
                        );

                        int result = blend(itemPixel, shinePixel);
                        output.setPixelRGBA(x, y, result);
                    }
                }

                return new SpriteContents(
                        this.resourceLocation,
                        new FrameSize(16, 16),
                        output,
                        ResourceMetadata.EMPTY
                );

            } catch (Exception e) {
                BetterBadges.LOGGER.error("Failed to generate frame {} for {}", frameIndex, resourceLocation, e);
            } finally {
                this.image.release();
            }

            return MissingTextureAtlasSprite.create();
        }

        @Override
        public void discard() {
            this.image.release();
        }

        private static int blend(int itemPixel, int shinePixel) {
            int itemAlpha = (itemPixel >>> 24) & 0xFF;
            if (itemAlpha == 0) return itemPixel;

            int shineAlpha = (shinePixel >>> 24) & 0xFF;
            if (shineAlpha == 0) return itemPixel;

            int iR = (itemPixel >> 16) & 0xFF;
            int iG = (itemPixel >> 8)  & 0xFF;
            int iB =  itemPixel        & 0xFF;

            int sR = (shinePixel >> 16) & 0xFF;
            int sG = (shinePixel >> 8)  & 0xFF;
            int sB =  shinePixel        & 0xFF;

            float a = shineAlpha / 255f;
            int r = (int)(sR * a + iR * (1 - a));
            int g = (int)(sG * a + iG * (1 - a));
            int b = (int)(sB * a + iB * (1 - a));

            return (itemAlpha << 24) | (r << 16) | (g << 8) | b;
        }
    }
}
