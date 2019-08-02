/**
 * 
 * 
 * @author: Saurav Pradhan
 
 * 
 * 
 * This code is responsible for the insert of the data part of the code. This program will give the user 
 * with the form like field to enter all the necessary data about the student.
 * The fields are all validated.
 * 
 * 
 * If the user inputs the invalid data, this program will capture that. It has tooltip features
 * that lets user know what each field is suppose to hold
 * Also, it gives error message if the user does input the invalid parameters.
 * 
 * The user cannot insert untill all the fields have valid inputs.
 * The errors are displayed using alert as well as labels.
 * The errors are displayed using alert as well as labels.
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

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.*;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.text.DecimalFormat;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class InsertRecord extends Application {

    //data needed to connect to the database
    static final String USER = "root"; //username
    static final String PASSWORD = ""; // password which is null here
    static final String DB_URL = "jdbc:mysql://localhost/gradeprocessing"; //url to the localhost
    private static Connection connection;
    private static Statement statement;
    Float resultOfStudent;
    String gradePasser;
    static ArrayList<String> idCollection = new ArrayList<String>();
    static Label idError, nameError, quizError, a1Error, a2Error, a3Error, examError;

    //creating a global counters
    static int idCounter=1;
    static int nameCounter=1;
    static int quizCounter=1;
    static int a1Counter=1;
    static int a2Counter=1;
    static int a3Counter=1;
    static int examCounter=1;
    static int counter = 1;
    static int counters = 1;
    static boolean idChecker, idLength;
    static int counterLengthID = 1;

    //global counters----------------------------------------------//

    //declaring the textfield
    TextField textIDField, textStudentNameField, textQuizField, textAssignment1Field, textAssignment2Field,
            textAssignment3Field, textExamField;

    //declaring the string
    String textIdData, textStudentData, textQuizData, textAss1Data, textAss2Data, textAss3Data, textExamData;

    //the getter method
    public int getExamCounter(){
        return examCounter;
    }

    //the getter method
    public int getA3Counter(){
        return a3Counter;
    }

    //the getter method
    public int getA2Counter(){
        return a2Counter;
    }

    //the getter method
    public int getA1Counter(){
        return a1Counter;
    }

    //the getter method
    public int getQuizCounter(){
        return quizCounter;
    }

    //the getter method
    public int getNameCounter(){
        return nameCounter;
    }

    //the getter method
    public int getIdCounter(){
        return idCounter;
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
            //adding all the id to the arraylist to be used for later
            while (resultSet.next()) {
                String iD = resultSet.getString(1);
                idCollection.add(iD);
            }

            //sql exceptions
        } catch (SQLException sqlEx) {
            System.out.println("Error during SQL process");

        } catch (ClassNotFoundException classNotEx) {
            System.out.println("Error during finding the class");
        }
    }

    @Override
    public void start(Stage stage) {
        //calling the method to connect to the database
        initializeDB();

        // creating label 
        Text labelId = new Text("ID: ");
        Text labelStudentName = new Text("Student Name: ");
        Text labelQuiz = new Text("Quiz: ");
        Text labelAss1 = new Text("Assignment 1: ");
        Text labelAss2 = new Text("Assignment 2: ");
        Text labelAss3 = new Text("Assignment 3: ");
        Text labelExam = new Text("Exam: ");

    

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

        //tooltip and creation of text field end------------------------------//

        VBox hBoxButton = new VBox(15);
        VBox verticalBox = new VBox();
        HBox headerLabelBox = new HBox();

        //creating a label
        Label headerLabel = new Label("Insert a Record");
        //setting up the font
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        //adding elements to the box
        headerLabelBox.getChildren().addAll(headerLabel);
        headerLabelBox.setPadding(new Insets(20, 0, 20, 0));

        //aligning all the elements to the center
        headerLabelBox.setAlignment(Pos.CENTER);

        VBox labelHolder = new VBox();
        Label messageDisplay = new Label("");
        //setting up the color of label to white
        messageDisplay.setTextFill(Color.WHITE);

        //adding the label to the box
        labelHolder.getChildren().addAll(messageDisplay);
        
        
        

        labelHolder.setPadding(new Insets(20, 0, 20, 0));

        // Creating Buttons
        Button buttonInsertRecord = new Button("Insert Record");
        buttonInsertRecord.setMinWidth(300);
        buttonInsertRecord.setMinHeight(50);
        hBoxButton.setPadding(new Insets(20, 0, 20, 0));

        //on button press, if it is valid shows the label that the data has been entered to the database.
        buttonInsertRecord.setOnAction(e -> {

            if (idCollection.get(idCollection.size() - 1).equals(textIDField.getText()) == false & counter == 1) {
                showResult();
                labelHolder.setStyle("-fx-background-color: green;");
                labelHolder.setAlignment(Pos.CENTER);
                messageDisplay.setText("The data with ID: " + textIdData + " and with name: " + textStudentData
                        + " has been added to the database");

            }

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
                idError.setText("ID is already in the database");
                
                buttonInsertRecord.setDisable(true);
                idCounter = 0;
                System.out.println("3");
            }

            //checks for ID length
            if (counterLengthID > 1) {
                if (duplicateChecker() == false && textIDField.getText().length() != 8) {
                    idError.setText("This field accepts 8 digit ID. Example: 22222222");
                    
                    buttonInsertRecord.setDisable(true);
                    idCounter = 0;
                    System.out.println("3!");
                }

            }

            //if all the criteria matches, sets all the counter to 1, calls the method which then allows user to enter the option
            if (duplicateChecker() == false && idChecker == false && textIDField.getText().length() == 8) {
                
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
                    buttonInsertRecord.setDisable(false);
                  }
            }

            counterLengthID++;

        });

        //validating the textfields. checks if it matches the criteria for student name.
        //if it does not, it prompts the user with proper message
        textStudentNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!textStudentNameField.getText().matches(".*[a-z].*") || textStudentNameField.getText().length() > 100
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
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

                if (checkIfValid(getExamCounter(), getNameCounter(), getIdCounter(),getA1Counter(),getA2Counter(),getA3Counter(),getQuizCounter())== true) {
                    buttonInsertRecord.setDisable(false);
                  }

            }
        });


        


        // Creating a Grid Pane
        GridPane gridPane = new GridPane();

        // Setting size for the pane
        gridPane.setMinSize(600, 280);

        // Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        // Arranging all the nodes in the grid
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

        //setting up the color for the label
        idError.setTextFill(Color.RED);
        nameError.setTextFill(Color.RED);
        quizError.setTextFill(Color.RED);
        a1Error.setTextFill(Color.RED);
        a2Error.setTextFill(Color.RED);
        a3Error.setTextFill(Color.RED);
        examError.setTextFill(Color.RED);

        hBoxButton.getChildren().addAll(buttonInsertRecord,labelHolder);

        verticalBox.getChildren().addAll(headerLabelBox, gridPane, hBoxButton);
        // Creating a scene object
        Scene scene = new Scene(verticalBox);

        // Setting title to the Stage
        stage.setTitle("Insert Record");
        stage.setHeight(525);
        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }

    //checks if all the field are valid or not
    public boolean checkIfValid(int counter, int counter1, int counter2, int counter3, int counter4, int counter5, int counter6) {
        if ((counter + counter1 + counter2+ counter3+ counter4+ counter5+ counter6) == 7) {
    
          return true;
    
        }
    
        return false;
    
    }

    //this method shows the result 
    public void showResult() {

        //getting data from the text field and storing it to the String variable
        textIdData = textIDField.getText();
        textStudentData = textStudentNameField.getText();
        textQuizData = textQuizField.getText();
        textAss1Data = textAssignment1Field.getText();
        textAss2Data = textAssignment2Field.getText();
        textAss3Data = textAssignment3Field.getText();
        textExamData = textExamField.getText();

        Float dataResultStudent = calculateResult(textQuizData, textAss1Data, textAss2Data, textAss3Data, textExamData);
        String dataGradeStudent = calculateGrade(dataResultStudent);

        try {
            //sql query to be
            String queryString = "INSERT INTO student (student.ID,student.StudentName,student.Quiz,student.A1,student.A2 ,student.A3,student.Exam , student.Result, student.Grade)"
                    + "VALUES (?, ?, ?,?, ?, ?,?, ?, ?)";
            System.out.println(queryString);

            PreparedStatement preparedStatement = connection.prepareStatement(queryString);

            preparedStatement.setString(1, textIdData);
            preparedStatement.setString(2, textStudentData);
            preparedStatement.setString(3, textQuizData);

            preparedStatement.setString(4, textAss1Data);
            preparedStatement.setString(5, textAss2Data);
            preparedStatement.setString(6, textAss3Data);

            preparedStatement.setString(7, textExamData);
            preparedStatement.setString(8, dataResultStudent.toString());
            preparedStatement.setString(9, dataGradeStudent);
            //executes the query
            preparedStatement.executeUpdate();

            //closes the connection
            connection.close();
            System.out.println("The data has been added to the database");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //show alert about the appropriate message
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    //method to calculate result. returns float value
    private Float calculateResult(String quiz, String a1, String a2, String a3, String exam) {
        // (Quiz * 0.05)+(A1* 0.15) +(A2* 0.2) + (A3* 0.10) + (Exam * 0.5)
       
        Float resultOfStudent = (Float.parseFloat(textQuizData) * 0.05f) + (Float.parseFloat(textAss1Data) * 0.15f)
                + (Float.parseFloat(textAss2Data) * 0.2f) + (Float.parseFloat(textAss3Data) * 0.10f)
                + (Float.parseFloat(textExamData) * 0.5f);
        System.out.println(resultOfStudent);

      
        return resultOfStudent;

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
            gradePasser = "DI";
        } else if (result <= 100) {
            gradePasser = "HD";
        }

        return gradePasser;

    }

    //error alert to prompt the user that the ID already exists
    public void errorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Occured");
        alert.setHeaderText("Duplicate ID found");
        alert.setContentText("The ID you entered is already in the database. Please check ID and enter again");

        alert.showAndWait();
    }

    public static void main(String args[]) {
        initializeDB();
        launch(args);

    }
}