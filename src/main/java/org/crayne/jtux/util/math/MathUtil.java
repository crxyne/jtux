package org.crayne.jtux.util.math;

public class MathUtil {

    private MathUtil() {}

    public static int forceEven(final int i) {
        return Math.max(0, i + (i % 2));
    }

    public static int forceOdd(final int i) {
        return Math.max(0, i - (i % 2)) + 1;
    }

}
