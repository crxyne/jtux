package org.crayne.jtux.text.color;

import org.crayne.jtux.text.color.space.HSV;
import org.crayne.jtux.text.color.space.RGB;
import org.crayne.jtux.text.component.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Function;

public class Color {

    @NotNull
    private final RGB rgb;

    @NotNull
    private final HSV hsv;

    public Color(@NotNull final RGB rgb) {
        this.rgb = rgb;
        this.hsv = rgb.toHSV();
    }

    public Color(@NotNull final HSV hsv) {
        this.rgb = hsv.toRGB();
        this.hsv = hsv;
    }

    @NotNull
    private static final Random random = new Random();

    @NotNull
    public static Color randomColor() {
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @NotNull
    public Color negate() {
        return Color.rgb(255 - rgb.r(), 255 - rgb.g(), 255 - rgb.b());
    }

    @NotNull
    public Color complementary() {
        return analogous1(180.0f);
    }

    @NotNull
    public Color[] complementaryPalette() {
        return offsetPalette(180.0f, 2);
    }

    @NotNull
    public Color[] triadicPalette() {
        return offsetPalette(120.0f, 3);
    }

    @NotNull
    public Color[] tetradicPalette() {
        return offsetPalette(90.0f, 4);
    }

    @NotNull
    public Color[] complementaryPalette2() {
        return offsetPalette2(180.0f, 2);
    }

    @NotNull
    public Color[] triadicPalette2() {
        return offsetPalette2(120.0f, 3);
    }

    @NotNull
    public Color[] tetradicPalette2() {
        return offsetPalette2(90.0f, 4);
    }

    @NotNull
    public Color[] complementaryPalette3(final float fs, final float fv, final float os, final float ov) {
        return offsetPalette3(180.0f, 2, fs, fv, os, ov);
    }

    @NotNull
    public Color[] triadicPalette3(final float fs, final float fv, final float os, final float ov) {
        return offsetPalette3(120.0f, 3, fs, fv, os, ov);
    }

    @NotNull
    public Color[] tetradicPalette3(final float fs, final float fv, final float os, final float ov) {
        return offsetPalette3(90.0f, 4, fs, fv, os, ov);
    }

    @NotNull
    public Color analogous1(final float step) {
        return Color.hsv(HSV.clamp(hsv.h() + step, hsv.s(), hsv.v()));
    }

    @NotNull
    public Color analogous2(final float step, final float fs, final float fv) {
        return Color.hsv(HSV.clamp(hsv.h() + step,
                hsv.s() + (Math.abs(step) / 360.0f * fs),
                hsv.v() + (Math.abs(step) / 360.0f * fv)));
    }

    @NotNull
    public Color analogous4(final float step, final float fs, final float fv, final float os, final float ov) {
        return Color.hsv(HSV.clamp(hsv.h() + step,
                hsv.s() + (Math.abs(step) / 360.0f * fs) + os,
                hsv.v() + (Math.abs(step) / 360.0f * fv) + ov));
    }

    @NotNull
    public Color analogous3(final float step) {
        return Color.hsv(HSV.clamp(hsv.h() + step, 1.0f, 1.0f));
    }

    @NotNull
    public Color monochromatic(final float step) {
        return Color.hsv(HSV.clamp(hsv.h(), hsv.s() - step, hsv.v()));
    }

    @NotNull
    public Color monochromaticBrightness(final float step) {
        return Color.hsv(HSV.clamp(hsv.h(), hsv.s(), hsv.v() - step));
    }

    @NotNull
    public Color[] offsetPalette(final float step, final int range) {
        return createPalette(step, range, (float) (range / 2) * step, this::analogous1);
    }

    @NotNull
    public Color[] offsetPalette2(final float step, final int range) {
        return createPalette(step, range, (float) (range / 2) * step, this::analogous3);
    }

    @NotNull
    public Color[] offsetPalette3(final float step, final int range, final float fs, final float fv, final float os, final float ov) {
        return createPalette(step, range, (float) (range / 2) * step, currentStep -> analogous4(currentStep, fs, fv, os, ov));
    }

    @NotNull
    public Color[] analogousPalette1(final float step, final int range) {
        return createPalette(step, 2 * range + 1, range * step, this::analogous1);
    }

    @NotNull
    public Color[] analogousPalette2(final float step, final int range, final float fs, final float fv) {
        return createPalette(step, 2 * range + 1, range * step, currentStep -> analogous2(currentStep, fs, fv));
    }

    @NotNull
    public Color[] analogousPalette3(final float step, final int range) {
        return createPalette(step, 2 * range + 1, range * step, this::analogous3);
    }

    @NotNull
    public Color[] analogousPalette4(final float step, final int range, final float fs, final float fv, final float os, final float ov) {
        return createPalette(step, 2 * range + 1, range * step, currentStep -> analogous4(currentStep, fs, fv, os, ov));
    }

    @NotNull
    public Color[] monochromaticPalette(final float step, final int range) {
        return createPalette(step, range + 1, 0, this::monochromatic);
    }

    @NotNull
    public Color[] monochromaticBrightnessPalette(final float step, final int range) {
        return createPalette(step, range + 1, 0.0f, this::monochromaticBrightness);
    }

    @NotNull
    private Color[] createPalette(final float step, final int range, final float initialStep, @NotNull final Function<Float, Color> variationFunction) {
        if (range < 0) throw new IllegalArgumentException("Range must be positive");

        final Color[] result = new Color[range];

        float currentStep = initialStep;
        for (int i = 0; i < result.length; i++) {
            result[i] = variationFunction.apply(currentStep);
            currentStep -= step;
        }
        return result;
    }

    @NotNull
    public Component stylize(@NotNull final String text, final boolean foreground) {
        return Component.text(this, text, foreground);
    }

    @NotNull
    public Component stylize(final char character, final boolean foreground) {
        return stylize("" + character, foreground);
    }

    @NotNull
    public Color interpolate(final double step, @NotNull final Color other) {
        return Color.rgb(rgb.interpolate(step, other.rgb));
    }

    @NotNull
    public static Color hex(@NotNull final String hex) {
        return new Color(RGB.ofHex(hex));
    }

    @NotNull
    public String hex() {
        return rgb.toHex();
    }

    @NotNull
    public static Color rgb(@NotNull final RGB rgb) {
        return new Color(rgb);
    }

    @NotNull
    public static Color rgb(final int r, final int g, final int b) {
        return new Color(RGB.of(r, g, b));
    }

    @NotNull
    public static Color rgb(final float r, final float g, final float b) {
        return new Color(RGB.of(r, g, b));
    }

    @NotNull
    public static Color hsv(@NotNull final HSV hsv) {
        return new Color(hsv);
    }

    @NotNull
    public static Color hsv(final float h, final float s, final float v) {
        return new Color(HSV.of(h, s, v));
    }

    @NotNull
    public HSV hsv() {
        return hsv;
    }

    @NotNull
    public RGB rgb() {
        return rgb;
    }

    public int r() {
        return rgb.r();
    }

    public int g() {
        return rgb.g();
    }

    public int b() {
        return rgb.b();
    }

    public double h() {
        return hsv.h();
    }

    public double s() {
        return hsv.s();
    }

    public double v() {
        return hsv.v();
    }

    public String toString() {
        return "Color{" +
                "" + rgb +
                ", " + hsv +
                '}';
    }
}
