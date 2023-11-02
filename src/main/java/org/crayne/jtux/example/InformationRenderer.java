package org.crayne.jtux.example;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.component.Component;
import org.crayne.jtux.ui.panel.content.ContentRenderer;
import org.crayne.jtux.util.math.vec.Vec2i;
import org.jetbrains.annotations.NotNull;

public class InformationPanelRenderer extends ContentRenderer {

    @NotNull
    private final Color color = Color.randomColor();

    private int offsetY = -1;

    public void updateTime() {
        if (offsetY < 0) return;
        final String text = "time that passed since program start (ms): " + (System.currentTimeMillis() - startedAt);
        characterGrid().ifPresent(grid -> grid.writeLineWrap(Vec2i.of(0, 1 + offsetY), createGradientText(color, text)));
    }

    public void run() {
        characterGrid().ifPresent(grid -> offsetY = grid.writeLineWrap(Vec2i.origin(), Component.text(totalUpdates + " total rendering updates")));
        updateTime();
    }

}
