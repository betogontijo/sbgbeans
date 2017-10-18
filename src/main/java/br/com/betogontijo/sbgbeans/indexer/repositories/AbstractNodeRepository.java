package br.com.betogontijo.sbgbeans.indexer.repositories;

import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public interface AbstractNodeRepository {

	int upsertNode(Node node);
}
