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

    @NotNull
    private final Component component;

    public ExampleUI() {
        parent = new Container(Vec2f.unary(), createBorder(BorderDefault.NORMAL,
                "i am the outer panel (this is the top title)",
                "i am the outer panel (this is the bottom title)"),
                RenderOrder.LEFT_TO_RIGHT);

        parent.fullGrid(JTuxLibrary.out);

        component = parent.addComponent(new Component(Vec2f.of(1.0f, 0.5f), 0.5f, createBorder(BorderDefault.NORMAL, "i am content panel 1")) {
            public void render() {
                final Optional<CharacterGrid> grid = contentGrid();
                grid.ifPresent(g -> g.writeLine(Vec2i.origin(), "hello, world!"));
            }
        });

        parent.addComponent(new Component(Vec2f.of(1.0f, 0.5f), createBorder(BorderDefault.NORMAL, "i am content panel 2")) {
            public void render() {
                final Optional<CharacterGrid> grid = contentGrid();
                grid.ifPresent(g -> g.writeLine(Vec2i.origin(), "hello, world!"));
            }
        });
    }

    public void start() {
        final KeyListener keyListener = this::handleKeyEvent;
        final WindowListener windowListener = this::handleWindowEvent;

        keyListener.start();
        windowListener.start();
    }

    private void handleKeyEvent(@NotNull final KeyEvent event) {
        if (!event.keyDown()) return;
        switch (event.keycode()) {
            case V -> component.hidden(!component.hidden());
            case C -> {
                JTuxLibrary.shutdown();
                System.exit(0);
            }
        }
    }

    private void handleWindowEvent(@NotNull final WindowEvent event) {
        final int width = event.rawTerminalWidth() + 1, height = event.rawTerminalHeight() + 1;
        final Optional<CharacterGrid> characterGrid = parent.fullGrid();

        if (characterGrid.isEmpty()) return;

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
