#include <jni.h>
#include <iostream>
#include <ncurses.h>
#include <deque>
#include <ctime>
#include <cmath>
#include <cstdlib>
#include <sys/ioctl.h>
#include <stdio.h>
#include <unistd.h>
#include <linux/input.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "JTuxLibrary.h"
/* header for class org_crayne_jtux_util_lib_NativeJTuxLibrary */

#undef main

bool initialized = false;

int kbhit() {
    int ch = getch();

    if (ch != ERR) {
        ungetch(ch);
        return 1;
    } else {
        return 0;
    }
}

typedef std::deque<int> keyboard_event;

keyboard_event await_keypress() {
    keyboard_event keypress;

    while (true) {
        if (kbhit()) {
            keypress.push_back(getch());
            refresh();
        } else if (!keypress.empty()) {
            break;
        }
    }
    return keypress;
}

keyboard_event transform_keypress_single(const int ev) {
    keyboard_event newpress;

    switch (ev) { // please tell me theres a better way to convert linux keypresses to windows keypresses
        case 0: newpress.push_back(17); newpress.push_back(32); break; // ctrl + space
        case 45: newpress.push_back(189); break; // minus sign
        case 43: newpress.push_back(187); break; // plus sign
        case 35: newpress.push_back(191); break; // hashtag
        case 42: newpress.push_back(106); break; // asterisk
        case 47: newpress.push_back(111); break; // slash
        case 46: newpress.push_back(190); break; // period
        case 44: newpress.push_back(188); break; // comma
        case 10: newpress.push_back(13); break; // enter
        case 127: newpress.push_back(8); break; // backspace
        case 60: newpress.push_back(226); break; // less than sign
        case 64: newpress.push_back(64); break;

        case 95: newpress.push_back(230); break; // underscore
        case 39: newpress.push_back(231); break; // apostrophe
        case 58: newpress.push_back(232); break; // colon
        case 59: newpress.push_back(233); break; // semicolon
        case 62: newpress.push_back(234); break; // greater than sign
        case 124: newpress.push_back(235); break; // vertical bar
        case 94: newpress.push_back(236); break; // caret
        case 33: newpress.push_back(237); break; // exclamation mark
        case 34: newpress.push_back(238); break; // quote sign
        case 36: newpress.push_back(239); break; // dollar sign
        case 37: newpress.push_back(240); break; // percentage
        case 38: newpress.push_back(241); break; // ampersand
        case 123: newpress.push_back(242); break; // left brace
        case 40: newpress.push_back(243); break; // left parenthesis
        case 91: newpress.push_back(244); break; // left bracket
        case 41: newpress.push_back(245); break; // right parenthesis
        case 93: newpress.push_back(246); break; // right bracket
        case 125: newpress.push_back(247); break; // right brace
        case 61: newpress.push_back(248); break; // equals sign
        case 92: newpress.push_back(249); break; // backslash
        case 63: newpress.push_back(250); break; // question mark
        case 96: newpress.push_back(251); break; // grave accent
        case 126: newpress.push_back(252); break; // tilde

        case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 11: case 12: case 13:
        case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: case 24: case 25: case 26:
            newpress.push_back(17);
            newpress.push_back(ev + 64);
            break; // ctrl + key
       case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109: case 110:
       case 111: case 112: case 113: case 114: case 115: case 116: case 117: case 118: case 119: case 120: case 121: case 122:
           newpress.push_back(ev - 32); // convert lowercase to uppercase, we only care about the physical key pressed, not the character (for now)
           break;
        case 65: case 66: case 67: case 68: case 69: case 70: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79:
        case 80: case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89: case 90:
            newpress.push_back(16);
            newpress.push_back(ev);
            break; // a symbol has been pressed with shift, so add the shift key to the result
        case 9: case 27: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: case 48: case 32:
            newpress.push_back(ev);
            break; // nothing special, a simple key event
    }
    keyboard_event press_release;
    for (int i = 0; i < newpress.size(); i++) {
        press_release.push_back(newpress[i]); // press
    }
    for (int i = newpress.size() - 1; i >= 0; i--) {
        press_release.push_back(-newpress[i]); // release keys in backwards order
    }
    return press_release;
}

void push_all(const keyboard_event& push, keyboard_event& storage) {
    for (int i = 0; i < push.size(); i++) storage.push_back(push[i]);
}

int uni_page(const int unicode) {
    return 192 + (unicode / 64);
}

