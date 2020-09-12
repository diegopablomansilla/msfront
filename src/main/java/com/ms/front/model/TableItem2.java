package com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem2 extends TableItem1 {

	protected final SimpleStringProperty att2 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty att2Property() {
		return att2;
	}

	// ---------------------------------------------------------------

	public String getAtt2() {
		this.att2.set(this.emptyIsNull(att2.get()));
		return att2.get();
	}

	public void setAtt2(String v) {
		this.att2.set(this.emptyIsNull(v));
	}

	public void setAtt2(Object v) {
		this.att2.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem2> toList2(Object[][] t) {

		List<TableItem2> items = new ArrayList<TableItem2>();

		for (int i = 0; i < t.length; i++) {

			TableItem2 item = new TableItem2();

			int j = 0;
			item.setAtt1(t[i][j]);

			j++;
			item.setAtt2(t[i][j]);

			items.add(item);
		}

		return items;
	}

}
