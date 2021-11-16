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
This class displays a pop-up window telling the user that their input was invalid
    Display method is created
    Creates a new stage, sets the title, width, and label
    Makes a simple ok button so user acknowledges the error
    VBox container is made to hold button and title
    Padding and aligning are added for aesthetic
    New scene is created, window displays and stays until button is clicked
 */

public class InvalidBox {

    // Declaring class properties
    static boolean answer;

    // Void method to display alert window
    public static void display(String title, String message) {

        // Creates new stage, sets the title, width, and label
        Stage window2 = new Stage();
        window2.initModality(Modality.APPLICATION_MODAL);
        window2.setTitle(title);
        window2.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        // New button called ok is made
        // Answer is always true, window closes upon clicking
        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> {
            answer = true;
            window2.close();
        });

        // Creates VBox container for window, adds the label and button
        // Sets alignment to centered, creates padding
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        // Creates a new scene, displays window
        // Requires user to interact with window before proceeding
        Scene scene = new Scene(layout);
        window2.setScene(scene);
        window2.showAndWait();
    }
}
