package org.crayne.jtux.ui.interactive;

import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.KeyListener;
import org.crayne.jtux.event.keyboard.Keycode;
import org.crayne.jtux.event.window.WindowEvent;
import org.crayne.jtux.event.window.WindowListener;
import org.crayne.jtux.ui.component.Container;
import org.crayne.jtux.ui.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class Application {

    @NotNull
    private final Container rootContainer;

    @NotNull
    private final Set<KeyEvent> selectorKeyEvents, clickKeyEvents;

    @NotNull
    private final WindowManager windowManager;

    @NotNull
    private final Consumer<KeyEvent> keyEventHandler;

    @NotNull
    private final KeyListener keyListener = this::handleKeyEvent;

    @NotNull
    private final WindowListener windowListener = this::handleWindowEvent;

    public Application(@NotNull final Container rootContainer, @NotNull final WindowManager windowManager,
                       @NotNull final Collection<KeyEvent> selectorKeyEvents, 
                       @NotNull final Collection<KeyEvent> clickKeyEvents,
                       @NotNull final Consumer<KeyEvent> keyEventHandler) {

        this.rootContainer = rootContainer;
        this.windowManager = windowManager;
        this.selectorKeyEvents = new HashSet<>(selectorKeyEvents);
        this.clickKeyEvents = new HashSet<>(clickKeyEvents);
        this.keyEventHandler = keyEventHandler;
    }

    @NotNull
    public static Application staticApplication(@NotNull final Container rootContainer) {
        return new Application(rootContainer, new WindowManager(List.of()), List.of(), List.of(), k -> {});
    }

    @NotNull
    public static Application interactiveApplication(@NotNull final Container rootContainer,
                                                     @NotNull final WindowManager windowManager,
                                                     @NotNull final Collection<KeyEvent> selectorKeyEvents,
                                                     @NotNull final Collection<KeyEvent> clickKeyEvents,
                                                     @NotNull final Consumer<KeyEvent> keyEventHandler) {

        return new Application(rootContainer, windowManager, selectorKeyEvents, clickKeyEvents, keyEventHandler);
    }

    @NotNull
    private static final List<KeyEvent> DEFAULT_SELECTOR = List.of(KeyEvent.keybind(Keycode.TAB));

    @NotNull
    private static final List<KeyEvent> DEFAULT_CLICK = List.of(
            KeyEvent.keybind(Keycode.ENTER),
            KeyEvent.keybind(Keycode.SPACE)
    );

    @NotNull
    public static Application interactiveApplication(@NotNull final Container rootContainer,
                                                     @NotNull final WindowManager windowManager) {
        return new Application(rootContainer, windowManager, DEFAULT_SELECTOR, DEFAULT_CLICK, k -> {});
    }

    @NotNull
    public static Application interactiveApplication(@NotNull final Container rootContainer,
                                                     @NotNull final WindowManager windowManager,
                                                     @NotNull final Consumer<KeyEvent> keyEventHandler) {
        return new Application(rootContainer, windowManager, DEFAULT_SELECTOR, DEFAULT_CLICK, keyEventHandler);
    }

    @NotNull
    public Set<KeyEvent> selectorKeyEvents() {
        return selectorKeyEvents;
    }

    @NotNull
    public Set<KeyEvent> clickKeyEvents() {
        return clickKeyEvents;
    }

    @NotNull
    public Container rootContainer() {
        return rootContainer;
    }

    public void start() {
        keyListener.start();
        windowListener.start();
    }

    public void close() {
        keyListener.close();
        windowListener.close();
    }

    public boolean selectorKeyEvent(@NotNull final KeyEvent event) {
        return selectorKeyEvents.stream().anyMatch(e -> e.sameKeyEvent(event));
    }

    public boolean clickKeyEvent(@NotNull final KeyEvent event) {
        return clickKeyEvents.stream().anyMatch(e -> e.sameKeyEvent(event));
    }

    private void handleKeyEvent(@NotNull final KeyEvent event) {
        if (!event.keyDown()) {
            keyEventHandler.accept(event);
            return;
        }
        if (selectorKeyEvent(event)) {
            windowManager.selectNext();
            return;
        }
        windowManager.currentlyFocused().ifPresent(focusable ->
                windowManager.handleInteraction(focusable, event, clickKeyEvent(event))
        );
        keyEventHandler.accept(event);
    }

    private void handleWindowEvent(@NotNull final WindowEvent event) {
        final int width = event.terminalWidth(), height = event.terminalHeight();
        final Optional<CharacterGrid> characterGrid = rootContainer.fullGrid();

        if (characterGrid.isEmpty()) return;

        rootContainer.updateFullSize(width, height);
        rootContainer.updateChildrenSizes();
        rootContainer.makeReady();
    }


}
