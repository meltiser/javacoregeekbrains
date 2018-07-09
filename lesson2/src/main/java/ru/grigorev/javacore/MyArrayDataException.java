package ru.grigorev.javacore;

/**
 * @author Dmitriy Grigorev
 */
public class MyArrayDataException extends Exception {
    private String address;

    public MyArrayDataException(Exception e, String address) {
        super(e.getMessage() + " in element with address " + address);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
