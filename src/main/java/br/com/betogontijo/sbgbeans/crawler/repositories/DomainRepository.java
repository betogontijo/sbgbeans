package br.com.betogontijo.sbgbeans.crawler.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.betogontijo.sbgbeans.crawler.documents.Domain;

public interface DomainRepository extends MongoRepository<Domain, String>, AbstractDomainRepository {

	Domain findByUri(String uri);

}
