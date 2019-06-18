package chat.privateChat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PrivateChat {


    /**
     * 稍稍修改，显示页面功能
     */
    public void displayLoginPane(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PrivateChat.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sceneGroupChat = new Scene(root, 800, 700);
        stage.setScene(sceneGroupChat);
        stage.setTitle("开撩");
        stage.show();
    }
}



