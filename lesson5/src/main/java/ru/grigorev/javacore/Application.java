package ru.grigorev.javacore;

import java.util.Arrays;

/**
 * @author Dmitriy Grigorev
 */
public final class Application {
    private static final int size = 10_000_000;
    private static final int halfSize = size / 2;
    private static final float[] array = new float[size];

    static {
        Arrays.fill(array, 1);
    }

    public static void main(String[] args) {
        taskMethodSingleStream();
        taskMethodTwoStreams();
    }

    public static void taskMethodSingleStream() {
        final long millisBeforeCycle = System.currentTimeMillis();
        fillArrayWithSpecialValues(array);
        final long millisAfterCycle = System.currentTimeMillis();
        printMethodExecutionTime("Method #1", millisBeforeCycle, millisAfterCycle);
    }

    public static void taskMethodTwoStreams() {
        final long millisBeforeCycle = System.currentTimeMillis();

        final float[] a1 = new float[halfSize];
        final float[] a2 = new float[halfSize];

        System.arraycopy(array, 0, a1, 0, halfSize);
        System.arraycopy(array, halfSize, a2, 0, halfSize);

        final Thread thread1 = new Thread(() -> fillArrayWithSpecialValues(a1));
        final Thread thread2 = new Thread(() -> fillArrayWithSpecialValues(a2));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, array, 0, halfSize);
        System.arraycopy(a2, 0, array, halfSize, halfSize);

        final long millisAfterCycle = System.currentTimeMillis();
        printMethodExecutionTime("Method #2", millisBeforeCycle, millisAfterCycle);
    }

    public static void fillArrayWithSpecialValues(final float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void printMethodExecutionTime(final String methodName, final long timeBefore, final long timeAfter) {
        System.out.println(methodName + " completed its work in " + (timeAfter - timeBefore) + " ms");
    }
}
