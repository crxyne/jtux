package org.crayne.jtux.util.vector;

import org.jetbrains.annotations.NotNull;

public interface Vec2<T> {

    @NotNull
    T x();

    @NotNull
    T y();

    @NotNull
    Vec2<T> swap();

}
