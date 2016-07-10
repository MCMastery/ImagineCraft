package net.imaginecraft.plugin.utils;

public class MathUtils {
    private MathUtils() {}

    public static int clamp(int value, int min, int max) {
        return (value < min) ? min : (value > max) ? max : value;
    }
}
