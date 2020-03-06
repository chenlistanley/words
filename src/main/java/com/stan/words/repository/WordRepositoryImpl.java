package com.stan.words.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.h2.jdbcx.JdbcDataSource;

public class WordRepositoryImpl implements WordRepository {

	private JdbcDataSource dataSource = new JdbcDataSource();

	{
		dataSource.setUrl("jdbc:h2:D:\\db\\h2db");
		dataSource.setUser("admin");
		dataSource.setPassword("admin");
	}

	@Override
	public List<Word> getWords(int size) {
		final String sql = "SELECT ENGLISH,CHINESE FROM WORD ORDER BY REMEMBER_COUNT ASC, UPDATE_DATE DESC, PRACTISE_COUNT DESC LIMIT ?";
		List<Word> list = new ArrayList<>();
		try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
			statement.setInt(1, size);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Word word = new Word();
				word.setEnglish(rs.getString("ENGLISH"));
				word.setChinese(rs.getString("CHINESE"));
				list.add(word);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public int increasePractiseCount(String[] words) {
		Objects.requireNonNull(words);
		int size = words.length;
		String[] val = new String[size];
		Arrays.fill(val, "?");
		String s = Arrays.stream(val).collect(Collectors.joining(","));
		final String sql = "UPDATE WORD SET PRACTISE_COUNT=PRACTISE_COUNT+1,UPDATE_DATE=CURRENT_TIMESTAMP WHERE ENGLISH in ("
				.concat(s).concat(")");
		try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
			for (int i = 0; i < size; i++) {
				statement.setString(i + 1, words[i]);
			}
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int increaseRememberCount(String[] words) {
		Objects.requireNonNull(words);
		int size = words.length;
		String[] val = new String[size];
		Arrays.fill(val, "?");
		String s = Arrays.stream(val).collect(Collectors.joining(","));
		String sql = "UPDATE WORD SET REMEMBER_COUNT=REMEMBER_COUNT+1,UPDATE_DATE=CURRENT_TIMESTAMP WHERE ENGLISH in ("
				.concat(s).concat(")");
		try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
			for (int i = 0; i < size; i++) {
				statement.setString(i + 1, words[i]);
			}
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
