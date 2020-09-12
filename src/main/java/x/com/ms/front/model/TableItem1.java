package x.com.ms.front.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class TableItem1 {

	protected final SimpleStringProperty att1 = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty att1Property() {
		return att1;
	}

	// ---------------------------------------------------------------

	public String getAtt1() {
		this.att1.set(this.emptyIsNull(att1.get()));
		return att1.get();
	}

	public void setAtt1(String v) {
		this.att1.set(this.emptyIsNull(v));
	}

	public void setAtt1(Object v) {
		this.att1.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public static List<TableItem1> toList1(Object[][] t) {

		List<TableItem1> items = new ArrayList<TableItem1>();

		for (int i = 0; i < t.length; i++) {

			TableItem1 item = new TableItem1();

			int j = 0;
			item.setAtt1(t[i][j]);

			items.add(item);
		}

		return items;
	}

	// ---------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableItem1 other = (TableItem1) obj;
		if (getAtt1() == null) {
			if (other.getAtt1() != null)
				return false;
		} else if (!getAtt1().equals(other.getAtt1()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getAtt1();
	}

	// ---------------------------------------------------------------

	protected String emptyIsNull(String value) {
		return (value == null || value.trim().length() == 0) ? null : value.trim();

	}

	protected String emptyIsNull(Object value) {
		return (value == null || value.toString().trim().length() == 0) ? null : value.toString().trim();

	}

}
