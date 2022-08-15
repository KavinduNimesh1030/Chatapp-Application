import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class ClientLoginFormController {
    public TextField txtUserName;
    private  static  String userName ;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtUserName.getText();
        if(!userName.equals(null)){
            URL resource = getClass().getResource("ClientForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }

    }
    public static String getUserName(){
        System.out.println(userName);
        return userName;
    }
}
