package br.com.betogontijo.sbgbeans.indexer.documents;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import me.lemire.integercompression.differential.IntegratedIntCompressor;

@Document(collection = "node")
public class Node {

	@Indexed
	private String word;

	private Set<Integer> docRefList;

	private Set<int[]> occurrencesList;

	private boolean isCompressed;

	public Node() {
		docRefList = new HashSet<Integer>();
		occurrencesList = new HashSet<int[]>();
		isCompressed = false;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Set<Integer> getDocRefList() {
		return docRefList;
	}

	public void setDocRefList(Set<Integer> docRefList) {
		this.docRefList = docRefList;
	}

	public Set<int[]> getOccurrencesList() {
		return occurrencesList;
	}

	public void setOccurrencesList(Set<int[]> occurrencesList) {
		this.occurrencesList = occurrencesList;
	}

	public long size() {
		long totalSize = getDocRefList().size();
		for (int[] i : getOccurrencesList()) {
			totalSize += i.length;
		}
		return (word.length() * 8) + (totalSize * 32);
	}

	public void compress() {
		if (getDocRefList().size() > 2) {
			IntegratedIntCompressor iic = new IntegratedIntCompressor();
			int[] docRefList = iic.compress(getDocRefList().stream().mapToInt(i -> i).toArray());
			setDocRefList(Arrays.stream(docRefList).boxed().collect(Collectors.toSet()));
			Set<int[]> newOccurencesList = new HashSet<int[]>();
			for (int[] i : getOccurrencesList()) {
				newOccurencesList.add(iic.compress(i));
			}
			setOccurrencesList(newOccurencesList);
			setCompressed(true);
		}
	}

	public void uncompress() {
		IntegratedIntCompressor iic = new IntegratedIntCompressor();
		int[] docRefList = iic.uncompress(getDocRefList().stream().mapToInt(i -> i).toArray());
		setDocRefList(Arrays.stream(docRefList).boxed().collect(Collectors.toSet()));
		Set<int[]> newOccurrencesList = new HashSet<int[]>();
		for (int[] i : getOccurrencesList()) {
			newOccurrencesList.add(iic.uncompress(i));
		}
		setOccurrencesList(occurrencesList);
		setCompressed(false);
	}

	public boolean isCompressed() {
		return isCompressed;
	}

	public void setCompressed(boolean isCompressed) {
		this.isCompressed = isCompressed;
	}
}