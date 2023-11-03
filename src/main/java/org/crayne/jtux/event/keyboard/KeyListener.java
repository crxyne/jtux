package org.crayne.jtux.event.keyboard;

import org.crayne.jtux.event.JTuxEventBus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface KeyListener extends Consumer<KeyEvent> {

    @NotNull
    static KeyListener empty() {
        return keyEvent -> {};
    }

    default void start() {
        JTuxEventBus.INSTANCE.newKeyListener(this);
    }

    default void close() {
        JTuxEventBus.INSTANCE.closeKeyListener(this);
    }

}
