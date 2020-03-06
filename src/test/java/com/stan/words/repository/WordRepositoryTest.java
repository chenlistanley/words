package com.stan.words.repository;

import org.junit.Assert;
import org.junit.Test;

public class WordRepositoryTest {

	private WordRepository repository = new WordRepositoryImpl();

	@Test
	public void test_getWords() {
		Assert.assertEquals(10, repository.getWords(10).size());
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
