package br.com.betogontijo.sbgbeans.indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.betogontijo.sbgbeans.indexer.documents.InvertedList;
import br.com.betogontijo.sbgbeans.indexer.documents.Node;
import br.com.betogontijo.sbgbeans.indexer.repositories.NodeRepository;

@SpringBootApplication
public class SbgBeansApplication {
	public static void main(String[] args) {
		SpringApplication.run(SbgBeansApplication.class, args);
	}

	@Bean
	CommandLineRunner init(NodeRepository domainRepository) {
		return args -> {
			Node obj = domainRepository.findByPrefix("batata");
			System.out.println(obj);

			InvertedList invertedList = new InvertedList();
			List<Integer> docRefList = new ArrayList<Integer>();
			docRefList.add(1);
			invertedList.setDocRefList(docRefList);
			List<int[]> occurrencesList = new ArrayList<int[]>();
			occurrencesList.add(new int[1]);
			invertedList.setOccurrencesList(occurrencesList);

			int n = domainRepository.upsertNode("batata", invertedList, new HashMap<Character, Node>());
			System.out.println("Number of records updated : " + n);
		};

	}
}
