/**
 * 
 * 
 * @author: Saurav Pradhan
 * 
 * This code is responsible for the calculate grade of the code. This program when launched will prompt user with screen.
 * This screen will allow user to input the ID field for the user and when the user preses the search button.
 * The text field that allows user to input the data is validated and will only work if the user inputs 
 * the right format of the ID
 * 
 * If ID is not found the user will prompt alert box stating that the ID was not found.
 * If found, the program will display table giving the user with ID, student name and the result of the student.
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.ComboBox;
import javafx.geometry.Pos;

public class CalculateResult extends Application {

    //data needed to connect to the database
    static final String USER = "root";//username
    static final String PASSWORD = "";//password
    static final String DB_URL = "jdbc:mysql://localhost/gradeprocessing"; //url to connect to the database
    private static Connection connection;
    private static Statement statement;

    private TextField studentTextField = new TextField();
    private Label lblStatus = new Label();
    private static ShowResultData dataFromDatabase;
    private static ArrayList<ShowResultData> collectionOutput;
    private static TableView<ShowResultData> table = new TableView<ShowResultData>();
    private static ObservableList<ShowResultData> data;
    
    //global flags needed for the code.
    private String stringInputValue;

    //main stage of this program
    public void start(Stage primaryStage) {
        //instanciating the database so that the program is connected to the database.
        initializeDB();

        //the main vertical box for the program with the distance of elements in it with 10.
        VBox vBox = new VBox(10);

         //instanciating the arraylist
        collectionOutput = new ArrayList<>();
        //creating a scene with the required dimensions
        Scene scene = new Scene(vBox, 550, 200);

        // setting up the width of the text field
        studentTextField.setPrefWidth(200);
        //creating a button 
        Button showResultButton = new Button("Calculate Result");
        VBox containers = new VBox();
        VBox discriptorHolder = new VBox();
        Label discriptor = new Label(
                "User can input the appropriate ID. \nBased on the ID, the option will calculate result\n\n");
        
        //setting up the padding for the label
        discriptor.setPadding(new Insets(3, 0, 3, 0));

        //changing the color of the label text to white.
        discriptor.setTextFill(Color.WHITE);
        //changing the color of the box to green using the css format
        discriptorHolder.setStyle("-fx-background-color: green;");
         //adding the children to the vbox
        discriptorHolder.getChildren().addAll(discriptor);
        
        //creating another vbox with the distance of each element in it to 15.
        VBox hBox = new VBox(15);
        hBox.setPadding(new Insets(20, 0, 20, 0));
        studentTextField.setMaxWidth(500);
        studentTextField.setMinHeight(50);
        studentTextField.setText("Please enter the ID you want to search");
        
        //setting up all the elements of the vbox to the center
        hBox.setAlignment(Pos.CENTER);
        
        //tooltip to display the information about the detail that needs to be entered in the text field
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
            "The ID should be 8 digits long. " +
            "Example: 11651300\n"  
        );
        studentTextField.setTooltip(tooltip);
        //------------tooltip--------------------------------------------------------/

        //setting up the padding for the button.
        showResultButton.setMaxWidth(180);
        
        showResultButton.setPadding(new Insets(15, 0,15, 0));
        hBox.getChildren().addAll(studentTextField, (showResultButton));
        

        containers.getChildren().addAll(hBox, discriptorHolder );
        containers.setStyle("-fx-background-color: white;");
        containers.setMinHeight(200.00);
        studentTextField.setPrefColumnCount(8);
        showResultButton.setOnAction(e -> showResult());

        // Create a scene and place it in the stage
        vBox.getChildren().addAll(containers, lblStatus);
        primaryStage.setTitle("Calculate Result"); // Set the stage title
        
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    int counter = 1;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        
        launch(args);

    }

    //this method instanciates the connnection to the database. This method is called at the beginning.
    //this method helps user connect to the database.
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

    //second global counters
    static int counters = 1;

    public void showResult() {
        //the value entered in the text field is stored in this string variable.
        stringInputValue = studentTextField.getText();

        //once the value is stored, it is then validated.
        //this checks if the number is integer and of the length 8. If the criteria is not matched then
        //it pops up alert box notifying the user that the data entered was not valid
        //if the data enter is valid then, it starts the search process.
        if (stringInputValue.matches("[0-99999999]+") == false | stringInputValue.length() != 8) {
            System.out.println("Please enter the valid number");
            errorAlert();
            studentTextField.clear();
            counters = 1;
        } else {
            try {
                //sql query
                String queryString = "SELECT * FROM `student` WHERE student.ID = '" + stringInputValue + "'";
                System.out.println(queryString);
                //the query is then executed.
                ResultSet resultSet = statement.executeQuery(queryString);

                //while all the query criteria is matched, it gets the individual data and stores in the 
                //specific variable
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

                    //the variable is then passed as parameters creating a object
                    dataFromDatabase = new ShowResultData(iD, studentName, quiz, a1, a2, a3, exam, result, grade);

                    //the object is then passed to the arraylist and added.
                    collectionOutput.add(dataFromDatabase);

                }


                //if the object is empty, it pops up the alert box notifying the user that the ID entered is not in the database.
                //the input field is cleared
                //if it is not empty, it is passed to the table class and shows the table to the user.
                if(dataFromDatabase == null){
                    idNotFoundAlert();
                    studentTextField.clear();
                    counters = 1;
                    

                }else{
                    TableDataView1.calcResult(dataFromDatabase);
                    
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    //alert that handles, if the input paramet is invalid.
    public void errorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("Invalid ID error");
        alert.setContentText("The ID must be eight digits. Eg: 11111111");

        alert.showAndWait();
    }

    //alert that handles if the entered ID by the user is not found
    public void idNotFoundAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("ID not found");
        alert.setContentText("The ID you have entered is not in the list");

        alert.showAndWait();
    }


}