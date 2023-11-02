package org.crayne.jtux.example;

import org.crayne.jtux.event.GlobalEventManager;
import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.KeyListener;
import org.crayne.jtux.event.window.WindowEvent;
import org.crayne.jtux.event.window.WindowListener;
import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.component.Component;
import org.crayne.jtux.text.component.TextUtil;
import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.border.BorderDefault;
import org.crayne.jtux.ui.panel.ContainerPanel;
import org.crayne.jtux.ui.panel.PanelOrder;
import org.crayne.jtux.ui.panel.content.grid.CharacterGrid;
import org.crayne.jtux.ui.title.Title;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.crayne.jtux.util.math.vec.Vec2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExampleUI {

    private int totalUpdates;

    private final long startedAt;

    @NotNull
    private final ContainerPanel parent;

    @NotNull
    private final InformationRenderer informationRenderer;

    public ExampleUI() {
        this.totalUpdates = 0;
        this.startedAt = System.currentTimeMillis();

        parent = ContainerPanel.of(createBorder(BorderDefault.NORMAL,
                "i am the outer panel (this is the top title)",
                "i am the outer panel (this is the bottom title)"),
                Vec2f.unary(), PanelOrder.LEFT_TO_RIGHT);

        parent.fullCharacterGrid(JTuxLibrary.out);

        parent.createPanel(createBorder(BorderDefault.NONE, "i am content panel 1"), new SimpleTextRenderer(
                createGradientText(Color.randomColor(),
                        "i am a wrapping text. if i don't have enough space on one line," +
                                " i will use the space on the next line instead."
                ), true)
        );
        parent.createPanel(createBorder(BorderDefault.NONE, "i am content panel 2"), new SimpleTextRenderer(
                createGradientText(Color.randomColor(),
                        "i am not a wrapping text. if i don't have enough space on one " +
                                "line, my text will be cut off."
                ), false)
        );

        final ContainerPanel innerContainer = parent.createContainer(createBorder(BorderDefault.DOUBLE,
                "i am a container"), PanelOrder.TOP_TO_BOTTOM);

        informationRenderer = new InformationRenderer(this);
        innerContainer.createPanel(createBorder(BorderDefault.NORMAL, "i am an inner panel, panel 3"),
                Vec2f.of(1.0f, 0.75f), informationRenderer);

        innerContainer.createPanel(createBorder(BorderDefault.NORMAL, "i am an inner panel, panel 4"),
                Vec2f.of(1.0f, 0.25f));
    }

    public void start() {
        final KeyListener keyListener = this::exit;
        final WindowListener windowListener = this::handleWindowEvent;

        keyListener.start();
        windowListener.start();

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> GlobalEventManager.scheduleSyncTaskCancellable(informationRenderer), 100, 100, TimeUnit.MILLISECONDS);
    }

    private void exit(@NotNull final KeyEvent event) {
        JTuxLibrary.shutdown();
        System.exit(0);
    }

    private void handleWindowEvent(@NotNull final WindowEvent event) {
        totalUpdates++;
        final int width = event.terminalWidth(), height = event.terminalHeight();
        final Optional<CharacterGrid> characterGrid = parent.fullCharacterGrid();

        if (characterGrid.isEmpty()) return;
        if (event.windowSizeUpdated()) characterGrid.get().clear();

        parent.updateSize(width, height);
        parent.updateChildrenSizes();
        parent.ready(true);
    }

    @NotNull
    public static Component createGradientText(@NotNull final String title) {
        final Color color = Color.randomColor();
        return TextUtil.colorizeGradient(title, List.of(color, color.complementary()), true);
    }

    @NotNull
    public static Title createGradientTitle(@NotNull final String title) {
        return Title.of(createGradientText(title));
    }

    @NotNull
    public static Component createGradientText(@NotNull final Color color, @NotNull final String title) {
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

    public int totalUpdates() {
        return totalUpdates;
    }

    public long startedAt() {
        return startedAt;
    }

}
