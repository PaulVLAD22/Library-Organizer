package com.company.MenuChoices.BookCheck;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class BorrowMenu {
    private static Date today;
    private static String bookName;
    private static String authorName;
    private static int numOfBooks;

    private static JFrame borrowMenuFrame;
    private static JTextField customerNameField;
    private static JTextField loanDurationField;
    private static JTextField bookNameField;
    private static JTextField authorNameField;
    private static JTextField phoneNumberField;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addALablel("Enter Customer Name:",pane);
        customerNameField=addATextField("Name ",pane,false);
        addALablel("Loan duration(months)",pane);
        loanDurationField=addATextField("3",pane,false);
        addALablel("Book Name",pane);
        bookNameField=addATextField(bookName,pane,true);
        addALablel("Author Name",pane);
        authorNameField=addATextField(authorName,pane,true);
        addALablel("Customer Phone Number",pane);
        phoneNumberField=addATextField("0727587574",pane,false);
        addAButton("Submit",pane,loanBook());




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
    private static JTextField addATextField(String text,Container container,boolean writeYourself){
        JTextField textField = new JTextField();
        textField.setForeground(Color.BLACK);
        if (writeYourself){
            textField.setText(text);
            textField.setEditable(false);
        }
        textField.setFont(new Font("SansSerif",Font.CENTER_BASELINE,16));
        textField.setMaximumSize(new Dimension(400,50));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(textField);
        return textField;
    }

    private static ActionListener loanBook(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!customerNameField.getText().equals("")&&
                isNumeric(loanDurationField.getText())&&
                !phoneNumberField.getText().equals("")) {

                    try {
                        //1.Get a connection to database
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
                        //2. Create a statement
                        Statement myStmt = myConn.createStatement();
                        //3.
                        //FIRSTLY SWITCH BOOKS VALUES
                        myStmt.executeUpdate("update sql_library.books \n" +
                                "set numBooksOnLoan=numBooksOnLoan+1,\n" +
                                "numAvailableBooks=numAvailableBooks-1\n" +
                                "where\n" +
                                "bookName='" + bookNameField.getText().toUpperCase() + "'" + " and bookAuthor='" + authorNameField.getText().toUpperCase() + "'");
                        myConn.close();
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                    try {
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
                        Statement myStmt = myConn.createStatement();


                        Calendar returnDate = Calendar.getInstance();
                        returnDate.add(Calendar.MONTH, Integer.parseInt(loanDurationField.getText()));
                        java.sql.Date aux = new java.sql.Date(returnDate.getTimeInMillis());
                        String dateOfReturn = aux.toString();
                        // calculated the dateOfReturn

                        // ADD A NEW LOAN

                        myStmt.executeUpdate("insert into sql_library.loans \n" +
                                "values ('" + bookNameField.getText().toUpperCase() + "','" +
                                authorNameField.getText().toUpperCase() + "','" +
                                dateOfReturn + "','" + customerNameField.getText().toUpperCase() +
                                "','" + phoneNumberField.getText() + "')");

                        myConn.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }





                borrowMenuFrame.dispose();
            }
        };
        return actionListener;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static void createAndShowGUI(String bookName,String authorName,int numOfBooks) {
        //Create and set up the window
        borrowMenuFrame = new JFrame("BoxLayoutDemo");
        borrowMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        borrowMenuFrame.setPreferredSize(new Dimension(600,400));
        borrowMenuFrame.setMaximumSize(new Dimension(600,400));
        borrowMenuFrame.setMinimumSize(new Dimension(600,400));
        borrowMenuFrame.setTitle("Loan a Book");

        BorrowMenu.bookName=bookName;
        BorrowMenu.authorName=authorName;
        BorrowMenu.numOfBooks=numOfBooks;

        today = Calendar.getInstance().getTime();
        System.out.println(today);


        //Set up the content pane.
        addComponentsToPane(borrowMenuFrame.getContentPane());

        //Display the window.
        borrowMenuFrame.pack();
        borrowMenuFrame.setVisible(true);
    }


}
