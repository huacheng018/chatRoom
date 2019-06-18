package loginAndRegister.loginPane;

import chat.groupChat.GroupChat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loginAndRegister.MySQL.SqlFiction;
import java.io.IOException;
import java.sql.SQLException;

public class Login {

    //登录用户名和密码框
    @FXML
    public TextField textField ;
    @FXML
    public PasswordField passwordField;

    /**
     * 稍稍修改，登陆页面
     */
    public void displayLoginScene(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sceneLogin = new Scene(root, 600, 400);
        stage.setScene(sceneLogin);
        stage.setTitle("登陆");
        stage.showAndWait();
    }

    /**
     * 登陆验证功能
     * @throws SQLException
     */
    public void login() throws SQLException {

        //从登录框拿数据
        String name = textField.getText();
        String password = passwordField.getText();

        /**
         * 避免数据库测试时出错误，自定义两个账号密码登陆,以保证其他模块测试的正常运行
         * 账号123456，密码654321；
         * 账号aaa，密码654321;
         */
        if((name.equals("123456")&&password.equals("654321"))||(name.equals("aaa")&&password.equals("654321"))) {
            GroupChat login = new GroupChat();
            login.displayChatPane(name);
            return;
        }

        //实例化对象，开启数据库
        SqlFiction sqlFiction = new SqlFiction();
        sqlFiction.openSQL();

        /**登陆验证阶段
         * 先检查是否已注册，已注册会返回true，加！变为false
         * 再检查密码是否正确
         */
        if (!sqlFiction.checkLoginOrRegistered(name)){
            displayRegistered();
        }else {
            //true则登陆成功，false则弹出提示框
            if (sqlFiction.checkLogin(name,password)) {
                GroupChat login = new GroupChat();
                login .displayChatPane(name);
                return;
            } else {
                displayWrongPassword();
            }
        }
    }

    //用户名或密码错误提示框
    public void displayWrongPassword(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("用户名或密码错误");
        Scene sceneWrongPassword = new Scene(label, 150, 40);
        stage.setScene(sceneWrongPassword);
        stage.setTitle("用户名或密码错误");
        stage.showAndWait();
    }

    //已注册提示框
    public void displayRegistered(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("该账号未注册");
        Scene sceneWrongPassword = new Scene(label, 150, 40);
        stage.setScene(sceneWrongPassword);
        stage.setTitle("该账号未注册");
        stage.showAndWait();
    }
}



