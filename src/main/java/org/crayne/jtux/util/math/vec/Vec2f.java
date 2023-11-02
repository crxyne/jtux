package org.crayne.jtux.util.math.vec;

import org.jetbrains.annotations.NotNull;

public class Vec2f implements Vec2<Float> {

    private final float x, y;

    public Vec2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public static Vec2f of(final float x, final float y) {
        return new Vec2f(x, y);
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
        return new Vec2f(0.0f, 0.0f);
    }

    @NotNull
    public static Vec2f unary() {
        return new Vec2f(1.0f, 1.0f);
    }

    @NotNull
    public String toString() {
        return "Vec2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
