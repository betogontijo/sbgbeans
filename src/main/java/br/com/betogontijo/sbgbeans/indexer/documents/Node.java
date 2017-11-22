package br.com.betogontijo.sbgbeans.indexer.documents;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
		// IntegratedIntCompressor iic = new IntegratedIntCompressor();
		// Set<int[]> newOccurencesList = new HashSet<int[]>();
		// for (int[] i : getInvertedList().values()) {
		// newOccurencesList.add(iic.compress(i));
		// }
		// setOccurrencesList(newOccurencesList);
			setCompressed(true);
	}

	public void uncompress() {
//		IntegratedIntCompressor iic = new IntegratedIntCompressor();
//		int[] docRefList = iic.uncompress(getDocRefList().stream().mapToInt(i -> i).toArray());
//		setDocRefList(Arrays.stream(docRefList).boxed().collect(Collectors.toSet()));
//		Set<int[]> newOccurrencesList = new HashSet<int[]>();
//		for (int[] i : getOccurrencesList()) {
//			newOccurrencesList.add(iic.uncompress(i));
//		}
//		setOccurrencesList(occurrencesList);
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