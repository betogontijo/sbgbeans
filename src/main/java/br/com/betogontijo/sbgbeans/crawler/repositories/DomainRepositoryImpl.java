package br.com.betogontijo.sbgbeans.crawler.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public class DomainRepositoryImpl implements AbstractDomainRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public int upsertDomain(String uri, byte[] robotsContent) {
		Query query = new Query(Criteria.where("uri").is(uri));
		Update update = new Update();

		update.set("uri", uri);
		update.set("robotsContent", robotsContent);

		WriteResult result = mongoTemplate.upsert(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

}
