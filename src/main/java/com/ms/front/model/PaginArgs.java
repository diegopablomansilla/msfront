package com.ms.front.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PaginArgs extends ServiceArgs implements Cloneable {

	private final String PAGE_REQUEST_NEXT = "NEXT";
	private final String PAGE_REQUEST_BACK = "BACK";
	private final String PAGE_REQUEST_FIRST = "FIRST";
	private final String PAGE_REQUEST_LAST = "LAST";

	public final String KEY_DB = "db";
	public final String KEY_PAGE_REQUEST = "pr";
	public final String KEY_LAST_INDEX_OLD = "i";

	private final SimpleStringProperty db = new SimpleStringProperty();
	private final SimpleStringProperty pageRequest = new SimpleStringProperty();
	private final SimpleIntegerProperty lastIndexOld = new SimpleIntegerProperty();

	// ---------------------------------------------------------------

	public PaginArgs() {
		super();
	}

	// ---------------------------------------------------------------

	public SimpleStringProperty dbProperty() {
		return db;
	}

	public SimpleStringProperty pageRequestProperty() {
		return pageRequest;
	}

	public SimpleIntegerProperty lastIndexOldProperty() {
		return lastIndexOld;
	}

	// ---------------------------------------------------------------

	public String getDb() {
		this.db.set(this.emptyIsNull(db.get()));
		return db.get();
	}

	public void setDb(String db) {
		this.db.set(this.emptyIsNull(db));
	}

	public String getPageRequest() {
		this.pageRequest.set(this.emptyIsNull(pageRequest.get()));
		return pageRequest.get();
	}

	public void setPageRequestNext() {
		this.pageRequest.set(PAGE_REQUEST_NEXT);
	}

	public void setPageRequestBack() {
		this.pageRequest.set(PAGE_REQUEST_BACK);
	}

	public void setPageRequestFirst() {
		this.pageRequest.set(PAGE_REQUEST_FIRST);
	}

	public void setPageRequestLast() {
		this.pageRequest.set(PAGE_REQUEST_LAST);
	}

	private void setPageRequest(String pageRequest) {
		this.pageRequest.set(pageRequest);
	}

	public Integer getLastIndexOld() {
		return lastIndexOld.get();
	}

	public void setLastIndexOld(Integer lastIndexOld) {
		if (lastIndexOld == null) {
			lastIndexOld = 0;
		}
		this.lastIndexOld.set(lastIndexOld);
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();

		if (this.getDb() != null) {
			map.put(KEY_DB, this.getDb());
		}

		if (this.getPageRequest() != null) {
			map.put(KEY_PAGE_REQUEST, this.getPageRequest());
		}

		if (this.getLastIndexOld() != null) {
			map.put(KEY_LAST_INDEX_OLD, this.getLastIndexOld().toString());
		}

		return map;
	}

	@Override
	protected PaginArgs clone() {

		PaginArgs other = new PaginArgs();

		other.setDb(getDb());
		other.setPageRequest(getPageRequest());
		other.setLastIndexOld(getLastIndexOld());

		return other;
	}

	@Override
	public String toString() {
		return "PaginArgs [getDb()=" + getDb() + ", getPageRequest()=" + getPageRequest() + ", getLastIndexOld()="
				+ getLastIndexOld() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDb() == null) ? 0 : getDb().hashCode());
		result = prime * result + ((lastIndexOld == null) ? 0 : lastIndexOld.hashCode());
		result = prime * result + ((getPageRequest() == null) ? 0 : getPageRequest().hashCode());
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
		PaginArgs other = (PaginArgs) obj;
		if (getDb() == null) {
			if (other.getDb() != null)
				return false;
		} else if (!getDb().equals(other.getDb()))
			return false;
		if (getLastIndexOld() == null) {
			if (other.getLastIndexOld() != null)
				return false;
		} else if (!getLastIndexOld().equals(other.getLastIndexOld()))
			return false;
		if (getPageRequest() == null) {
			if (other.getPageRequest() != null)
				return false;
		} else if (!getPageRequest().equals(other.getPageRequest()))
			return false;
		return true;
	}

	// ---------------------------------------------------------------

}
