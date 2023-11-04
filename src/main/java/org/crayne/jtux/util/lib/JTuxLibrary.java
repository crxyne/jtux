package org.crayne.jtux.util.lib;

import org.crayne.jtux.event.JTuxEventBus;
import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.KeyEventType;
import org.crayne.jtux.event.keyboard.Keycode;
import org.crayne.jtux.ui.grid.SystemOutCharacterGrid;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.PrintStream;

public class JTuxLibrary {

    private JTuxLibrary() {}

    public static class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(@NotNull final Thread t, @NotNull final Throwable e) {
            handle(e);
        }

        public void handle(@NotNull final Throwable t) {
            JTuxEventBus.INSTANCE.shutdown();
            out.clear();
            out.printStackTrace(t);
        }

        public static void registerExceptionHandler() {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        }

    }

    public static void init(@NotNull final File nativeLibrary) {
        System.load(nativeLibrary.getAbsolutePath());
        NativeJTuxLibrary.init();
        JTuxEventBus.INSTANCE.start();
        JTuxLibrary.cursorVisible(false);
        ExceptionHandler.registerExceptionHandler();

        Runtime.getRuntime().addShutdownHook(new Thread(JTuxLibrary::shutdown));
    }

    public static void shutdown() {
        NativeJTuxLibrary.shutdown();
        JTuxEventBus.INSTANCE.shutdown();
        cursorVisible(true);
    }

    @NotNull
    public static final SystemOutCharacterGrid out = new SystemOutCharacterGrid();

    public static int terminalWidth() {
        return NativeJTuxLibrary.terminalWidth() + 1;
    }

    public static int terminalHeight() {
        return NativeJTuxLibrary.terminalHeight() + 1;
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
