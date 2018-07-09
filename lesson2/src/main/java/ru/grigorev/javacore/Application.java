package ru.grigorev.javacore;

/**
 * @author Dmitriy Grigorev
 */
public class Application {
    private final static String[][] TEST_ARRAY = {
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "2", "3", "4"}
    };

    public static void main(String[] args) {
        try {
            System.out.println(myMethod(TEST_ARRAY));
        } catch (MyArraySizeException e) {
            System.err.println("Wrong array size!");
        } catch (MyArrayDataException e) {
            System.err.println("Exception in element with address " + e.getAddress());
        }
    }

    public static int myMethod(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr.length != 4) throw new MyArraySizeException();
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            if (arr[i].length != 4) throw new MyArraySizeException();
            for (int j = 0; j < 4; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    String address = String.format("[%d, %d]", i, j);
                    throw new MyArrayDataException(e, address);
                }
            }
        }
        return sum;
    }
}