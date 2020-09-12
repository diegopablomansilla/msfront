package x.com.ms.front.model;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;

public class Page extends ObjectModel implements Cloneable {

	private Integer pageNumber;
	private Integer indexFrom;
	private Integer indexTo;
	private Boolean firstPage;
	private Boolean thisPage = false;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getIndexFrom() {
		return indexFrom;
	}

	public void setIndexFrom(Integer indexFrom) {
		this.indexFrom = indexFrom;
	}

	public Integer getIndexTo() {
		return indexTo;
	}

	public void setIndexTo(Integer indexTo) {
		this.indexTo = indexTo;
	}

	public Boolean getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Boolean firstPage) {
		this.firstPage = firstPage;
	}

	public Boolean getThisPage() {
		return thisPage;
	}

	public void setThisPage(Boolean thisPage) {
		this.thisPage = thisPage;
	}

	@Override
	public String toString() {
		String firstPageSufix = "";
		String thisPageSufix = "      ";
		if (firstPage != null && firstPage == true) {
			firstPageSufix = "(FIRST)";
		} else if (firstPage != null && firstPage == false) {
			firstPageSufix = "(LAST)";
		}
		if (thisPage != null) {
			thisPageSufix = "(THIS)";
		}
		return thisPageSufix + " PAGE (" + pageNumber + ") [" + indexFrom + "-" + indexTo + "] "
				+ firstPageSufix.trim();
	}

//	public Page clone() {
//		return new Page(this.pageNumber, this.fromIndex, this.toIndex);
//	}

	public JsonObject toJson() {

		JsonObjectWrapper j = new JsonObjectWrapper();

		j.set("pageNumber", this.getPageNumber());
		j.set("indexFrom", this.getIndexFrom());
		j.set("indexTo", this.getIndexTo());
		j.set("firstPage", this.getFirstPage());
		j.set("thisPage", this.getThisPage());

		return j.build();
	}

	public static Page fromJson(String json) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}

		JsonObject jo = Json.createReader(new StringReader(json)).readObject();

		return fromJson(jo);
	}

	public static Page fromJson(JsonObject jo) {

		if (jo == null) {
			return null;
		}

		Page p = new Page();

		String att = "pageNumber";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setPageNumber(jo.getInt(att));
		}

		att = "indexFrom";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setIndexFrom(jo.getInt(att));
		}

		att = "indexTo";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setIndexTo(jo.getInt(att));
		}

		att = "firstPage";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setFirstPage(jo.getBoolean(att));
		}

		att = "thisPage";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setThisPage(jo.getBoolean(att));
		}

		return p;
	}

}
