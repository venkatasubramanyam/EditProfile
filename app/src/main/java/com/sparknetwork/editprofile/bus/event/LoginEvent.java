package com.sparknetwork.editprofile.bus.event;

/**
 * POJO class for {@link com.sparknetwork.editprofile.bus.RxBus} event type.
 * final variable value and getter(s).
 */
public class LoginEvent {

    private final String email;
    private final String password;

    public LoginEvent(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
