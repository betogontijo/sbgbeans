package br.com.betogontijo.sbgbeans.crawler.documents;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "domain")
public class Domain {

	@Indexed(unique = true)
	private String uri;

	private byte[] robotsContent;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public byte[] getRobotsContent() {
		return robotsContent;
	}

	public void setRobotsContent(byte[] robotsContent) {
		this.robotsContent = robotsContent;
	}

}