int uni_symbol_relative(const int unicode) {
    return 128 + (unicode % 64);
}

int unicode(const int uni_page, const int uni_symbol_relative) {
    int uni_page_reverse = (uni_page - 192) * 64;
    int uni_symbol_reverse = (uni_symbol_relative - 128) % 64;
    return uni_page_reverse + uni_symbol_reverse;
}

keyboard_event transform_keypress_unicode(const keyboard_event& ev) {
    keyboard_event newpress;
    newpress.push_back(60000 + unicode(ev[0], ev[1]));
    newpress.push_back(-(60000 + unicode(ev[0], ev[1])));
    return newpress;
}

keyboard_event transform_keypress_alt(const keyboard_event& ev) {
    if (ev[0] != 27) {
        if (ev[0] >= 194) return transform_keypress_unicode(ev);
        return ev;
    }
    keyboard_event newpress;
    newpress.push_back(18);

    push_all(transform_keypress_single(ev[1]), newpress);
    newpress.push_back(-18);
    return newpress;
}

void handle_function_key_1(const int ev, keyboard_event& add_to, bool negate) {
    switch (ev) {
        case 80: add_to.push_back(negate ? -112 : 112); break;
        case 81: add_to.push_back(negate ? -113 : 113); break;
        case 82: add_to.push_back(negate ? -114 : 114); break;
        case 83: add_to.push_back(negate ? -115 : 115); break;
    }
}

void handle_cursor_key(const int ev, keyboard_event& add_to, bool negate) {
    handle_function_key_1(ev, add_to, negate); // fits the format nicely with ctrl, alt / shift, so just adding it here (this is only the case for f1 - f4)
    switch (ev) {
        case 70: add_to.push_back(negate ? -35 : 35); break;
        case 72: add_to.push_back(negate ? -36 : 36); break;
        case 68: add_to.push_back(negate ? -37 : 37); break;
        case 65: add_to.push_back(negate ? -38 : 38); break;
        case 67: add_to.push_back(negate ? -39 : 39); break;
        case 66: add_to.push_back(negate ? -40 : 40); break;
    }
}

void handle_adv_cursor_ctrl(const int ev, keyboard_event& add_to, bool negate) {
    switch (ev) {
        case 50: add_to.push_back(negate ? -45 : 45); break; // insert
        case 51: add_to.push_back(negate ? -46 : 46); break; // delete
        case 53: add_to.push_back(negate ? -33 : 33); break; // page up
        case 54: add_to.push_back(negate ? -34 : 34); break; // page down
    }
}

keyboard_event transform_keypress_cursor_control(const keyboard_event& ev) {
    keyboard_event newpress;
    handle_cursor_key(ev[2], newpress, false);
    handle_cursor_key(ev[2], newpress, true);
    return newpress;
}

keyboard_event transform_keypress_function_key_1(const int ev) {
    keyboard_event newpress;
    handle_function_key_1(ev, newpress, false);
    handle_function_key_1(ev, newpress, true);
    return newpress;
}

keyboard_event transform_keypress_triple(const keyboard_event& ev) {
    if (ev[0] == 27) {
        if (ev[1] == 91) return transform_keypress_cursor_control(ev);
        if (ev[1] == 79) return transform_keypress_function_key_1(ev[2]);
    }
    return ev;
}

void handle_ctrl_alt_shift(const int ev, keyboard_event& add_to, bool negate) {
    switch (ev) {
        case 50: add_to.push_back(negate ? -16 : 16); break; // shift
        case 51: add_to.push_back(negate ? -18 : 18); break; // alt
        case 52: add_to.push_back(negate ? -18 : 16); add_to.push_back(negate ? -16 : 18); break; // alt + shift
        case 53: add_to.push_back(negate ? -17 : 17); break; // ctrl
        case 54: add_to.push_back(negate ? -17 : 16); add_to.push_back(negate ? -16 : 17); break; // ctrl + shift
        case 55: add_to.push_back(negate ? -18 : 17); add_to.push_back(negate ? -17 : 18); break; // ctrl + alt
        case 56: add_to.push_back(negate ? -18 : 16); add_to.push_back(negate ? -17 : 17); add_to.push_back(negate ? -16 : 18); break; // ctrl + shift + alt
    }
}

