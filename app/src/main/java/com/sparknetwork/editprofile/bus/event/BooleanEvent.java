package com.sparknetwork.editprofile.bus.event;

/**
 * POJO class for {@link com.sparknetwork.editprofile.bus.RxBus} event type.
 * final variable value and getter(s).
 */
public class BooleanEvent {

    private final boolean booleanValue;

    public BooleanEvent(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

}
