package org.crayne.jtux.event.window;

import org.jetbrains.annotations.NotNull;

public class WindowEvent {

    private final int rawTerminalWidth, rawTerminalHeight,
            previousTerminalWidth, previousTerminalHeight,
            terminalWidth, terminalHeight;

    private final boolean forcedUpdate, initialUpdate;

    public WindowEvent(final int rawTerminalWidth, final int rawTerminalHeight,
                       final int previousTerminalWidth, final int previousTerminalHeight,
                       final int terminalWidth, final int terminalHeight,
                       final boolean forcedUpdate, final boolean initialUpdate) {
        this.rawTerminalWidth = rawTerminalWidth;
        this.rawTerminalHeight = rawTerminalHeight;
        this.previousTerminalWidth = previousTerminalWidth;
        this.previousTerminalHeight = previousTerminalHeight;
        this.terminalWidth = terminalWidth;
        this.terminalHeight = terminalHeight;

        this.forcedUpdate = forcedUpdate;
        this.initialUpdate = initialUpdate;
    }

    public boolean windowSizeUpdated() {
        if (forcedUpdate && !initialUpdate) return false;
        return initialUpdate || rawTerminalHeight != previousTerminalHeight || rawTerminalWidth != previousTerminalWidth;
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

    public int rawTerminalHeight() {
        return rawTerminalHeight;
    }

    public int rawTerminalWidth() {
        return rawTerminalWidth;
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
                "rawTerminalWidth=" + rawTerminalWidth +
                ", rawTerminalHeight=" + rawTerminalHeight +
                ", previousTerminalWidth=" + previousTerminalWidth +
                ", previousTerminalHeight=" + previousTerminalHeight +
                ", terminalWidth=" + terminalWidth +
                ", terminalHeight=" + terminalHeight +
                ", forcedUpdate=" + forcedUpdate +
                ", initialUpdate=" + initialUpdate +
                '}';
    }

}
