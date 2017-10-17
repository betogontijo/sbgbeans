package br.com.betogontijo.sbgbeans.crawler.documents;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "document")
public class SbgDocument {
	@Id
	@Indexed
	private String uri;

	private Long lastModified;

	Map<String, int[]> wordsMap;

	@Transient
	private String body;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isOutDated() {
		return getLastModified() != null;
	}
}
