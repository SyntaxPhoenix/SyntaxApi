package com.syntaxphoenix.syntaxapi.logging.color;

import java.awt.Color;

public class ColorTools {

    public static final String ANSI_FORMAT = "\033[r;g;bm";

    public static Color hex2rgb(String hexHash) {
        return hex2rgba(hexHash, 255);
    }

    public static Color hex2rgba(String hexHash, int alpha) {
        if (hexHash.startsWith("#")) {
            hexHash = hexHash.replace("#", "");
        }
        if (hexHash.length() != 6) {
            return Color.BLACK;
        }
        String[] hex = hexHash.split("(?<=\\G..)");
        return hex2rgba(hex[0], hex[1], hex[2], alpha);
    }

    public static Color hex2rgba(String red, String green, String blue) {
        return hex2rgba(red, green, blue, 255);
    }

    public static Color hex2rgba(String red, String green, String blue, int alpha) {
        return rgba(Integer.parseInt(red, 16), Integer.parseInt(green, 16), Integer.parseInt(blue, 16), alpha);
    }

    public static Color rgb(int red, int green, int blue) {
        return rgba(red, green, blue, 255);
    }

    public static Color rgba(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, alpha);
    }

    public static String toAnsiColor(Color color) {
        return ANSI_FORMAT.replace("r", color.getRed() + "").replace("g", color.getGreen() + "").replace("b", color.getBlue() + "");
    }

}
