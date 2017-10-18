package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.util.List;

import br.com.betogontijo.sbgbeans.crawler.documents.Domain;

public interface AbstractDomainRepository {

	void insertAllDomains(List<Domain> domain);

	void insertDomain(Domain domain);

	int updateDomain(Domain domain);

}
