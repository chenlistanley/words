package com.stan.words.repository;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        String sql = "SELECT ENGLISH,CHINESE FROM WORD ORDER BY REMEMBER_COUNT ASC, UPDATE_DATE DESC, PRACTISE_COUNT DESC LIMIT ?";
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
    public int increasePractiseCount(List<String> words) {
        if (!Optional.ofNullable(words).filter(list -> !list.isEmpty()).isPresent()) {
            return 0;
        }
        int size = words.size();
        String[] val = new String[size];
        Arrays.fill(val, "?");
        String s = Arrays.stream(val).collect(Collectors.joining(","));
        String sql = String.format("UPDATE WORD SET PRACTISE_COUNT=PRACTISE_COUNT+1, UPDATE_DATE=CURRENT_TIMESTAMP WHERE ENGLISH in (%s)", s);
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < size; i++) {
                statement.setString(i + 1, words.get(i));
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int increaseRememberCount(List<String> words) {
        if (!Optional.ofNullable(words).map(list -> !list.isEmpty()).isPresent()) {
            return 0;
        }
        int size = words.size();
        String[] val = new String[size];
        Arrays.fill(val, "?");
        String s = Arrays.stream(val).collect(Collectors.joining(","));
        String sql = String.format("UPDATE WORD SET REMEMBER_COUNT=REMEMBER_COUNT+1,UPDATE_DATE=CURRENT_TIMESTAMP WHERE ENGLISH in (%s)", s);
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < size; i++) {
                statement.setString(i + 1, words.get(i));
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int saveWords(List<Word> words) {
        return insertWords(words) + updateWords(words);
    }

    public int insertWords(List<Word> words) {
        if (!Optional.ofNullable(words).map(list -> !list.isEmpty()).isPresent()) {
            return 0;
        }
        String sql = "insert into word (english, chinese)  select ?, ? where not exists (select 1 from word where english = ?)";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
            for (int i = 0, size = words.size(); i < size; i++) {
                statement.setString(1, words.get(i).getEnglish());
                statement.setString(2, words.get(i).getChinese());
                statement.setString(3, words.get(i).getEnglish());
                statement.addBatch();
            }
            return Arrays.stream(statement.executeBatch()).sum();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateWords(List<Word> words) {
        if (!Optional.ofNullable(words).map(list -> !list.isEmpty()).isPresent()) {
            return 0;
        }
        String sql = "UPDATE WORD SET CHINESE=? WHERE ENGLISH=?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql)) {
            for (int i = 0, size = words.size(); i < size; i++) {
                statement.setString(1, words.get(i).getChinese());
                statement.setString(2, words.get(i).getEnglish());
                statement.addBatch();
            }
            return Arrays.stream(statement.executeBatch()).sum();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
