package br.com.betogontijo.sbgbeans.crawler.repositories;

public interface AbstractSbgDocumentRepository {

	int upsertDocument(String uri, Long lastModified, String body);

}
