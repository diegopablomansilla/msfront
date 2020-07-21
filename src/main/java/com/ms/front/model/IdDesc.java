package com.ms.front.model;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;

public class IdDesc extends ObjectModel {

	private String id;
	private String desc;

	public String getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public JsonObject toJson() {

		JsonObjectWrapper j = new JsonObjectWrapper();

		j.set("id", this.getId());

		j.set("desc", this.getDesc());

		return j.build();
	}

	public static IdDesc fromJson(String json) {
		if (json == null || json.trim().length() == 0) {
			return new IdDesc();
		}

		JsonObject jo = Json.createReader(new StringReader(json)).readObject();

		return fromJson(jo);
	}

	public static IdDesc fromJson(JsonObject jo) {

		if (jo == null) {
			return null;
		}

		if (jo.isEmpty()) {
			return null;
		}

//		String att = "payload";

//		if (jo.containsKey(att) && jo.isNull(att)) {
//			return null;
//		}

//		jo = jo.getJsonObject(att);

		IdDesc p = new IdDesc();

		String att = "id";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setId(jo.getString(att));
		}

		att = "desc";

		if (jo.containsKey(att) && !jo.isNull(att)) {
			p.setDesc(jo.getString(att));
		}

		return p;
	}

}
