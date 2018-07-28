package ru.grigorev.javacore;

/**
 * @author Dmitriy Grigorev
 */
public final class ChatCommands {
    public static final String COMMAND_AUTHOK = "/authok ";
    public static final String COMMAND_AUTH = "/auth ";
    public static final String COMMAND_END = "/end";
    public static final String COMMAND_PRIVATE = "/w ";
    public static final String COMMAND_CLIENTS = "/clients";
    public static final String COMMAND_SIGN_UP = "/signup ";
    public static final String SERVER_HAS_CLOSED_CONNECTION = "The server has closed connection";
    public static final String GREETINGS_AND_AUTHORIZE = "Welcome to chat! Please sign in or sign up" +
            "(use \"/auth login password\" or \"/signup nickname login password\")";
    public static final String NOT_AUTHORIZED = "You're are not authorized!";
    public static final String WARNING_TIMEOUT_KICK = "If you don't authorize in 2 minutes, you will be kicked";
    public static final String CLIENTS_IN_CHAT = "People in chat: ";
    public static final String ACCOUNT_IN_USE = "Account is in use";
    public static final String WRONG_LOGIN_OR_PASSWORD = "Wrong login or password!";
    public static final String LOGIN_IS_IN_USE = "Login is busy!";
    public static final String NICKNAME_IS_IN_USE = "Nickname is busy!";
    public static final String USER_IS_ABSENT = " is absent!";
    public static final String PRIVATE_FROM = "private message from ";
    public static final String PRIVATE_TO = "private message to ";
    public static final String ENTERED_THE_CHAT = " has entered the chat!";
    public static final String LEFT_THE_CHAT = " has left the chat";
    public static final String TRY_AGAIN = "Try again";
    public static final String ALLOWED_COMMANDS = "\"/w nick message\" - private messages\n"
            + "\"/clients\" - see people in chat online\n"
            + "\"/end\" - ends the session";

    private ChatCommands() {
    }
}
