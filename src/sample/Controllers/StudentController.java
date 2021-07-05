package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Student;

//import javax.xml.catalog.BaseEntry;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class StudentController implements Initializable {

    //khai báo các trường ở bảng column dữ liệu
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student,String> idCol;
    @FXML
    private TableColumn<Student,String> nameCol;
    @FXML
    private TableColumn<Student,String> genderCol;
    @FXML
    private TableColumn<Student,String> DateCol;
    @FXML
    private TableColumn<Student,String> EmailCol;
    @FXML
    private TableColumn<Student,String> phoneCol;
    @FXML
    private TableColumn<Student, Double> scoreCol;


    private ObservableList<Student> listStudents;

    //textField
    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField genderText;
    @FXML
    private TextField DateofBirthText;
    @FXML
    private TextField EmailText;
    @FXML
    private TextField phoneNumberText;
    @FXML
    private TextField scoreText;
    @FXML
    private TextField filterText;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Student newStudent = new Student();
        listStudents = FXCollections.observableArrayList(
                new Student("a36341","nguyen quang vinh","nam","12/4/2000","a36341@thanglong.edu.vn","0944864265",10),
                new Student("a32367","luu hoang nam","nam","2001","a32367@thanglong.edu.vn","0123456789",10)
        );
        idCol.setCellValueFactory(new PropertyValueFactory<Student,String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<Student,String>("gender"));
        DateCol.setCellValueFactory(new PropertyValueFactory<Student,String>("DateOfBirth"));
        EmailCol.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Student,String>("PhoneNumber"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Student,Double>("score"));
        table.setItems(listStudents);

    }
    //save data to file
    public void write(){
        try{
            WRITE("studentManager.txt",listStudents);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //write data file student.txt
    private void WRITE(String filename, ObservableList<Student> listStudents) throws IOException {
        FileWriter write = new FileWriter(filename);
        for(Student student : listStudents) {
            write.write(student.getId()+","+student.getName()+","+student.getGender()+","+student.getDateOfBirth()
                        +","+student.getEmail()+","+student.getPhoneNumber()+","+student.getScore()+"\n");
        }
        write.close();

    }

    private static List<Student> readStudents(String filename) throws IOException{
        List<Student> studentlist = new ArrayList<>();
        String temp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("studentManager.txt")));
        while((temp = reader.readLine()) != null){
            String[] s = temp.split(",");
            studentlist.add(new Student(s[0],s[1],s[2],s[3],s[4],s[5],Double.parseDouble(s[6])));

        }
        return studentlist;
    }

    public List<Student> readList(){
        List<Student> inputSV = null;
        try {
            inputSV = readStudents("studentManager.txt");
        }catch(IOException e){
            e.printStackTrace();
        }
        return inputSV;
    }

    public void Backspace(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/workspace.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addItem(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Xác Nhận");
        alert.setHeaderText("Xác nhận thêm sinh viên");
        alert.setContentText("Bạn có xác nhận thêm sinh viên này?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo,buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeYes) {
            Student newStudents = new Student();
            newStudents.setId(idText.getText());
            newStudents.setName(nameText.getText());
            newStudents.setGender(genderText.getText());
            newStudents.setDateOfBirth(DateofBirthText.getText());
            newStudents.setEmail(EmailText.getText());
            newStudents.setPhoneNumber(phoneNumberText.getText());
            newStudents.setScore(Double.parseDouble(scoreText.getText()));
            listStudents.add(newStudents);

            write();
        }
        else {
            resetText();
            Alert alertAdd = new Alert(Alert.AlertType.INFORMATION);
            alertAdd.setTitle("Information");
            alertAdd.setContentText("Chưa thêm");
            alertAdd.show();

            write();
        }

    }

    public void resetText(){
        idText.setText("");
        nameText.setText("");
        genderText.setText("");
        DateofBirthText.setText("");
        EmailText.setText("");
        phoneNumberText.setText("");
        scoreText.setText("");
    }


    public void EditItem(ActionEvent actionEvent) {
        Alert alertEdit1 = new Alert(Alert.AlertType.INFORMATION);
        alertEdit1.setTitle("Xác Nhận");
        alertEdit1.setHeaderText("Xác nhận sửa thông tin?");
        alertEdit1.setContentText("Bạn có xác nhận sửa thông tin này?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertEdit1.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo,buttonTypeCancel);
        alertEdit1.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo,buttonTypeCancel);

        Optional<ButtonType> result = alertEdit1.showAndWait();
        if(result.get() == buttonTypeYes){
            Student select = table.getSelectionModel().getSelectedItem();
            Student newStudents = new Student();
            for (Student student : listStudents) {
                if(student == select){
                    newStudents.setId(idText.getText());
                    newStudents.setName(nameText.getText());
                    newStudents.setGender(genderText.getText());
                    newStudents.setDateOfBirth(DateofBirthText.getText());
                    newStudents.setEmail(EmailText.getText());
                    newStudents.setPhoneNumber(phoneNumberText.getText());
                    newStudents.setScore(Double.parseDouble(scoreText.getText()));
                    listStudents.set(listStudents.indexOf(student), newStudents);
                }
            }
        }
        else {
            resetText();
            Alert alertEdit2 = new Alert(Alert.AlertType.INFORMATION);
            alertEdit2.setTitle("Information");
            alertEdit2.setHeaderText("Sửa thành công");
            alertEdit2.show();

            write();
        }

    }

    public void DeleteItem(ActionEvent actionEvent) {
        Alert alertDel = new Alert(Alert.AlertType.INFORMATION);
        alertDel.setTitle("Xác Nhận");
        alertDel.setHeaderText("Xác nhận xoá thông tin?");
        alertDel.setContentText("Bạn có xác nhận xoá thông tin này?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertDel.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo,buttonTypeCancel);
        alertDel.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo,buttonTypeCancel);

        Optional<ButtonType> result = alertDel.showAndWait();
        if(result.get() == buttonTypeYes){
            Student select = table.getSelectionModel().getSelectedItem();
            listStudents.remove(select);
        }
        else{
            Alert alertDel2 = new Alert(Alert.AlertType.INFORMATION);
            alertDel2.setTitle("Information");
            alertDel2.setHeaderText("Xoá thành công");
            alertDel2.show();

            write();
        }
    }

    public void FilterItem(ActionEvent actionEvent) {
        FilteredList<Student> filtered = new FilteredList<>(listStudents,e -> true);
        filterText.setOnKeyReleased(e->{
            filterText.textProperty().addListener((observableValue, oldValue, newValue)-> {
                filtered.setPredicate((Predicate<? super Student>) Student->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    if(Student.getScore() >= Double.parseDouble(filterText.getText())){
                        return true;
                    }
                    return false;
                });
            });
        });
        SortedList<Student> sortData = new SortedList<>(filtered);
        sortData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(filtered);
    }

    //get data from tableview
    @FXML
    public void handleClickView(MouseEvent mouseEvent) {
        Student students = table.getSelectionModel().getSelectedItem();
        String str = String.valueOf(students.getScore());
        if(students != null){
            idText.setText(students.getId());
            nameText.setText(students.getName());
            genderText.setText(students.getGender());
            DateofBirthText.setText(students.getDateOfBirth());
            EmailText.setText(students.getEmail());
            phoneNumberText.setText(students.getPhoneNumber());
            scoreText.setText(str);

        }

    }
}
