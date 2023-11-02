package org.crayne.jtux.ux.panel.content.grid;

public class SystemOutCharacterGrid extends AnsiCharacterGrid {

    public SystemOutCharacterGrid() {
        super(System.out);
    }

    public void clear() {
        out().print("\33[H\33[J");
    }

}
