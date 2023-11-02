package org.crayne.jtux.event;

import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.KeyListener;
import org.crayne.jtux.event.keyboard.Keycode;
import org.crayne.jtux.event.window.WindowEvent;
import org.crayne.jtux.event.window.WindowListener;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.crayne.jtux.util.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GlobalEventManager extends Thread {

    @NotNull
    private final Set<KeyListener> subscribedKeyListeners;

    @NotNull
    private final Set<WindowListener> subscribedWindowListeners;

    @NotNull
    private final List<Keycode> heldDown;

    private int previousTerminalWidth, previousTerminalHeight;

    private boolean active;
    private volatile boolean taskLock;

    @NotNull
    private final List<Runnable> tasksSynchronized;

    @NotNull
    public static final GlobalEventManager INSTANCE = new GlobalEventManager();

    private GlobalEventManager() {
        active = false;
        taskLock = false;
        previousTerminalWidth = -1;
        previousTerminalHeight = -1;
        subscribedKeyListeners = Collections.synchronizedSet(new HashSet<>());
        subscribedWindowListeners = Collections.synchronizedSet(new HashSet<>());
        heldDown = new ArrayList<>();
        tasksSynchronized = new ArrayList<>();
    }

    public void run() {
        active = true;

        while (active) {
            handleKeyEvent(JTuxLibrary.awaitKeyboardEvent());
            handleWindowEvent();

            synchronized (tasksSynchronized) {
                taskLock = true;
                tasksSynchronized.forEach(Runnable::run);
                tasksSynchronized.clear();
                taskLock = false;
            }
        }
    }

    @NotNull
    private static WindowEvent createWindowEvent(final int rawTerminalWidth, final int rawTerminalHeight,
                                                 final int previousTerminalWidth, final int previousTerminalHeight,
                                                 final boolean forcedUpdate, final boolean initialUpdate) {
        final int terminalWidth = MathUtil.forceEven(rawTerminalWidth);
        final int terminalHeight = MathUtil.forceEven(rawTerminalHeight);

        return new WindowEvent(
                rawTerminalWidth, rawTerminalHeight,
                previousTerminalWidth, previousTerminalHeight,
                terminalWidth, terminalHeight,
                forcedUpdate, initialUpdate
        );
    }

    private void initialWindowUpdate(@NotNull final WindowListener listener) {
        listener.accept(createWindowEvent(JTuxLibrary.rawTerminalWidth(), JTuxLibrary.rawTerminalHeight(),
                -1, -1,
                true, true));
    }

    private void handleWindowEvent() {
        final int rawTerminalWidth = JTuxLibrary.rawTerminalWidth();
        final int rawTerminalHeight = JTuxLibrary.rawTerminalHeight();

        final boolean widthChanged = rawTerminalWidth != previousTerminalWidth;
        final boolean heightChanged = rawTerminalHeight != previousTerminalHeight;

        if (widthChanged || heightChanged) {
            final WindowEvent event = createWindowEvent(rawTerminalWidth, rawTerminalHeight,
                    previousTerminalWidth, previousTerminalHeight,
                    false, false);

            synchronized (subscribedWindowListeners) {
                subscribedWindowListeners.forEach(w -> w.accept(event));
            }

            if (rawTerminalHeight != -1) previousTerminalHeight = rawTerminalHeight;
            if (rawTerminalWidth != -1) previousTerminalWidth = rawTerminalWidth;
        }
    }

    private void handleKeyEvent(@NotNull final KeyEvent event) {
        if (event.isEmpty()) return;
        if (!event.keyDown()) heldDown.remove(event.keycode());

        new KeyEvent(event.keycode(),
                heldDown,
                event.character().orElse(null),
                event.pressType());

        synchronized (subscribedKeyListeners) {
            subscribedKeyListeners.forEach(k -> k.accept(event));
        }

        if (event.keyDown()) heldDown.add(event.keycode());
    }

    public void newKeyListener(@NotNull final KeyListener listener) {
        synchronized (subscribedKeyListeners) {
            subscribedKeyListeners.add(listener);
        }
    }

    public void closeKeyListener(@NotNull final KeyListener listener) {
        synchronized (subscribedKeyListeners) {
            subscribedKeyListeners.remove(listener);
        }
    }

    public void newWindowListener(@NotNull final WindowListener listener) {
        synchronized (subscribedWindowListeners) {
            subscribedWindowListeners.add(listener);
            initialWindowUpdate(listener);
        }
    }

    public void closeWindowListener(@NotNull final WindowListener listener) {
        synchronized (subscribedWindowListeners) {
            subscribedWindowListeners.remove(listener);
        }
    }

    public void pushSynchronizedTask(@NotNull final Runnable runnable) throws ConcurrentModificationException {
        if (taskLock) throw new ConcurrentModificationException();
        synchronized (tasksSynchronized) {
            tasksSynchronized.add(runnable);
        }
    }

    public void forcePushSynchronizedTask(@NotNull final Runnable runnable) {
        synchronized (tasksSynchronized) {
            tasksSynchronized.add(runnable);
        }
    }

    public static void scheduleSyncTask(@NotNull final Runnable runnable) throws ConcurrentModificationException {
        GlobalEventManager.INSTANCE.pushSynchronizedTask(runnable);
    }

    public static void scheduleSyncTaskForced(@NotNull final Runnable runnable) {
        GlobalEventManager.INSTANCE.forcePushSynchronizedTask(runnable);
    }

    public static void scheduleSyncTaskCancellable(@NotNull final Runnable runnable) {
        try {
            scheduleSyncTask(runnable);
        } catch (final ConcurrentModificationException ignored) {

        }
    }

    public synchronized void shutdown() {
        active = false;
    }

}
