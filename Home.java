
/**
 * 
 * 
 * @author: Saurav Pradhan
 
 * 
 * This is the main interface for the program. This program will give user with all the option in the assignmentSystem
 * It has simple UI interface. The user can select one of the option from the six options. 
 * The program will then display another window that will allows user to do the intended task.
 * Also, once another window popsup, the user cannot go to the previous window untill the current window is closed. 
 * This is done so that the user can always have access to the home of the UI.
 * 
 * Once the user selects one of the option, then user can do the intended task.
 * 
 */

//importing the necessary libraries for the code.
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

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

import javafx.stage.Window;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.sql.DatabaseMetaData;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Home extends Application {

    //declaring the buttons and label
    public static Button insertRecordButton, updateTableButton, createTableButton, calculateResultButton,
            calculateGradeButton, searchTableButton;
    public static Label headerGridText;

    //data needed to connect to the database
    static final String USER = "root";
    static final String PASSWORD = "";
    static final String DB_URL = "jdbc:mysql://localhost/gradeprocessing";
    private static Connection connection;
    private static Statement statement;
    private static DatabaseMetaData dbMetaData;
    public static int gradeCounter, updateCounter, insertCounter, resultCounter, searchCounter ;

    //global counter
    static int counterr = 0;

    public static void main(String[] args) {
        initializeDB();

        launch(args);

    }

    //method that connects this code to the database
    //any type of exception is handled 
    private static void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded");

            // connecting to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected");

            statement = connection.createStatement();
            dbMetaData = connection.getMetaData();
            ResultSet resultSet = statement.executeQuery("select * from student");

        } catch (SQLException sqlEx) {
            System.out.println("Error during SQL process");

        } catch (ClassNotFoundException classNotEx) {
            System.out.println("Error during finding the class");
        }
    }

    //the main UI of this code
    public void start(Stage stage) {
        
        VBox verticalBox = new VBox();
        //label for the header part
        Label headerText = new Label("Welcome to Grade Processing System");
        headerText.setFont(Font.font("Calibri", FontWeight.BOLD, 40));

        //creating the grid view to keep the 6 button or features
        GridPane gridPane = new GridPane();

        Label detailText = new Label(
                "\nThis program performs following tasks \n Create table \n Insert a Record \n Search a record \n Update a record \n Calculate Results and Grade ");
        detailText.setFont(Font.font("Ubuntu", FontWeight.NORMAL, 14));

        VBox headerBox = new VBox();
        headerBox.setPadding(new Insets(20, 20, 20, 20));
        //setting the background to green
        headerBox.setStyle("-fx-background-color: white;");
        headerBox.getChildren().addAll(headerText, detailText);
        headerBox.setMinHeight(150);

        VBox gridHeaderHolder = new VBox();
        headerGridText = new Label("Please select one of the options from above");
        headerGridText.setFont(Font.font("Ubuntu", FontWeight.NORMAL, 14));
        gridHeaderHolder.getChildren().addAll(headerGridText);
        gridHeaderHolder.setAlignment(Pos.CENTER);
        gridHeaderHolder.setStyle("-fx-background-color: white;");
        gridHeaderHolder.setMinHeight(40);

        VBox gridHolder = new VBox();
        gridHolder.setStyle("-fx-background-color: green;");
        gridHolder.getChildren().addAll(gridPane, gridHeaderHolder);

        //getting the image from the location
        Image image = new Image(getClass().getResourceAsStream("table.png"));
        //creating an object
        ImageView createTablePic = new ImageView(image);
        createTablePic.setFitHeight(40);
        createTablePic.setFitWidth(40);
        //instanciating the button with text and picture
        createTableButton = new Button("Create Table",createTablePic );

        //getting the image from the location
        Image image1 = new Image(getClass().getResourceAsStream("insert.png"));
        //creating an object
        ImageView insertTablePic = new ImageView(image1);
        insertTablePic.setFitHeight(40);
        insertTablePic.setFitWidth(40);
        //instanciating the button with text and picture
        insertRecordButton = new Button("Insert into the table",insertTablePic );

        Image image2 = new Image(getClass().getResourceAsStream("search.png"));
        //creating an object
        ImageView searchTablePic = new ImageView(image2);
        searchTablePic.setFitHeight(40);
        searchTablePic.setFitWidth(40);
        //instanciating the button with text and picture
        searchTableButton = new Button("Search the table",searchTablePic );

        Image image3 = new Image(getClass().getResourceAsStream("update.png"));
        //creating an object
        ImageView updateTablePic = new ImageView(image3);
        updateTablePic.setFitHeight(40);
        updateTablePic.setFitWidth(40);
        //instanciating the button with text and picture
        updateTableButton = new Button("Update the table",updateTablePic );

        Image image4 = new Image(getClass().getResourceAsStream("grade.png"));
        //creating an object
        ImageView gradePic = new ImageView(image4);
        gradePic.setFitHeight(40);
        gradePic.setFitWidth(40);
        //instanciating the button with text and picture
        calculateGradeButton = new Button("Calculate Grade",gradePic );

        Image image5 = new Image(getClass().getResourceAsStream("result.png"));
        //creating an object
        ImageView resultPic = new ImageView(image5);
        resultPic.setFitHeight(40);
        resultPic.setFitWidth(40);
        //instanciating the button with text and picture
        calculateResultButton = new Button("Calculate Result",resultPic );

        
        
        //calling the checktable method
        checkTable();

        //creating different stages for the features to be launched on
        Stage insertStage = new Stage();
        Stage updateStage = new Stage();
        Stage createTableStage = new Stage();
        Stage gradeStage = new Stage();
        Stage resultStage = new Stage();
        Stage searchStage = new Stage();

        
        //decorating the buttons
        createTableButton.setMinWidth(180);
        createTableButton.setMinHeight(120);

        insertRecordButton.setMinWidth(180);
        insertRecordButton.setMinHeight(120);
        

        searchTableButton.setMinWidth(180);
        searchTableButton.setMinHeight(120);

        calculateGradeButton.setMinWidth(180);
        calculateGradeButton.setMinHeight(120);

        calculateResultButton.setMinWidth(180);
        calculateResultButton.setMinHeight(120);

        updateTableButton.setMinWidth(180);
        updateTableButton.setMinHeight(120);
//---------------decorating the button end-----------------------------//

        //on selecting this option, new stage is launched
        insertRecordButton.setOnAction(e -> {

            InsertRecord newRecord = new InsertRecord();
            if(insertCounter == 0){
                insertStage.initOwner(stage);
                insertStage.initModality(Modality.APPLICATION_MODAL); 
                insertCounter++;
            }

            newRecord.start(insertStage);

        });

        //on selecting this option, new stage is launched
        createTableButton.setOnAction(e -> {
            initializeDB();
            try {

                ResultSet rsTables = dbMetaData.getTables(null, null, "Java2", null);
                //if the database does not exists.
                //all the other five feature is disabled.
                //It prompts user to create a database.
                if (rsTables.next()) {
                    System.out.print(rsTables.getString("TABLE_NAME") + " ");
                    errorAlertBox();
                    if (counterr == 1) {
                        insertRecordButton.setDisable(true);
                        updateTableButton.setDisable(true);
                        calculateResultButton.setDisable(true);
                        calculateGradeButton.setDisable(true);
                        searchTableButton.setDisable(true);

                        counterr = 0;
                    }

                } else {
                    try {
                        //query for creation of table
                        String sql = "CREATE TABLE Java2 " + "(ID VARCHAR(255) not NULL, " + "StudentName VARCHAR(255), "
                                + " Quiz VARCHAR(255), " + " A1 VARCHAR(3), " + " A2 VARCHAR(3), "+ " A3 VARCHAR(3), " + " Exam VARCHAR(3), "+ " Results VARCHAR(6), "+ " Grade VARCHAR(2), "+ " PRIMARY KEY ( ID ))";

                        //execute the query
                        statement.executeUpdate(sql);
                        System.out.println("Created table in given database...");
                        //gives access to all the other five feature
                        showAlert();
                        insertRecordButton.setDisable(false);
                        updateTableButton.setDisable(false);
                        calculateResultButton.setDisable(false);
                        calculateGradeButton.setDisable(false);
                        searchTableButton.setDisable(false);
                    } catch (SQLException se) {
                        // Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception ex) {
                        // Handle errors for Class.forName
                        // ex.printStackTrace();
                        System.out.println("Error");

                    } finally {
                        // finally block used to close resources
                        try {
                            if (statement != null)
                                connection.close();
                        } catch (SQLException se) {
                        } // do nothing
                        try {
                            if (connection != null)
                                connection.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        } // end finally try
                    } // end try
                }
            } catch (SQLException se) {
                // Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception ex) {
                // Handle errors for Class.forName
                // ex.printStackTrace();
                ex.printStackTrace();

            }

        });

        //on selecting this option, new stage is launched
        searchTableButton.setOnAction(e -> {

            GradeProcessing searchTable = new GradeProcessing();

            if(searchCounter == 0){
                searchStage.initOwner(stage);
                searchStage.initModality(Modality.APPLICATION_MODAL); 
                searchCounter++;
            }

            searchTable.start(searchStage);

        });

        //on selecting this option, new stage is launched
        calculateGradeButton.setOnAction(e -> {

            CalculateGrade newGrade = new CalculateGrade();
            
            if(gradeCounter == 0){
                gradeStage.initOwner(stage);
                gradeStage.initModality(Modality.APPLICATION_MODAL); 
                gradeCounter++;
            }
            
            
            // gradeStage.showAndWait();
            newGrade.start(gradeStage);

        });

        //on selecting this option, new stage is launched
        calculateResultButton.setOnAction(e -> {

            CalculateResult newResult = new CalculateResult();

            if(resultCounter == 0){
                resultStage.initOwner(stage);
                resultStage.initModality(Modality.APPLICATION_MODAL); 
                resultCounter++;
            }

            newResult.start(resultStage);

        });

        //on selecting this option, new stage is launched
        updateTableButton.setOnAction(e -> {

            UpdateTable newUpdate = new UpdateTable();

            if(updateCounter == 0){
                updateStage.initOwner(stage);
                updateStage.initModality(Modality.APPLICATION_MODAL); 
                updateCounter++;
            }

            newUpdate.start(updateStage);

        });

        

        // Creating a Grid Pane

        // Setting size for the pane
        gridPane.setMinSize(600, 340);

        // Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        // Arranging all the nodes in the grid
        gridPane.add(createTableButton, 0, 0);
        gridPane.add(insertRecordButton, 1, 0);

        gridPane.add(searchTableButton, 2, 0);
        gridPane.add(updateTableButton, 0, 1);

        gridPane.add(calculateResultButton, 1, 1);
        gridPane.add(calculateGradeButton, 2, 1);

        verticalBox.getChildren().addAll(headerBox, gridHolder);
        // Creating a scene object
        Scene scene = new Scene(verticalBox);
        scene.getStylesheets().add("style.css");
        // Setting title to the Stage
        stage.setTitle("Grade Processing System");
        stage.setHeight(640);
        stage.setWidth(900);
        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }

    //this alert box pops up, notify the user if they want to delete the existing table
    //if they press ok , the table is deleted, if the user presses cancel, nothing happens
    //and pop up gets closed.
    public static void errorAlertBox() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Table Status");
        alert.setHeaderText("Table Already Exists!!!");
        alert.setContentText("Do you want to delete the table");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            try {
                //sql query for deleting the table
                PreparedStatement st = connection.prepareStatement("DROP TABLE Java2");
                st.executeUpdate();
                System.out.println("Table is deleted");
                counterr = 1;
            } catch (SQLException se) {
                // Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception ex) {
                // Handle errors for Class.forName
                // ex.printStackTrace();
                System.out.println("Error");

            }
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel button was pressed");
            alert.close();
        }

        // } else {
        // System.out.println("Cancel button was pressed");

        // }

    }

    //alert box for showing that the table has been created.
    private static void showAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Table Status");

        // alert.setHeaderText("Results:");
        alert.setContentText("Table has been sucessfully created");

        alert.showAndWait();

    }

    //method that checks if the table is already created or not.
    //if table is not created then all the other button is disabled.
    public static void checkTable() {
        try {
            ResultSet rsTables = dbMetaData.getTables(null, null, "Java2", null);

            if (rsTables.next()) {
                System.out.println("Table already exists");
                headerGridText.setText("Connected table: Java 2");

            } else {
                System.out.println("Table does not exists");
                insertRecordButton.setDisable(true);
                updateTableButton.setDisable(true);
                calculateResultButton.setDisable(true);
                calculateGradeButton.setDisable(true);
                searchTableButton.setDisable(true);
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception ex) {
            // Handle errors for Class.forName
            // ex.printStackTrace();
            System.out.println("Error");
        }
    }
}