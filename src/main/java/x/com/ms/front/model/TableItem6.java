package x.com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem6 extends TableItem5 {

	protected final SimpleStringProperty att6 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty att6Property() {
		return att6;
	}

	// ---------------------------------------------------------------

	public String getAtt6() {
		this.att6.set(this.emptyIsNull(att6.get()));
		return att6.get();
	}

	public void setAtt6(String v) {
		this.att6.set(this.emptyIsNull(v));
	}

	public void setAtt6(Object v) {
		this.att6.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem6> toList6(Object[][] t) {

		List<TableItem6> items = new ArrayList<TableItem6>();

		for (int i = 0; i < t.length; i++) {

			TableItem6 item = new TableItem6();

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

			j++;
			item.setAtt6(t[i][j]);

			items.add(item);
		}

		return items;
	}

}
