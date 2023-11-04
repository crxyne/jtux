package org.crayne.jtux.util.vector;

import org.jetbrains.annotations.NotNull;

public abstract class BoundedVec3<T> {

    @NotNull
    private final T x, y, z;

    public BoundedVec3(@NotNull final T x, @NotNull final T y, @NotNull final T z) {
        checkBounds(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @NotNull
    public abstract BoundedVec3<T> add(@NotNull final T x, @NotNull final T y, @NotNull final T z);

    public static void checkFloatComponentBounds(final float v, @NotNull final String comp, final float min, final float max) {
        if (v < min || v > max) throw new IllegalArgumentException(comp + " component out of floating-point " +
                "range " + min + " - " + max + " (" + v + ")");
    }

    public static void checkIntegerComponentBounds(final int v, @NotNull final String comp, final int min, final int max) {
        if (v < min || v > max) throw new IllegalArgumentException(comp + " component out of byte " +
                "range " + min + " - " + max + " (" + v + ")");
    }

    public static int clampIntegerComponent(final int v, final int min, final int max) {
        return Math.min(Math.max(v, min), max);
    }

    public static float clampFloatComponent(final float v, final float min, final float max) {
        return Math.min(Math.max(v, min), max);
    }

    public abstract void checkBounds(@NotNull final T x, @NotNull final T y, @NotNull final T z);

    @NotNull
    public T x() {
        return x;
    }

    @NotNull
    public T y() {
        return y;
    }

    @NotNull
    public T z() {
        return z;
    }

}
