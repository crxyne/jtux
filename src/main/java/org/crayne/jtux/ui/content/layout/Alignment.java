package org.crayne.jtux.ui.content.layout;

import org.jetbrains.annotations.NotNull;

public enum Alignment {

    PRIMARY(0.0f),
    LEFT(0.0f),
    TOP(0.0f),
    CENTER(0.5f),
    SECONDARY(1.0f),
    RIGHT(1.0f),
    BOTTOM(1.0f);

    private final float floatValue;

    Alignment(final float floatValue) {
        this.floatValue = floatValue;
    }

    public float floatValue() {
        return floatValue;
    }

    public int align(@NotNull final String string, final int width) {
        return align(string, width, floatValue);
    }

    public int align(final int objectWidth, final int width) {
        return align(objectWidth, width, floatValue);
    }

    public static int align(@NotNull final String string, final int usableWidth, final float alignment) {
        return align(string.length(), usableWidth, alignment);
    }

    public static int align(final int objectWidth, final int usableWidth, final float alignment) {
        if (alignment < 0.0f || alignment > 1.0f) throw new IllegalArgumentException("Alignment must be between 0 and 1 (inclusive)");
        if (usableWidth < 0) throw new IllegalArgumentException("Width must be >= 0");

        return (int) Math.min(usableWidth - objectWidth, Math.max(0, usableWidth * alignment - objectWidth * alignment));
    }

}
