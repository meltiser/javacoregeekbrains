package ru.grigorev.javacore.server.service;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private List<Entry> entries;

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("login1", "pass1", "nick1"));
        entries.add(new Entry("login2", "pass2", "nick2"));
        entries.add(new Entry("login3", "pass3", "nick3"));
    }

    @Override
    public String getNickByLoginPass(final String login, final String pass) {
        for (final Entry entryData : entries) {
            if (entryData.login.equals(login) && entryData.pass.equals(pass)) return entryData.nick;
        }
        return null;
    }

    @Override
    public boolean isNickFree(final String nick) {
        for (final Entry entryData : entries) {
            if (entryData.nick.equals(nick)) return false;
        }
        return true;
    }

    @Override
    public boolean isLoginFree(final String login) {
        for (final Entry entryData : entries) {
            if (entryData.login.equals(login)) return false;
        }
        return true;
    }

    @Override
    public void addNewUser(final String nickname, final String login, final String password) {
        entries.add(new Entry(login, password, nickname));
    }

    private class Entry {
        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
}
