package br.com.betogontijo.sbgbeans.crawler.repositories;

import br.com.betogontijo.sbgbeans.crawler.documents.Domain;

public interface AbstractDomainRepository {

	void insertDomain(Domain domain);

	int updateDomain(Domain domain);

}
