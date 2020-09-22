package com.company.MenuChoices.BookAdders;

import com.company.Objects.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookAdder {
    private static JFrame bookAdderFrame;
    private static JTextField bookNameField;
    private static JTextField bookAuthorField;
    private static JTextField bookCategoryField;
    private static JTextField bookNumOfAvailableField;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addALablel("Enter Book Name:",pane);
        bookNameField=addATextField("Book Name",pane);
        addALablel("Enter Book's Author",pane);
        bookAuthorField=addATextField("Author",pane);
        addALablel("Enter Book Category",pane);
        bookCategoryField=addATextField("Category",pane);
        addALablel("Number of Books",pane);
        bookNumOfAvailableField=addATextField("Number",pane);
        addAButton("Submit",pane,addBookToDb());




    }

    private static void addAButton(String text, Container container, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setMaximumSize(new Dimension(300,50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    private static void addALablel(String text, Container container) {
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(400,50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
    }
    private static JTextField addATextField(String text,Container container){
        JTextField textField = new JTextField();
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font("SansSerif",Font.CENTER_BASELINE,16));
        textField.setMaximumSize(new Dimension(400,50));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(textField);
        return textField;
    }

    private static ActionListener addBookToDb(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(bookNameField.getText().equals("")) && !(bookAuthorField.getText().equals(""))
                        && !(bookCategoryField.getText().equals("") && !(bookNumOfAvailableField.getText().equals(""))) ){
                    try {
                        Book newBook=new Book(bookNameField.getText(), bookAuthorField.getText(), bookCategoryField.getText(), Integer.parseInt(bookNumOfAvailableField.getText()));
                        newBook.addToDb();
                    }catch (Exception exc){
                        exc.printStackTrace();
                    }



                    //closing the window
                    bookAdderFrame.dispose();

                }

            }
        };
        return actionListener;
    }

    public static void createAndShowGUI() {
        //Create and set up the window
        bookAdderFrame = new JFrame("BoxLayoutDemo");
        bookAdderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookAdderFrame.setPreferredSize(new Dimension(600,400));
        bookAdderFrame.setMaximumSize(new Dimension(600,400));
        bookAdderFrame.setMinimumSize(new Dimension(600,400));
        bookAdderFrame.setTitle("Add a new book");

        //Set up the content pane.
        addComponentsToPane(bookAdderFrame.getContentPane());

        //Display the window.
        bookAdderFrame.pack();
        bookAdderFrame.setVisible(true);
    }


}
