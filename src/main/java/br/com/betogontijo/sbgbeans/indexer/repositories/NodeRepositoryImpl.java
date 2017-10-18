package br.com.betogontijo.sbgbeans.indexer.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.indexer.documents.InvertedList;
import br.com.betogontijo.sbgbeans.indexer.documents.Node;
import me.lemire.integercompression.differential.IntegratedIntCompressor;

public class NodeRepositoryImpl implements AbstractNodeRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public int upsertNode(Node node) {
		Query query = new Query(Criteria.where("children").not());
		Update update = new Update();
		if (node.getInvertedList() != null) {
			update.set("invertedList", compress(node.getInvertedList()));
		}
		update.set("children", node.getChildren());
		WriteResult result = mongoTemplate.upsert(query, update, Node.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	public InvertedList compress(InvertedList invertedList) {
		IntegratedIntCompressor iic = new IntegratedIntCompressor();
		InvertedList tmpInvertedList = new InvertedList();
		int[] docRefList = iic.compress(invertedList.getDocRefList().stream().mapToInt(i -> i).toArray());
		List<int[]> occurrencesList = invertedList.getOccurrencesList();
		for (int i = 0; i < occurrencesList.size(); i++) {
			occurrencesList.set(i, iic.uncompress(occurrencesList.get(i)));
		}
		tmpInvertedList.setDocRefList(Arrays.stream(docRefList).boxed().collect(Collectors.toList()));
		tmpInvertedList.setOccurrencesList(occurrencesList);
		return tmpInvertedList;
	}

}
