/**
 * 
 * 
 * @author: Saurav Pradhan
 * 
 * 
 * This code is responsible for the update part of the code. This program when launched will prompt user with screen.
 * This screen will allow user to input the ID field for the user and when the user preses the search button.
 * 
 * 
 * If the field is valid and ID is in the data base it will give the user with next interface that allows
 * user to input the details of the ID that was entered by the user.
 * User is able to change all the field except the ID field which is not changeable.
 * 
 * After user puts all the necessary details that needs to be changed, the program then 
 * shows the message that the data was sucessfully entered and then shows the table giving 
 * all the details that was entered by the user.
 * 
 * If the user inputs the invalid data, this program will capture that. It has tooltip features
 * that lets user know what each field is suppose to hold
 * Also, it gives error message if the user does input the invalid parameters.
 * 
 */

//importing the necessary libraries for the code.
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.*;
import javafx.stage.Modality;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import javafx.scene.control.Tooltip;

public class UpdateTable extends Application {

    //data needed to connect to the database
    static final String USER = "root"; //username
    static final String PASSWORD = ""; // password which is null here
    static final String DB_URL = "jdbc:mysql://localhost/gradeprocessing"; //url to the localhost
    private static Connection connection;
    private static Statement statement;

    private TextField studentTextField = new TextField();
    private Label lblStatus = new Label();
    private static ShowResultData dataFromDatabase;
    private static ArrayList<ShowResultData> collectionOutput;
    private static TableView<ShowResultData> table = new TableView<ShowResultData>();
    private static ObservableList<ShowResultData> data;
    private String stringInputValue;

    //declaring textfield
    TextField textIDField, textStudentNameField, textQuizField, textAssignment1Field, textAssignment2Field,
            textAssignment3Field, textExamField;
    Float resultOfStudent;
    String gradePasser;
    Label headerLabel;
    //declaring the text
    Text labelId, labelStudentName, labelQuiz, labelAss1, labelAss2, labelAss3, labelExam;
    //declaring the string
    String textIdData, textStudentData, textQuizData, textAss1Data, textAss2Data, textAss3Data, textExamData;

    VBox hBoxButton;
    VBox verticalBox;
    HBox headerLabelBox;
    Label messageDisplay;
    GridPane gridPane;
    public static Stage stage1;

    Button buttonInsertRecord;

    static ArrayList<String> idCollection = new ArrayList<String>();
    static Label idError, nameError, quizError, a1Error, a2Error, a3Error, examError;

    //creating a global counters
    static int idCounter = 1;
    static int nameCounter = 1;
    static int quizCounter = 1;
    static int a1Counter = 1;
    static int a2Counter = 1;
    static int a3Counter = 1;
    static int examCounter = 1;
    static int counter = 1;

    //creating a global counters----------------------------------
    static boolean idChecker, idLength;
    static int counterLengthID = 1;

    //the getter method
    public int getExamCounter() {
        return examCounter;
    }

    //the getter method
    public int getA3Counter() {
        return a3Counter;
    }

    //the getter method
    public int getA2Counter() {
        return a2Counter;
    }

    //the getter method
    public int getA1Counter() {
        return a1Counter;
    }

    //the getter method
    public int getQuizCounter() {
        return quizCounter;
    }

    //the getter method
    public int getNameCounter() {
        return nameCounter;
    }

    //the getter method
    public int getIdCounter() {
        return idCounter;
    }

