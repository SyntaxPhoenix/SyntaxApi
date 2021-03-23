package com.syntaxphoenix.syntaxapi.config.nbt;

import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.config.ConfigTools;
import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtParser;
import com.syntaxphoenix.syntaxapi.nbt.utils.NbtStorage;

public class NbtConfigSection extends BaseSection implements NbtStorage<NbtCompound> {

    public NbtConfigSection() {
        super("");
    }

    public NbtConfigSection(String name) {
        super(name);
    }

    @Override
    public NbtCompound asNbt() {
        NbtCompound data = new NbtCompound();
        if (!values.isEmpty()) {
            for (Entry<String, Object> entry : values.entrySet()) {
                Object raw = entry.getValue();
                if (raw instanceof NbtConfigSection) {
                    data.set(entry.getKey(), ((NbtConfigSection) raw).asNbt());
                } else if (raw instanceof NbtTag) {
                    data.set(entry.getKey(), (NbtTag) raw);
                } else {
                    NbtTag tag = NbtParser.fromObject(raw);
                    if (tag != null) {
                        data.set(entry.getKey(), tag);
                    }
                }
            }
        }
        data.set("section", true);
        return data;
    }

    @Override
    public void fromNbt(NbtCompound data) {
        values.clear();
        for (String key : data.getKeys()) {
            NbtTag base = data.get(key);
            if (base.getType() == NbtType.COMPOUND) {
                NbtCompound compound = (NbtCompound) base;
                if (compound.hasKey("section")) {
                    if (compound.getBoolean("section")) {
                        saveSection(initSection(key, compound));
                        continue;
                    }
                }
                set(key, compound);
            } else {
                if (key.equals("section")) {
                    if (base.getType() == NbtType.BYTE) {
                        if (((NbtByte) base).getByteValue() == (byte) 0) {
                            continue;
                        }
                    }
                }
                set(key, base);
            }
        }
    }

    @Override
    public boolean contains(String path) {
        return get(path) != null;
    }

    public NbtTag check(String path, NbtTag value) {
        NbtTag current = get(path);
        if (current != null) {
            return current;
        }
        set(path, value);
        return value;
    }

    @Override
    public NbtTag get(String path) {
        if (!path.isEmpty()) {
            return get(ConfigTools.getKeys(path));
        }
        return null;
    }

    private NbtTag get(String[] key) {
        if (key.length != 0) {
            if (values.containsKey(key[0])) {
                if (key.length > 1) {
                    NbtConfigSection section;
                    if ((section = getSection(key[0])) != null) {
                        return section.get(ConfigTools.getNextKeys(key));
                    }
                } else {
                    return (NbtTag) values.get(key[0]);
                }
            }
        }
        return null;
    }

    @Override
    public NbtConfigSection getSection(String path) {
        if (!path.isEmpty()) {
            return getSection(ConfigTools.getKeys(path));
        }
        return null;
    }

    private NbtConfigSection getSection(String[] key) {
        if (key.length != 0) {
            if (values.containsKey(key[0])) {
                if (key.length > 1) {
                    NbtConfigSection section;
                    if ((section = getSection(key[0])) != null) {
                        return section.getSection(ConfigTools.getNextKeys(key));
                    }
                } else {
                    Object uncasted;
                    if ((uncasted = values.get(key[0])) instanceof NbtConfigSection) {
                        return (NbtConfigSection) uncasted;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public NbtConfigSection createSection(String path) {
        if (!path.isEmpty()) {
            return createSection(ConfigTools.getKeys(path));
        }
        return null;
    }

    private NbtConfigSection createSection(String[] key) {
        if (key.length != 0) {
            if (values.containsKey(key[0])) {
                if (key.length > 1) {
                    NbtConfigSection section;
                    if ((section = getSection(key[0])) == null) {
                        section = saveSection(initSection(key[0]));
                    }
                    return section.createSection(ConfigTools.getNextKeys(key));
                } else {
                    Object uncasted;
                    if (!((uncasted = get(key[0])) instanceof NbtConfigSection)) {
                        uncasted = saveSection(initSection(key[0]));
                    }
                    return (NbtConfigSection) uncasted;
                }
            } else {
                NbtConfigSection section = saveSection(initSection(key[0]));
                if (key.length > 1) {
                    return section.createSection(ConfigTools.getNextKeys(key));
                } else {
                    return section;
                }
            }
        }
        return null;
    }

    public void set(String path, NbtTag value) {
        String[] keys = ConfigTools.getKeys(path);
        String key = ConfigTools.getLastKey(keys);
        set(key, ConfigTools.getKeysWithout(keys, key), value);
    }

    public void set(String key, String[] path, NbtTag value) {
        if (path.length == 0) {
            values.put(key, value);
        } else {
            NbtConfigSection section = getSection(path);
            if (section == null) {
                section = createSection(path);
            }
            section.set(key, value);
        }
    }

    @Override
    protected NbtConfigSection initSection(String name) {
        return new NbtConfigSection(name);
    }

    protected NbtConfigSection initSection(String name, NbtCompound data) {
        NbtConfigSection section = new NbtConfigSection(name);
        section.fromNbt(data);
        return section;
    }

    @Override
    protected boolean isSectionInstance(BaseSection section) {
        return section instanceof NbtConfigSection;
    }

    private NbtConfigSection saveSection(NbtConfigSection section) {
        set(section.getName(), section.asNbt());
        return section;
    }

    @Override
    public String toString() {
        return asNbt().toMSONString();
    }

}