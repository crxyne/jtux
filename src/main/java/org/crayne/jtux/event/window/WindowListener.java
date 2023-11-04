package org.crayne.jtux.event.window;

import org.crayne.jtux.event.JTuxEventBus;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface WindowListener extends Consumer<WindowEvent> {

    @NotNull
    static WindowListener empty() {
        return windowEvent -> {};
    }

    default void update() {
        final int terminalWidth = JTuxLibrary.terminalWidth();
        final int terminalHeight = JTuxLibrary.terminalHeight();

        accept(new WindowEvent(terminalWidth, terminalHeight,
                -1, -1,
                true, false));
    }

    default void start() {
        JTuxEventBus.INSTANCE.newWindowListener(this);
    }

    default void close() {
        JTuxEventBus.INSTANCE.closeWindowListener(this);
    }

}
