package ru.grigorev.javacore.client;

import ru.grigorev.javacore.ChatCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JPanel bottomPanel;
    private JButton buttonSend;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(final String address, final int port) {
        setBounds(600, 300, 500, 500);
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        bottomPanel = new JPanel(new BorderLayout());
        buttonSend = new JButton("SEND");
        textField = new JTextField();

        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(buttonSend, BorderLayout.EAST);
        bottomPanel.add(textField, BorderLayout.CENTER);

        buttonSend.addActionListener(e -> {
            if (!textField.getText().trim().isEmpty()) {
                sendMessage();
                textField.grabFocus();
            }
        });
        textField.addActionListener(e -> sendMessage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF(ChatCommands.COMMAND_END);
                    out.flush();
                    closeAll();
                } catch (IOException exc) {
                    exc.printStackTrace();
                } finally {
                    setVisible(false);
                    dispose();
                }
            }
        });

        try {
            socket = new Socket(address, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        final Thread clientThread = new Thread(() -> {
            try {
                while (true) {
                    final String str = in.readUTF();
                    if (ChatCommands.COMMAND_END.equals(str)) {
                        break;
                    }
                    textArea.append(str + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientThread.setDaemon(true);
        clientThread.start();
        setVisible(true);
    }

    public void sendMessage() {
        try {
            out.writeUTF(textField.getText());
            textField.setText("");
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

    public void closeAll() throws IOException {
        socket.close();
        out.close();
        in.close();
    }
}
