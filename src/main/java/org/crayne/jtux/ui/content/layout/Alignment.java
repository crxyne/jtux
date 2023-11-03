package org.crayne.jtux.ui.content.layout;

public enum Alignment {

    LEFT(0.0f),
    CENTER(0.5f),
    RIGHT(1.0f);

    private final float floatValue;

    Alignment(final float floatValue) {
        this.floatValue = floatValue;
    }

    public float floatValue() {
        return floatValue;
    }

}
