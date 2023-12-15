package dev.huskuraft.effortless.tag;

import dev.huskuraft.effortless.core.Item;
import dev.huskuraft.effortless.core.ItemStack;
import dev.huskuraft.effortless.core.Resource;
import dev.huskuraft.effortless.text.Text;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class TagRecord extends TagElement {

    public abstract String getString(String key);

    public abstract void putString(String key, String value);

    public abstract Text getText(String key);

    public abstract void putText(String key, Text value);

    public abstract boolean getBoolean(String key);

    public abstract void putBoolean(String key, boolean value);

    public abstract byte getByte(String key);

    public abstract void putByte(String key, byte value);

    public abstract short getShort(String key);

    public abstract void putShort(String key, short value);

    public abstract int getInt(String key);

    public abstract void putInt(String key, int value);

    public abstract long getLong(String key);

    public abstract void putLong(String key, long value);

    public abstract float getFloat(String key);

    public abstract void putFloat(String key, float value);

    public abstract double getDouble(String key);

    public abstract void putDouble(String key, double value);

    public abstract boolean[] getBooleanArray(String key);

    public abstract void putBooleanArray(String key, boolean[] value);

    public abstract byte[] getByteArray(String key);

    public abstract void putByteArray(String key, byte[] value);

    public abstract short[] getShortArray(String key);

    public abstract void putShortArray(String key, short[] value);

    public abstract int[] getIntArray(String key);

    public abstract void putIntArray(String key, int[] value);

    public abstract long[] getLongArray(String key);

    public abstract void putLongArray(String key, long[] value);

    public abstract float[] getFloatArray(String key);

    public abstract void putFloatArray(String key, float[] value);

    public abstract double[] getDoubleArray(String key);

    public abstract void putDoubleArray(String key, double[] value);

    public abstract TagElement getElement(String key);

    public abstract void putElement(String key, TagElement value);

    public abstract <T> T getElement(String key, TagReader<T> reader);

    public abstract <T> void putElement(String key, T value, TagWriter<T> writer);

    public abstract <T> List<T> getList(String key, TagReader<T> writer);

    public abstract <T> void putList(String key, Collection<T> collection, TagWriter<T> writer);


    public final <T> T get(TagReader<T> writer) {
        return writer.read(this);
    }

    public final <T> void put(T value, TagWriter<T> writer) {
        writer.write(this, value);
    }

    public final <T extends Enum<T>> T getEnum(String key, Class<T> clazz) {
        var id = Resource.decompose(getString(key));
        return Enum.valueOf(clazz, id.getPath().toUpperCase(Locale.ROOT));
    }

    public final <T extends Enum<T>> void putEnum(String key, Enum<T> value) {
        var id = Resource.of(value.name().toLowerCase(Locale.ROOT));
        putString(key, id.toString());
    }

    public final Resource getResource(String key) {
        return Resource.decompose(getString(key));
    }

    public final void putResource(String key, Resource value) {
        putString(key, value.toString());
    }

    public final UUID getUUID(String key) {
        return UUID.fromString(getString(key));
    }

    public final void putUUID(String key, UUID value) {
        putString(key, value.toString());
    }

    public final Item getItem(String key) {
        return Item.fromId(getResource(key));
    }

    public final void putItem(String key, Item value) {
        putResource(key, value.getId());
    }

    public final ItemStack getItemStack(String key) {
        return getElement(key, (tag1) -> ItemStack.of(
                tag1.getAsRecord().getItem("Item"),
                tag1.getAsRecord().getInt("Count"),
                tag1.getAsRecord().getElement("Tag").getAsRecord()
        ));
    }

    public final void putItemStack(String key, ItemStack value) {
        putElement(key, value, (tag1, itemStack) -> {
            tag1.getAsRecord().putItem("Item", itemStack.getItem());
            tag1.getAsRecord().putInt("Count", itemStack.getStackSize());
            tag1.getAsRecord().putElement("Tag", itemStack.getTag());
        });
    }

}
