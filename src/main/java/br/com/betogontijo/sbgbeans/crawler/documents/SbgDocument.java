package br.com.betogontijo.sbgbeans.crawler.documents;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import net.minidev.json.annotate.JsonIgnore;

@Document(collection = "document")
public class SbgDocument {
	@Id
	private int id;

	@Indexed(unique = true)
	private String uri;

	private long lastModified;

	private Map<String, int[]> wordsMap;

	private byte[] robotsContent;

	@Transient
	private String body;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isOutDated() {
		return getLastModified() <= 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, int[]> getWordsMap() {
		return wordsMap;
	}

	public void setWordsMap(Map<String, int[]> wordsMap) {
		this.wordsMap = wordsMap;
	}

	@JsonIgnore
	public byte[] getRobotsContent() {
		return robotsContent;
	}

	@JsonIgnore
	public void setRobotsContent(byte[] robotsContent) {
		this.robotsContent = robotsContent;
	}
}
