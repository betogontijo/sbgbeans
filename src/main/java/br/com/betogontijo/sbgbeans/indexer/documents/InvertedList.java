package br.com.betogontijo.sbgbeans.indexer.documents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.lemire.integercompression.differential.IntegratedIntCompressor;

public class InvertedList {

	private List<Integer> docRefList;
	private List<int[]> occurrencesList;
	private boolean isCompressed;

	InvertedList() {
		setDocRefList(new ArrayList<Integer>());
		setOccurrencesList(new ArrayList<int[]>());
		setCompressed(false);
	}

	public void addInvertedItem(Integer docRef, int[] occurrences) {
		if (!getDocRefList().contains(docRef)) {
			getDocRefList().add(docRef);
			getOccurrencesList().add(occurrences);
		}
	}

	public void addInvertedItems(InvertedList invertedList) {
		if (isCompressed()) {
			uncompress();
		}
		List<Integer> docRef = invertedList.getDocRefList();
		List<int[]> occurrences = invertedList.getOccurrencesList();
		if (docRef.size() != occurrences.size()) {
			throw new RuntimeException("Invalid inverted item");
		}
		for (int i = 0; i < docRef.size(); i++) {
			if (!getDocRefList().contains(docRef.get(i))) {
				getDocRefList().add(docRef.get(i));
				getOccurrencesList().add(occurrences.get(i));
			}
		}
	}

	public List<Integer> getDocRefList() {
		return docRefList;
	}

	public void setDocRefList(List<Integer> docRefList) {
		this.docRefList = docRefList;
	}

	public List<int[]> getOccurrencesList() {
		return occurrencesList;
	}

	public void setOccurrencesList(List<int[]> occurrencesList) {
		this.occurrencesList = occurrencesList;
	}

	public long size() {
		long totalSize = getDocRefList().size();
		for (int[] i : getOccurrencesList()) {
			totalSize += i.length;
		}
		return totalSize * 32;
	}

	public void compress() {
		if (getDocRefList().size() > 2) {
			IntegratedIntCompressor iic = new IntegratedIntCompressor();
			int[] docRefList = iic.compress(getDocRefList().stream().mapToInt(i -> i).toArray());
			setDocRefList(Arrays.stream(docRefList).boxed().collect(Collectors.toList()));
			List<int[]> newOccurencesList = new ArrayList<int[]>();
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
		setDocRefList(Arrays.stream(docRefList).boxed().collect(Collectors.toList()));
		List<int[]> newOccurrencesList = new ArrayList<int[]>();
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
