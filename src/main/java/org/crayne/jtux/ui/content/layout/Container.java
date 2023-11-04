package org.crayne.jtux.ui.content.layout;

import org.crayne.jtux.ui.border.AbstractBorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("UnusedReturnValue")
public class Container extends Component {

    @NotNull
    private final List<Component> components;

    @NotNull
    private final Set<Component> hiddenComponents;

    @NotNull
    private final RenderOrder order;

    public Container(final float sizeProportion, final float sizePercentage, @Nullable final AbstractBorder border,
                     @NotNull final RenderOrder order) {
        super(sizeProportion, sizePercentage, border);
        this.order = order;
        this.components = new ArrayList<>();
        this.hiddenComponents = new HashSet<>();
    }

    public Container(final float sizeProportion, final float sizePercentage, @Nullable final AbstractBorder border,
                     @NotNull final RenderOrder order, @NotNull final Collection<Component> components) {
        super(sizeProportion, sizePercentage, border);
        this.order = order;
        this.components = new ArrayList<>(components);
        this.hiddenComponents = new HashSet<>();
    }

    public Container(@Nullable final AbstractBorder border, @NotNull final RenderOrder order) {
        super(border);
        this.order = order;
        this.components = new ArrayList<>();
        this.hiddenComponents = new HashSet<>();
    }

    public Container(@NotNull final RenderOrder order) {
        super();
        this.order = order;
        this.components = new ArrayList<>();
        this.hiddenComponents = new HashSet<>();
    }

    public void makeReady() {
        super.setReady();
        components.forEach(Component::makeReady);
        update();
    }

    @NotNull
    public List<Component> components() {
        return Collections.unmodifiableList(components);
    }

    @NotNull
    public Set<Component> hiddenComponents() {
        return Collections.unmodifiableSet(hiddenComponents);
    }

    @NotNull
    public Component addComponentSilent(@NotNull final Component component) {
        return addComponentSilent(components.size(), component);
    }

    @NotNull
    public Component addComponentSilent(final int index, @NotNull final Component component) {
        component.parent(this);
        components.add(index, component);
        return component;
    }

    @NotNull
    public Component addComponent(@NotNull final Component component) {
        return addComponent(components.size(), component);
    }

    @NotNull
    public Component addComponent(final int index, @NotNull final Component component) {
        addComponentSilent(index, component);
        updateChildrenSizes();
        update();
        return component;
    }

    @NotNull
    public Component removeComponent(@NotNull final Component component) {
        removeComponentSilent(component);
        updateChildrenSizes();
        update();
        return component;
    }

    @NotNull
    public Component removeComponentSilent(@NotNull final Component component) {
        component.parent(null);
        components.remove(component);
        return component;
    }

    public void hideComponent(@NotNull final Component component) {
        hiddenComponents.add(component);
        updateChildrenSizes();
        update();
    }

    public void showComponent(@NotNull final Component component) {
        hiddenComponents.remove(component);
        updateChildrenSizes();
        update();
    }

    public void componentHidden(@NotNull final Component component, final boolean hidden) {
        if (hidden) hideComponent(component);
        else showComponent(component);
    }

    public boolean componentHidden(@NotNull final Component component) {
        return hiddenComponents.contains(component);
    }

    public void updateChildrenSizes() {
        if (components.isEmpty() || contentGrid().isEmpty()) return;

        final int usableWidth = contentGrid().get().width();
        final int usableHeight = contentGrid().get().height();

        if (usableWidth < 0 || usableHeight < 0) return;
        updateChildrenSizes(order == RenderOrder.TOP_TO_BOTTOM, usableWidth, usableHeight);
    }

    private void updateChildrenSizes(final boolean useY, final int usableWidth, final int usableHeight) {
        int totalUsedSpace = 0;
        final int usableSpace      = useY ? usableHeight : usableWidth;
        final int otherUsableSpace = useY ? usableWidth  : usableHeight;

        final double proportionTotal = components.stream().mapToDouble(Component::sizeProportion).sum();

        for (int i = 0; i < components.size(); i++) {
            final Component component = components.get(i);
            if (hiddenComponents.contains(component)) continue;

            final boolean last = i + 1 == components.size();

            final double spaceRatio = component.sizeProportion() / proportionTotal;
            if (spaceRatio == 0.0f) continue;

            final double otherSpaceModifier = Math.min(1.0f, component.sizePercentage());
            if (otherSpaceModifier == 0.0f) continue;

            final float alignment = component.alignment();

            final int usedSpace = last ? usableSpace - totalUsedSpace : (int) (spaceRatio * usableSpace);
            final int previousOffset = totalUsedSpace;
            totalUsedSpace += usedSpace;

            final int alignedSpace = Alignment.align((int) (otherUsableSpace * otherSpaceModifier), otherUsableSpace, alignment);

            contentGrid().ifPresent(grid -> {
                if (useY) {
                    component.updateFullGridSize(grid, alignedSpace, previousOffset, (int) (usableWidth * otherSpaceModifier), usedSpace);
                    return;
                }
                component.updateFullGridSize(grid, previousOffset, alignedSpace, usedSpace, (int) (usableHeight * otherSpaceModifier));
            });
        }
        components.forEach(Component::updateSize);
    }

    public void updateBorder() {
        super.updateBorder();
        components.forEach(Component::updateBorder);
    }

    public void updateContent() {
        super.updateContent();
        components.forEach(Component::updateContent);
    }

    public void updateSize() {
        super.updateSize();
        updateChildrenSizes();
    }

    public void render() {

    }

}
