/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Noah Taylor
 */

package ucf.assignments;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

/*
This class displays a pop-up window asking the user for confirmation before closing the program
    Display method is created
    Creates a new stage, sets the title, width, and label
    Two buttons (yes and no) are made
    Lambdas are used to handle button clicking
    VBox container is made to hold buttons and title
    Padding and aligning are added for aesthetic
    New scene is created, window displays and stays until a button is clicked
    Returns user's choice to Main class
 */

public class ConfirmBox {

    // Declaring class properties
    static boolean answer;

    // Display method to create confirmation box
    public static boolean display(String title, String message) {

        // Creates new stage, sets the title, width, and label
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        // Creates two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        // Provides functionality to buttons
        // Closes window once one is clicked
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        // Creates VBox container for window, adds the label and buttons
        // Sets alignment to centered, creates padding
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        // Creates a new scene, displays window
        // Requires user to interact with window before proceeding
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        // Returns user's choice to closeProgram method in Main class
        return answer;
    }
}