    //the getter method
    public void start(Stage primaryStage) {
        //calling the method to connect to the database
        initializeDB();
        VBox vBox = new VBox(10);
        collectionOutput = new ArrayList<>();
        Scene scene = new Scene(vBox, 550, 200);

        // setting up the width of the text field
        studentTextField.setPrefWidth(200);

        Button showResultButton = new Button("Search");
        VBox containers = new VBox();
        VBox discriptorHolder = new VBox();
        Label discriptor = new Label(
                "User can input the appropriate ID. \nBased on the ID, the option will be given to update the record\n\n");

        discriptor.setPadding(new Insets(3, 0, 3, 0));
        discriptor.setTextFill(Color.WHITE);
        discriptorHolder.setStyle("-fx-background-color: green;");

        discriptorHolder.getChildren().addAll(discriptor);

        VBox hBox = new VBox(15);
        hBox.setPadding(new Insets(20, 0, 20, 0));
        studentTextField.setMaxWidth(500);
        studentTextField.setMinHeight(50);
        studentTextField.setText("Please enter the ID you want to search");

        hBox.setAlignment(Pos.CENTER);

        final Tooltip tooltip = new Tooltip();
        tooltip.setText("The ID should be 8 digits long. " + "Example: 11651300\n");
        studentTextField.setTooltip(tooltip);

        showResultButton.setMaxWidth(180);

        showResultButton.setPadding(new Insets(15, 0, 15, 0));
        hBox.getChildren().addAll(studentTextField, (showResultButton));

        containers.getChildren().addAll(hBox, discriptorHolder);
        containers.setStyle("-fx-background-color: white;");
        containers.setMinHeight(200.00);
        studentTextField.setPrefColumnCount(8);
        showResultButton.setOnAction(e -> showResult());

        // Create a scene and place it in the stage
        vBox.getChildren().addAll(containers, lblStatus);
        primaryStage.setTitle("Seach for Update table"); // Set the stage title

        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        initializeDB();
        launch(args);

    }

    //method to connect to the database
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

