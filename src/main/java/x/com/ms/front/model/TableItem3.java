package x.com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem3 extends TableItem2 {

	protected final SimpleStringProperty att3 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public TableItem3() {

	}

	public TableItem3(Object[] r) {

		int j = 0;

		this.setAtt1(r[j]);

		j++;
		this.setAtt2(r[j]);

		j++;
		this.setAtt3(r[j]);

	}

	// ---------------------------------------------------------------

	public SimpleStringProperty att3Property() {
		return att3;
	}

	// ---------------------------------------------------------------

	public String getAtt3() {
		this.att3.set(this.emptyIsNull(att3.get()));
		return att3.get();
	}

	public void setAtt3(String v) {
		this.att3.set(this.emptyIsNull(v));
	}

	public void setAtt3(Object v) {
		this.att3.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem3> toList3(Object[][] t) {

		List<TableItem3> items = new ArrayList<TableItem3>();

		for (int i = 0; i < t.length; i++) {

			TableItem3 item = new TableItem3();

			int j = 0;
			item.setAtt1(t[i][j]);

			j++;
			item.setAtt2(t[i][j]);

			j++;
			item.setAtt3(t[i][j]);

			items.add(item);
		}

		return items;
	}

}
