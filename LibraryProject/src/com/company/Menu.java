package com.company;

import com.company.MenuChoices.BookAdders.BookAdder;
import com.company.MenuChoices.BookAdders.BookCopiesAdder;
import com.company.MenuChoices.BookCheck.BookChecker;
import com.company.MenuChoices.BookDeleter;
import com.company.MenuChoices.BookView.BookViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addAButton("Check for books", pane,openCheckBookMenu());
        addAButton("Add books", pane,openAddBookMenu());
        addAButton("Add new copies of book",pane,openAddCopiesMenu());
        addAButton("Delete books", pane, openDeleteBookMenu());
        addAButton("View all books", pane, openViewBooksMenu());
        addAButton("View borrowed books information", pane, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //View all loans

            }
        });
    }

    private static void addAButton(String text, Container container,ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setMaximumSize(new Dimension(300,50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    private static ActionListener openAddBookMenu(){
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookAdder.createAndShowGUI();
            }
        };
        return actionListener;
    }
    private static ActionListener openAddCopiesMenu(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookCopiesAdder.createAndShowGUI();
            }
        };
        return actionListener;
    }
    private static ActionListener openCheckBookMenu(){
        ActionListener actionListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookChecker.createAndShowGUI();
            }
        };
        return actionListener;
    }
    private static ActionListener openDeleteBookMenu(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDeleter.createAndShowGUI();
            }
        };
        return actionListener;
    }
    private static ActionListener openViewBooksMenu(){
        ActionListener actionListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookViewer.createAndShowGUI();
            }
        };
        return actionListener;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window
        JFrame frame = new JFrame("BoxLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Library Menu");



        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}




