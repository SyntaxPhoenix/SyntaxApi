package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;

public class Skin implements Serializable {

    public static final Skin NONE = new Skin();
    private static final JsonParser PARSER = new JsonParser();
    private static final JsonWriter WRITER = new JsonWriter().setPretty(true);

    /**
    * 
    */
    private static final long serialVersionUID = -6279824025820566499L;
    private final boolean editable;
    private String name;
    private String value;
    private String signature;
    private SkinModel model;

    private Skin() {
        this.editable = false;
    }

    public Skin(String name, String value, String signature) {
        this.name = name;
        this.editable = false;
        this.value = value;
        this.signature = signature;
        this.model = parseModel();
    }

    public Skin(String name, String value, String signature, boolean editable) {
        this.name = name;
        this.editable = editable;
        this.value = value;
        this.signature = signature;
        this.model = parseModel();
    }

    public boolean isEditable() {
        return editable;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (!isEditable() || value == null) {
            return;
        }
        this.value = value;
        setModel(parseModel());
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        if (!isEditable() || signature == null) {
            return;
        }
        this.signature = signature;
    }

    public SkinModel getModel() {
        return model;
    }

    public void setModel(SkinModel model) {
        if (!isEditable() || model == null) {
            return;
        }
        this.model = model;
    }

    /**
     * @return Decoded skin url string, or null if skin value empty
     */
    public String getURL() {
        JsonObject textures = getTextures();

        if (textures.isEmpty()) {
            return ((JsonObject) textures.get("SKIN")).get("url").getValue().toString();
        }

        return null;
    }

    /**
     * @return UUID string of real skin owner
     */
    public String getOwnerUUID() {
        return (String) fromString(new String(Base64.getDecoder().decode(value))).get("profileId").getValue();
    }

    /**
     * @return Name of real skin owner
     */
    public String getOwnerName() {
        return (String) fromString(new String(Base64.getDecoder().decode(value))).get("profileName").getValue();
    }

    private SkinModel parseModel() {
        JsonObject textures = getTextures();

        if (!textures.isEmpty()) {
            JsonObject skinObj = (JsonObject) textures.get("SKIN");
            if (skinObj.has("metadata", ValueType.OBJECT)) {
                JsonObject metadata = (JsonObject) skinObj.get("metadata");
                if (metadata.has("model", ValueType.STRING)) {
                    return SkinModel.fromString((String) metadata.get("model").getValue());
                }
            }
        }

        return SkinModel.NORMAL;
    }

    private JsonObject getTextures() {
        return (JsonObject) fromString(new String(Base64.getDecoder().decode(value))).get("textures");
    }

    private JsonObject fromString(String value) {
        try {
            return (JsonObject) PARSER.fromString(value);
        } catch (IOException | ClassCastException exp) {
            return new JsonObject();
        }
    }

    /*
    * 
    */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Skin) {
            Skin skin = (Skin) obj;
            return skin.name.equals(name) && isSimilar(skin);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (signature.hashCode() << 16) + value.hashCode();
    }

    public boolean isSimilar(Skin skin) {
        return skin != null && skin.value.equals(value) && skin.signature.equals(signature);
    }

    /*
    * 
    */
    
    public void fromJson(JsonObject json) {
        name = (String) json.get("name").getValue();
        value = (String) json.get("value").getValue();
        signature = (String) json.get("signature").getValue();
        model = SkinModel.fromString((String) json.get("model").getValue());
    }

    public JsonObject asJson() {
        JsonObject json = new JsonObject();
        json.set("name", name);
        json.set("value", value);
        json.set("signature", signature);
        json.set("model", model.name());
        return json;
    }

    @Override
    public String toString() {
        try {
            return WRITER.toString(asJson());
        } catch (IOException e) {
            return "";
        }
    }

}