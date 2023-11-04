package org.crayne.jtux.event.window;

import org.jetbrains.annotations.NotNull;

public class WindowEvent {

    private final int terminalWidth, terminalHeight,
            previousTerminalWidth, previousTerminalHeight;

    private final boolean forcedUpdate, initialUpdate;

    public WindowEvent(final int terminalWidth, final int terminalHeight,
                       final int previousTerminalWidth, final int previousTerminalHeight,
                       final boolean forcedUpdate, final boolean initialUpdate) {
        this.terminalWidth = terminalWidth;
        this.terminalHeight = terminalHeight;
        this.previousTerminalWidth = previousTerminalWidth;
        this.previousTerminalHeight = previousTerminalHeight;

        this.forcedUpdate = forcedUpdate;
        this.initialUpdate = initialUpdate;
    }

    public boolean windowSizeUpdated() {
        if (forcedUpdate && !initialUpdate) return false;
        return initialUpdate || terminalHeight != previousTerminalHeight || terminalWidth != previousTerminalWidth;
    }

    public boolean forcedUpdate() {
        return forcedUpdate;
    }

    public boolean initialUpdate() {
        return initialUpdate;
    }

    public int terminalHeight() {
        return terminalHeight;
    }

    public int terminalWidth() {
        return terminalWidth;
    }

    public int previousTerminalHeight() {
        return previousTerminalHeight;
    }

    public int previousTerminalWidth() {
        return previousTerminalWidth;
    }

    @NotNull
    public String toString() {
        return "WindowEvent{" +
                "terminalWidth=" + terminalWidth +
                ", terminalHeight=" + terminalHeight +
                ", previousTerminalWidth=" + previousTerminalWidth +
                ", previousTerminalHeight=" + previousTerminalHeight +
                ", forcedUpdate=" + forcedUpdate +
                ", initialUpdate=" + initialUpdate +
                '}';
    }

}
