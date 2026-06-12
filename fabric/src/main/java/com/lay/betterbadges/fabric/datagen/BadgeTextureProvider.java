package com.lay.betterbadges.fabric.datagen;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class BadgeTextureProvider implements DataProvider {

    private final PackOutput output;

    public BadgeTextureProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {

        return CompletableFuture.runAsync(() -> {

            try {

                Path root = Paths.get("C:\\Users\\Clarence\\Desktop\\projects\\betterbadges-0.0.1\\common\\src\\main\\resources");

                Path shinePath = root.resolve(
                        "assets/betterbadges/textures/animation/shine.png"
                );

                NativeImage shine = NativeImage.read(
                        Files.newInputStream(shinePath)
                );

                Path badgeDir = root.resolve(
                        "assets/betterbadges/textures/item/badge"
                );

                Files.list(badgeDir)
                        .filter(path -> path.toString().endsWith(".png"))
                        .forEach(path -> {

                            try {

                                NativeImage badge = NativeImage.read(
                                        Files.newInputStream(path)
                                );

                                NativeImage result =
                                        BadgeTextureGenerator.applyShine(
                                                badge,
                                                shine
                                        );

                                Path outputFile =
                                        output.getOutputFolder()
                                                .resolve(
                                                        "assets/betterbadges/textures/generated/"
                                                                + path.getFileName()
                                                );

                                Files.createDirectories(
                                        outputFile.getParent()
                                );

                                result.writeToFile(outputFile.toFile());

                                badge.close();
                                result.close();

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });

                shine.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String getName() {
        return "Badge Texture Generator";
    }

    public final class BadgeTextureGenerator {

        public static NativeImage applyShine(
                NativeImage badge,
                NativeImage shine
        ) {
            NativeImage result = new NativeImage(
                    NativeImage.Format.RGBA,
                    badge.getWidth(),
                    badge.getHeight(),
                    false
            );

            for (int y = 0; y < badge.getHeight(); y++) {
                for (int x = 0; x < badge.getWidth(); x++) {

                    int badgePixel = badge.getPixelRGBA(x, y);

                    int badgeAlpha = (badgePixel >>> 24) & 0xFF;

                    // preserve transparency
                    if (badgeAlpha == 0) {
                        result.setPixelRGBA(x, y, 0);
                        continue;
                    }

                    result.setPixelRGBA(x, y, badgePixel);

                    int shinePixel = shine.getPixelRGBA(
                            x % shine.getWidth(),
                            y % shine.getHeight()
                    );

                    int shineAlpha = (shinePixel >>> 24) & 0xFF;

                    if (shineAlpha > 0) {
                        result.setPixelRGBA(x, y, shinePixel);
                    }
                }
            }

            return result;
        }

        private BadgeTextureGenerator() {}
    }
}