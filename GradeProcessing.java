/**
 * 
 * 
 * @author: Saurav Pradhan
 * 
 * 
 * This code is responsible for the calculate grade of the code. This program when launched will prompt user with screen.
 * This screen will allow user to input the ID field for the user and when the user preses the search button.
 * The text field that allows user to input the data is validated and will only work if the user inputs 
 * the right format of the ID.
 * 
 * If ID is not found the user will prompt alert box stating that the ID was not found.
 * If found, the program will display all the possible result that matches the parameters passed by the user.
 * 
 * In addition to that, there is a combo box feature. This will lets users select different option on the choice.
 * After the user selects the choice that they want, then user can enter the valid parameter meeting the standard of the selection.
 * The table is then displayed matching all the datas that meet that criteria.
 * 
 * There is also tool tip feature that informs user what is supposed in the text field.
 * 
 * 
 */

 //importing the necessary libraries for the code.
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.geometry.Pos;

import javafx.scene.control.ComboBox;

public class GradeProcessing extends Application {

    //data needed to connect to the database
    static final String USER = "root";//username
    static final String PASSWORD = "";//password
    static final String DB_URL = "jdbc:mysql://localhost/gradeprocessing";//url to connect to the database
    private static Connection connection;
    private static Statement statement;

    //declaring and instancating the global variable needed for the code.
    private TextField studentTextField = new TextField();
    private Label lblStatus = new Label();
    private static ShowResultData dataFromDatabase;
    private static ArrayList<ShowResultData> collectionOutput;// arraylist
    private static TableView<ShowResultData> table = new TableView<ShowResultData>();
    private static ObservableList<ShowResultData> data;
    private String stringInputValue;

    // ****************************************************************************************
    // */
    //Creating the combo box
    ComboBox<String> menuSelectionCombo;
    //menus for the combo box 
    String[] menuSelection = { "ID", "StudentName", "Quiz", "A1", "A2", "A3", "Exam", "Result", "Grade" };
    String comboBoxValue;

    // ****************************************************************************************
    // */

