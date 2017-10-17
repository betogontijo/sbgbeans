package br.com.betogontijo.sbgbeans.indexer.repositories;

import java.util.Map;

import br.com.betogontijo.sbgbeans.indexer.documents.InvertedList;
import br.com.betogontijo.sbgbeans.indexer.documents.Node;

public interface AbstractNodeRepository {

	int upsertNode(String prefix, InvertedList invertedList, Map<Character, Node> children);
}
