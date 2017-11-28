package br.com.betogontijo.sbgbeans.indexer.documents;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import me.lemire.integercompression.differential.IntegratedIntCompressor;

@Document(collection = "node")
public class Node {

	@Indexed
	private String word;

	private Map<Integer, int[]> invertedList;

	private boolean isCompressed;

	public Node() {
		setInvertedList(new HashMap<Integer, int[]>());
		isCompressed = false;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void compress() {
		IntegratedIntCompressor iic = new IntegratedIntCompressor();
		for (Entry<Integer, int[]> entry : getInvertedList().entrySet()) {
			int[] value = entry.getValue();
			if (value.length > 1) {
				entry.setValue(iic.compress(value));
			}
		}
		setCompressed(true);
	}

	public void uncompress() {
		IntegratedIntCompressor iic = new IntegratedIntCompressor();
		for (Entry<Integer, int[]> entry : getInvertedList().entrySet()) {
			int[] value = entry.getValue();
			if (value.length > 1) {
				entry.setValue(iic.uncompress(value));
			}
		}
		setCompressed(false);
	}

	public boolean isCompressed() {
		return isCompressed;
	}

	public void setCompressed(boolean isCompressed) {
		this.isCompressed = isCompressed;
	}

	public Map<Integer, int[]> getInvertedList() {
		return invertedList;
	}

	public void setInvertedList(Map<Integer, int[]> invertedList) {
		this.invertedList = invertedList;
	}
}