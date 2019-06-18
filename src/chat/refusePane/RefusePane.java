package chat.refusePane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RefusePane {


    /**
     * 稍稍修改，显示页面功能
     */
    public void displayLoginPane(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Refuse.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sceneGroupChat = new Scene(root, 400, 300);
        stage.setScene(sceneGroupChat);
        stage.setTitle("抱歉，对方不信仰圣光，你们没有共同话题");
        stage.show();
    }
}



