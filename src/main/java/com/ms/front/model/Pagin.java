package com.ms.front.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;

public class Pagin extends ObjectModel {

	private String pageRequest;
	private Integer lastIndexOld;
	private Integer pageSize;
	private Integer cantRows = 0;
	private Integer cantPages = 0;
	private List<Page> pages = new ArrayList<Page>();
	private Page firstPage;
	private Page lastPage;
	private Page thisPage;

	private Integer thisPageSize = 0;
	private Integer thisPageColumns = 0;
	private Object[][] thisPageItems;
	private Integer lastIndex = 0;

	public String getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(String pageRequest) {
		this.pageRequest = pageRequest;
	}

	public Integer getLastIndexOld() {
		return lastIndexOld;
	}

	public void setLastIndexOld(Integer lastIndexOld) {
		this.lastIndexOld = lastIndexOld;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCantRows() {
		return cantRows;
	}

	public void setCantRows(Integer cantRows) {
		this.cantRows = cantRows;
	}

	public Integer getCantPages() {
		return cantPages;
	}

	public void setCantPages(Integer cantPages) {
		this.cantPages = cantPages;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public Page getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Page firstPage) {
		this.firstPage = firstPage;
	}

	public Page getLastPage() {
		return lastPage;
	}

	public void setLastPage(Page lastPage) {
		this.lastPage = lastPage;
	}

	public Page getThisPage() {
		return thisPage;
	}

	public void setThisPage(Page thisPage) {
		this.thisPage = thisPage;
	}

	public Integer getThisPageSize() {
		return thisPageSize;
	}

	public Integer getThisPageColumns() {
		return thisPageColumns;
	}

	public void setThisPageColumns(Integer thisPageColumns) {
		this.thisPageColumns = thisPageColumns;
	}

	public void setThisPageSize(Integer thisPageSize) {
		this.thisPageSize = thisPageSize;
	}

	public Object[][] getThisPageItems() {
		return thisPageItems;
	}

	public void setThisPageItems(Object[][] thisPageItems) {
		this.thisPageItems = thisPageItems;
	}

	public Integer getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(Integer lastIndex) {
		this.lastIndex = lastIndex;
	}

	public Page getPage(int pageNumber) {
		for (Page page : pages) {
			if (page.getPageNumber() == pageNumber) {
				return page;
			}
		}

		return null;
	}

	public String toString() {

		String s = "";

		s += "\n" + "[" + pageRequest + "] => PAGE_SIZE=[" + pageSize + "], ROWS=[" + cantRows + "], PAGES=["
				+ cantPages + "], LAST_INDEX_OLD=[" + lastIndexOld + "]";

		s += "\n\n";
		for (Page page : pages) {
			s += "\n\t" + page;
		}

//		s += "\n\n" + "first: " + firstPage;
//		s += "\n" + "last: " + lastPage;
//		s += "\n\n" + "this: " + thisPage;

		if (thisPageItems != null) {
			s += "\n\nPAGE = (" + thisPage.getPageNumber() + ") [" + thisPage.getIndexFrom() + "-"
					+ thisPage.getIndexTo() + "], THIS_PAGE_SIZE=[" + thisPageSize + "], THIS_PAGE_COLUMNS=["
					+ thisPageColumns + "], LAST_INDEX=[" + lastIndex + "]";

			for (Object[] row : thisPageItems) {
				s += "\n" + Arrays.toString(row);
			}
		}

		return s;
	}

	public JsonObject toJson() {

		JsonObjectWrapper j = new JsonObjectWrapper();

//		j.set("args", args);

		j.set("pageRequest", this.getPageRequest());
		j.set("lastIndexOld", this.getLastIndexOld());
		j.set("pageSize", this.getPageSize());
		j.set("cantRows", this.getCantRows());
		j.set("cantPages", this.getCantPages());

		JsonArrayWrapper arrayPages = new JsonArrayWrapper();
		for (Page page : pages) {
			arrayPages.add(page);
		}
		j.set("pages", arrayPages.build());

		j.set("firstPage", this.getFirstPage());
		j.set("lastPage", this.getLastPage());
		j.set("thisPage", this.getThisPage());

		j.set("thisPageSize", this.getThisPageSize());
		j.set("thisPageColumns", this.getThisPageColumns());
		j.set("lastIndex", this.getLastIndex());

//		JsonArrayWrapper jaItems = new JsonArrayWrapper();
//		jaItems.addTable(thisPageItems);				
//		j.set("thisPageItems", jaItems);	

		j.set("thisPageItems", thisPageItems);

		return j.build();
	}

	public static Pagin fromJson(String json) {
		if (json == null || json.trim().length() == 0) {
			return new Pagin();
		}

		JsonObject jo = Json.createReader(new StringReader(json)).readObject();

		return fromJson(jo);
	}

	public static Pagin fromJson(JsonObject jo) {

		if (jo == null) {
			return null;
		}

		if (jo.isEmpty()) {
			return null;
		}

//		att = "payload";
//
//		if (jo.containsKey(att) && jo.isNull(att)) {
//			return null;
//		}

//		jo = jo.getJsonObject(att);

		Pagin p = new Pagin();

		String att = "pageRequest";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setPageRequest(jo.getString(att));
		}

		att = "lastIndexOld";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setLastIndexOld(jo.getInt(att));
		}

		att = "pageSize";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setPageSize(jo.getInt(att));
		}

		att = "cantRows";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setCantRows(jo.getInt(att));
		}

		att = "thisPageSize";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setThisPageSize(jo.getInt(att));
		}

		att = "cantPages";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setCantPages(jo.getInt(att));
		}

		att = "pages";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			JsonArray jsonArrayRows = jo.getJsonArray(att);
			for (int i = 0; i < jsonArrayRows.size(); i++) {
				p.getPages().add(Page.fromJson(jsonArrayRows.getJsonObject(i)));
			}
		}

		att = "firstPage";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setFirstPage(Page.fromJson(jo.getJsonObject(att)));
		}

		att = "lastPage";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setLastPage(Page.fromJson(jo.getJsonObject(att)));
		}

		att = "thisPage";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setThisPage(Page.fromJson(jo.getJsonObject(att)));
		}

		att = "lastIndex";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setLastIndex(jo.getInt(att));
		}

		att = "thisPageColumns";
		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setThisPageColumns(jo.getInt(att));
		}

		att = "thisPageItems";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			JsonArray jsonArrayRows = jo.getJsonArray(att);

			Object[][] table = new Object[jsonArrayRows.size()][p.getThisPageColumns()];

			for (int i = 0; i < jsonArrayRows.size(); i++) {
				JsonArray jsonArrayRow = jsonArrayRows.getJsonArray(i);

				for (int j = 0; j < jsonArrayRow.size(); j++) {

					if (jsonArrayRow.isNull(j) == false) {

						if (jsonArrayRow.get(j).getValueType() == ValueType.STRING) {
							table[i][j] = jsonArrayRow.getString(j);
						} 

					}

				}

			}

			p.setThisPageItems(table);
		}

		return p;
	}

}
