package org.crayne.jtux.event.keyboard;

import org.crayne.jtux.event.GlobalEventManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface KeyListener extends Consumer<KeyEvent> {

    @NotNull
    static KeyListener empty() {
        return keyEvent -> {};
    }

    default void start() {
        GlobalEventManager.INSTANCE.newKeyListener(this);
    }

    default void close() {
        GlobalEventManager.INSTANCE.closeKeyListener(this);
    }

}
