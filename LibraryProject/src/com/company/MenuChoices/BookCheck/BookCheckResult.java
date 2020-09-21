package com.company.MenuChoices.BookCheck;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookCheckResult {
    private static JFrame bookCheckResultFrame;
    private static String bookName;
    private static String authorName;
    private static int numOfAvailableBooks;
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        int [] bookInfo = bookIsInStock(bookName,authorName);

        numOfAvailableBooks=bookInfo[0];
        int numOfLoanedBooks = bookInfo[1];

        if (numOfAvailableBooks!=0){
            addALabel("We do have it!",pane);
            addALabel("Number of Available Books : "+numOfAvailableBooks,pane);
            addALabel("Number of Loaned Books : "+numOfLoanedBooks,pane);
            addALabel("Do you want to loan one?",pane);

            addAButton("Yes",pane,openBorrowMenu());
            addAButton("No", pane, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bookCheckResultFrame.dispose();
                    BookChecker.bookCheckerFrame.dispose();
                }
            });
        }
        else{
            addALabel("We don't have it",pane);
            addALabel("Number of Available Books : "+numOfAvailableBooks,pane);
            addALabel("Number of Loaned Books : "+numOfLoanedBooks,pane);
        }



    }

    private static void addAButton(String text, Container container, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setMaximumSize(new Dimension(100,50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    private static void addALabel(String text, Container container) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif",Font.CENTER_BASELINE,20));
        label.setMaximumSize(new Dimension(400,50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(label);
    }


    private static int [] bookIsInStock(String bookName, String authorName){
        int [] bookInfo = new int[2];
        bookInfo[0]=0;
        bookInfo[1]=0;
        // values in order to know if we don't have the specific book in our db
        if (!bookName.equals("") && !authorName.equals("")) {
            try {
                //1.Get a connection to database
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
                //2. Create a statement
                Statement myStmt = myConn.createStatement();
                //3. Get information about the certain book
                ResultSet myRs = myStmt.executeQuery("select * from books where bookName='" + bookName.toUpperCase() + "' and bookAuthor='" + authorName.toUpperCase() + "'");
                //4. Process the result set
                while (myRs.next()) {
                    bookInfo[0] = Integer.parseInt(myRs.getString("numAvailableBooks"));
                    bookInfo[1] = Integer.parseInt(myRs.getString("numBooksOnLoan"));
                }
                myConn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return bookInfo; //returns the number of books available
    }

    private static ActionListener openBorrowMenu(){
        ActionListener actionListener=  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookChecker.bookCheckerFrame.dispose();
                BookCheckResult.bookCheckResultFrame.dispose();
                BorrowMenu.createAndShowGUI(bookName,authorName,numOfAvailableBooks);

            }
        };
        return actionListener;

    }



    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI(String bookName, String authorName) {
        //Create and set up the window
        bookCheckResultFrame = new JFrame("BoxLayoutDemo");
        bookCheckResultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookCheckResultFrame.setPreferredSize(new Dimension(450,300));
        bookCheckResultFrame.setMaximumSize(new Dimension(450,300));
        bookCheckResultFrame.setMinimumSize(new Dimension(450,300));
        bookCheckResultFrame.setTitle("Add a new book");

        // assign values to attributes
        BookCheckResult.bookName=bookName;
        BookCheckResult.authorName=authorName;

        //Set up the content pane.
        addComponentsToPane(bookCheckResultFrame.getContentPane());

        //Display the window.
        bookCheckResultFrame.pack();
        bookCheckResultFrame.setVisible(true);
    }


}
