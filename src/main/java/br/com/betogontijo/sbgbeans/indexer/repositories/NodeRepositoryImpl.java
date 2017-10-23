package br.com.betogontijo.sbgbeans.indexer.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public class NodeRepositoryImpl implements AbstractNodeRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertNode(Node node) {
		mongoTemplate.insert(node);
	}

	@Override
	public int upsertNode(Node node) {
		Query query = new Query(Criteria.where("word").is(node.getWord()));
		Update update = new Update();
		update.set("docRefList", node.getDocRefList());
		update.set("occurrencesList", node.getOccurrencesList());
		WriteResult result = mongoTemplate.upsert(query, update, Node.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	@Override
	public int updateNode(Node node) {
		Query query = new Query(Criteria.where("word").is(node.getWord()));
		Update update = new Update();
		update.set("docRefList", node.getDocRefList());
		update.set("occurrencesList", node.getOccurrencesList());

		WriteResult result = mongoTemplate.updateFirst(query, update, Node.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

}
