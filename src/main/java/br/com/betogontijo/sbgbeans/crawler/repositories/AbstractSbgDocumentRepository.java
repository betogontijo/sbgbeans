package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.util.List;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public interface AbstractSbgDocumentRepository {

	void insertDocument(SbgDocument document);

	int updateDocument(SbgDocument document);

	int upsertDocument(SbgDocument document);

	List<SbgDocument> findByWord(String word);
}
