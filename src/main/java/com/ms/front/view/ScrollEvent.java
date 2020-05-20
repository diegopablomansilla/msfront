package com.ms.front.view;

import javafx.event.Event;
import javafx.event.EventType;

public class ScrollEvent extends Event {

	public static final EventType<ScrollEvent> ANY = new EventType<>(Event.ANY, "MY_EVENT");
	public static final EventType<ScrollEvent> TOP_REACHED = new EventType<>(ANY, "TOP_REACHED");
	public static final EventType<ScrollEvent> BOTTOM_REACHED = new EventType<>(ANY, "BOTTOM_REACHED");

	public ScrollEvent(EventType<? extends ScrollEvent> eventType) {
		super(eventType);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4847954036022597426L;

}
