package com.ms.front.view.cuenta_contable;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

	private int offset;
	private List<T> items = new ArrayList<T>();

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
