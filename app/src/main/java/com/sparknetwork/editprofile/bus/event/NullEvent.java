package com.sparknetwork.editprofile.bus.event;

/**
 * Empty object to be used when no data is to be published.
 *
 * This is a requirement to be able to subscribe/publish anyting, because RxBus publishsubject cannot
 * accept nulls
 */
public class NullEvent {
}
