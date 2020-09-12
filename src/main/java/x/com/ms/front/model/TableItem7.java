package x.com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem7 extends TableItem6 {

	protected final SimpleStringProperty att7 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public TableItem7() {

	}

	public TableItem7(Object[] r) {

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

		j++;
		this.setAtt6(r[j]);

		j++;
		this.setAtt7(r[j]);

	}

	// ---------------------------------------------------------------

	public SimpleStringProperty att7Property() {
		return att7;
	}

	// ---------------------------------------------------------------

	public String getAtt7() {
		this.att7.set(this.emptyIsNull(att7.get()));
		return att7.get();
	}

	public void setAtt7(String v) {
		this.att7.set(this.emptyIsNull(v));
	}

	public void setAtt7(Object v) {
		this.att7.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem7> toList7(Object[][] t) {

		List<TableItem7> items = new ArrayList<TableItem7>();

		for (int i = 0; i < t.length; i++) {

			TableItem7 item = new TableItem7();

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

			j++;
			item.setAtt7(t[i][j]);

			items.add(item);
		}

		return items;
	}

}
