package com.stan.words.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;



public class WordRepositoryTest {

	private WordRepository repository = new WordRepositoryImpl();

	@Test
	public void test_getWords() {
		List<String> words = repository.getWords(10);
		Assert.assertEquals(10, words.size());
	}

	@Test
	public void test_increasePractiseCount() {
		Assert.assertEquals(2, repository.increasePractiseCount(new String[] { "tackle", "strife" }));
	}

	@Test
	public void test_increaseRememberCount() {
		Assert.assertEquals(2, repository.increaseRememberCount(new String[] { "selection", "senator" }));
	}
}
