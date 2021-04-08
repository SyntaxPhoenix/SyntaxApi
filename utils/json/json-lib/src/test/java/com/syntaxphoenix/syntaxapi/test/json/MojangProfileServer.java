package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

public final class MojangProfileServer {

    private MojangProfileServer() {}

    private static final JsonParser PARSER = new JsonParser();

    public static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    public static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    public static UUID getUniqueId(String name) {
        return fromShortenId(getUniqueIdString(name));
    }

    public static UUID fromShortenId(String uid) {
        return UUID.fromString(uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-" + uid.substring(16, 20)
            + "-" + uid.substring(20, 32));
    }

    public static String shortUUID(UUID id) {
        return id.toString().replace("-", "");
    }

    public static String getUniqueIdString(String name) {
        try {
            URL url = new URL(String.format(UUID_URL, name));
            String jsonString = Streams.toString(url.openStream());
            if (!jsonString.isEmpty()) {
                JsonObject object = (JsonObject) fromString(jsonString);
                String uuid = object.get("id").getValue().toString();
                return uuid;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getName(UUID uniqueId) {
        return getNameShorten(shortUUID(uniqueId));
    }

    public static String getNameShorten(String uniqueId) {
        try {
            URL url = new URL(String.format(PROFILE_URL, uniqueId));
            String jsonString = Streams.toString(url.openStream());
            if (!jsonString.isEmpty()) {
                JsonObject object = (JsonObject) fromString(jsonString);
                String name = object.get("name").getValue().toString();
                return name;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Skin getSkin(UUID uniqueId) {
        return getSkinShorten(shortUUID(uniqueId));
    }

    public static Skin getSkin(String name, UUID uniqueId) {
        return getSkinShorten(name, shortUUID(uniqueId));
    }

    public static Skin getSkinShorten(String uniqueId) {
        String name = getNameShorten(uniqueId);
        if (name.isEmpty()) {
            return null;
        }
        return getSkinShorten(name, uniqueId);
    }

    public static Skin getSkinShorten(String name, String uniqueId) {
        try {
            URL url = new URL(String.format(PROFILE_URL, uniqueId));
            String jsonString = Streams.toString(url.openStream());
            if (jsonString.isEmpty()) {
                return null;
            }
            JsonObject object = (JsonObject) ((JsonArray) ((JsonObject) fromString(jsonString)).get("properties")).get(0);
            
            String value = object.get("value").getValue().toString();
            String signature = object.get("signature").getValue().toString();
            String skinUrl = getSkinUrl(value);
            if (skinUrl == null) {
                return null;
            }
            return new Skin(name, value, signature, false);
        } catch (IOException ignore) {
            return null;
        }
    }

    private static String getSkinUrl(String base64) {
        String decoded = new String(Base64.getDecoder().decode(base64));
        JsonObject object = (JsonObject) fromString(decoded);
        if (!object.has("textures")) {
            return null;
        }
        JsonObject textures = (JsonObject) object.get("textures");
        if (textures.isEmpty()) {
            return null;
        }
        return ((JsonObject) textures.get("SKIN")).get("url").getValue().toString();
    }

    private static JsonValue<?> fromString(String value) {
        try {
            return PARSER.fromString(value);
        } catch (IOException exp) {
            return JsonNull.get();
        }
    }

}
