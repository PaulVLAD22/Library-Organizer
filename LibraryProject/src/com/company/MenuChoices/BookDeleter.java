package com.company.MenuChoices;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BookDeleter {
    private static JFrame bookDeleteFrame;
    private static JTextField bookNameField;
    private static JTextField bookAuthorField;
    private static JTextField booksToDeleteField;
    private static JTextField subtractTypeOfBooks;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addALabel("Enter Book Name:",pane);
        bookNameField=addATextField("Book Name",pane);
        addALabel("Enter Book's Author",pane);
        bookAuthorField=addATextField("Author",pane);
        addALabel("What's the number of books you wish to subtract?",pane);
        booksToDeleteField=addATextField("Num of books to delete",pane);
        addALabel("Type of action: remove Available books/loaned Books/delete from db?",pane);
        addALabel("(Available/Loan/Delete)?",pane);
        subtractTypeOfBooks=addATextField("Available/Loan",pane);
        addAButton("Submit",pane,deleteBook());

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

    private static ActionListener deleteBook(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(bookNameField.getText().equals("")) && !(bookAuthorField.getText().equals(""))
                        && !(booksToDeleteField.getText().equals("")) && (subtractTypeOfBooks.getText().toUpperCase().equals("Available".toUpperCase())
                        || subtractTypeOfBooks.getText().toUpperCase().equals("Loan".toUpperCase())
                        || subtractTypeOfBooks.getText().toUpperCase().equals("delete".toUpperCase()))) {
                    try {
                        //1.Get a connection to database
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
                        //2. Create a statement
                        Statement myStmt = myConn.createStatement();
                        //3. Update availableBooks, booksonLoan or deleteBook

                        switch (subtractTypeOfBooks.getText().toUpperCase()) {
                            case ("AVAILABLE"):
                                myStmt.executeUpdate("update sql_library.books set numAvailableBooks=numAvailableBooks-" + booksToDeleteField.getText()
                                        + " where bookName='" + bookNameField.getText().toUpperCase() +
                                        "' and " + "bookAuthor='" + bookAuthorField.getText().toUpperCase() + "'");
                                break;
                            case ("LOAN"):
                                myStmt.executeUpdate("update sql_library.books set numBooksOnLoan=numBooksOnLoan-" + booksToDeleteField.getText()
                                        + "  where bookName='" + bookNameField.getText().toUpperCase() + "' and " +
                                        "bookAuthor='" + bookAuthorField.getText().toUpperCase() + "'");
                                break;
                            case ("DELETE"):
                                myStmt.executeUpdate("delete from sql_library.books where bookName='" + bookNameField.getText().toUpperCase()
                                        + "' and " + "bookAuthor='" + bookAuthorField.getText().toUpperCase() + "'");
                                break;
                            default:
                                break;

                        }
                        myConn.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    bookDeleteFrame.dispose();
                }
            }
        };
        return actionListener;
    }

    public static void createAndShowGUI() {
        //Create and set up the window
        bookDeleteFrame = new JFrame("BoxLayoutDemo");
        bookDeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookDeleteFrame.setPreferredSize(new Dimension(600,400));
        bookDeleteFrame.setMaximumSize(new Dimension(600,400));
        bookDeleteFrame.setMinimumSize(new Dimension(600,400));
        bookDeleteFrame.setTitle("Delete Book");

        //Set up the content pane.
        addComponentsToPane(bookDeleteFrame.getContentPane());

        //Display the window.
        bookDeleteFrame.pack();
        bookDeleteFrame.setVisible(true);
    }


}
