package org.crayne.jtux.event.keyboard;

import java.util.Arrays;
import java.util.HashSet;

public enum Keycode {

    ESCAPE(27),
    F1(112),
    F2(113),
    F3(114),
    F4(115),
    F5(116),
    F6(117),
    F7(118),
    F8(119),
    F9(120),
    F10(121),
    F11(122),
    F12(123),
    PAGE_ROLL(145),

    CIRCUMFLEX_ACCENT(220),
    ONE(49),
    TWO(50),
    THREE(51),
    FOUR(52),
    FIVE(53),
    SIX(54),
    SEVEN(55),
    EIGHT(56),
    NINE(57),
    ZERO(48),
    GERMAN_SHARP_S(219),
    ACUTE_ACCENT(221),
    BACKSPACE(8),

    TAB(9),
    Q(81, 113),
    W(87, 119),
    E(69, 101),
    R(82, 114),
    T(84, 116),
    Z(90, 122),
    U(85, 117),
    I(73, 105),
    O(79, 111),
    P(80, 112),
    GERMAN_U_DIAERESIS(186),
    PLUS(187, 43),
    ENTER(13),

    CAPS_LOCK(20),
    A(65, 97),
    S(83, 115),
    D(68, 100),
    F(70, 102),
    G(71, 103),
    H(72, 104),
    J(74, 106),
    K(75, 107),
    L(76, 108),
    GERMAN_O_DIAERESIS(192),
    GERMAN_A_DIAERESIS(222),
    HASH(191, 35),

    SHIFT(16),
    LESS_THAN_SIGN(226, 60),
    Y(89, 121),
    X(88, 120),
    C(67, 99),
    V(86, 118),
    B(66, 98),
    N(78, 110),
    M(77, 109),
    COMMA(188, 44),
    PERIOD(190, 46),
    MINUS(189, 45),

    CTRL(17),
    WINDOWS_KEY_LEFT(91),
    ALT(18),
    SPACE(32),
    WINDOWS_KEY_RIGHT(92),
    CLIPBOARD_KEY(93),

    INSERT(45),
    HOME(36),
    PAGE_UP(33),
    DELETE(46),
    END(35),
    PAGE_DOWN(34),

    ARROW_UP(38),
    ARROW_LEFT(37),
    ARROW_DOWN(40),
    ARROW_RIGHT(39),

    NUM_LOCK(144),
    NUMPAD_SLASH(111, 47),
    NUMPAD_ASTERISK(106, 42),
    NUMPAD_MINUS(109, 45),
    NUMPAD_SEVEN(103, 55),
    NUMPAD_EIGHT(104, 56),
    NUMPAD_NINE(105, 57),
    NUMPAD_PLUS(107, 43),
    NUMPAD_FOUR(100, 52),
    NUMPAD_FIVE(101, 53),
    NUMPAD_SIX(102, 54),
    NUMPAD_ONE(97, 49),
    NUMPAD_TWO(98, 50),
    NUMPAD_THREE(99, 51),
    NUMPAD_ZERO(96, 48),
    NUMPAD_COMMA(110, 44),

    PLAYER_KEY_STOP(178),
    PLAYER_KEY_PREVIOUS(177),
    PLAYER_KEY_PAUSE(179),
    PLAYER_KEY_NEXT(176),
    PLAYER_MUTE(173),
    PLAYER_VOLUME_DOWN(174),
    PLAYER_VOLUME_UP(175),

    CTRL_SPACE_LINUX(0),
    UNDERSCORE(230, 95),
    APOSTROPHE(231, 39),
    COLON(232, 58),
    SEMICOLON(233, 59),
    GREATER_THAN(234, 62),
    VERTICAL_BAR(235, 124),
    CARET(236, 94),
    EXCLAMATION_MARK(237, 33),
    QUOTE(238, 34),
    DOLLAR_SIGN(239, 36),
    PERCENTAGE(240, 37),
    AMPERSAND(241, 38),
    LEFT_BRACE(242, 123),
    LEFT_PARENTHESIS(243, 40),
    LEFT_BRACKET(244, 91),
    RIGHT_PARENTHESIS(245, 41),
    RIGHT_BRACKET(246, 93),
    RIGHT_BRACE(247, 125),
    EQUALS_SIGN(248, 61),
    BACKSLASH(249, 92),
    QUESTION_MARK(250, 63),
    GRAVE_ACCENT(251, 96),
    TILDE(252, 126),

    AT(64),

    UNICODE_CHARACTER(60000),

    UNKNOWN(-1);

    private final int keycode;
    private final int[] expect;

    Keycode(final int keycode, final int... expect) {
        this.keycode = keycode;
        this.expect = expect;
    }

    public static Keycode of(final int keycode) {
        return Arrays.stream(values()).filter(k -> k.keycode == keycode).findFirst().orElse(keycode > 60000 ? UNICODE_CHARACTER : UNKNOWN);
    }

    int expect() {
        return expect.length == 0 ? keycode : expect[0];
    }

    boolean expected(final int keychar) {
        if (keycode == 60000) return true;
        final HashSet<Integer> expected = new HashSet<>(Arrays.stream(expect).boxed().toList());
        return expected.contains(keychar);
    }

    public int keycode() {
        return keycode;
    }

}
