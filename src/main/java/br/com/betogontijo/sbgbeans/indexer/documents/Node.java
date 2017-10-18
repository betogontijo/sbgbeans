package br.com.betogontijo.sbgbeans.indexer.documents;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "node")
public class Node {

	@Id
	@Indexed
	private int id;

	private Map<Character, Node> children;

	private InvertedList invertedList;

	public Map<Character, Node> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, Node> children) {
		this.children = children;
	}

	public InvertedList getInvertedList() {
		return invertedList;
	}

	public void setInvertedList(InvertedList invertedList) {
		this.invertedList = invertedList;
	}
}