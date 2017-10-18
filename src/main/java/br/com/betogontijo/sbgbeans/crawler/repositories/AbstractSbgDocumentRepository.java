package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.util.List;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public interface AbstractSbgDocumentRepository {

	void insertDocument(SbgDocument document);

	void insertAllDocuments(List<SbgDocument> documents);

	int updateDocument(SbgDocument document);
}
