package com.stan.words.repository;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class WordRepositoryTest {

	private WordRepository repository = new WordRepositoryImpl();

	@Test
	public void test_getWords() {
		Assert.assertEquals(10, repository.getWords(10).size());
	}

	@Test
	public void test_increasePractiseCount() {
		Assert.assertEquals(2, repository.increasePractiseCount(Arrays.asList("tackle", "strife")));
		Assert.assertEquals(0, repository.increasePractiseCount(null));
		Assert.assertEquals(0, repository.increasePractiseCount(Collections.emptyList()));
	}

	@Test
	public void test_increaseRememberCount() {
		Assert.assertEquals(2, repository.increaseRememberCount(Arrays.asList("selection", "senator")));
		Assert.assertEquals(0, repository.increaseRememberCount(null));
		Assert.assertEquals(0, repository.increaseRememberCount(Collections.emptyList()));
	}

	@Test
	public void test_saveWords() {
		Assert.assertEquals(2, repository.saveWords(Arrays.asList(
				new Word("stun", "打昏"),
				new Word("evacuate", "疏散"))));
	}
}
