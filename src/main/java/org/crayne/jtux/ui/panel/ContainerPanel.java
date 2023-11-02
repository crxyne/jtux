package org.crayne.jtux.ui.panel;

import org.crayne.jtux.util.math.vec.Vec2f;
import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.panel.content.Content;
import org.crayne.jtux.ui.panel.content.ContentRenderer;
import org.crayne.jtux.ui.panel.content.type.EmptyContentRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class ContainerPanel extends AbstractPanel {

    @NotNull
    private final List<AbstractPanel> children;

    @NotNull
    private final PanelOrder order;

    public ContainerPanel(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                          @NotNull final Collection<AbstractPanel> children,
                          @NotNull final PanelOrder order) {
        super(border, formatSize, null, new Content(null, new EmptyContentRenderer()));
        this.children = new ArrayList<>(children);
        this.order = order;
    }

    @NotNull
    public static ContainerPanel of(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                                    @NotNull final Collection<AbstractPanel> children,
                                    @NotNull final PanelOrder order) {
        return new ContainerPanel(border, formatSize, children, order);
    }

    @NotNull
    public static ContainerPanel of(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize, @NotNull final PanelOrder order) {
        return new ContainerPanel(border, formatSize, Collections.emptyList(), order);
    }

    @NotNull
    public static ContainerPanel of(@Nullable final AbstractBorder border, @NotNull final PanelOrder order) {
        return new ContainerPanel(border, Vec2f.unary(), Collections.emptyList(), order);
    }

    @NotNull
    public static ContainerPanel of(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                                    @NotNull final Collection<AbstractPanel> children) {
        return new ContainerPanel(border, formatSize, children, PanelOrder.TOP_TO_BOTTOM);
    }

    @NotNull
    public static ContainerPanel of(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize) {
        return new ContainerPanel(border, formatSize, Collections.emptyList(), PanelOrder.TOP_TO_BOTTOM);
    }

    @NotNull
    public static ContainerPanel of(@Nullable final AbstractBorder border) {
        return new ContainerPanel(border, Vec2f.unary(), Collections.emptyList(), PanelOrder.TOP_TO_BOTTOM);
    }

    @NotNull
    public static ContainerPanel of(@NotNull final Vec2f formatSize,
                                    @NotNull final Collection<AbstractPanel> children,
                                    @NotNull final PanelOrder order) {
        return of(null, formatSize, children, order);
    }

    @NotNull
    public static ContainerPanel of(@NotNull final Vec2f formatSize, @NotNull final PanelOrder order) {
        return of(null, formatSize, order);
    }

    @NotNull
    public static ContainerPanel of(@NotNull final Vec2f formatSize,
                                    @NotNull final Collection<AbstractPanel> children) {
        return of(null, formatSize, children);
    }

    @NotNull
    public static ContainerPanel of(@NotNull final Vec2f formatSize) {
        return of(null, formatSize);
    }

    @NotNull
    public static ContainerPanel of() {
        return of((AbstractBorder) null);
    }

    @NotNull
    public List<AbstractPanel> children() {
        return Collections.unmodifiableList(children);
    }

    public void addPanel(@NotNull final AbstractPanel panel) {
        children.add(panel);
        if (ready()) updateChildrenSizes();
    }

    public void ready(final boolean ready) {
        super.ready(ready);
        children.forEach(c -> c.ready(ready));
        if (ready) updatePanel();
    }

    @NotNull
    public ContentPanel createPanel(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                                    @NotNull final ContentRenderer renderer) {
        final ContentPanel panel = new ContentPanel(border, formatSize, this, renderer);
        addPanel(panel);
        return panel;
    }

    @NotNull
    public ContentPanel createPanel(@Nullable final AbstractBorder border, @NotNull final ContentRenderer renderer) {
        return createPanel(border, Vec2f.unary(), renderer);
    }

    @NotNull
    public ContentPanel createPanel(@NotNull final Vec2f formatSize, @NotNull final ContentRenderer renderer) {
        return createPanel(null, formatSize, renderer);
    }

    @NotNull
    public ContentPanel createPanel(@NotNull final Vec2f formatSize) {
        return createPanel(null, formatSize, new EmptyContentRenderer());
    }

    @NotNull
    public ContentPanel createPanel(@Nullable final AbstractBorder border) {
        return createPanel(border, new EmptyContentRenderer());
    }

    @NotNull
    public ContentPanel createPanel(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize) {
        return createPanel(border, formatSize, new EmptyContentRenderer());
    }

    @NotNull
    public ContentPanel createPanel() {
        return createPanel((AbstractBorder) null);
    }

    @NotNull
    public ContainerPanel createContainer(@Nullable final AbstractBorder border,
                                          @NotNull final Vec2f formatSize,
                                          @NotNull final Collection<AbstractPanel> children,
                                          @NotNull final PanelOrder order) {
        final ContainerPanel containerPanel = of(border, formatSize, children, order);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@Nullable final AbstractBorder border,
                                          @NotNull final Vec2f formatSize,
                                          @NotNull final PanelOrder order) {
        final ContainerPanel containerPanel = of(border, formatSize, order);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@Nullable final AbstractBorder border,
                                          @NotNull final PanelOrder order) {
        final ContainerPanel containerPanel = of(border, order);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@Nullable final AbstractBorder border,
                                          @NotNull final Vec2f formatSize,
                                          @NotNull final Collection<AbstractPanel> children) {
        final ContainerPanel containerPanel = of(border, formatSize, children);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@Nullable final AbstractBorder border,
                                          @NotNull final Vec2f formatSize) {
        final ContainerPanel containerPanel = of(border, formatSize);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@Nullable final AbstractBorder border) {
        final ContainerPanel containerPanel = of(border);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@NotNull final Vec2f formatSize,
                                          @NotNull final Collection<AbstractPanel> children,
                                          @NotNull final PanelOrder order) {
        final ContainerPanel containerPanel = of(formatSize, children, order);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@NotNull final Vec2f formatSize,
                                          @NotNull final PanelOrder order) {
        final ContainerPanel containerPanel = of(formatSize, order);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@NotNull final Vec2f formatSize,
                                          @NotNull final Collection<AbstractPanel> children) {
        final ContainerPanel containerPanel = of(formatSize, children);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer(@NotNull final Vec2f formatSize) {
        final ContainerPanel containerPanel = of(formatSize);
        addPanel(containerPanel);
        return containerPanel;
    }

    @NotNull
    public ContainerPanel createContainer() {
        final ContainerPanel containerPanel = of();
        addPanel(containerPanel);
        return containerPanel;
    }

    public void updateChildrenSizes() {
        if (children.isEmpty() || fullCharacterGrid().isEmpty() || content().characterGrid().isEmpty()) return;

        final int fullWidth = fullCharacterGrid().get().width();
        final int fullHeight = fullCharacterGrid().get().height();
        final int usableWidth = content().characterGrid().get().width();
        final int usableHeight = content().characterGrid().get().height();

        if (fullWidth < 0 || fullHeight < 0 || usableWidth < 0 || usableHeight < 0) return;

        final List<Vec2f> formatSizes = children.stream().map(AbstractPanel::formatSize).toList();
        updateChildrenSizes(formatSizes, order == PanelOrder.TOP_TO_BOTTOM, usableWidth, usableHeight);
    }

    private void updateChildrenSizes(@NotNull final List<Vec2f> formatSizes,
                                     final boolean useY,
                                     final int usableWidth, final int usableHeight) {
        int totalUsedSpace = 0;
        final int usableSpace = useY ? usableHeight : usableWidth;
        final ToDoubleFunction<Vec2f> componentFunction = useY ? Vec2f::y : Vec2f::x;
        final double maxSpaceRatio = formatSizes.stream().mapToDouble(componentFunction).sum();

        for (int i = 0; i < children.size(); i++) {
            final AbstractPanel panel = children.get(i);
            final boolean last = i + 1 == children.size();

            final double spaceRatio = componentFunction.applyAsDouble(panel.formatSize()) / maxSpaceRatio;
            final int usedSpace = last ? usableSpace - totalUsedSpace : (int) (spaceRatio * usableSpace);

            final int previousOffset = totalUsedSpace;
            totalUsedSpace += usedSpace;

            content().characterGrid().ifPresent(grid -> {
                if (useY) {
                    panel.updateFullGridSize(grid, 0, previousOffset, usableWidth, usedSpace);
                    return;
                }
                panel.updateFullGridSize(grid, previousOffset, 0, usedSpace, usableHeight);
            });
        }
        children.forEach(AbstractPanel::updateSize);
    }

    public void updateBorder() {
        super.updateBorder();
        children.forEach(AbstractPanel::updateBorder);
    }

    public void updateContent() {
        super.updateContent();
        children.forEach(AbstractPanel::updateContent);
    }

    public void updateSize() {
        super.updateSize();
        updateChildrenSizes();
    }

}