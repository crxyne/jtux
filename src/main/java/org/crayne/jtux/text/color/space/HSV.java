package org.crayne.jtux.text.color.space;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.util.vector.BoundedVec3;
import org.jetbrains.annotations.NotNull;

public class HSV extends BoundedVec3<Float> {

    public HSV(final float h, final float s, final float v) {
        super(h, s, v);
    }

    @NotNull
    public Color color() {
        return Color.hsv(this);
    }

    @NotNull
    public static HSV ofRGB(@NotNull final RGB rgb) {
        return rgb.toHSV();
    }

    @NotNull
    public static HSV ofRGB(final int r, final int g, final int b) {
        return ofRGB(RGB.of(r, g, b));
    }

    @NotNull
    public static HSV ofRGB(final float r, final float g, final float b) {
        return ofRGB(RGB.of(r, g, b));
    }

    @NotNull
    public static HSV of(final float h, final float s, final float v) {
        return new HSV(h, s, v);
    }

    @NotNull
    public static HSV clamp(final float h, final float s, final float v) {
        float hueWrapped = h % 360.0f;
        if (hueWrapped < 0) hueWrapped = 360 + hueWrapped;
        return new HSV(
                clampFloatComponent(hueWrapped, 0.0f, 360.0f),
                clampFloatComponent(s, 0.0f, 1.0f),
                clampFloatComponent(v, 0.0f, 1.0f)
        );
    }

    public float h() {
        return x();
    }

    public float s() {
        return y();
    }

    public float v() {
        return z();
    }

    public void checkBounds(@NotNull final Float x, @NotNull final Float y, @NotNull final Float z) {
        checkFloatComponentBounds(x, "Hue",        0.0f, 360.0f);
        checkFloatComponentBounds(y, "Saturation", 0.0f, 1.0f);
        checkFloatComponentBounds(z, "Value",      0.0f, 1.0f);
    }

    @NotNull
    public HSV add(@NotNull final Float x, @NotNull final Float y, @NotNull final Float z) {
        return HSV.clamp(x() + x, y() + y, z() + z);
    }

    @NotNull
    public RGB toRGB() {
        final int rgb = java.awt.Color.HSBtoRGB(h() / 360.0f, s(), v());
        return new RGB(rgb >> 16 & 255, rgb >> 8 & 255, rgb & 255);
    }

    @NotNull
    public String toString() {
        return "hsv(" + h() +
                "°, " + s() * 100.0f +
                "%, " + v() * 100.0f +
                "%)";
    }

}
