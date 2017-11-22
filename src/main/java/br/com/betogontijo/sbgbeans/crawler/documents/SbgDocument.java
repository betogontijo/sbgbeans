package br.com.betogontijo.sbgbeans.crawler.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "document")
public class SbgDocument {
	@Id
	private int id;

	@Indexed(unique = true)
	private String uri;

	private String title;

	private long lastModified;

	private List<String> wordsList;

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

	public List<String> getWordsList() {
		return wordsList;
	}

	public void setWordsList(List<String> wordsList) {
		this.wordsList = wordsList;
	}

	public byte[] getRobotsContent() {
		return robotsContent;
	}

	public void setRobotsContent(byte[] robotsContent) {
		this.robotsContent = robotsContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object obj) {
		SbgDocument other = (SbgDocument) obj;
		if (this.getId() != other.getId()) {
			return false;
		}
		if (this.getTitle() == null) {
			if (other.getTitle() != null) {
				return false;
			}
		} else if (!this.getTitle().equals(other.getTitle())) {
			return false;
		}
		if (this.getUri() == null) {
			if (other.getUri() != null) {
				return false;
			}
		} else if (!this.getUri().equals(other.getUri())) {
			return false;
		}
		// if (this.getBody() == null) {
		// if (other.getBody() != null) {
		// return false;
		// }
		// } else if (!this.getBody().equals(other.getBody())) {
		// return false;
		// }
		// if (this.getLastModified() != other.getLastModified()) {
		// return false;
		// }
		// if (this.getRobotsContent() == null) {
		// if (other.getRobotsContent() != null) {
		// return false;
		// }
		// } else if (!this.getRobotsContent().equals(this.getRobotsContent()))
		// {
		// return false;
		// }
		// if (this.getWordsMap() == null) {
		// if (other.getWordsMap() != null) {
		// return false;
		// }
		// } else if (!this.getWordsMap().equals(other.getWordsMap())) {
		// return false;
		// }
		return true;
	}
}
