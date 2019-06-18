package chat.groupChat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * @Author: 枠成
 * @Data:2019-06-15
 * @Description:chat.groupChat
 * @version:1.0
 */
public class Controller extends Thread {

    /**
     * 单例模式
     * 源于群里的师兄教导
     */

    private static Controller instacnce;

    public static Controller getInstacnce(){
        return instacnce;
    }

    public Controller(){
        instacnce = this;
    }

    //用户名
    String name = null ;

    //自身发送框
    @FXML
    public TextArea sendText;

    //接收文本框
    @FXML
    public TextArea receivText;

    /**
     * 发送按钮，因为发送线程为while死循环，未发送时isSending为false，发送则改为true，发送完改为false
     */
    @FXML
    public Button buttonSend ;

    //发送终端，如果为true则发送，然后set为false
    public Boolean isSending = false;

    public void sendMessage(){
        isSending = true ;
    }

    //清空按钮
    @FXML
    public Button buttonClean ;

    public void cleanTextArea(){
        sendText.clear();
    }

    //显示在线用户
    @FXML
    public TextArea loginPeople2;

    //关闭按钮
    @FXML
    public Button exitAccount;

    public void exitAccount(){
        try {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
