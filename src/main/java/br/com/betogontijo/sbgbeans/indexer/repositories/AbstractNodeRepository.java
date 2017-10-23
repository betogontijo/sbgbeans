package br.com.betogontijo.sbgbeans.indexer.repositories;

import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public interface AbstractNodeRepository {

	void insertNode(Node node);

	int upsertNode(Node node);

	int updateNode(Node node);
}
