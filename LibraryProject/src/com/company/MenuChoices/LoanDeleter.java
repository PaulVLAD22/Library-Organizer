package com.company.MenuChoices;

import com.company.ServerPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LoanDeleter {
    private static JFrame loanDeleteFrame;
    private static JTextField bookNameField;
    private static JTextField bookAuthorField;
    private static JTextField dateOfLoanEndField;
    private static JTextField customerNameField;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addALabel("Enter Book Name:",pane);
        bookNameField=addATextField("Book Name",pane);
        addALabel("Enter Book's Author",pane);
        bookAuthorField=addATextField("Author",pane);
        addALabel("Loan ending date?(year-month-day)",pane);
        dateOfLoanEndField =addATextField("Num of books to delete",pane);
        addALabel("Enter Customer Name",pane);
        customerNameField =addATextField("Available/Loan",pane);
        addAButton("Submit",pane, deleteLoan());

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

    private static ActionListener deleteLoan(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(bookNameField.getText().equals("")) && !(bookAuthorField.getText().equals(""))
                        && !(dateOfLoanEndField.getText().equals("")) && !(customerNameField.getText().equals(""))) {
                    try {
                        //1.Get a connection to database
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
                        //2. Create a statement
                        Statement myStmt = myConn.createStatement();
                        //3. Delete Loan
                        myStmt.executeUpdate("delete from loans where bookName='"+bookNameField.getText().toUpperCase()
                        +"' and authorName='"+bookAuthorField.getText().toUpperCase()+"' and dateOfLoanEnd='"+
                                dateOfLoanEndField.getText()+"' and borrowerName='"+ customerNameField.getText().toUpperCase()+"'");



                        myConn.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    loanDeleteFrame.dispose();
                }
            }
        };
        return actionListener;
    }

    public static void createAndShowGUI() {
        //Create and set up the window
        loanDeleteFrame = new JFrame("BoxLayoutDemo");
        loanDeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanDeleteFrame.setPreferredSize(new Dimension(600,400));
        loanDeleteFrame.setMaximumSize(new Dimension(600,400));
        loanDeleteFrame.setMinimumSize(new Dimension(600,400));
        loanDeleteFrame.setTitle("Close Loan");

        //Set up the content pane.
        addComponentsToPane(loanDeleteFrame.getContentPane());

        //Display the window.
        loanDeleteFrame.pack();
        loanDeleteFrame.setVisible(true);
    }


}
