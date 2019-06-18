package chat.invitePane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InvitePane {


    /**
     * 稍稍修改，显示页面功能
     */
    public void displayLoginPane(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Invite.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sceneGroupChat = new Scene(root, 400, 300);
        stage.setScene(sceneGroupChat);
        stage.setTitle("邀请对方一起讨论量子人缘力学");
        stage.show();
    }
}



