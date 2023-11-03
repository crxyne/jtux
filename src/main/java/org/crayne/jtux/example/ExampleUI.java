package org.crayne.jtux.example;

import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.KeyListener;
import org.crayne.jtux.event.window.WindowEvent;
import org.crayne.jtux.event.window.WindowListener;
import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.component.Text;
import org.crayne.jtux.text.util.TextUtil;
import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.border.BorderDefault;
import org.crayne.jtux.ui.border.Title;
import org.crayne.jtux.ui.content.grid.CharacterGrid;
import org.crayne.jtux.ui.content.layout.Component;
import org.crayne.jtux.ui.content.layout.Container;
import org.crayne.jtux.ui.content.layout.RenderOrder;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.crayne.jtux.util.math.vec.Vec2f;
import org.crayne.jtux.util.math.vec.Vec2i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ExampleUI {

    @NotNull
    private final Container parent;

    public ExampleUI() {
        parent = new Container(null, Vec2f.unary(), createBorder(BorderDefault.NORMAL,
                "i am the outer panel (this is the top title)",
                "i am the outer panel (this is the bottom title)"),
                RenderOrder.LEFT_TO_RIGHT);

        parent.fullGrid(JTuxLibrary.out);

        parent.addComponent(new Component(parent, Vec2f.unary(), createBorder(BorderDefault.NONE, "i am content panel 1")) {
            public void render() {
                final Optional<CharacterGrid> grid = contentGrid();
                grid.ifPresent(g -> g.writeLine(Vec2i.origin(), "hello, world!"));
            }
        });
    }

    public void start() {
        final KeyListener keyListener = this::exit;
        final WindowListener windowListener = this::handleWindowEvent;

        keyListener.start();
        windowListener.start();
    }

    private void exit(@NotNull final KeyEvent event) {
        JTuxLibrary.shutdown();
        System.exit(0);
    }

    private void handleWindowEvent(@NotNull final WindowEvent event) {
        final int width = event.terminalWidth(), height = event.terminalHeight();
        final Optional<CharacterGrid> characterGrid = parent.fullGrid();

        if (characterGrid.isEmpty()) return;
        if (event.windowSizeUpdated()) characterGrid.get().clear();

        parent.updateFullSize(width, height);
        parent.updateChildrenSizes();
        parent.makeReady();
    }

    @NotNull
    public static Text createGradientText(@NotNull final String title) {
        final Color color = Color.randomColor();
        return TextUtil.colorizeGradient(title, List.of(color, color.complementary()), true);
    }

    @NotNull
    public static Title createGradientTitle(@NotNull final String title) {
        return Title.of(createGradientText(title));
    }

    @NotNull
    public static Text createGradientText(@NotNull final Color color, @NotNull final String title) {
        return TextUtil.colorizeGradient(title, List.of(color, color.complementary()), true);
    }

    @NotNull
    public static Title createGradientTitle(@NotNull final Color color, @NotNull final String title) {
        return Title.of(createGradientText(color, title));
    }

    @NotNull
    public static AbstractBorder createBorder(@NotNull final BorderDefault type, @Nullable final String topTitle,
                                               @Nullable final String bottomTitle) {
        final Color randomColorTop = Color.randomColor();
        final Color randomColorBottom = Color.randomColor();

        return type.builder()
                .topTitle(topTitle == null ? null : createGradientTitle(randomColorTop, topTitle))
                .bottomTitle(bottomTitle == null ? null : createGradientTitle(randomColorBottom, bottomTitle))
                .build();
    }

    @NotNull
    public static AbstractBorder createBorder(@NotNull final BorderDefault type, @Nullable final String topTitle) {
        return createBorder(type, topTitle, null);
    }

}
