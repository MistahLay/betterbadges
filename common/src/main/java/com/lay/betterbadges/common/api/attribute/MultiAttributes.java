package com.lay.betterbadges.common.api.attribute;

import com.google.common.collect.HashBiMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.Map;

/**
 * Register multiple attributes with a similar theme
 */
public class MultiAttributes<T> {

    private final String root;
    private final AttributeNameProvider<T> nameProvider;
    private final Map<T, Holder<Attribute>> attributes = HashBiMap.create();

    public MultiAttributes(String root, AttributeNameProvider<T> nameProvider, AttributeProvider attribute, Iterable<T> toRegister){
        this.root = root;
        this.nameProvider = nameProvider;

        for (T object : toRegister){
            this.attributes.put(object, attribute.getAttribute(this.getPath(object)));
        }
    }

    public Holder<Attribute> get(T object){
        return this.attributes.get(object);
    }

    public Map<T, Holder<Attribute>> getAll(){
        return HashBiMap.create(this.attributes);
    }

    public String getPath(T object){
        return this.root + this.nameProvider.getName(object);
    }

    public void applyToBuilder(AttributeSupplier.Builder builder){
        for (Holder<Attribute> attribute : this.attributes.values()){
            builder.add(attribute);
        }
    }

    public interface AttributeNameProvider<T> {
        String getName(T original);
    }

    public interface AttributeProvider {
        Holder<Attribute> getAttribute(String id);
    }

}
