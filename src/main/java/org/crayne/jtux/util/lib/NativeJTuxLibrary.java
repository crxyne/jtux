package org.crayne.jtux.util.lib;

public class NativeJTuxLibrary {

    private NativeJTuxLibrary() {}

    public static native int terminalWidth();
    public static native int terminalHeight();

    protected static native int[] keyPress();

    public static native void init();

    public static native void shutdown();

}