void handle_function_key_2(const int ev, keyboard_event& add_to, bool negate) {
    switch (ev) {
        case 53: add_to.push_back(negate ? -116 : 116); break;
        case 55: add_to.push_back(negate ? -117 : 117); break;
        case 56: add_to.push_back(negate ? -118 : 118); break;
        case 57: add_to.push_back(negate ? -119 : 119); break;
        case 48: add_to.push_back(negate ? -120 : 120); break;
        case 49: add_to.push_back(negate ? -121 : 121); break;
        case 51: add_to.push_back(negate ? -122 : 122); break;
        case 52: add_to.push_back(negate ? -123 : 123); break;
    }
}

keyboard_event transform_keypress_quintuple(const keyboard_event& ev) {
    if ((ev[0] == 27 && ev[1] == 91) && (ev[2] == 49 || ev[2] == 50)) { // f keys f5-f12
        keyboard_event newpress;
        handle_function_key_2(ev[3], newpress, false);
        handle_function_key_2(ev[3], newpress, true);
        return newpress;
    }
    if (ev[0] == 27 && ev[1] == 27 && ev[2] == 91 && ev[4] == 126) {
        keyboard_event newpress;
        newpress.push_back(18);
        handle_adv_cursor_ctrl(ev[3], newpress, false);
        handle_adv_cursor_ctrl(ev[3], newpress, true);
        newpress.push_back(-18);
        return newpress;
    }
    return ev;
}

keyboard_event transform_keypress_sixtuple(keyboard_event& ev) {
    if (ev[0] == 27 && ev[1] == 27 && ev[2] == 91) {
        ev.pop_front();
        return transform_keypress_quintuple(ev);
    }
    if (ev[0] == 27 && ev[1] == 91) {
        keyboard_event newpress;
        handle_ctrl_alt_shift(ev[4], newpress, false);
        if (ev[2] == 49 && ev[3] == 59) {
            // cursor control with alt / ctrl / shift
            handle_cursor_key(ev[5], newpress, false);
            handle_cursor_key(ev[5], newpress, true);
        }
        if (ev[5] == 126) {
            // advanced cursor control with alt / ctrl / shift, like page up, page down, etc
            handle_adv_cursor_ctrl(ev[2], newpress, false);
            handle_adv_cursor_ctrl(ev[2], newpress, true);
        }
        handle_ctrl_alt_shift(ev[4], newpress, true);
        return newpress;
    }
    return ev;
}

keyboard_event transform_keypress_quadruple(const keyboard_event& ev) {
    if (ev[0] == 27 && ev[1] == 91) {
        keyboard_event newpress;

        if (ev[3] == 126) {
        // keys like insert, delete, page up, page down
            handle_adv_cursor_ctrl(ev[2], newpress, false);
            handle_adv_cursor_ctrl(ev[2], newpress, true);
        }
        keyboard_event temp;
        handle_cursor_key(ev[3], temp, false);
        if (!temp.empty()) {
            handle_ctrl_alt_shift(ev[2], newpress, false);
            handle_cursor_key(ev[3], newpress, false);
            handle_cursor_key(ev[3], newpress, true);
            handle_ctrl_alt_shift(ev[2], newpress, true);
        }
        return newpress;
    }
    if (ev[0] == 27 && ev[1] == 27 && ev[2] == 91) { // seperate case for alt + cursor movement key, only comes up in other shells
        keyboard_event newpress;
        newpress.push_back(18);
        handle_cursor_key(ev[3], newpress, false);
        handle_cursor_key(ev[3], newpress, true);
        newpress.push_back(-18);
        return newpress;
    }
    return ev;
}

keyboard_event transform_keypress_function_key_2_adv(const keyboard_event& ev) {
    keyboard_event newpress;
    handle_ctrl_alt_shift(ev[5], newpress, false);
    handle_function_key_2(ev[3], newpress, false);
    handle_function_key_2(ev[3], newpress, true);
    handle_ctrl_alt_shift(ev[5], newpress, true);
    return newpress;
}

keyboard_event transform_keypress_septuple(const keyboard_event& ev) {
    if (ev[0] == 27 && ev[1] == 91 && (ev[2] == 49 || ev[2] == 50) && ev[4] == 59 && ev[6] == 126) return transform_keypress_function_key_2_adv(ev);
    return ev;
}

