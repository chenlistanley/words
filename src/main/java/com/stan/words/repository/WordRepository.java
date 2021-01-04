package com.stan.words.repository;

import java.util.List;

public interface WordRepository {

	List<Word> getWords(int size);

	int increasePractiseCount(List<String> words);

	int increaseRememberCount(List<String> words);

	int saveWords(List<Word> words);

}
