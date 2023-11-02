package org.crayne.jtux.ui.title;

public enum TitleAlignment {

    LEFT(0.0f),
    CENTER(0.5f),
    RIGHT(1.0f);

    private final float floatValue;

    TitleAlignment(final float floatValue) {
        this.floatValue = floatValue;
    }

    public float floatValue() {
        return floatValue;
    }

}
