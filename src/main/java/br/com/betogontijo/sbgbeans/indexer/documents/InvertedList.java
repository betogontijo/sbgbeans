package br.com.betogontijo.sbgbeans.indexer.documents;

import java.util.List;

public class InvertedList {

	private List<Integer> docRefList;
	private List<int[]> occurrencesList;

	public void addInvertedItem(Integer docRef, int[] occurrences) {
		getDocRefList().add(docRef);
		getOccurrencesList().add(occurrences);
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
}
