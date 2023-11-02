package org.crayne.jtux.util.lib;

import org.crayne.jtux.event.GlobalEventManager;
import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.KeyEventType;
import org.crayne.jtux.event.keyboard.Keycode;
import org.crayne.jtux.util.math.MathUtil;
import org.crayne.jtux.ui.content.grid.SystemOutCharacterGrid;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.PrintStream;

public class JTuxLibrary {

    private JTuxLibrary() {}

    public static void init(@NotNull final File nativeLibrary) {
        System.load(nativeLibrary.getAbsolutePath());
        NativeJTuxLibrary.init();
        GlobalEventManager.INSTANCE.start();
        JTuxLibrary.cursorVisible(false);

        Runtime.getRuntime().addShutdownHook(new Thread(JTuxLibrary::shutdown));
    }

    public static void shutdown() {
        NativeJTuxLibrary.shutdown();
        GlobalEventManager.INSTANCE.shutdown();
        cursorVisible(true);
    }

    @NotNull
    public static final SystemOutCharacterGrid out = new SystemOutCharacterGrid();

    public static int rawTerminalWidth() {
        return NativeJTuxLibrary.terminalWidth();
    }

    public static int terminalWidth() {
        return MathUtil.forceEven(rawTerminalWidth());
    }

    public static int fullTerminalWidth() {
        return MathUtil.forceOdd(rawTerminalWidth());
    }

    public static int rawTerminalHeight() {
        return NativeJTuxLibrary.terminalHeight();
    }

    public static int terminalHeight() {
        return MathUtil.forceEven(rawTerminalHeight());
    }

    public static int fullTerminalHeight() {
        return MathUtil.forceOdd(rawTerminalHeight());
    }

    public static void cursorVisible(final boolean b) {
        cursorVisible(System.out, b);
    }

    public static void cursorVisible(@NotNull final PrintStream out, final boolean b) {
        out.print("\33[?25" + (b ? "h" : "l"));
        out.flush();
    }

    @NotNull
    public static KeyEvent awaitKeyboardEvent() {
        final int[] keyPress = NativeJTuxLibrary.keyPress();
        if (keyPress == null) return KeyEvent.empty();
        final boolean keyDown = keyPress[2] != 0;

        final KeyEventType pressType = keyDown ? KeyEventType.PRESS : KeyEventType.RELEASE;

        return new KeyEvent(Keycode.of(keyPress[0]), keyPress[1], pressType);
    }

}
