/**
 * 
 * 
 * @author: Saurav Pradhan
 * 
 * This is a simple class that is responsible for displaying the data to the table.
 * It is a class for creating an object can be called in the table to view that data to the user
 * This class also gives different method to the user so the data can be displayed 
 * according to the criteria needed by the user
 * 
 * This mainly gives struture to the data and the way the data is stored so that it can be retrived later.
 * 
 * 
 */
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

public class ShowResultData {

    //declaring and instanciating the variables.
    private SimpleStringProperty studentIdCol = new SimpleStringProperty("");
    private SimpleStringProperty studentNameCol = new SimpleStringProperty("");
    private SimpleStringProperty quizCol = new SimpleStringProperty("");
    private SimpleStringProperty assignment1Col = new SimpleStringProperty("");
    private SimpleStringProperty assignment2Col = new SimpleStringProperty("");
    private SimpleStringProperty assignment3Col = new SimpleStringProperty("");
    private SimpleStringProperty examCol = new SimpleStringProperty("");
    private SimpleStringProperty resultsCol = new SimpleStringProperty("");

    
    private SimpleStringProperty gradeCol = new SimpleStringProperty("");

    //constructor of the class
    public ShowResultData(String studentIdCol1, String studentNameCol1, String quizCol1, String assignment1Col1,
            String assignment2Col1, String assignment3Col1, String examCol1, String resultsCol1, String gradeCol1) {

        this.studentIdCol = new SimpleStringProperty(studentIdCol1);
        this.studentNameCol = new SimpleStringProperty(studentNameCol1);
        this.assignment1Col = new SimpleStringProperty(assignment1Col1);
        this.assignment2Col = new SimpleStringProperty(assignment2Col1);
        this.assignment3Col = new SimpleStringProperty(assignment3Col1);
        this.examCol = new SimpleStringProperty(examCol1);
        this.quizCol = new SimpleStringProperty(quizCol1);
        this.resultsCol = new SimpleStringProperty(resultsCol1);
        this.gradeCol = new SimpleStringProperty(gradeCol1);

    }

    
    //getter method
    public String getStudentIdCol() {
        return studentIdCol.get();
    }

     //getter method
    public String getStudentNameCol() {
        return studentNameCol.get();
    }

     //getter method
    public String getQuizCol() {
        return quizCol.get();
    }

     //getter method
    public String getAssignment1Col() {
        return assignment1Col.get();
    }

     //getter method
    public String getAssignment2Col() {
        return assignment2Col.get();
    }

     //getter method
    public String getAssignment3Col() {
        return assignment3Col.get();
    }

     //getter method

    public String getExamCol() {
        return examCol.get();
    }

    //the name should be the same eg:ResultCol is variable name then the getter should also have same name getResultCol
    public String getResultsCol() {
        return resultsCol.get();
    }

     //getter method
    public String getGradeCol() {
        return gradeCol.get();
    }

    //getter method
    public String toString(){
        return studentIdCol.get() + studentNameCol.get() + resultsCol.get()+ gradeCol.get();
    }
}
