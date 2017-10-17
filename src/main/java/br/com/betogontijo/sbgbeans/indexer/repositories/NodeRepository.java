package br.com.betogontijo.sbgbeans.indexer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public interface NodeRepository extends MongoRepository<Node, String>, AbstractNodeRepository {
	
	Node findByPrefix(String node);
	
}