    public void showTextInputFields(String iD, String studentName, String quiz, String a1, String a2, String a3,
            String exam) {
        //creating a stage
        Stage stage = new Stage();

        // creating label 
        labelId = new Text("ID: ");
        labelStudentName = new Text("Student Name: ");
        labelQuiz = new Text("Quiz: ");
        labelAss1 = new Text("Assignment 1: ");
        labelAss2 = new Text("Assignment 2: ");
        labelAss3 = new Text("Assignment 3: ");
        labelExam = new Text("Exam: ");

        // Creating Text Filed
        textIDField = new TextField();

        //creating a textField and adding tooltip-------------------------------------//
        final Tooltip textIDtip = new Tooltip();
        textIDtip.setText(
            "The ID should be 8 digits long. " +
            "Example: 11651300\n"  
        );
        textIDField.setTooltip(textIDtip);

        textStudentNameField = new TextField();
        final Tooltip textIDName = new Tooltip();
        textIDName.setText(
            "This field is for the name.It can be full name or first name " +
            "Example: Saurav or Saurav Pradhan\n"  
        );
        textStudentNameField.setTooltip(textIDName);

        textQuizField = new TextField();
        final Tooltip textIDQuiz = new Tooltip();
        textIDQuiz.setText(
            "Please insert marks between 1 to 100 " +
            "Example: 80\n"  
        );
        textQuizField.setTooltip(textIDQuiz);


        textAssignment1Field = new TextField();

        final Tooltip textIDA1 = new Tooltip();
        textIDA1.setText(
            "Please insert marks between 1 to 100 " +
            "Example: 80\n"  
        );
        textAssignment1Field.setTooltip(textIDA1);

        textAssignment2Field = new TextField();
        final Tooltip textIDA2 = new Tooltip();

        textIDA2.setText(
            "Please insert marks between 1 to 100 " +
            "Example: 70\n"  
        );
        textAssignment2Field.setTooltip(textIDA2);

        textAssignment3Field = new TextField();
        final Tooltip textIDA3 = new Tooltip();

        textIDA3.setText(
            "Please insert marks between 1 to 100 " +
            "Example: 60\n"  
        );
        textAssignment3Field.setTooltip(textIDA3);

        textExamField = new TextField();
        final Tooltip textIDExam = new Tooltip();

        textIDExam.setText(
            "Please insert marks between 1 to 100 " +
            "Example: 90\n"  
        );
        textExamField.setTooltip(textIDExam);
        //creating a textField and adding tooltip(end)-------------------------------------//

        //setting the default text to the textfields
        textIDField.setText(iD);
        textStudentNameField.setText(studentName);
        textQuizField.setText(quiz);
        textAssignment1Field.setText(a1);
        textAssignment2Field.setText(a2);
        textAssignment3Field.setText(a3);
        textExamField.setText(exam);

        //setting the default text to the textfields(end)

        //the textfield containing id cannot be edited
        textIDField.setEditable(false);
        //setting up the style
        textIDField.setStyle("-fx-border-color: yellow ;");
        hBoxButton = new VBox();
        verticalBox = new VBox();
        headerLabelBox = new HBox();

        headerLabel = new Label("Update the table");
        //adding design to the label
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        headerLabelBox.getChildren().addAll(headerLabel);

        //setting up the padding 
        headerLabelBox.setPadding(new Insets(20, 0, 20, 0));
        //creating elements to the center
        headerLabelBox.setAlignment(Pos.CENTER);

        VBox labelHolder = new VBox();
        Label messageDisplay = new Label("");
        //setting the text label to white
        messageDisplay.setTextFill(Color.WHITE);
        //adding the label to the box
        labelHolder.getChildren().addAll(messageDisplay);

        // Creating Buttons
        buttonInsertRecord = new Button("Insert Record");
        buttonInsertRecord.setMinWidth(300);
        buttonInsertRecord.setMinHeight(50);
        hBoxButton.setPadding(new Insets(20, 0, 20, 0));

        //on button click, it shows to the user that the data has been added
        //in a green background white text
        buttonInsertRecord.setOnAction(e -> {
            showResultAfterButtonClick();

            labelHolder.setStyle("-fx-background-color: green;");
            labelHolder.setAlignment(Pos.CENTER);
            messageDisplay.setText("The data with ID: " + textIdData + " and with name: " + textStudentData
                    + " has been updated to the database");
            
            
            

        });

        //designing the textfields---------------------------------------------------------//
        textIDField.setMinWidth(200);
        textStudentNameField.setMinWidth(200);
        textQuizField.setMinWidth(200);
        textAssignment1Field.setMinWidth(200);
        textAssignment2Field.setMinWidth(200);
        textAssignment3Field.setMinWidth(200);
        textExamField.setMinWidth(200);

        textIDField.setMinHeight(35);
        textStudentNameField.setMinHeight(35);
        textQuizField.setMinHeight(35);
        textAssignment1Field.setMinHeight(35);
        textAssignment2Field.setMinHeight(35);
        textAssignment3Field.setMinHeight(35);
        textExamField.setMinHeight(35);

        //------------------designing label (end)--------------------------------------------------//
        //------------------creating label--------------------------------------------------//

        idError = new Label();
        nameError = new Label();
        quizError = new Label();
        a1Error = new Label();
        a2Error = new Label();
        a3Error = new Label();
        examError = new Label();

        //-------------------------creating label(end)--------------------------------------//


        //validating the textfields. checks if it matches the criteria for ID.
        //if it does not, it prompts the user

        textIDField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textIDField.getText().matches("[00000001-99999999]")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    idError.setText("This field accepts 8 digit ID. Example: 22222222");
                    idCounter = 0;
                    buttonInsertRecord.setDisable(true);

                }
            } else {
                idError.setText("                                           ");
            }

            System.out.println("matching " + textIDField.getText().matches("[10000000-99999999]+"));
            System.out.println("length " + textIDField.getText().length());

            //checks for duplication in ID field
            if (duplicateChecker() == true) {
                messageDisplay.setText("ID is already in the list");
                idError.setText("                                           ");
                buttonInsertRecord.setDisable(true);
                idCounter = 0;
                System.out.println("3");
            }

            //checks for ID length
            if (counterLengthID > 1) {
                if (duplicateChecker() == false && textIDField.getText().length() != 8) {
                    idError.setText("This field accepts 8 digit ID. Example: 22222222");
                    messageDisplay.setText("                                           ");
                    buttonInsertRecord.setDisable(true);
                    idCounter = 0;
                    System.out.println("3!");
                }

            }

            //if all the criteria matches, sets all the counter to 1, calls the method which then allows user to enter the option
            if (duplicateChecker() == false && idChecker == false && textIDField.getText().length() == 8) {
                messageDisplay.setText("                                           ");
                idError.setText("                                           ");
                idCounter = 1;
                System.out.println("4");
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }
            }

            counterLengthID++;

        });

        //validating the textfields. checks if it matches the criteria for student name.
        //if it does not, it prompts the user with proper message
        textStudentNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textStudentNameField.getText().matches(".*[a-z].*") || textStudentNameField.getText().length() > 50
                        || !textStudentNameField.getText().matches(".*[A-Z].*")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    nameError.setText("Please enter the appropriate name");
                    nameCounter = 0;
                    buttonInsertRecord.setDisable(true);

                }

            } else {
                nameError.setText("                                           ");
                nameCounter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }

            }

            if (textStudentNameField.getText().matches(".*[a-z].*") && textStudentNameField.getText().length() < 50) {
                nameError.setText("                                 ");
                nameCounter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }
            }

            if (textStudentNameField.getText().matches(".*[A-Z].*") && textStudentNameField.getText().length() < 50) {
                nameError.setText("                                 ");
                nameCounter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }
            }

        });

        //validating the textfields. checks if it matches the criteria for quiz.
        //if it does not, it prompts the user with proper message
        textQuizField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textQuizField.getText().matches("[0-99]+(.[0-9][0-9]?)?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    quizError.setText("Please enter the appropriate marks");
                    quizCounter = 0;
                    buttonInsertRecord.setDisable(true);

                }

            } else {
                quizError.setText("                                           ");
                quizCounter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();
                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }

            }

        });

        //validating the textfields. checks if it matches the criteria for assignment1.
        //if it does not, it prompts the user with proper message
        textAssignment1Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textAssignment1Field.getText().matches("[0-99]+(.[0-9][0-9]?)?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    a1Error.setText("Please enter the appropriate marks");
                    a1Counter = 0;
                    buttonInsertRecord.setDisable(true);

                }

            } else {
                a1Error.setText("                                           ");
                a1Counter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }

            }
        });

        //validating the textfields. checks if it matches the criteria for assignment2.
        //if it does not, it prompts the user with proper message
        textAssignment2Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textAssignment2Field.getText().matches("[0-99]+(.[0-9][0-9]?)?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    a2Error.setText("Please enter the appropriate marks");
                    a2Counter = 0;
                    buttonInsertRecord.setDisable(true);

                }
            } else {
                a2Error.setText("                                           ");
                a2Counter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }

            }
        });

        //validating the textfields. checks if it matches the criteria for assignment3.
        //if it does not, it prompts the user with proper message

        textAssignment3Field.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textAssignment3Field.getText().matches("[0-99]+(.[0-9][0-9]?)?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    a3Error.setText("Please enter the appropriate marks");
                    a3Counter = 0;
                    buttonInsertRecord.setDisable(true);

                }

            } else {
                a3Error.setText("                                           ");
                a3Counter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }

            }
        });

        //validating the textfields. checks if it matches the criteria for exam.
        //if it does not, it prompts the user with proper message
        textExamField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textExamField.getText().matches("[0-99]+(.[0-9][0-9]?)?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    examError.setText("Please enter the appropriate marks");
                    examCounter = 0;
                    buttonInsertRecord.setDisable(true);

                }

            } else {
                examError.setText("                                           ");
                examCounter = 1;
                getExamCounter();
                getA1Counter();
                getA2Counter();
                getA3Counter();
                getIdCounter();
                getNameCounter();
                getQuizCounter();

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(), getA1Counter(), getA2Counter(),
                        getA3Counter(), getQuizCounter()) == true) {
                    buttonInsertRecord.setDisable(false);
                }

            }
        });

        // Creating a Grid Pane
        gridPane = new GridPane();

        // Setting size for the pane
        gridPane.setMinSize(600, 280);

        // Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(labelId, 0, 0);
        gridPane.add(textIDField, 1, 0);
        gridPane.add(idError, 2, 0);

        gridPane.add(labelStudentName, 0, 1);
        gridPane.add(textStudentNameField, 1, 1);
        gridPane.add(nameError, 2, 1);

        gridPane.add(labelQuiz, 0, 2);
        gridPane.add(textQuizField, 1, 2);
        gridPane.add(quizError, 2, 2);

        gridPane.add(labelAss1, 0, 3);
        gridPane.add(textAssignment1Field, 1, 3);
        gridPane.add(a1Error, 2, 3);

        gridPane.add(labelAss2, 0, 4);
        gridPane.add(textAssignment2Field, 1, 4);
        gridPane.add(a2Error, 2, 4);

        gridPane.add(labelAss3, 0, 5);
        gridPane.add(textAssignment3Field, 1, 5);
        gridPane.add(a3Error, 2, 5);

        gridPane.add(labelExam, 0, 6);
        gridPane.add(textExamField, 1, 6);
        gridPane.add(examError, 2, 6);

        //setting up all the elements in the box to center
        hBoxButton.setAlignment(Pos.CENTER);
        hBoxButton.getChildren().addAll(buttonInsertRecord, labelHolder);

        //setting up the padding
        labelHolder.setPadding(new Insets(20, 0, 20, 0));

        //setting up the color for the label
        idError.setTextFill(Color.RED);
        nameError.setTextFill(Color.RED);
        quizError.setTextFill(Color.RED);
        a1Error.setTextFill(Color.RED);
        a2Error.setTextFill(Color.RED);
        a3Error.setTextFill(Color.RED);
        examError.setTextFill(Color.RED);

        verticalBox.getChildren().addAll(headerLabelBox, gridPane, hBoxButton, labelHolder);

        // Creating a scene object
        Scene scene = new Scene(verticalBox);

        // Setting title to the Stage
        stage.setTitle("Insert Record");
        stage.setHeight(530);
        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.initOwner(stage1);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    //checks if all the field are valid or not
    public boolean checkIfValid(int counter, int counter1, int counter2, int counter3, int counter4, int counter5,
            int counter6) {
        if ((counter + counter1 + counter2 + counter3 + counter4 + counter5 + counter6) == 7) {

            return true;

        }

        return false;

    }

    //method to check if the ID entered is already in the database. 
    //Returns true if it is there, otherwise false
    public boolean duplicateChecker() {
        if (textIDField.getText().length() == 8) {
            for (int i = 0; i < idCollection.size(); i++) {
                if (idCollection.get(i).equals(textIDField.getText())) {
                    System.out.println("Duplicate found");
                    return true;

                }

            }
        }

        return false;
    }

    //method to calculate result. returns float value
    private Float calculateResult(String quiz, String a1, String a2, String a3, String exam) {
        // (Quiz * 0.05)+(A1* 0.15) +(A2* 0.2) + (A3* 0.10) + (Exam * 0.5)

        resultOfStudent = (Float.parseFloat(textQuizData) * 0.05f) + (Float.parseFloat(textAss1Data) * 0.15f)
                + (Float.parseFloat(textAss2Data) * 0.2f) + (Float.parseFloat(textAss3Data) * 0.10f)
                + (Float.parseFloat(textExamData) * 0.5f);
        System.out.println(resultOfStudent);
        return resultOfStudent;

    }

    //method to calculate grade
    private String calculateGrade(Float result) {

        // HD: Results>=85
        // DI: 75<=Results<85
        // CR: 65<=Results<75
        // PS: 50<=Results<65
        // FL: Results<50

        if (result < 50) {
            gradePasser = "FL";
        } else if (result < 65) {
            gradePasser = "PS";
        } else if (result < 75) {
            gradePasser = "CR";
        } else if (result < 85) {
            gradePasser = "PS";
        } else if (result <= 100) {
            gradePasser = "HD";
        }

        return gradePasser;

    }

    //if all the condition matches, it shows the result in the table
    public void showResultAfterButtonClick() {

        textIdData = textIDField.getText();
        textStudentData = textStudentNameField.getText();
        textQuizData = textQuizField.getText();
        textAss1Data = textAssignment1Field.getText();
        textAss2Data = textAssignment2Field.getText();
        textAss3Data = textAssignment3Field.getText();
        textExamData = textExamField.getText();
        Float dataResultStudent = calculateResult(textQuizData, textAss1Data, textAss2Data, textAss3Data, textExamData);
        String dataGradeStudent = calculateGrade(dataResultStudent);

        System.out.println(dataGradeStudent);

        try {

            String queryString = "UPDATE student SET student.StudentName  = ?,student.Quiz  = ?,student.A1  = ?,student.A2  = ? ,student.A3  = ?,student.Exam  = ?, student.Result = ?, student.Grade = ? WHERE student.ID ='"
                    + textIdData + "'";
            System.out.println(queryString);

            PreparedStatement preparedStatement = connection.prepareStatement(queryString);

            preparedStatement.setString(1, textStudentData);
            preparedStatement.setString(2, textQuizData);

            preparedStatement.setString(3, textAss1Data);
            preparedStatement.setString(4, textAss2Data);
            preparedStatement.setString(5, textAss3Data);

            preparedStatement.setString(6, textExamData);
            preparedStatement.setString(7, dataResultStudent.toString());
            preparedStatement.setString(8, dataGradeStudent);

            preparedStatement.executeUpdate();
            dataFromDatabase = new ShowResultData(textIdData, textStudentData, textQuizData, textAss1Data, textAss2Data,
                    textAss3Data, textExamData, dataResultStudent.toString(), dataGradeStudent);
            Stage tableStage = new Stage();

            TableDataView1.tableShow(dataFromDatabase);
            // statement.executeUpdate(queryString);
            //connection.close();
            System.out.println("The data has been added to the database");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //global counter
    static int counters = 1;

    //shows the result if all the criteria matches
    public void showResult() {
        ShowResultData dataFromDatabase1 = null;

        //storing the data from the textfield to the String variable
        stringInputValue = studentTextField.getText();
        //checks if the ID matches the criteria when user inputs it
        if (stringInputValue.matches("[0-99999999]+") == false | stringInputValue.length() != 8) {
            System.out.println("Please enter the valid number");
            errorAlert();
            studentTextField.clear();
            counters = 1;
        } else {
            try {
                // System.out.println(comboBoxValue);

                String queryString = "SELECT * FROM `student` WHERE student.ID = '" + stringInputValue + "'";
                System.out.println(queryString);

                ResultSet resultSet = statement.executeQuery(queryString);

                if (resultSet.next()) {
                    String iD = resultSet.getString(1);
                    String studentName = resultSet.getString(2);
                    String quiz = resultSet.getString(3);
                    String a1 = resultSet.getString(4);
                    String a2 = resultSet.getString(5);
                    String a3 = resultSet.getString(6);
                    String exam = resultSet.getString(7);
                    String result = resultSet.getString(8);
                    String grade = resultSet.getString(9);
                    dataFromDatabase1 = new ShowResultData(iD, studentName, quiz, a1, a2, a3, exam, result, grade);
                    showTextInputFields(iD, studentName, quiz, a1, a2, a3, exam);

                }

                //if ID enter by user is not there in data base, it shows error alert
                if (dataFromDatabase1 == null) {
                    idNotFoundAlert();
                    studentTextField.clear();
                    counters = 1;

                }

                

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }


    //error alert to prompt the user that the ID already exists
    public void errorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("Invalid ID error");
        alert.setContentText("The ID must be eight digits. Eg: 11111111");

        alert.showAndWait();
    }

    //alert for Id not found in the database
    public void idNotFoundAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("ID not found");
        alert.setContentText("The ID you have entered is not in the list");

        alert.showAndWait();
    }

}