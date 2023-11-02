package org.crayne.jtux.text.color.ansi;

import org.crayne.jtux.text.color.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TextColor {

    @Nullable
    private final Color fg;

    @Nullable
    private final Color bg;

    private final boolean @NotNull [] flags;

    protected TextColor(@Nullable final Color fg, @Nullable final Color bg, final boolean @NotNull [] flags) {
        if (flags.length != 9) throw new IllegalArgumentException("Expected exactly 9 color flags (reset, bold, dim," +
                " italic, underline, blinking, inverse, hidden, strikethrough)");
        this.fg = fg;
        this.bg = bg;
        this.flags = flags;
    }

    public TextColor(@Nullable final Color fg, @Nullable final Color bg) {
        this.fg = fg;
        this.bg = bg;
        this.flags = new boolean[9];
    }

    public TextColor() {
        this.fg = null;
        this.bg = null;
        this.flags = new boolean[9];
    }

    @NotNull
    public static TextColor foreground(@NotNull final Color fg) {
        return new TextColor(fg, null);
    }

    @NotNull
    public static TextColor background(@NotNull final Color bg) {
        return new TextColor(null, bg);
    }

    @NotNull
    public static TextColor color(@NotNull final Color fg, @NotNull final Color bg) {
        return new TextColor(fg, bg);
    }

    @NotNull
    public static final String ANSI_BEGIN = "\33[";

    @NotNull
    private static final String RGB_BEGIN = "8;2;";

    @NotNull
    public static final String RESET_ANSI_STRING = ANSI_BEGIN + "0m";

    @NotNull
    public static final String RGB_FG_BEGIN = ANSI_BEGIN + "3" + RGB_BEGIN;

    @NotNull
    public static final String RGB_BG_BEGIN = ANSI_BEGIN + "4" + RGB_BEGIN;

    @NotNull
    public static final TextColor RESET = new TextColorBuilder().reset(true).build();

    @NotNull
    public static String rgbAnsiString(final int r, final int g, final int b) {
        return r + ";" + g + ";" + b + "m";
    }

    @NotNull
    public static String foregroundAnsiString(final int r, final int g, final int b) {
        return RGB_FG_BEGIN + rgbAnsiString(r, g, b);
    }

    @NotNull
    public static String foregroundAnsiString(@Nullable final Color color) {
        return color == null ? "" : foregroundAnsiString(color.r(), color.g(), color.b());
    }

    @NotNull
    public static String backgroundAnsiString(final int r, final int g, final int b) {
        return RGB_BG_BEGIN + rgbAnsiString(r, g, b);
    }

    @NotNull
    public static String backgroundAnsiString(@Nullable final Color color) {
        return color == null ? "" : backgroundAnsiString(color.r(), color.g(), color.b());
    }

    @NotNull
    private static String flagAnsiString(final boolean flag, final int index) {
        return flag ? ANSI_BEGIN + index + "m" : "";
    }

    public boolean flag(final int index) {
        return flags[index];
    }

    @NotNull
    private static String flagsToAnsiStrings(final boolean[] flags) {
        return
                flagAnsiString(flags[0], 0)
                        + flagAnsiString(flags[1], 1)
                        + flagAnsiString(flags[2], 2)
                        + flagAnsiString(flags[3], 3)
                        + flagAnsiString(flags[4], 4)
                        + flagAnsiString(flags[5], 5)
                        + flagAnsiString(flags[6], 7)
                        + flagAnsiString(flags[7], 8)
                        + flagAnsiString(flags[8], 9);
    }

    @NotNull
    public Optional<Color> foreground() {
        return Optional.ofNullable(fg);
    }

    @NotNull
    public Optional<Color> background() {
        return Optional.ofNullable(bg);
    }

    public boolean reset() {
        return flags[0];
    }

    public boolean bold() {
        return flags[1];
    }

    public boolean dim() {
        return flags[2];
    }

    public boolean italic() {
        return flags[3];
    }

    public boolean underline() {
        return flags[4];
    }

    public boolean blinking() {
        return flags[5];
    }

    public boolean inverted() {
        return flags[6];
    }

    public boolean hidden() {
        return flags[7];
    }

    public boolean strikethrough() {
        return flags[8];
    }

    @NotNull
    public String toString() {
        return foregroundAnsiString(fg) + backgroundAnsiString(bg) + flagsToAnsiStrings(flags);
    }

}
