package com.sparknetwork.editprofile.bus.event;

/**
 * POJO class for {@link com.sparknetwork.editprofile.bus.RxBus} event type.
 * final variable value and getter(s).
 */
public class ChangePassowordEvent {

    private final String newPassword;

    public ChangePassowordEvent(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
