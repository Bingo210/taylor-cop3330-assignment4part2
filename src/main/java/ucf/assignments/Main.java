/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Noah Taylor
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main extends Application {

    // Declaring class properties
    Stage window;
    TableView<Items> table;
    TextField numberInput, dateInput, descriptionInput;
    BorderPane layout;
    Scene scene, helpScene;

    // Main method to launch application
    public static void main(String[] args) {
        launch(args);
    }

    @Override

    // Start method to display GUI from FXML file
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("To Do List");

        // Method is called when user requests to close window via red x button
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });




        /*
        First, the main menu bar must be created
        Basic functionality is added for now, but will be improved later
            Create a new menu, title it
            Create dropdown options for the menu item
            Use lambda expressions to implement a functional interface
        Repeat for each item in menu bar
         */




        // Creates a file menu
        Menu fileMenu = new Menu("File");

        // Adds save list option and provides functionality
        MenuItem saveList = new MenuItem("Save List...");
        saveList.setOnAction(e -> System.out.println("Saves current list"));
        fileMenu.getItems().add(saveList);

        // Adds view all lists option and provides functionality
        MenuItem loadLists = new MenuItem("Load Saved List...");
        loadLists.setOnAction(e -> System.out.println(
                "Opens menu with all saved lists, offers user choice to add/delete lists, " +
                        "as well as save all or delete all lists choices"));
        fileMenu.getItems().add(loadLists);

        // Creates separator line between view all and exit
        fileMenu.getItems().add(new SeparatorMenuItem());

        // Adds exit menu option and provides functionality
        MenuItem exitMenu = new MenuItem("Exit");
        exitMenu.setOnAction(e -> {
            e.consume();
            closeProgram();
        });
        fileMenu.getItems().addAll(exitMenu);

        // Creates an edit menu
        Menu editMenu = new Menu("Edit");

        // Adds edit title option and provides functionality
        MenuItem editTitle = new MenuItem("Edit List Title...");
        editTitle.setOnAction(e -> System.out.println("Allows user to edit list title"));
        editMenu.getItems().add(editTitle);

        // Adds edit description option and provides functionality
        MenuItem editDescription = new MenuItem("Edit Item Description...");
        editDescription.setOnAction(e -> System.out.println(
                "Allows user to edit the description of any item"));
        editMenu.getItems().add(editDescription);

        // Adds edit due date option and provides functionality
        MenuItem editDate = new MenuItem("Edit Item Due Date...");
        editDate.setOnAction(e -> System.out.println(
                "Allows user to edit the due date of any item"));
        editMenu.getItems().add(editDate);

        // Creates a display menu
        Menu displayMenu = new Menu("Display");

        // Adds display all option and provides functionality
        MenuItem displayAll = new MenuItem("Display All Existing Items...");
        displayAll.setOnAction(e -> System.out.println(
                "Allows user to display all existing items in list"));
        displayMenu.getItems().add(displayAll);

        // Adds display incompleted option and provides functionality
        MenuItem displayIncompleted = new MenuItem("Display All Incompleted Items...");
        displayIncompleted.setOnAction(e -> System.out.println(
                "Allows user to display all incompleted items in list"));
        displayMenu.getItems().add(displayIncompleted);

        // Adds display completed option and provides functionality
        MenuItem displayCompleted = new MenuItem("Display All Completed Items...");
        displayCompleted.setOnAction(e -> System.out.println(
                "Allows user to display all completed items in list"));
        displayMenu.getItems().add(displayCompleted);

        // Creates a help menu and a new window for it
        Menu helpMenu = new Menu("Help");
        MenuItem showHelp = new MenuItem("Open Help Window...");
        showHelp.setOnAction(e -> {
            Text topHelpMenuText = new Text();
            topHelpMenuText.setText("Welcome to my javafx to do list!\n\n");

            Text helpMenuText = new Text();
            helpMenuText.setText("""
                    This is a GUI-based application built with javafx to manage a to do list for a user.


                    Add - adds another item to the list
                    Delete - deletes an item from the list
                    Completed - marks an item in the list as completed, and hides it
                    Clear All - removes all items from the list
                    File - opens the file menu
                    Edit - opens the edit menu
                    Display - opens the display menu

                    Note that the following inputs are restricted to:
                    Number - integers from 1 through 100
                    Due Date - must be formatted YYYY-MM-DD
                    Description - any input between 1 and 256 characters in length


                    Invalid inputs will display a warning message.
                    Though fields may remain empty, you cannot input a completely blank item.
                    """);

            Button backButton = new Button("Back");
            backButton.setOnAction(f -> {
                window.setScene(scene);
                window.setTitle("To Do List 1");
                window.show();
            });

            // Creates new container, adds button and text
            StackPane helpPane = new StackPane();
            helpPane.getChildren().addAll(backButton, topHelpMenuText, helpMenuText);
            helpPane.setAlignment(backButton, Pos.BOTTOM_CENTER);
            helpPane.setAlignment(topHelpMenuText, Pos.TOP_CENTER);
            helpPane.setAlignment(helpMenuText, Pos.CENTER);
            helpPane.setPadding(new Insets(10, 10, 10, 10));
            helpScene = new Scene(helpPane, 600, 400);

            // Sets scene to help menu
            window.setScene(helpScene);
            window.setTitle("Help Window");
            window.show();
        });
        helpMenu.getItems().add(showHelp);

        // Creates main menu bar, adds file, edit and display menus to it
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, displayMenu, helpMenu);




        /*
        Next we must create a container for the GUI and add features

        Add the menu bar to the top of the borderpane

        Create a few columns for the tableview by:
            Declaring and titling each column
            Setting width parameter
            Use setCellValueFactory to specify how to populate cells
        Repeat for the three needed cells

        Create input text fields at bottom for user input
        Set some prompt text for clarity

        Create buttons needed for list to function by
            Making and titling the buttons
            Use lambdas to call a method for button click handling
         */




        // Creates borderpane container, places menu bar at the top
        layout = new BorderPane();
        layout.setTop(menuBar);

        // Creates number column, sets width
        TableColumn<Items, String> numberColumn = new TableColumn<>("Number");
        numberColumn.setMinWidth(10);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        // Creates due date column, sets width
        TableColumn<Items, String> dateColumn = new TableColumn<>("Due Date");
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Creates description column, sets width
        TableColumn<Items, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(600);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Number input
        numberInput = new TextField();
        numberInput.setPromptText("Number");
        numberInput.setMinWidth(100);

        // Date input
        dateInput = new TextField();
        dateInput.setPromptText("Due Date");

        // Description input
        descriptionInput = new TextField();
        descriptionInput.setPromptText("Description");

        // Creates add, delete, clear, and completed buttons, implements button functionality
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button completedButton = new Button("Completed");
        completedButton.setOnAction(e -> System.out.println(
                "Marks selected list item as completed"));
        Button clearButton = new Button("Clear All");
        clearButton.setOnAction(e -> clearButtonClicked());




        /*
        Now, the buttons and text fields need to be put into an hbox container
            Declare the hbox
            Give it some padding/spacing for aesthetic purposes
            Add the buttons and input fields using getChildren
         */




        // Creates HBox container, adds buttons, padding, and input text fields
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(numberInput, dateInput, descriptionInput,
                addButton, deleteButton, completedButton, clearButton);




        /*
        Let us now create the table
            Declare the new table
            Insert the first example row using setItems
            Add the columns to the table

        Combine them all into a singular interface
            Declare a new vbox container
            Add the menu, table, and hbox to a parent container
            Make a new scene, add the vbox and show it in a new window
         */




        // Creates the table, adds items and columns
        table = new TableView<>();
        table.setItems(getItems());
        table.getColumns().addAll(numberColumn, dateColumn, descriptionColumn);

        // Creates vbox container to store the menu, table, and hbox for buttons
        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar, table, hBox);

        // Creates new scene, shows GUI in new window
        scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }




    /*
    Now, the methods must be created
        addButtonClicked restricts user input and adds the item to the list
        deleteButtonClicked deletes a selected item from the list
        clearButtonClicked clears all items from the list
        getItems is used to create an observable list of items from user input
        closeProgram creates a pop-up when the user tries to exit the program
        invalidInput creates a pop-up when the user's input is invalid
        checkDate is used to ensure the user's date input is formatted YYYY-MM-DD
     */




    // Method for handling add button clicked
    public void addButtonClicked(){
        Items Items = new Items();
        Items.setNumber(Integer.parseInt(numberInput.getText()));
        Items.setDate(dateInput.getText());
        Items.setDescription(descriptionInput.getText());
        table.getItems().add(Items);

        // Restricts number input to fall within 1 through 100
        // Displays warning pop-up, removes invalid row
        if (Integer.parseInt(numberInput.getText()) < 1 ||
                Integer.parseInt(numberInput.getText()) > 100) {
            invalidInput();
            table.getItems().remove(Items);
        }

        // Calls checkDate method and restricts date input to have YYYY-MM-DD formatting
        // Displays warning pop-up, removes invalid row
        else if (!checkDate(dateInput.getText())) {
            invalidInput();
            table.getItems().remove(Items);
        }

        // Restricts description input to have 1 through 256 characters
        // Displays warning pop-up, removes invalid row
        else if (descriptionInput.getText().length() < 1 ||
                descriptionInput.getText().length() > 256) {
            invalidInput();
            table.getItems().remove(Items);
        }

        // Clears input text fields so user doesn't need to after each item's addition
        numberInput.clear();
        dateInput.clear();
        descriptionInput.clear();
    }

    // Method for handling delete button clicked
    // Deletes selected row
    public void deleteButtonClicked(){
        ObservableList<Items> ItemsSelected, allItems;
        allItems = table.getItems();
        ItemsSelected = table.getSelectionModel().getSelectedItems();
        ItemsSelected.forEach(allItems::remove);
    }

    // Method for handling clear button clicked
    // Clears all rows in table
    public void clearButtonClicked() {
        ObservableList<Items> allItems;
        allItems = table.getItems();
        allItems.removeAll(allItems);
    }

    // Method to convert objects from items class into observable list
    public ObservableList<Items> getItems(){
        ObservableList<Items> Items = FXCollections.observableArrayList();
        Items.add(new Items(1, "2021-11-15",
                "*****list formatted like this, delete and add your own*****"));
        return Items;
    }

    // Method for closing the program
    // Calls method in ConfirmBox class to display the associated window
    private void closeProgram() {
        Boolean confirm = ConfirmBox.display("Alert!",
                "Are you sure you want to exit the program?");
        if (confirm)
            window.close();
    }

    // Method to display alert box for invalid inputs
    // Calls method in InvalidBox class to create the associated window
    private void invalidInput() {
        InvalidBox.display("Alert!",
                "Invalid input. See help page for input constraints.");
    }

    // Method to check for proper formatting of user's date input
    public static boolean checkDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}