package br.com.betogontijo.sbgbeans.crawler.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.crawler.documents.Domain;

public class DomainRepositoryImpl implements AbstractDomainRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertDomain(Domain domain) {
		mongoTemplate.insert(domain);
	}

	@Override
	public int updateDomain(Domain domain) {
		Query query = new Query(Criteria.where("uri").is(domain.getUri()));
		Update update = new Update();

		update.set("uri", domain.getUri());
		update.set("robotsContent", domain.getRobotsContent());

		WriteResult result = mongoTemplate.updateFirst(query, update, Domain.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

}
