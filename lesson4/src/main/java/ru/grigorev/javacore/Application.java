package ru.grigorev.javacore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dmitriy Grigorev
 */
public class Application extends JFrame implements ActionListener {
    private JPanel sendingPanel;
    private JTextField textField;
    private String defaultMessage = " Type your message here:";
    private JButton buttonSend;
    private JTextArea textArea;
    private JScrollPane textPanel;


    public Application() {
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 200, 600, 400);
        setLayout(new BorderLayout());

        sendingPanel = new JPanel();
        sendingPanel.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setText(defaultMessage);
        textField.setActionCommand(defaultMessage);

        buttonSend = new JButton("Send");

        sendingPanel.add(textField, BorderLayout.CENTER);
        sendingPanel.add(buttonSend, BorderLayout.EAST);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textPanel = new JScrollPane(textArea);

        add(textPanel, BorderLayout.CENTER);
        add(sendingPanel, BorderLayout.SOUTH);

        buttonSend.addActionListener(this);
        textField.addActionListener(this);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(defaultMessage)) {
                    textField.setText("");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // получаем действие от текстового поля или от кнопки
        if (e.getActionCommand().equals(defaultMessage) || e.getActionCommand().equals("Send")) {
            // если поле не пустое и не содержит сообщение по умолчанию, переносим в основное окно
            if (!textField.getText().equals("") && !textField.getText().equals(defaultMessage)) {
                textArea.append(": " + textField.getText() + "\n");
            }
            textField.setText("");
        }
    }

    public static void main(String[] args) {
        final Application app = new Application();
        app.setVisible(true);
    }
}
