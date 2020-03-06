package com.stan.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.stan.words.repository.Word;
import com.stan.words.repository.WordRepository;
import com.stan.words.repository.WordRepositoryImpl;

public class Words {

	public static void main(String[] args) {
		int size = 50;
		System.out.print("How many words to practise today? ");
		try (Scanner scanner = new Scanner(System.in)) {
			long startTime = System.nanoTime();
			if (scanner.hasNextInt()) {
				size = scanner.nextInt();
			}
			WordRepository repository = new WordRepositoryImpl();
			List<Word> words = repository.getWords(size);
			List<String> practise = new ArrayList<>();
			List<String> remember = new ArrayList<>();
			for (int i = 0, max = words.size(); i < max; i++) {
				Word word = words.get(i);
				String english = word.getEnglish();
				System.out.print(String.format("%s ", english));
				while (scanner.hasNext()) {
					String s = scanner.next();
					if (english.equals(s))
						break;
				}
				practise.add(english);
				System.out.println(word.getChinese());
				System.out.print("Remember (y): ");
				if (scanner.hasNext()) {
					if ("Y".equalsIgnoreCase(scanner.next()))
						remember.add(english);
				}
			}
			repository.increasePractiseCount(practise.toArray(new String[0]));
			repository.increaseRememberCount(remember.toArray(new String[0]));
			System.out.println(String.format("%s words practised", practise.size()));
			System.out.println(String.format("%s words remembered", remember.size()));
			long duration = TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - startTime);
			System.out.print(String.format("Used %s mins", duration));
		}

	}

}
