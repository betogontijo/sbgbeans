package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;

public class SbgDocumentRepositoryImpl implements AbstractSbgDocumentRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertDocument(SbgDocument document) {
		document.setWordsMap(createWordsMap(document.getBody()));
		mongoTemplate.insert(document);
	}

	@Override
	public int updateDocument(SbgDocument document) {
		Query query = new Query(Criteria.where("uri").is(document.getUri()));
		Update update = new Update();

		update.set("id", document.getId());
		update.set("uri", document.getUri());
		update.set("lastModified", document.getLastModified());
		update.set("wordsMap", createWordsMap(document.getBody()));

		WriteResult result = mongoTemplate.updateFirst(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	public Map<String, int[]> createWordsMap(String body) {
		Map<String, int[]> wordsMap = new HashMap<String, int[]>();
		if (body != null && !body.isEmpty()) {
			Scanner in = new Scanner(body);
			int pos = 0;
			while (in.hasNext()) {
				String word = normalize(in.next());
				if (!word.isEmpty()) {
					int[] positions;
					if (wordsMap.get(word) != null) {
						positions = wordsMap.get(word);
						positions = Arrays.copyOf(positions, positions.length + 1);
						positions[positions.length - 1] = pos++;
					} else {
						positions = new int[1];
						positions[0] = pos++;
					}
					wordsMap.put(word, positions);
				}
			}
			in.close();
		}
		return wordsMap;
	}

	public static String normalize(String string) {
		if (string != null) {
			string = string.toLowerCase();
			string = Normalizer.normalize(string, Normalizer.Form.NFD);
			string = string.replaceAll("[^a-z0-9\\s]", "");
		}
		return string;
	}

	@Override
	public int upsertDocument(SbgDocument document) {
		Query query = new Query(Criteria.where("uri").is(document.getUri()));
		Update update = new Update();

		update.set("id", document.getId());
		update.set("uri", document.getUri());
		update.set("lastModified", document.getLastModified());
		update.set("wordsMap", createWordsMap(document.getBody()));

		WriteResult result = mongoTemplate.upsert(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	@Override
	public List<SbgDocument> findByWord(String word) {
		Query query = new Query(Criteria.where("wordsMap." + word).exists(true));
		return mongoTemplate.find(query, SbgDocument.class);
	}

}
