package br.com.betogontijo.sbgbeans.indexer.repositories;

import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public interface NodeRepository extends MongoRepository<Node, String>, AbstractNodeRepository {

	@SuppressWarnings("rawtypes")
	Node findByChildren(Map node);

}
