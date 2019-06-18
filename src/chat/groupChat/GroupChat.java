package chat.groupChat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;

public class GroupChat {

    static private String name;

    //创建窗口
    public Stage stageGroupChat;

    /**
     * 稍稍修改，显示页面功能
     */
    public void displayChatPane(String name){
        //传入用户名
        GroupChat.name = name;
        stageGroupChat = new Stage();
        stageGroupChat.initModality(Modality.APPLICATION_MODAL);
//        stageGroupChat.initStyle(TRANSPARENT);//不敢用，会瞎。。。
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("GroupChat.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sceneGroupChat = new Scene(root, 630, 470);
        stageGroupChat.setScene(sceneGroupChat);
        stageGroupChat.setTitle("欢迎"+name+"来到 瞎吉尔吹 聊天室");

        //启动发送接收线程
        loginConnect(name);

        stageGroupChat.show();

//        //设置窗口监听直接x掉释放
//        stageGroupChat.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                System.exit(0);
//        }
//        });
    }

    public void loginConnect(String name){

        //1 建立连接：使用Socket创建客户端+服务器的地址和端口
        Socket client = null;

        try {
            client = new Socket("localhost",8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 2 客户端发送消息、获取消息
         * 方案为显式添加线程
         */
        new Thread(new Send(client,name)).start();
        new Thread(new Receive(client)).start();
    }

}



