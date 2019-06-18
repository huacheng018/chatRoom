package loginAndRegister.MySQL;

import java.sql.*;

/**
 * @Author: 枠成
 * @Data:2019-06-17
 * @Description:loginAndRegister.MySQL
 * @version:1.0
 */
public class SqlFiction {

    /** Connection建立连接（连接对象内部其实包含了Socket对象，
     * 是一个远程的连接，比较耗时。
     * 这是Connection对象管理的一个要点
     * 真正开发对象用连接池
     */

    //拉出外面，方便写关闭总方法，方法在最下面
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet rs = null;

    public void openSQL(){
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?useSSL=false","root","1234");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 此处不能close，最后所有注册完才能close
         */
    }

    /**
     * 注册账号,即添加数据进去
     */
    public void registerAccount(String name,String password) throws SQLException {
        String sql = "" +
                "insert into test" +
                "(name,password)" +
                "values(?,?)";//？占位符
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);
        preparedStatement.execute();
        closeAll();
    }

    /**
     * 登陆账号和注册账号，验证是否已注册，即只要查询name
     * 查询得到，就是已注册，即true
     */
    public boolean checkLoginOrRegistered(String tempName) throws SQLException{
        String sql = "" +
                "select " +
                "* " +
                "from test "+
                "where name='"+
                tempName+"'";
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery(sql);
        while (rs.next()){
            if (rs.getString("name").equals(tempName)){
                return true;
            }
        }
        return false;
    }

    /**登陆账号验证密码是否正确
     * 同时查询name和password
     * 正确则返回true
     */
    public boolean checkLogin(String tempName, String tempPassword) throws SQLException{
        String sql = "" +
                "select " +
                "* " +
                "from test "+
                "where name='"+
                tempName+"'"+
                "and password='"+
                tempPassword+"'";
        statement= connection.prepareStatement(sql);
        rs = statement.executeQuery(sql);
        while (rs.next()){
            if (rs.getString("name").equals(tempName)&&rs.getString("password").equals(tempPassword)){
                closeAll();
                return true;
            }
        }
        return false;
    }

    /**
     * 用完关闭所有
     * @throws SQLException
     */
    public void closeAll()  {
        try {
            if (connection!=null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement!=null){
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
