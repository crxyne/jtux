package org.crayne.jtux.example;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.component.Text;
import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.border.BorderDefault;
import org.crayne.jtux.ui.border.BorderTitle;
import org.crayne.jtux.ui.component.Component;
import org.crayne.jtux.ui.grid.CharacterGrid;
import org.crayne.jtux.ui.interactive.Clickable;
import org.crayne.jtux.util.vector.Vec2i;
import org.jetbrains.annotations.NotNull;

public class ExamplePanel extends Component implements Clickable {

    private boolean toggle;

    @NotNull
    private static AbstractBorder createBorder(@NotNull final BorderDefault borderDefault) {
        return borderDefault.builder()
                .topTitle(BorderTitle.of("this is an example clickable panel"))
                .build();
    }

    public ExamplePanel() {
        super(createBorder(BorderDefault.NORMAL));
    }

    public void render(@NotNull final CharacterGrid contentGrid) {
        final Text text = Text.text(
                toggle ? Color.rgb(255, 0, 0) : Color.rgb(0, 255, 0),
                toggle ? "OFF" : "ON"
        );
        contentGrid.writeLineWrap(Vec2i.origin(), text.prepend(Text.text("current toggle: ")));
    }

    public void whenClicked() {
        toggle = !toggle;
        updateContent();
    }

    public void whenFocused() {
        border(createBorder(BorderDefault.BOLD));
        updateBorder();
    }

    public void whenExited() {
        border(createBorder(BorderDefault.NORMAL));
        updateBorder();
    }

}
