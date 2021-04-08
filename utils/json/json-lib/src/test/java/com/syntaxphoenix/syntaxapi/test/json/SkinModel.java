package com.syntaxphoenix.syntaxapi.test.json;

public enum SkinModel {

    SLIM,
    NORMAL;

    public static SkinModel get(String name) {
        if (name.equalsIgnoreCase("SLIM")) {
            return SkinModel.SLIM;
        }
        return SkinModel.NORMAL;
    }

    @Override
    public String toString() {
        String name = name();
        if (name.equals("NORMAL")) {
            return "steve";
        }
        return name.toLowerCase();
    }

    public static SkinModel fromString(String name) {
        name = name.toLowerCase();
        if (name.equals("slim") || name.equals("alex")) {
            return SkinModel.SLIM;
        }
        return SkinModel.NORMAL;
    }

}