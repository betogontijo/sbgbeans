package br.com.betogontijo.sbgbeans.crawler.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public interface SbgDocumentRepository extends MongoRepository<SbgDocument, String>, AbstractSbgDocumentRepository {

	SbgDocument findByUri(String uri);

	SbgDocument findById(int id);
}
