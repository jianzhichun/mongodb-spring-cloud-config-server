package io.github.jianzhichun.spring.cloud.config.mongodb.domain;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


@Document
public class MongoPropertySource implements Comparable<MongoPropertySource>{
	
	private @Id String id;
	private String app;
	private String label;
	private String profile;
	private int order = Integer.MAX_VALUE;
	private List<String> includes;
	private LinkedHashMap<String, Object> source = newLinkedHashMap();
	
	@JsonCreator
	public MongoPropertySource(@JsonProperty("app") String app
			, @JsonProperty("label") String label
			, @JsonProperty("profile") String profile
			, @JsonProperty("order") int order
			, @JsonProperty("includes") List<String> includes
			, @JsonProperty("source") LinkedHashMap<String, Object> source){
		this.app = app;
		this.label = label;
		this.profile = profile;
		this.order = order;
		this.includes = includes;
		this.source = source;
		this.id = String.format("%s-%s-%s", app, label, profile);
	}
	
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<String> getIncludes() {
		return includes;
	}
	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}
	public LinkedHashMap<String, Object> getSource() {
		return source;
	}
	public void setSource(LinkedHashMap<String, Object> source) {
		this.source = source;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int compareTo(MongoPropertySource o) {
		return Integer.compare(this.order, o.order);
	}
}
