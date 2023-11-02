package org.crayne.jtux.util.math;

import org.jetbrains.annotations.NotNull;

public class Vec2f implements Vec2<Float> {

    private final float x, y;

    public Vec2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public Float x() {
        return x;
    }

    @NotNull
    public Float y() {
        return y;
    }

    @NotNull
    public static Vec2f zero() {
        return new Vec2f(0f, 0f);
    }

    @NotNull
    public String toString() {
        return "Vec2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
