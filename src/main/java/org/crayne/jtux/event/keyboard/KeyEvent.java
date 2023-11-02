package org.crayne.jtux.keyboard;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KeyEvent {

    private final Keycode keycode;
    private final List<Keycode> heldDown;
    private final Integer character;
    private final KeyEventType pressType;

    public KeyEvent(@NotNull final Keycode keycode, @NotNull final Collection<Keycode> heldDown, final Integer character, @NotNull final KeyEventType pressType) {
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

    public KeyEvent(@NotNull final Keycode keycode, final Integer character, @NotNull final KeyEventType pressType) {
        this.keycode = keycode;
        this.heldDown = new ArrayList<>();
        this.character = character;
        this.pressType = pressType;
    }

    public static KeyEvent empty() {
        return new KeyEvent(Keycode.UNKNOWN, Collections.emptyList(), null, KeyEventType.UNKNOWN);
    }

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

    public KeyEventType pressType() {
        return pressType;
    }

    public Optional<Integer> character() {
        final Integer c = character == null || character > 60000 || character <= 0 ? null : character == '\n' ? '\r' : character;
        if (c != null && !keycode.expected(c)) return Optional.of(keycode.expect());
        return Optional.ofNullable(c);
    }

    public Keycode keycode() {
        return keycode;
    }

    public List<Keycode> heldDown() {
        return heldDown;
    }

    public boolean same(@NotNull final KeyEvent other) {
        return keycode == other.keycode;
    }

    public boolean sameStrict(@NotNull final KeyEvent other) {
        return same(other) && (new HashSet<>(heldDown).containsAll(other.heldDown) && (heldDown.isEmpty() || !other.heldDown.isEmpty()));
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final KeyEvent keyEvent = (KeyEvent) o;
        return sameStrict(keyEvent);
    }

    public int hashCode() {
        return keycode.hashCode();
    }

    public String toString() {
        return "KeyEvent{" +
                "keycode=" + keycode +
                ", heldDown=" + heldDown +
                ", character=" + character() + " | " + character +
                ", pressType=" + pressType +
                '}';
    }
}
