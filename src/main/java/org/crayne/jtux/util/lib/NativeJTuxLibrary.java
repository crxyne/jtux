package org.crayne.sketch.util.lib;

public class NativeSketchLibrary {

    private NativeSketchLibrary() {}

    public static native int terminalWidth();
    public static native int terminalHeight();

    protected static native int[] keyPress();

    public static native void init();

    public static native void shutdown();

}
