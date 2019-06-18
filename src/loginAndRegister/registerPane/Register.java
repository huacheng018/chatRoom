package loginAndRegister.registerPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loginAndRegister.MySQL.SqlFiction;

import java.io.IOException;
import java.sql.SQLException;

public class Register {

    //四个组件
    @FXML
    public TextField textField ;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField passwordFieldAgain;
    @FXML
    public Button buttonRegister;

    /**
     * 稍稍修改，注册页面
     */


    public void displayRegisterScene(){
        Stage stage= new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sceneRegister = new Scene(root, 600, 400);
        stage.setScene(sceneRegister);
        stage.setTitle("用户注册");
        stage.showAndWait();
    }

    /**
     * 注册 完成 按钮功能方法
     * @throws SQLException
     */
    public void registerFiction() throws SQLException {
        //实例化对象，开启数据库
        SqlFiction sqlFiction = new SqlFiction();
        sqlFiction.openSQL();

        /**
         * 获取注册数据
         */
        String name = textField.getText();
        String password = passwordField.getText();
        String passwordAgain = passwordFieldAgain.getText();

        //验证两次密码是否一致
        if (!password.equals(passwordAgain)){
            displayWrongPasswordAgain();
            return;
        }

        //验证是否已注册
        if (sqlFiction.checkLoginOrRegistered(name)){
            displayRegistered();
            return;
        }

        //到最后一步，注册
        sqlFiction.registerAccount(name,password);
        //注册成功，请关闭注册窗口
        displaySuccessRegister();
    }

    /**
     * 已注册 提示框
     */
    public void displayRegistered(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("该账号已注册");
        Scene sceneWrongPassword = new Scene(label, 150, 40);
        stage.setScene(sceneWrongPassword);
        stage.setTitle("该账号已注册");
        stage.showAndWait();
    }

    /**
     * 两次密码不一致 提示框
     */
    public void displayWrongPasswordAgain(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("两次密码不一致");
        Scene sceneWrongPassword = new Scene(label, 150, 40);
        stage.setScene(sceneWrongPassword);
        stage.setTitle("两次密码不一致");
        stage.showAndWait();
    }

    /**
     * 注册成功，请关闭注册窗口 提示框
     */
    public void displaySuccessRegister(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("注册成功，请关闭注册窗口");
        Scene sceneWrongPassword = new Scene(label, 150, 40);
        stage.setScene(sceneWrongPassword);
        stage.setTitle("注册成功，请关闭注册窗口");
        stage.showAndWait();
    }
}