keyboard_event transform_clipboard_paste(keyboard_event& ev) {
    keyboard_event new_ev;
    for (int i = ev.size() - 1; i > 0; i--) {
        if (ev[i] >= 194) {
            int uni = 60000 + unicode(ev[i], ev[i - 1]);
            new_ev.push_back(uni); // press
            new_ev.push_back(-uni); // release
            i--;
            continue;
        }
        keyboard_event transformed = transform_keypress_single(ev[i]);
        for (int j = 0; j < transformed.size(); j++) new_ev.push_back(transformed[j]);
    }
    return new_ev;
}

// transform key events in such a way, that they resemble the windows api key events
keyboard_event transform_keypress(keyboard_event& ev) {
    keyboard_event ev_clone = ev;

    switch (ev.size()) {
        case 1: ev = transform_keypress_single(ev[0]); break;
        case 2: ev = transform_keypress_alt(ev); break;
        case 4: ev = transform_keypress_quadruple(ev); break;
        case 5: ev = transform_keypress_quintuple(ev); break;
        case 6: ev = transform_keypress_sixtuple(ev); break;
        case 7: ev = transform_keypress_septuple(ev); break;
        default: ev = transform_keypress_triple(ev); break;
    }
    return ev == ev_clone ? transform_clipboard_paste(ev) : ev; // if nothing has changed, obviously ctrl v has been pressed and we have to transform the characters. otherwise, return the transformed result
}

typedef struct KeyEvent {
    int key; int keychar; bool press_or_release;
} KEY_EVENT;

std::deque<KEY_EVENT> key_event(keyboard_event& ev) {
    std::deque<KEY_EVENT> event_deque;

    char actual = (ev.size() == 1 ? ev[0] : 0);
    keyboard_event tr = transform_keypress(ev);
    for (int key : tr) {
        event_deque.push_back(KEY_EVENT {abs(key), actual != 0 ? actual : abs(key) > 60000 ? abs(key) - 60000 : abs(key), key < 0});
        if (key < 0) actual = 0;
    }
    return event_deque;
}

std::deque<KEY_EVENT> await_key_event() {
    keyboard_event ev = await_keypress();
    return key_event(ev);
}

void init_jtux() {
    if (initialized) return;
    initscr();
    noecho();
    nodelay(stdscr, true);
    initialized = true;
}

void shutdown_jtux() {
    if (!initialized) return;
    nodelay(stdscr, false);
    echo();
    endwin();
    initialized = false;
}

std::deque<KEY_EVENT> pending_key_events;

typedef struct Coord {
    short X;
    short Y;
} COORD;

/*
 * Class:     org_crayne_jtux_util_lib_NativeJTuxLibrary
 * Method:    terminalWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_crayne_jtux_util_lib_NativeJTuxLibrary_terminalWidth(JNIEnv * env, jclass o) {
    struct winsize w;
    ioctl(STDOUT_FILENO, TIOCGWINSZ, &w);
    return w.ws_col - 1;
}

/*
 * Class:     org_crayne_jtux_util_lib_NativeJTuxLibrary
 * Method:    terminalHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_crayne_jtux_util_lib_NativeJTuxLibrary_terminalHeight(JNIEnv * env, jclass o) {
    struct winsize w;
    ioctl(STDOUT_FILENO, TIOCGWINSZ, &w);
    return w.ws_row - 1;
}

/*
 * Class:     org_crayne_jtux_util_lib_NativeJTuxLibrary
 * Method:    keyPress
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_org_crayne_jtux_util_lib_NativeJTuxLibrary_keyPress(JNIEnv * env, jclass o) {
    if (pending_key_events.empty()) {
        pending_key_events = await_key_event();
    }
    if (pending_key_events.empty()) return NULL;
    KEY_EVENT ev = pending_key_events.back();
    pending_key_events.pop_back();

    jintArray result = env->NewIntArray(3);
    if (result == NULL) {
        return NULL; // out of memory error thrown
    }
    int keyDown = ev.press_or_release;
    int keyChar = ev.keychar;
    int keyCode = ev.key;

    jint fill[] = {keyCode, keyChar, keyDown};
    env->SetIntArrayRegion(result, 0, 3, fill);
    return result;
}


/*
 * Class:     org_crayne_jtux_util_lib_NativeJTuxLibrary
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_crayne_jtux_util_lib_NativeJTuxLibrary_init
  (JNIEnv * env, jclass o) {
    init_jtux();
  }

/*
 * Class:     org_crayne_jtux_util_lib_NativeJTuxLibrary
 * Method:    shutdown
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_crayne_jtux_util_lib_NativeJTuxLibrary_shutdown
  (JNIEnv * env, jclass o) {
    shutdown_jtux();
  }