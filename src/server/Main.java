package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //存放用户线程
    ServerManagement serverManagement = new ServerManagement();

    /**服务器端界面
     * 原封不动首页面
     * 纯模板语法
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //开一个线程执行后台发送接收
        new Thread(serverManagement).start();

        //显示框框
        Parent root = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
        primaryStage.setTitle("“真香”实验室服务端");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
