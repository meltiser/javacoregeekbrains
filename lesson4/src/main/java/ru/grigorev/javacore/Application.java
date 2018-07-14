package ru.grigorev.javacore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitriy Grigorev
 */
public class Application {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        numbers.forEach(e -> System.out.print(e + " "));
    }
}
