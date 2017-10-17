package br.com.betogontijo.sbgbeans.crawler.repositories;

public interface AbstractDomainRepository {

	int upsertDomain(String uri, byte[] robotsContent);

}
