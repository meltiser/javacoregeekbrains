package ru.grigorev.javacore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Grigorev
 * The second point of the hometask
 */
public class Phonebook {
    private static Map<String, String> phonebook = new HashMap<>();

    public static void main(String[] args) {
        add("Ivanov", "89111234567");
        add("Petrov", "+79991264567");
        add("Sidorov", "+79113454567");
        add("Ivanov", "89217534947");
        get("Petrov");
        get("Ivanov");
    }

    private static void get(String lastname) {
        for (Map.Entry<String, String> pair : phonebook.entrySet()) {
            if (pair.getValue().equals(lastname)) {
                System.out.println(String.format("%-8s : %s", pair.getValue(), pair.getKey()));
            }
        }
    }

    private static void add(String lastname, String telephoneNumber) {
        phonebook.put(telephoneNumber, lastname);
    }
}
