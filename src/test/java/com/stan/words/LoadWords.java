package com.stan.words;

import com.stan.words.repository.Word;
import com.stan.words.repository.WordRepository;
import com.stan.words.repository.WordRepositoryImpl;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class LoadWords {

    private static final String DATA = "data/word.txt";

    private WordRepository wordRepository = new WordRepositoryImpl();

    @Test
    public void load() throws IOException {
        List<Word> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(DATA), Charset.forName("utf-8")))) {
            String s = null;
            while ((s = reader.readLine()) != null) {
                int a = s.indexOf(" ");
                words.add(new Word(s.substring(0, a).trim(), s.substring(a).trim()));
            }
        }
        wordRepository.saveWords(words);
    }

}