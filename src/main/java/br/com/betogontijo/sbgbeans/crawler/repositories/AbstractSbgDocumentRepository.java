package br.com.betogontijo.sbgbeans.crawler.repositories;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public interface AbstractSbgDocumentRepository {

	void insertDocument(SbgDocument document);

	int updateDocument(SbgDocument document);

	int upsertDocument(SbgDocument document);
}
