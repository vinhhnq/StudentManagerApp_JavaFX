package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
    public TextField user;
    public PasswordField pass;

    public void onSubmit(ActionEvent actionEvent) throws Exception {

        try {
            if (user.getText().equals("vinh") && pass.getText().equals("1")) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/workspace.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Tài khoản và mật khẩu không đúng, vui lòng kiểm tra lại");
                alert.show();
                System.out.println("login fail, please try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onCancel(ActionEvent actionEvent) {
        System.exit(0);
    }
}
