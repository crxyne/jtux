package org.crayne.jtux.util.math;

import org.jetbrains.annotations.NotNull;

public class Vec2i implements Vec2<Float> {

    private final float x, y;

    public Vec2i(final float x, final float y) {
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
    public static Vec2i zero() {
        return new Vec2i(0f, 0f);
    }

    @NotNull
    public String toString() {
        return "Vec2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
