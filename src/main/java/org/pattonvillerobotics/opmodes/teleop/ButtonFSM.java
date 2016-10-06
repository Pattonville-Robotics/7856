package org.pattonvillerobotics.opmodes.teleop;

/**
 * Created by skaggsw on 10/4/16.
 */

public class ButtonFSM extends TeleOp {

    public ButtonState button = ButtonState.NOT_BEEN_PRESSED;

    public void CheckButton(boolean buttonValue) {

        switch (button) {

            case NOT_BEEN_PRESSED:
                if (buttonValue) {

                    button = ButtonState.JUST_PRESSED;

                }
            case JUST_PRESSED:
                if (buttonValue) {

                    button = ButtonState.BEING_PRESSED;

                } else {

                    button = ButtonState.JUST_BEEN_RELEASED;

                }
            case BEING_PRESSED:
                if (buttonValue) {

                } else {

                    button = ButtonState.JUST_BEEN_RELEASED;

                }
            case JUST_BEEN_RELEASED:
                if (buttonValue) {
                    button = ButtonState.JUST_PRESSED;
                } else {
                    button = ButtonState.NOT_BEEN_PRESSED;
                }
        }

    }

    public enum ButtonState {
        NOT_BEEN_PRESSED, JUST_PRESSED, BEING_PRESSED, JUST_BEEN_RELEASED
    }


}
