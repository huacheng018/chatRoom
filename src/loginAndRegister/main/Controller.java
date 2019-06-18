package loginAndRegister.main;

import loginAndRegister.loginPane.*;
import loginAndRegister.registerPane.Register;

public class Controller {

    //点击登陆按钮显示登录框
    public void loginFiction() {
        Login login = new Login() ;
        login.displayLoginScene();
    }

    //点击注册按钮显示注册框
    public void registerFiction() {
        Register register = new Register() ;
        register.displayRegisterScene();
    }

}
