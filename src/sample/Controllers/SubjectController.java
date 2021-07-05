package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Student;
import sample.Subjects;

import javax.security.auth.Subject;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SubjectController implements Initializable {

    @FXML
    public TextField idSub;
    @FXML
    public TextField nameSub;
    @FXML
    public TextField creSub;
    @FXML
    public TextField factor;


    @FXML
    private TableView<Subjects> table;
    @FXML
    private TableColumn<Subjects,String> SubIDCol;
    @FXML
    private TableColumn<Subjects,String> SubNameCol;
    @FXML
    private TableColumn<Subjects,String> creditSubCol;
    @FXML
    private TableColumn<Subjects,String> factorSubCol;

    private ObservableList<Subjects> listSubject;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Subjects newSubject = new Subjects();
        listSubject = FXCollections.observableArrayList(
                new Subjects("CS223", "Lập trình Java",3,4.8)
        );
        SubIDCol.setCellValueFactory(new PropertyValueFactory<Subjects,String>("subID"));
        SubNameCol.setCellValueFactory(new PropertyValueFactory<Subjects, String>("subName"));
        creditSubCol.setCellValueFactory(new PropertyValueFactory<Subjects,String>("creditSub"));
        factorSubCol.setCellValueFactory(new PropertyValueFactory<Subjects, String>("factorSub"));
        table.setItems(listSubject);


    }

    public void write(){
        try{
            WRITE("subjectManager.txt",listSubject);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void WRITE(String filename, ObservableList<Subjects> listSubject) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(Subjects subject : listSubject){
            writer.write(subject.getSubID()+","+subject.getSubName()+","+subject.getCreditSub()+","+subject.getFactorSub()+"\n");

        }
        writer.close();

    }
    private static List<Subjects> readSubject(String filename) throws IOException {
        List<Subjects> subjects = new ArrayList<Subjects>();
        String temp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("subjectManager.txt")));
        while ((temp = reader.readLine()) != null){
            String[] str = temp.split(",");
            subjects.add(new Subjects(str[0],str[1],Double.parseDouble(str[2]),Double.parseDouble(str[3])));
        }
        return subjects;
    }

    public List<Subjects> readlistsubject(){
        List<Subjects> inputMonhoc = null;
        try{
            inputMonhoc = readSubject("subjectManager.txt");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return inputMonhoc;
    }


    public void goBack(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/workspace.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void resetText(){
        idSub.setText("");
        nameSub.setText("");
        creSub.setText("");
        factor.setText("");
    }
    public void AddSubject(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Xác Nhận");
        alert.setHeaderText("Xác nhận thêm môn học");
        alert.setContentText("Bạn có xác nhận thêm môn học này?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo,buttonTypeCancel);

        Optional<ButtonType> result1 = alert.showAndWait();
        if(result1.get() == buttonTypeYes){
            Subjects newSubject = new Subjects();
            newSubject.setSubID(idSub.getText());
            newSubject.setSubName(nameSub.getText());
            newSubject.setCreditSub(Double.parseDouble(creSub.getText()));
            newSubject.setFactorSub(Double.parseDouble(factor.getText()));
            listSubject.add(newSubject);

            write();

        }
        else{
            resetText();
            Alert alertAdd = new Alert(Alert.AlertType.INFORMATION);
            alertAdd.setTitle("Information");
            alertAdd.setContentText("Chưa thêm");
            alertAdd.show();
            write();
        }
    }

    public void EditSubject(ActionEvent actionEvent) {
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
            Subjects select = table.getSelectionModel().getSelectedItem();
            Subjects newSubject = new Subjects();
            for (Subjects subjects : listSubject) {
                if(subjects == select){
                    newSubject.setSubID(idSub.getText());
                    newSubject.setSubName(nameSub.getText());
                    newSubject.setCreditSub(Double.parseDouble(creSub.getText()));
                    newSubject.setFactorSub(Double.parseDouble(factor.getText()));
                    listSubject.set(listSubject.indexOf(subjects), newSubject);
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

    public void DeleteSubject(ActionEvent actionEvent) {
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
            Subjects select = table.getSelectionModel().getSelectedItem();
            listSubject.remove(select);
        }
        else{
            Alert alertDel2 = new Alert(Alert.AlertType.INFORMATION);
            alertDel2.setTitle("Information");
            alertDel2.setHeaderText("Xoá thành công");
            alertDel2.show();

            write();
        }
    }


    public void handleClickView(MouseEvent mouseEvent) {
        Subjects subjects = table.getSelectionModel().getSelectedItem();
        String str1 = String.valueOf(subjects.getCreditSub());
        String str2 = String.valueOf(subjects.getFactorSub());
        if(subjects != null){
            idSub.setText(subjects.getSubID());
            nameSub.setText(subjects.getSubName());
            creSub.setText(str1);
            factor.setText(str2);

        }
    }
}
