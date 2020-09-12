package x.com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem4 extends TableItem3 {

	protected final SimpleStringProperty att4 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public TableItem4() {

	}

	public TableItem4(Object[] r) {

		int j = 0;

		this.setAtt1(r[j]);

		j++;
		this.setAtt2(r[j]);

		j++;
		this.setAtt3(r[j]);

		j++;
		this.setAtt4(r[j]);

	}

	// ---------------------------------------------------------------

	public SimpleStringProperty att4Property() {
		return att4;
	}

	// ---------------------------------------------------------------

	public String getAtt4() {
		this.att4.set(this.emptyIsNull(att4.get()));
		return att4.get();
	}

	public void setAtt4(String v) {
		this.att4.set(this.emptyIsNull(v));
	}

	public void setAtt4(Object v) {
		this.att4.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem4> toList4(Object[][] t) {

		List<TableItem4> items = new ArrayList<TableItem4>();

		for (int i = 0; i < t.length; i++) {

			TableItem4 item = new TableItem4();

			int j = 0;
			item.setAtt1(t[i][j]);

			j++;
			item.setAtt2(t[i][j]);

			j++;
			item.setAtt3(t[i][j]);

			j++;
			item.setAtt4(t[i][j]);

			items.add(item);
		}

		return items;
	}

}
