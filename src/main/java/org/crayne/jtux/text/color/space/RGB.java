package org.crayne.jtux.text.color.space;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.util.vector.BoundedVec3;
import org.jetbrains.annotations.NotNull;

public class RGB extends BoundedVec3<Integer> {

    public RGB(final int r, final int g, final int b) {
        super(r, g, b);
    }

    public RGB(final float r, final float g, final float b) {
        this((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    @NotNull
    public Color color() {
        return Color.rgb(this);
    }

    @NotNull
    public static RGB ofHex(@NotNull final String hex) {
        final java.awt.Color color = java.awt.Color.decode(hex);
        return RGB.of(color.getRed(), color.getGreen(), color.getBlue());
    }

    @NotNull
    public String toHex() {
        return String.format("#%02x%02x%02x", r(), g(), b());
    }

    @NotNull
    public static RGB ofHSV(@NotNull final HSV hsv) {
        return hsv.toRGB();
    }

    @NotNull
    public static RGB ofHSV(final float h, final float s, final float v) {
        return ofHSV(HSV.of(h, s, v));
    }

    @NotNull
    public static RGB of(final int r, final int g, final int b) {
        return new RGB(r, g, b);
    }

    @NotNull
    public static RGB of(final float r, final float g, final float b) {
        return new RGB(r, g, b);
    }

    @NotNull
    public static RGB clampInt(final int r, final int g, final int b) {
        return new RGB(
                clampIntegerComponent(r, 0, 255),
                clampIntegerComponent(g, 0, 255),
                clampIntegerComponent(b, 0, 255)
        );
    }

    @NotNull
    public static RGB clampFloat(final float r, final float g, final float b) {
        return new RGB(
                clampFloatComponent(r, 0.0f, 1.0f),
                clampFloatComponent(g, 0.0f, 1.0f),
                clampFloatComponent(b, 0.0f, 1.0f)
        );
    }

    @NotNull
    public RGB add(@NotNull final Integer x, @NotNull final Integer y, @NotNull final Integer z) {
        return RGB.clampInt(x() + x, y() + y, z() + z);
    }

    public void checkBounds(@NotNull final Integer x, @NotNull final Integer y, @NotNull final Integer z) {
        checkIntegerBounds(x, y, z, 0, 255);
    }

    private static void checkIntegerBounds(final int r, final int g, final int b, final int min, final int max) {
        checkIntegerComponentBounds(r, "Red",   min, max);
        checkIntegerComponentBounds(g, "Green", min, max);
        checkIntegerComponentBounds(b, "Blue",  min, max);
    }

    @NotNull
    public RGB interpolate(final double step, @NotNull final RGB other) {
        return RGB.of(
                lerp(step, r(), other.r()),
                lerp(step, g(), other.g()),
                lerp(step, b(), other.b())
        );
    }

    private static int lerp(final double step, final int min, final int max) {
        return (int) (min + step * (max - min));
    }

    public int r() {
        return x();
    }

    public int g() {
        return y();
    }

    public int b() {
        return z();
    }

    @NotNull
    public HSV toHSV() {
        final float[] hsv = java.awt.Color.RGBtoHSB(r(), g(), b(), null);
        return new HSV(hsv[0] * 360f, hsv[1], hsv[2]);
    }

    @NotNull
    public String toString() {
        return "rgb(" + r() +
                 ", " + g() +
                 ", " + b() +
                 ')';
    }

}
