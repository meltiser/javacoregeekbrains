package ru.grigorev.javacore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Grigorev
 * The first point of hometask
 */
public class Application {
    private static final String[] WORDS_ARRAY = {
            "London", "is", "the", "capital", "of", "Great", "Britain",
            "make", "Great", "Britain", "great", "again",
            "Is", "London", "the", "capital"};

    public static void main(String[] args) {
        printUniqueWordsCount();
    }

    private static void printUniqueWordsCount() {
        Map<String, Integer> map = new HashMap<>();
        for (String s : WORDS_ARRAY) {
            String word = s.toLowerCase();
            if (map.containsKey(word)) map.put(word, map.get(word) + 1); //если содержит слово - увеличиваем счетчик
            else map.put(word, 1);
        }
        for (Map.Entry<String, Integer> mapEntry : map.entrySet()) {
            System.out.println(String.format("%-8s : %d", mapEntry.getKey(), mapEntry.getValue()));
        }
    }
}
