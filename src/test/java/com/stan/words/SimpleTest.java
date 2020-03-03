package com.stan.words;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class SimpleTest {
	
	@Test
	public void test() {
		String[] val = new String[3];
		Arrays.fill(val, "?");
		String s = Arrays.stream(val).collect(Collectors.joining(","));
		System.out.println(s);
	}

}
