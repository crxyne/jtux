package org.crayne.jtux.event.window;

import org.crayne.jtux.event.GlobalEventManager;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.crayne.jtux.util.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface WindowListener extends Consumer<WindowEvent> {

    @NotNull
    static WindowListener empty() {
        return windowEvent -> {};
    }

    default void update() {
        final int rawTerminalWidth = JTuxLibrary.rawTerminalWidth();
        final int rawTerminalHeight = JTuxLibrary.rawTerminalHeight();

        final int terminalWidth = MathUtil.forceEven(rawTerminalWidth);
        final int terminalHeight = MathUtil.forceEven(rawTerminalHeight);

        accept(new WindowEvent(rawTerminalWidth, rawTerminalHeight,
                -1, -1,
                terminalWidth, terminalHeight, true, false));
    }

    default void start() {
        GlobalEventManager.INSTANCE.newWindowListener(this);
    }

    default void close() {
        GlobalEventManager.INSTANCE.closeWindowListener(this);
    }

}
