package ru.grigorev.javacore.server.service;

public interface AuthService {
    boolean isLoginFree(final String login);

    boolean isNickFree(final String nick);

    void addNewUser(final String nickname, final String login, final String password);

    String getNickByLoginPass(String login, String pass);
}
