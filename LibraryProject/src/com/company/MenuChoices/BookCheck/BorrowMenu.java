package com.company.MenuChoices.BookCheck;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


// CHANGE THIS ENTIRELY THIS IS BOOKADDER

// ADD A FUNCTION THAT ADDS A LOAN OBJECT
// ALSO CHANGE THE BOOK BOOLEAN ATTRIBUTE onLoan to true
// also change the numberOfBooks of this book object (by 1)


public class BorrowMenu {
    private static String bookName;
    private static String authorName;
    private static int numOfBooks;

    private static JFrame borrowMenuFrame;
    private static JTextField borrowerNameField;
    private static JTextField loanDurationField;
    private static JTextField bookNameField;
    private static JTextField authorNameField;
    private static JTextField phoneNumberField;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addALablel("Enter Borrower Name:",pane);
        borrowerNameField=addATextField("Name ",pane,false);
        addALablel("Loan duration(months)",pane);
        loanDurationField=addATextField("3",pane,false);
        addALablel("Book Name",pane);
        bookNameField=addATextField(bookName,pane,true);
        addALablel("Author Name",pane);
        authorNameField=addATextField(authorName,pane,true);
        addALablel("Phone Number",pane);
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
        }
        textField.setFont(new Font("SansSerif",Font.CENTER_BASELINE,16));
        textField.setMaximumSize(new Dimension(400,50));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(textField);
        return textField;
    }

    private static ActionListener loanBook(){
        //subtract from the number of those type of books 1
        // AND ADD TO THE NUMBER OF ONLOANBOOKS 1
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

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
                            "bookName='"+bookNameField.getText().toUpperCase()+"'"+" and bookAuthor='"+authorNameField.getText().toUpperCase()+"'");

                    // ADD A NEW LOAN

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }





                borrowMenuFrame.dispose();
            }
        };
        return actionListener;
    }



    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
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


        //Set up the content pane.
        addComponentsToPane(borrowMenuFrame.getContentPane());

        //Display the window.
        borrowMenuFrame.pack();
        borrowMenuFrame.setVisible(true);
    }


}
