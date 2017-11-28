package br.com.betogontijo.sbgbeans.crawler.repositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import br.com.betogontijo.sbgbeans.crawler.documents.SbgDocument;
import br.com.betogontijo.sbgbeans.utils.WordUtils;

public class SbgDocumentRepositoryImpl implements AbstractSbgDocumentRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertDocument(SbgDocument document) {
		document.setWordsList(createWordsList(document.getBody()));
		mongoTemplate.insert(document);
	}

	@Override
	public int updateDocument(SbgDocument document) {
		Query query = new Query(Criteria.where("uri").is(document.getUri()));
		Update update = new Update();

		update.set("id", document.getId());
		update.set("title", document.getTitle());
		update.set("uri", document.getUri());
		update.set("lastModified", document.getLastModified());
		update.set("wordsList", createWordsList(document.getBody()));

		WriteResult result = mongoTemplate.updateFirst(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	public List<String> createWordsList(String body) {
		List<String> wordsList = new ArrayList<String>();
		if (body != null && !body.isEmpty()) {
			Scanner in = new Scanner(body);
			while (in.hasNext()) {
				String word = WordUtils.normalize(in.next());
				if (!word.isEmpty()) {
					wordsList.add(word);
				}
			}
			in.close();
		}
		return wordsList;
	}

	@Override
	public int upsertDocument(SbgDocument document) {
		Query query = new Query(Criteria.where("uri").is(document.getUri()));
		Update update = new Update();

		update.set("id", document.getId());
		update.set("uri", document.getUri());
		update.set("title", document.getTitle());
		update.set("lastModified", document.getLastModified());
		update.set("wordsList", createWordsList(document.getBody()));

		WriteResult result = mongoTemplate.upsert(query, update, SbgDocument.class);

		if (result != null)
			return result.getN();
		else
			return 0;
	}

	@Override
	public List<SbgDocument> findByWord(String word) {
		Query query = new Query(Criteria.where("wordsList").in(word));
		return mongoTemplate.find(query, SbgDocument.class);
	}

	@Override
	public Iterator<SbgDocument> iterator(int skip) {
		return mongoTemplate.stream(new Query().noCursorTimeout().skip(skip), SbgDocument.class);
	}

}
