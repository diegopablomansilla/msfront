package com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem5 extends TableItem4 {

	protected final SimpleStringProperty att5 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public TableItem5() {

	}

	public TableItem5(Object[] r) {

		int j = 0;

		this.setAtt1(r[j]);

		j++;
		this.setAtt2(r[j]);

		j++;
		this.setAtt3(r[j]);

		j++;
		this.setAtt4(r[j]);
		
		j++;
		this.setAtt5(r[j]);

	}

	// ---------------------------------------------------------------

	public SimpleStringProperty att5Property() {
		return att5;
	}

	// ---------------------------------------------------------------

	public String getAtt5() {
		this.att5.set(this.emptyIsNull(att5.get()));
		return att5.get();
	}

	public void setAtt5(String v) {
		this.att5.set(this.emptyIsNull(v));
	}

	public void setAtt5(Object v) {
		this.att5.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem5> toList5(Object[][] t) {

		List<TableItem5> items = new ArrayList<TableItem5>();

		for (int i = 0; i < t.length; i++) {

			TableItem5 item = new TableItem5();

			int j = 0;
			item.setAtt1(t[i][j]);

			j++;
			item.setAtt2(t[i][j]);

			j++;
			item.setAtt3(t[i][j]);

			j++;
			item.setAtt4(t[i][j]);

			j++;
			item.setAtt5(t[i][j]);

			items.add(item);
		}

		return items;
	}

}
