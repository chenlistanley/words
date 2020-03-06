package com.stan.words.repository;

import java.util.List;

public interface WordRepository {

	List<Word> getWords(int size);

	int increasePractiseCount(String[] words);

	int increaseRememberCount(String[] words);

}
