package org.crayne.jtux.example;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.component.Component;
import org.crayne.jtux.ui.panel.content.ContentRenderer;
import org.crayne.jtux.util.math.vec.Vec2i;
import org.jetbrains.annotations.NotNull;

public class InformationRenderer extends ContentRenderer {

    @NotNull
    private final Color color = Color.randomColor();

    private int offsetY = -1;

    private int timeUpdates = 0;

    @NotNull
    private final ExampleUI ui;

    public InformationRenderer(@NotNull final ExampleUI ui) {
        this.ui = ui;
    }

    public void updateTime() {
        if (offsetY < 0) return;
        timeUpdates++;
        final String text = "time that passed since program start (ms): " + (System.currentTimeMillis() - ui.startedAt());
        final String text2 = timeUpdates + " total time updates (not entire screen redraws, only this single text)";
        characterGrid().ifPresent(grid -> {
            final int offsetY2 = grid.writeLineWrap(Vec2i.of(0, 1 + offsetY), ExampleUI.createGradientText(color, text));
            grid.writeLineWrap(Vec2i.of(0, 2 + offsetY + offsetY2), Component.text(text2));
        });
    }

    public void run() {
        characterGrid().ifPresent(grid -> offsetY = grid.writeLineWrap(Vec2i.origin(), Component.text(ui.totalUpdates() + " full rendering updates (full terminal ui redraws)")));
        updateTime();
    }

}
