package org.crayne.jtux.keyboard;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface KeyListener extends Consumer<KeyEvent> {

    @NotNull
    static KeyListener empty() {
        return keyEvent -> {};
    }

    default void start() {
        GlobalKeyListener.INSTANCE.newKeylistener(this);
    }

    default void close() {
        GlobalKeyListener.INSTANCE.closeKeylistener(this);
    }

}
