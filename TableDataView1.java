/**
 * 
 * 
 * @author: Saurav Pradhan
 * 
 * This class is responsible for displaying the table when it is required for the program.
 * It calls the ShowResultData class, so that the data is structured properly.
 * After recieving the objects, it then displays the data according to the criteria.
 * This is the class responsilbe for all the data in whole program.
 * Every code that requires table, calls this class and then the table is displayed 
 * according to the criteria needed for displaying the data.
 * 
 * 
 */

//importing the necessary libraries
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;

public class TableDataView1 {

    private static ArrayList<ShowResultData> arrayList;
    public static ShowResultData dataForList;
    private static ObservableList<ShowResultData> data;
    private static ObservableList<ShowResultData> data1;
    private static boolean tableNotifierGrade;
    private static boolean tableNotifierResult;
    
    
    private ObservableList<ShowResultData> dataList = FXCollections.observableArrayList(dataForList);

    static Label label = new Label("Result of the Student");

    //constructor1
    public TableDataView1(ArrayList<ShowResultData> arrayList) {
        this.arrayList = arrayList;
    }
    //constructor 2
    public TableDataView1(ShowResultData datas) {
        dataForList = datas;
    }


    // when this method is called, it shows table with all the details of the student 
    public static void tableShow(ArrayList<ShowResultData> dataFromUser) {

        //instanciating the tableView
        TableView<ShowResultData> table = new TableView<ShowResultData>();
        VBox vbox = new VBox();
        data = FXCollections.observableArrayList(dataFromUser);
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(950);
        stage.setHeight(500);
    
        //setting up the font type and the size
        label.setFont(new Font("Arial", 20));

        //setting the table to edit able mode
        table.setEditable(true);
        TableColumn studentIdCols = new TableColumn("IdCol");
        studentIdCols.setMinWidth(100);
        studentIdCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentIdCol"));

        TableColumn studentNameCols = new TableColumn("StudentName");
        studentNameCols.setMinWidth(100);
        studentNameCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentNameCol"));

        TableColumn quizCols = new TableColumn("Quiz");
        quizCols.setMinWidth(100);
        quizCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("quizCol"));

