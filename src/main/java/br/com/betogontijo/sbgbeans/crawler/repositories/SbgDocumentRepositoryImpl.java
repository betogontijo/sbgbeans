package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
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
	public int upsertDocument(String uri, Long lastModified, String body) {
		Query query = new Query(Criteria.where("uri").is(uri));
		Update update = new Update();

		update.set("uri", uri);
		update.set("lastModified", lastModified);
		update.set("wordsMap", createWordsMap(body));

		WriteResult result = mongoTemplate.upsert(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	public Map<String, int[]> createWordsMap(String body) {
		Map<String, int[]> wordsMap = new HashMap<String, int[]>();
		Scanner in = new Scanner(body);
		int pos = 0;
		while (in.hasNext()) {
			String word = normalize(in.next());
			if (!word.isEmpty()) {
				if (wordsMap.get(word) != null) {
					int[] positions = wordsMap.get(word);
					int[] newPositions = Arrays.copyOf(positions, positions.length + 1);
					newPositions[positions.length] = pos++;
				} else {
					int[] positions = new int[1];
					positions[0] = pos++;
					wordsMap.put(word, positions);
				}
			}
		}
		in.close();
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

}
