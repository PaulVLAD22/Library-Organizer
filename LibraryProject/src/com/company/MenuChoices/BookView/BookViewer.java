package com.company.MenuChoices.BookView;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookViewer {
    private static JFrame bookViewerFrame;
    private static JTable bookTable;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        String [] columnNames={"BookName","AuthorName","Category","BooksAvailable","BooksOnLoan"};

        bookTable = new JTable(getBooks(),columnNames);
        bookTable.setDefaultEditor(Object.class,null);
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(600);
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(600);
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(200);



        bookTable.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));


        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(bookTable);
        bookViewerFrame.add(sp);



    }

    public static String [] [] getBooks(){
        String [] [] data=new String [10000] [5];
        int indexRow=0;
        String bookName;
        String bookAuthor;
        String bookCategory;
        String numAvailableBooks;
        String numBooksOnLoan;
        try {
            //1.Get a connection to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
            //2. Create a statement
            Statement myStmt = myConn.createStatement();
            //3. Take info a about all books
            ResultSet myRs = myStmt.executeQuery("select * from books");
            //4. store data in a String matrix
            while (myRs.next()) {
                bookName=myRs.getString("bookName");
                bookAuthor=myRs.getString("bookAuthor");
                bookCategory=myRs.getString("bookCategory");
                numAvailableBooks=myRs.getString("numAvailableBooks");
                numBooksOnLoan=myRs.getString("numBooksOnLoan");
                String [] partialRow ={bookName,bookAuthor,bookCategory,numAvailableBooks,numBooksOnLoan};
                data[indexRow]=partialRow;
                indexRow++;

            }
            myConn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }


    public static void createAndShowGUI() {
        //Create and set up the window
        bookViewerFrame = new JFrame("BoxLayoutDemo");
        bookViewerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookViewerFrame.setTitle("View Loans");
        bookViewerFrame.setPreferredSize(new Dimension(1400,768));
        bookViewerFrame.setMaximumSize(new Dimension(1400,768));
        bookViewerFrame.setMinimumSize(new Dimension(1400,768));



        //Set up the content pane.
        addComponentsToPane(bookViewerFrame.getContentPane());

        //Display the window.
        bookViewerFrame.pack();
        bookViewerFrame.setVisible(true);
    }


}
