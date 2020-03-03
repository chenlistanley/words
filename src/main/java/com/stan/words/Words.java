package com.stan.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.stan.words.repository.WordRepository;
import com.stan.words.repository.WordRepositoryImpl;

public class Words {

	public static void main(String[] args) {
		int size = 50;
		System.out.print("Please enter practise size: ");
		try (Scanner scanner = new Scanner(System.in)) {
			if (scanner.hasNextInt()) {
				size = scanner.nextInt();
			}
			WordRepository repository = new WordRepositoryImpl();
			List<String> words = repository.getWords(size);
			List<String> practise = new ArrayList<>();
			List<String> remember = new ArrayList<>();
			for (int i = 0, max = words.size(); i < max; i++) {
				String word = words.get(i);
				System.out.print(String.format("%s ", word));
				while (scanner.hasNext()) {
					String s = scanner.next();
					if (word.equals(s))
						break;
				}
				practise.add(word);
				System.out.print("Remember Y:");
				if (scanner.hasNext()) {
					if ("Y".equalsIgnoreCase(scanner.next()))
						remember.add(word);
				}
			}
			repository.increasePractiseCount(practise.toArray(new String[0]));
			repository.increaseRememberCount(remember.toArray(new String[0]));
		}

	}

}