    public void start(Stage primaryStage) {
        //instanciating the database so that the program is connected to the database.
        initializeDB();

        //the main vertical box for the program .
        VBox vBox = new VBox();

        //instanciating the arraylist
        collectionOutput = new ArrayList<>();
        //creating a scene with the required dimensions
        Scene scene = new Scene(vBox, 550, 240);

        VBox discriptorHolder = new VBox();
        //creating a label to inform users
        Label discriptor = new Label(
                "User can select one of the option from the combo box. \nBased on the selection from the combo box, the user can then enter the appropriate input\n\n");
        //setting up padding for the label.
        discriptor.setPadding(new Insets(8, 3, 8, 0));
        //changing the color of the label text to white.
        discriptor.setTextFill(Color.WHITE);
        //changing the color of the box to green using the css format
        discriptorHolder.setStyle("-fx-background-color: green;");

        discriptorHolder.getChildren().addAll(discriptor);

        // setting up the width of the text field
        studentTextField.setPrefWidth(200);
        //instanciating the combo box
        menuSelectionCombo = new ComboBox<>();

        // getting text value from combo box menu

        ObservableList<String> itemsOfMenu = FXCollections.observableArrayList(menuSelection);
        menuSelectionCombo.getItems().addAll(itemsOfMenu);
        //setting up the default text for the combo box
        menuSelectionCombo.setValue("Please select one of the option");

        //one click one of the option in the combo box is selected.
        menuSelectionCombo.setOnAction(e -> comboBoxValue = menuSelectionCombo.getValue());

        //creating a button
        Button showResultButton = new Button("Show Result");
        VBox twoContainer = new VBox();
        showResultButton.setMaxWidth(180);

        //setting up padding for the button
        showResultButton.setPadding(new Insets(15, 0, 15, 0));

        VBox hBox = new VBox(15);
        HBox comboAndText = new HBox(10);
        comboAndText.setPadding(new Insets(0, 0.1, 0, 20));

        comboAndText.getChildren().addAll(menuSelectionCombo, new Label("<--- Please select one of the option"));
        hBox.setAlignment(Pos.CENTER);
        studentTextField.setMaxWidth(500);
        studentTextField.setMinHeight(50);
        hBox.setPadding(new Insets(10, 20, 10, 20));
        //setting the background color of the vertical box to green
        hBox.setStyle("-fx-background-color: white;");
        hBox.getChildren().addAll(comboAndText, studentTextField, (showResultButton));
        twoContainer.getChildren().addAll(hBox, discriptorHolder);
        studentTextField.setPrefColumnCount(8);

        //once this button is pressed, it check if the combo box value is null or not
        //if null it pops up alert box notifying the user that the combo box is not selected
        //if the value is not null it calls the showResult method
        showResultButton.setOnAction(e -> {
            System.out.println(comboBoxValue);
            
            if (comboBoxValue == null) {
                errorComboAlert(comboBoxValue);
            } else {
                showResult();
            }
        });

        // Create a scene and place it in the stage
        vBox.getChildren().addAll(twoContainer);
        primaryStage.setTitle("Search Record"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        
        launch(args);

    }

    //instanciating the database.
    private static void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded");

            // connecting to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected");

            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from student");

        } catch (SQLException sqlEx) {
            System.out.println("Error during SQL process");

        } catch (ClassNotFoundException classNotEx) {
            System.out.println("Error during finding the class");
        }
    }

    static int counters = 1;


    public void showResult() {

        //the value entered in the text field is stored in this string variable.
        stringInputValue = studentTextField.getText();

        //once the value is stored, it is then validated.
        //this checks if the number is integer and of the length 8. If the criteria is not matched then
        //it pops up alert box notifying the user that the data entered was not valid
        //if the data enter is valid then, it starts the search process.
        if ((stringInputValue.matches("[0-99999999]+") == false && comboBoxValue.equals("ID"))
                | (stringInputValue.length() != 8 && comboBoxValue.equals("ID"))) {
            System.out.println("Please enter the valid number");
            errorAlert(comboBoxValue);
            studentTextField.clear();
            counters = 1;

            //checks if the combo box value is student Name.
        }else if ((!stringInputValue.matches(".*[a-z].*") && comboBoxValue.equals("StudentName")||(!stringInputValue.matches(".*[A-Z].*") && comboBoxValue.equals("StudentName"))
                | (stringInputValue.length() > 100 && comboBoxValue.equals("StudentName")))) {
            System.out.println("Please enter the valid name");
            errorAlert(comboBoxValue);
            studentTextField.clear();
            counters = 1;

          //checks the matching criteria for the combobox value for quiz, assignment1, assignment2, assignment3,exam  
        }else if(!stringInputValue.matches("[0-99]+(.[0-9][0-9]?)?")&& (comboBoxValue.equals("Quiz")||comboBoxValue.equals("A1")||comboBoxValue.equals("A2")||comboBoxValue.equals("A3")||comboBoxValue.equals("Exam")||comboBoxValue.equals("Result")) ){
            System.out.println("Please enter the valid marks");
            errorAlert(comboBoxValue);
            studentTextField.clear();
            counters = 1;
        
            //checks for grade, the the criteria. If not valid pops up the alert box
        }else if((!stringInputValue.matches("FL")||!stringInputValue.matches("PS")||!stringInputValue.matches("CR")||!stringInputValue.matches("DI")||!stringInputValue.matches("HD"))&& (comboBoxValue.equals("Grade"))){
            System.out.println("Please enter the valid grade");
            errorAlert(comboBoxValue);
            studentTextField.clear();
            counters = 1;
        }

        else {

            try {
                System.out.println(comboBoxValue);
                //query for selecting the data
                String queryString = "SELECT * FROM `student` WHERE student." + comboBoxValue + "= '" + stringInputValue
                        + "'";
                System.out.println(queryString);

                ResultSet resultSet = statement.executeQuery(queryString);

                while (resultSet.next()) {
                    String iD = resultSet.getString(1);
                    String studentName = resultSet.getString(2);
                    String quiz = resultSet.getString(3);
                    String a1 = resultSet.getString(4);
                    String a2 = resultSet.getString(5);
                    String a3 = resultSet.getString(6);
                    String exam = resultSet.getString(7);
                    String result = resultSet.getString(8);
                    String grade = resultSet.getString(9);

                    dataFromDatabase = new ShowResultData(iD, studentName, quiz, a1, a2, a3, exam, result, grade);

                    collectionOutput.add(dataFromDatabase);
                    // System.out.println(collectionOutput);

                }

                
                if (dataFromDatabase == null) {
                    notFoundAlert(comboBoxValue);
                    studentTextField.clear();
                    counters = 1;

                } else {
                    TableDataView1 tableDataView = new TableDataView1(collectionOutput);
                    tableDataView.tableShow(collectionOutput);

                }

                // showTable(collectionOutput);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    //alert box pops up if the combo box is not selected.
    //it notifies the user that the combo box is not selected.
    public void errorComboAlert(String comboBoxValue) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("No combo box value");

        alert.setContentText("Please select one of the option from combo box to continue");

        alert.showAndWait();
    }

    //alertbox pops up based on the criteria of the selection from the combo box
    public void errorAlert(String comboBoxValue) {
        Alert alert = new Alert(AlertType.ERROR);

        
        alert.setTitle("Error Occured");
        alert.setHeaderText("Invalid " + comboBoxValue + " error");

        //alert for ID
        if (comboBoxValue.equals("ID")) {
            alert.setContentText("The ID must be eight digits. Eg: 11111111");

        //alert for studentName
        }else if(comboBoxValue.equals("StudentName")){
            alert.setContentText("Please enter the appropriate name");
        //alert for quiz
        }else if (comboBoxValue.equals("Quiz")){
            alert.setContentText("Please enter the appropriate quiz marks.\nExample: 60.This field accepts number from 1 to 100");
        }else if (comboBoxValue.equals("A1")){
            alert.setContentText("Please enter the appropriate assignment 1 marks.\nExample:50.This field accepts number from 1 to 100");
        }else if (comboBoxValue.equals("A2")){
            alert.setContentText("Please enter the appropriate assignment 2 marks.\nExample:90. This field accepts number from 1 to 100");
        }
        else if (comboBoxValue.equals("A3")){
            alert.setContentText("Please enter the appropriate assignment 3 marks.\nExample:80.This field accepts number from 1 to 100");
        }
        else if (comboBoxValue.equals("Exam")){
            alert.setContentText("Please enter the appropriate exam marks.\nExample: 75.This field accepts number from 1 to 100");
        }else if (comboBoxValue.equals("Result")){
            alert.setContentText("Please enter the appropriate result marks.\nExample: 78.This field accepts number from 1 to 100");
        }else if (comboBoxValue.equals("Grade" )){
            alert.setContentText("Please enter the appropriate grade.\nExample: FL,PS,CR,DI,HD");
        }

        //untill the alert box is closed, user cannot go back to the searching menu.
        alert.showAndWait();
    }

    //if the data entered are valid and is not in the database, it pop up 
    //notifying the user.
    public void notFoundAlert(String comboBoxValue) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText(comboBoxValue + " not found");
        alert.setContentText("The " + comboBoxValue + " you have entered is not in the list");

        alert.showAndWait();
    }

}