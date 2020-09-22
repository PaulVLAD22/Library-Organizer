package com.company.MenuChoices.BookCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class BookChecker {
    public static JFrame bookCheckerFrame;
    private static JTextField bookNameField;
    private static JTextField bookAuthorField;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addALabel("Enter Book Name:",pane);
        bookNameField=addATextField("Book Name",pane);
        addALabel("Enter Book's Author",pane);
        bookAuthorField=addATextField("Author",pane);
        addAButton("Submit",pane,openCheckResultMenu());

    }

    private static void addAButton(String text, Container container, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setMaximumSize(new Dimension(300,50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    private static void addALabel(String text, Container container) {
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(400,50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
    }
    private static JTextField addATextField(String text,Container container){// ADDS TO CONTAINER AND RETURNS IT
        JTextField textField = new JTextField();
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font("SansSerif",Font.CENTER_BASELINE,16));
        textField.setMaximumSize(new Dimension(400,50));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(textField);
        return textField;
    }

    private static ActionListener openCheckResultMenu(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookCheckResult.createAndShowGUI(bookNameField.getText(),bookAuthorField.getText());
            }
        };
        return actionListener;
    }



    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window
        bookCheckerFrame = new JFrame("BoxLayoutDemo");
        bookCheckerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookCheckerFrame.setPreferredSize(new Dimension(600,400));
        bookCheckerFrame.setMaximumSize(new Dimension(600,400));
        bookCheckerFrame.setMinimumSize(new Dimension(600,400));
        bookCheckerFrame.setTitle("Check for a Book");

        //Set up the content pane.
        addComponentsToPane(bookCheckerFrame.getContentPane());

        //Display the window.
        bookCheckerFrame.pack();
        bookCheckerFrame.setVisible(true);
    }


}
