package org.crayne.jtux.ui.content.layout;

import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.util.math.vec.Vec2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class Container extends Component {

    @NotNull
    private final List<Component> components;

    @NotNull
    private final RenderOrder order;

    public Container(@Nullable final Container parent, @NotNull final Vec2f format,
                     @Nullable final AbstractBorder border, @NotNull final RenderOrder order) {
        super(parent, format, border);
        this.order = order;
        this.components = new ArrayList<>();
    }

    public Container(@Nullable final Container parent, @NotNull final Vec2f format,
                     @Nullable final AbstractBorder border, @NotNull final RenderOrder order,
                     @NotNull final Collection<Component> components) {
        super(parent, format, border);
        this.order = order;
        this.components = new ArrayList<>(components);
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

    public void addComponent(@NotNull final Component component) {
        components.add(component);
        updateChildrenSizes();
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
        final int usableSpace = useY ? usableHeight : usableWidth;
        final ToDoubleFunction<Vec2f> componentFunction = useY ? Vec2f::y : Vec2f::x;
        final ToDoubleFunction<Vec2f> otherComponentFunction = useY ? Vec2f::x : Vec2f::y;

        for (int i = 0; i < components.size(); i++) {
            final Component component = components.get(i);
            final boolean last = i + 1 == components.size();

            final double spaceRatio = componentFunction.applyAsDouble(component.format());
            final double otherSpaceModifier = otherComponentFunction.applyAsDouble(component.format());

            final int usedSpace = last ? usableSpace - totalUsedSpace : (int) (spaceRatio * usableSpace);
            final int previousOffset = totalUsedSpace;
            totalUsedSpace += usedSpace;

            contentGrid().ifPresent(grid -> {
                if (useY) {
                    component.updateFullGridSize(grid, 0, previousOffset, (int) (usableWidth * otherSpaceModifier), usedSpace);
                    return;
                }
                component.updateFullGridSize(grid, previousOffset, 0, usedSpace, (int) (usableHeight * otherSpaceModifier));
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
