package br.com.betogontijo.sbgbeans.indexer.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BulkWriteResult;
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
	public int insertAllNodes(List<Node> nodes) {
		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.ORDERED, Node.class);
		for (Node node : nodes) {
			Query query = new Query(Criteria.where("word").is(node.getWord()));
			Update update = new Update();
			update.set("docRefList", node.getDocRefList());
			update.set("occurrencesList", node.getOccurrencesList());
			bulkOps.upsert(query, update);
		}
		BulkWriteResult result = bulkOps.execute();

		if (result != null)
			return result.getInsertedCount() + result.getModifiedCount();
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
