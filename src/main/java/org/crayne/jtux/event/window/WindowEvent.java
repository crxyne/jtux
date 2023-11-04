package org.crayne.jtux.event.window;

public record WindowEvent(int terminalWidth, int terminalHeight, int previousTerminalWidth, int previousTerminalHeight,
                          boolean forcedUpdate, boolean initialUpdate) {

    public boolean windowSizeUpdated() {
        if (forcedUpdate && !initialUpdate) return false;
        return initialUpdate || terminalHeight != previousTerminalHeight || terminalWidth != previousTerminalWidth;
    }

}
