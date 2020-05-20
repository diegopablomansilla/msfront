package com.ms.front.view;

import javafx.beans.property.SimpleIntegerProperty;

public class TableFilter implements Cloneable {

//	private final SimpleIntegerProperty limit = new SimpleIntegerProperty();
	private final SimpleIntegerProperty offset = new SimpleIntegerProperty();

	// ---------------------------------------------------------------

	public TableFilter() {
		super();
//		limit.set(20);
		offset.setValue(0);
	}

	// ---------------------------------------------------------------

//	public SimpleIntegerProperty limitProperty() {
//		return limit;
//	}

	public SimpleIntegerProperty offsetProperty() {
		return offset;
	}

	// ---------------------------------------------------------------

//	public Integer getLimit() {
//		return limit.get();
//	}
//
//	public void setLimit(Integer id) {
//		this.limit.set(id);
//	}

	public Integer getOffset() {
		return offset.get();
	}

	public void setOffset(Integer offset) {
		this.offset.set(offset);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((this.getLimit() == null) ? 0 : this.getLimit().hashCode());
		result = prime * result + ((this.getOffset() == null) ? 0 : getOffset().hashCode());
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
		TableFilter other = (TableFilter) obj;
//		if (this.getLimit() == null) {
//			if (other.getLimit() != null)
//				return false;
//		} else if (!this.getLimit().equals(other.getLimit()))
//			return false;
		if (this.getOffset() == null) {
			if (other.getOffset() != null)
				return false;
		} else if (!this.getOffset().equals(other.getOffset()))
			return false;
		return true;
	}

	@Override
	protected TableFilter clone() {

		TableFilter other = new TableFilter();

//		other.setLimit(this.getLimit());
		other.setOffset(this.getOffset());

		return other;
	}

//	@Override
//	public String toString() {
//		return "[limit=" + this.getLimit() + ", offset=" + this.getOffset() + "]";
//	}

	@Override
	public String toString() {
		return "[offset=" + this.getOffset() + "]";
	}
	
	// ---------------------------------------------------------------

}
