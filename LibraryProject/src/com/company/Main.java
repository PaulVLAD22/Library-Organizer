package com.company;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        // IN DB OBJECTS BOOKS->String name,String authorName,String Category, int numOfAvailableBooks, int booksOnLoan
        //               LOAN-> BOOKNAME,BookAuthor,DateOfLoanEnd,LoanerFirstName,LoanerLastName,LoanerPhoneNumber

        Menu.createAndShowGUI();
    }
}
