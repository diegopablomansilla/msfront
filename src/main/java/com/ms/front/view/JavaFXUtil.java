package com.ms.front.view;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;

public class JavaFXUtil {
	
//	JavaFXUtil.addEvenTtableviewScrolling(table);

//	table.addEventHandler(ScrollEvent.ANY, event -> {
//		if (event.getEventType().equals(ScrollEvent.TOP_REACHED)) {
//			onBuscarBack();
//		} else if (event.getEventType().equals(ScrollEvent.BOTTOM_REACHED)) {
//			onBuscarNext();
//		}
//
//	});

	public static ScrollBar getVerticalScrollbar(final TableView<?> table) {
		ScrollBar result = null;
		for (final Node n : table.lookupAll(".scroll-bar")) {
			if (n instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) n;
				if (bar.getOrientation().equals(Orientation.VERTICAL)) {
					result = bar;
				}
			}
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addEvenTtableviewScrolling(TableView table) {

		int TOP_REACHED = 0;
		int BOTTOM_REACHED = 1;

		// Now, let's add a listener to the TableView's scrollbar. We can only access
		// the ScrollBar after the Scene is
		// rendered, so we need to do schedule this to run later.
		Platform.runLater(() -> {

			ScrollBar scrollBar = (ScrollBar) table.lookup(".scroll-bar:vertical");

			System.out.println(scrollBar.computeAreaInScreen());

			scrollBar.setOnScroll(e -> System.out.println("xxxxxxxxxxxxx0"));

			scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {

				double d = (Double) newValue;

				int index = table.getSelectionModel().getSelectedIndex();

				int r = -1;

//				System.out.println(d);

				if (d == 0.0 || d <= 0.0000000000000009 ) {
					r = TOP_REACHED;
				} else if (d == 1.0 || d == 0.9999999999999999) {
					r = BOTTOM_REACHED;
				}

			});

		});

		// ==================================================================================

		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			int index = table.getSelectionModel().getSelectedIndex();
			int size = table.getItems().size();
			int lastIndex = size - 1;
			int result = -1;

			if (index > -1 && size > 0) {

				if (index == 0) {
					result = TOP_REACHED;
				} else if (index == lastIndex) {
					result = BOTTOM_REACHED;
				}

				if (result == TOP_REACHED) {
					System.out.printf("%s%n", "BOTTOM_REACHED TTT");
//					table.fireEvent(new ScrollEvent(ScrollEvent.TOP_REACHED));					
				} else if (result == BOTTOM_REACHED) {
					System.out.printf("%s%n", "TOP_REACHED TTT");
//					table.fireEvent(new ScrollEvent(ScrollEvent.BOTTOM_REACHED));					
				}
			}

		});

	}
}
