package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.crawler.documents.Domain;
import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public class DomainRepositoryImpl implements AbstractDomainRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertAllDomains(List<Domain> domains) {
		mongoTemplate.insert(domains);
	}

	@Override
	public void insertDomain(Domain domain) {
		mongoTemplate.insert(domain);
	}

	@Override
	public int updateDomain(Domain domain) {
		Query query = new Query(Criteria.where("uri").is(domain.getUri()));
		Update update = new Update();

		update.set("uri", domain.getUri());
		update.set("robotsContent", domain.getUri());

		WriteResult result = mongoTemplate.updateFirst(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

}
