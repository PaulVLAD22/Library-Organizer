package com.company.MenuChoices.LoanView;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;

public class LoanViewer {
    private static JFrame bookViewerFrame;
    private static JTable loanTable;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        String [] columnNames={"BookName","AuthorName","DateOfEnd","CustomerName","CustomerPhone"};

        loanTable = new JTable(getBooks(),columnNames);
        loanTable.setDefaultEditor(Object.class,null);
        loanTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        loanTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        loanTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        loanTable.getColumnModel().getColumn(3).setPreferredWidth(400);
        loanTable.getColumnModel().getColumn(4).setPreferredWidth(400);



        loanTable.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));


        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(loanTable);
        bookViewerFrame.add(sp);
        // Frame Size



    }

    public static String [] [] getBooks(){
        String [] [] data=new String [10000] [5];
        int indexRow=0;
        String bookName;
        String bookAuthor;
        String dateOfEnd;
        String customerName;
        String customerPhone;
        try {
            //1.Get a connection to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
            //2. Create a statement
            Statement myStmt = myConn.createStatement();
            //3. Take info a about all books
            ResultSet myRs = myStmt.executeQuery("select * from loans");
            //4. store data in a String matrix
            while (myRs.next()) {
                bookName=myRs.getString("bookName");
                bookAuthor=myRs.getString("authorName");
                dateOfEnd=myRs.getString("dateOfLoanEnd");
                customerName=myRs.getString("borrowerName");
                customerPhone=myRs.getString("borrowerPhoneNum");
                String [] partialRow ={bookName,bookAuthor,dateOfEnd,customerName,customerPhone};
                data[indexRow]=partialRow;
                indexRow++;

            }
            myConn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(data);
        return data;
    }




    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window
        bookViewerFrame = new JFrame("BoxLayoutDemo");
        bookViewerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookViewerFrame.setTitle("View Books");
        bookViewerFrame.setPreferredSize(new Dimension(1900,768));
        bookViewerFrame.setMaximumSize(new Dimension(1900,768));
        bookViewerFrame.setMinimumSize(new Dimension(1900,768));



        //Set up the content pane.
        addComponentsToPane(bookViewerFrame.getContentPane());

        //Display the window.
        bookViewerFrame.pack();
        bookViewerFrame.setVisible(true);
    }


}
