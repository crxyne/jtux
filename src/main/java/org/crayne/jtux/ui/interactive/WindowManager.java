package org.crayne.jtux.ui.interactive;

import org.crayne.jtux.event.keyboard.KeyEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class WindowManager {

    @NotNull
    private final List<Focusable> windows;

    @Nullable
    private Focusable currentlyFocused;

    private int currentlyFocusedIndex = -1;

    public WindowManager(@NotNull final List<Focusable> windows) {
        this.windows = List.copyOf(windows);
    }

    public int currentlyFocusedIndex() {
        return currentlyFocusedIndex;
    }

    public void selectNext() {
        select(currentlyFocusedIndex + 1);
    }

    public void select(final int index) {
        if (windows.isEmpty()) return;

        currentlyFocusedIndex = index % windows.size();
        select(windows.get(currentlyFocusedIndex));
    }

    @NotNull
    public List<Focusable> windows() {
        return windows;
    }

    @NotNull
    public Optional<Focusable> currentlyFocused() {
        return Optional.ofNullable(currentlyFocused);
    }

    public void handleInteraction(@NotNull final Focusable interactedWith, @NotNull final KeyEvent keyEvent, final boolean clickEvent) {
        if (!windows.contains(interactedWith))
            throw new IllegalArgumentException("Focusable is not contained in windows list of WindowManager");

        if (clickEvent && interactedWith instanceof final Clickable clickable)
            clickable.whenClicked();

        if (interactedWith instanceof final Interactable interactable)
            interactable.whenInteracted(keyEvent);
    }

    public void select(@Nullable final Focusable currentlyFocused) {
        if (!windows.contains(currentlyFocused))
            throw new IllegalArgumentException("Focusable is not contained in windows list of WindowManager");

        final Focusable previouslyFocused = this.currentlyFocused;
        this.currentlyFocused = currentlyFocused;

        if (previouslyFocused != null) previouslyFocused.whenExited();
        if (currentlyFocused != null) currentlyFocused.whenFocused();
    }

}
