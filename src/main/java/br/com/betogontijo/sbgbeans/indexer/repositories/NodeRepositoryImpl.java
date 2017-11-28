package br.com.betogontijo.sbgbeans.indexer.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

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
	public int updateNode(Node node) {
		Query query = new Query(Criteria.where("word").is(node.getWord()));
		Update update = new Update();
		update.set("word", node.getWord());
		update.addToSet("invertedList").each(node.getInvertedList());
		update.set("isCompressed", node.isCompressed());

		WriteResult result = mongoTemplate.updateFirst(query, update, Node.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	@Override
	public int upsertNode(Node node) {
		Query query = new Query(Criteria.where("word").is(node.getWord()));
		Update update = new Update();
		update.set("word", node.getWord());
		for (Entry<Integer, int[]> entry : node.getInvertedList().entrySet()) {
			update.set("invertedList." + entry.getKey(), entry.getValue());
		}
		update.set("isCompressed", node.isCompressed());

		WriteResult result = mongoTemplate.upsert(query, update, Node.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	@Override
	public int insertAllNodes(List<Node> nodes) {
		List<Pair<Query, Update>> updates = new ArrayList<Pair<Query, Update>>();
		for (Node node : nodes) {
			Query query = new Query(Criteria.where("word").is(node.getWord()));
			Update update = new Update();
			update.set("word", node.getWord());
			update.set("docRefList", node.getInvertedList());
			update.set("isCompressed", node.isCompressed());
			updates.add(Pair.of(query, update));
		}
		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, Node.class);
		BulkWriteResult result = bulkOps.upsert(updates).execute();

		if (result != null)
			return result.getInsertedCount() + result.getModifiedCount();
		else
			return 0;
	}

	@Document
	class DocumentCount {
		private int count;

		public int getCount() {
			return count;
		}

		public DocumentCount setCount(int count) {
			this.count = count;
			return this;
		}
	}

	@Override
	public boolean saveDocumentsIndexed(int count) {
		try {
			mongoTemplate.insert(new DocumentCount().setCount(count));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int getCurrentDocumentsIndexed() {
		try {
			return mongoTemplate.findOne(new Query(), DocumentCount.class).getCount();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	// @Override
	// public int insertAllNodes(List<Node> nodes) {
	// int updates = 0;
	// for (Node node : nodes) {
	// updates += upsertNode(node);
	// }
	// return updates;
	// }

}