        TableColumn assignment1Cols = new TableColumn("A1");
        assignment1Cols.setMinWidth(100);
        assignment1Cols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("assignment1Col"));

        TableColumn assignment2Cols = new TableColumn("A2");
        assignment2Cols.setMinWidth(100);
        assignment2Cols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("assignment2Col"));

        TableColumn assignment3Cols = new TableColumn("A3");
        assignment3Cols.setMinWidth(100);
        assignment3Cols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("assignment3Col"));

        TableColumn examCols = new TableColumn("Exam");
        examCols.setMinWidth(100);
        examCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("examCol"));

        TableColumn gradeCols = new TableColumn("Grade");
        gradeCols.setMinWidth(100);
        gradeCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("gradeCol"));

        TableColumn resultsCols = new TableColumn("Results");
        resultsCols.setMinWidth(100);
        resultsCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("resultsCol"));

        //setting up the data in the table
        table.setItems(data);

        // this is not an error
        //adding all the columns
        table.getColumns().addAll(studentIdCols, studentNameCols, quizCols, assignment1Cols, assignment2Cols,
                assignment3Cols, examCols, resultsCols, gradeCols);

        // final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        //adding all the element to the vertical box
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static void tableShow(ShowResultData dataFromUser) {
        TableView<ShowResultData> table = new TableView<ShowResultData>();
        VBox vbox = new VBox();
        data = FXCollections.observableArrayList(dataFromUser);
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(950);
        stage.setHeight(500);

        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        TableColumn studentIdCols = new TableColumn("IdCol");
        studentIdCols.setMinWidth(100);
        studentIdCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentIdCol"));

        TableColumn studentNameCols = new TableColumn("StudentName");
        studentNameCols.setMinWidth(100);
        studentNameCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentNameCol"));

        TableColumn quizCols = new TableColumn("Quiz");
        quizCols.setMinWidth(100);
        quizCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("quizCol"));

        TableColumn assignment1Cols = new TableColumn("A1");
        assignment1Cols.setMinWidth(100);
        assignment1Cols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("assignment1Col"));

        TableColumn assignment2Cols = new TableColumn("A2");
        assignment2Cols.setMinWidth(100);
        assignment2Cols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("assignment2Col"));

        TableColumn assignment3Cols = new TableColumn("A3");
        assignment3Cols.setMinWidth(100);
        assignment3Cols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("assignment3Col"));

        TableColumn examCols = new TableColumn("Exam");
        examCols.setMinWidth(100);
        examCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("examCol"));

        TableColumn gradeCols = new TableColumn("Grade");
        gradeCols.setMinWidth(100);
        gradeCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("gradeCol"));

        TableColumn resultsCols = new TableColumn("Results");
        resultsCols.setMinWidth(100);
        resultsCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("resultsCol"));

        table.setItems(data);

        // this is not an error
        table.getColumns().addAll(studentIdCols, studentNameCols, quizCols, assignment1Cols, assignment2Cols,
                assignment3Cols, examCols, resultsCols, gradeCols);

        // final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    static int counter = 1;

    //when this method is called, it launches the table view showing the studentID, name and grade
    public static void calcGrade(ShowResultData dataFromUsers) {
        TableView<ShowResultData> table1 = new TableView<ShowResultData>();
        VBox vbox = new VBox();

        //creating a stage
        Stage stage1 = new Stage();

        //creating a scene
        Scene scene = new Scene(new Group());

        stage1.setTitle("Table View Grade");
        stage1.setWidth(350);
        stage1.setHeight(500);
        //seting the font to the label
        label.setFont(new Font("Arial", 20));

        //setting the table view to be editable
        table1.setEditable(true);
        TableColumn studentIdCols = new TableColumn("IdCol");
        studentIdCols.setMinWidth(100);
        studentIdCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentIdCol"));

        TableColumn studentNameCols = new TableColumn("StudentName");
        studentNameCols.setMinWidth(100);
        studentNameCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentNameCol"));

        TableColumn gradeCols = new TableColumn("Grade");
        gradeCols.setMinWidth(100);
        gradeCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("gradeCol"));

        

            ObservableList<ShowResultData> data1 = FXCollections.observableArrayList(dataFromUsers);
            System.out.println(data1);
            //setting up the data in the table
            table1.setItems(data1);

            // this is not an error
           
                
            
               //adding up all the columns
            table1.getColumns().addAll(studentIdCols, studentNameCols, gradeCols);
               
            
            
            counter++;
        
        

        // final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(table1);
        

        ((Group) scene.getRoot()).getChildren().add(vbox);

        stage1.setScene(scene);

        stage1.show();

    }

    static int counterGrade = 1;

    //when this method is called, it launches the table view showing the studentID, name and result
    public static void calcResult(ShowResultData dataFromUsers) {
        TableView<ShowResultData> table = new TableView<ShowResultData>();
        VBox vbox = new VBox();

        Stage stage = new Stage();
        Scene scene = new Scene(new Group());

        stage.setTitle("Table View Result");
        stage.setWidth(350);
        stage.setHeight(500);

        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        TableColumn studentIdCols = new TableColumn("IdCol");
        studentIdCols.setMinWidth(100);
        studentIdCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentIdCol"));

        TableColumn studentNameCols = new TableColumn("StudentName");
        studentNameCols.setMinWidth(100);
        studentNameCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("studentNameCol"));

    
        TableColumn resultsCols = new TableColumn("Results");
        resultsCols.setMinWidth(100);
        resultsCols.setCellValueFactory(new PropertyValueFactory<ShowResultData, String>("resultsCol"));

        
        

            ObservableList<ShowResultData> data1 = FXCollections.observableArrayList(dataFromUsers);
            System.out.println(data1);
            //passing the data to the table
            table.setItems(data1);

            // this is not an error
                //adding all the columns
                table.getColumns().addAll(studentIdCols, studentNameCols, resultsCols);
                System.out.println("First result loop was activated");
                
                tableNotifierResult = true;
            
            
        
         

        

        // final VBox vbox = new VBox();
        vbox.setSpacing(5);
        //setting up the padding for the vertical box
        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(table);
        

        ((Group) scene.getRoot()).getChildren().add(vbox);

        stage.setScene(scene);

        stage.show();

    }

}