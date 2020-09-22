package com.company.Objects;

import com.company.ServerPass;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;

public class Book {
    private String bookName;
    private String authorName;
    private String categoryName;
    private int numOfAvailableBooks; // not on loan
    private int numOfBooksOnLoan; // on loan

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getNumOfAvailableBooks() {
        return numOfAvailableBooks;
    }

    public void setNumOfAvailableBooks(int numOfAvailableBooks) {
        this.numOfAvailableBooks = numOfAvailableBooks;
    }

    public int getNumOfBooksOnLoan() {
        return numOfBooksOnLoan;
    }

    public void setNumOfBooksOnLoan(int numOfBooksOnLoan) {
        this.numOfBooksOnLoan = numOfBooksOnLoan;
    }

    public Book(String bookName, String authorName, String categoryName, int numOfAvailableBooks) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.numOfAvailableBooks = numOfAvailableBooks;
        this.numOfBooksOnLoan = 0;
    }


    public void addToDb(){
        try {
            //1.Get a connection to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_library?serverTimezone=EET", "root", ServerPass.serverPass);
            //2. Create a statement
            Statement myStmt = myConn.createStatement();
            //3. Insert book into table
            myStmt.executeUpdate("insert into books values("+"'"+bookName.toUpperCase()+"'"+","+"'"+authorName.toUpperCase()+"'"+","+"'"+categoryName.toUpperCase()+"'"+","+numOfAvailableBooks+",0)");

            myConn.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
