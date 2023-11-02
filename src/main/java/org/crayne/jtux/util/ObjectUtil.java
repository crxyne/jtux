package org.crayne.jtux.util;

import org.jetbrains.annotations.Nullable;

public class ObjectUtil {

    private ObjectUtil() {}

    public static boolean nullityDifferent(@Nullable final Object o1, @Nullable final Object o2) {
        return (o1 == null && o2 != null) || (o2 == null && o1 != null);
    }

}
