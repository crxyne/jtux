package org.crayne.jtux.event.keyboard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class KeyEvent {

    @NotNull
    private final Keycode keycode;

    @NotNull
    private final List<Keycode> heldDown;

    @Nullable
    private final Integer character;

    @NotNull
    private final KeyEventType pressType;

    public KeyEvent(@NotNull final Keycode keycode, @NotNull final Collection<Keycode> heldDown,
                    @Nullable final Integer character, @NotNull final KeyEventType pressType) {
        this.keycode = keycode;
        this.heldDown = new ArrayList<>(heldDown);
        this.character = character;
        this.pressType = pressType;
    }

    public KeyEvent(@NotNull final Keycode keycode, @NotNull final Keycode... heldDown) {
        this.keycode = keycode;
        this.heldDown = new ArrayList<>(List.of(heldDown));
        this.character = null;
        this.pressType = KeyEventType.PRESS;
    }

    public KeyEvent(@NotNull final Keycode keycode, @Nullable final Integer character,
                    @NotNull final KeyEventType pressType) {
        this.keycode = keycode;
        this.heldDown = new ArrayList<>();
        this.character = character;
        this.pressType = pressType;
    }

    @NotNull
    public static KeyEvent empty() {
        return new KeyEvent(Keycode.UNKNOWN, Collections.emptyList(), null, KeyEventType.UNKNOWN);
    }

    public boolean isEmpty() {
        return keycode == Keycode.UNKNOWN && heldDown.isEmpty()
                && character().isEmpty() && pressType == KeyEventType.UNKNOWN;
    }

    @NotNull
    public static KeyEvent keybind(@NotNull final Keycode keycode, @NotNull final Keycode... heldDown) {
        return new KeyEvent(keycode, heldDown);
    }

    public boolean keyDown() {
        return pressType == KeyEventType.PRESS;
    }

    //shortcuts for easy usage
    public boolean alt() {
        return heldDown.contains(Keycode.ALT);
    }

    public boolean ctrl() {
        return heldDown.contains(Keycode.CTRL);
    }

    public boolean shift() {
        return heldDown.contains(Keycode.SHIFT);
    }

    public boolean altNotCtrl() {
        return alt() && !ctrl();
    }

    public boolean ctrlNotAlt() {
        return ctrl() && !alt();
    }

    public boolean ctrlAlt() {
        return ctrl() && alt();
    }

    public boolean ctrlOrAlt() {
        return ctrl() || alt();
    }

    @NotNull
    public KeyEventType pressType() {
        return pressType;
    }

    @NotNull
    public Optional<Integer> character() {
        final Integer c = character == null || character > 60000 || character <= 0 ? null : character == '\n' ? '\r' : character;
        if (c != null && !keycode.expected(c)) return Optional.of(keycode.expect());
        return Optional.ofNullable(c);
    }

    @NotNull
    public Keycode keycode() {
        return keycode;
    }

    @NotNull
    public List<Keycode> heldDown() {
        return heldDown;
    }

    public boolean sameKeycode(@NotNull final KeyEvent other) {
        return keycode == other.keycode;
    }

    public boolean sameKeyEvent(@NotNull final KeyEvent other) {
        return sameKeycode(other) && (new HashSet<>(heldDown).containsAll(other.heldDown) && (heldDown.isEmpty() || !other.heldDown.isEmpty()));
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final KeyEvent keyEvent = (KeyEvent) o;
        return sameKeyEvent(keyEvent);
    }

    public int hashCode() {
        return keycode.hashCode();
    }

    @NotNull
    public String toString() {
        return "KeyEvent{" +
                "keycode=" + keycode +
                ", heldDown=" + heldDown +
                ", character=" + character() + " | " + character +
                ", pressType=" + pressType +
                '}';
    }

}
