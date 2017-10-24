package br.com.betogontijo.sbgbeans.indexer.repositories;

import java.util.List;

import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public interface AbstractNodeRepository {

	void insertNode(Node node);

	int insertAllNodes(List<Node> nodes);

	int updateNode(Node node);
}
