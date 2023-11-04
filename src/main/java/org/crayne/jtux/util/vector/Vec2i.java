package org.crayne.jtux.util.vector;

import org.jetbrains.annotations.NotNull;

public class Vec2i implements Vec2<Integer> {

    private final int x, y;

    public Vec2i(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public static Vec2i of(final int x, final int y) {
        return new Vec2i(x, y);
    }
    @NotNull
    public Integer x() {
        return x;
    }

    @NotNull
    public Integer y() {
        return y;
    }

    @NotNull
    public static Vec2i origin() {
        return new Vec2i(0, 0);
    }

    @NotNull
    public Vec2i add(final int x, final int y) {
        return Vec2i.of(this.x + x, this.y + y);
    }

    @NotNull
    public Vec2i swap() {
        //noinspection SuspiciousNameCombination
        return new Vec2i(y, x);
    }

    @NotNull
    public String toString() {
        return "Vec2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
