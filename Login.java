import java.sql.*;

public class Login extends Connect{
    // 声明 SQL 语句、结果集、连接和用户
    Statement sql;
    ResultSet rs;
    Connection conn;
    String user;

    // 连接到数据库的方法
    public void connect(Connection con){
        conn = con;
    }

    // 检查给定的 ID 是否存在于数据库中
    public boolean selectid(String id){
        boolean s = false;
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select * from password where 账号='"+id+"'");
            // 如果结果集中有数据，说明 ID 存在
            while(rs.next()){
                s = true;
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }

    // 获取给定 ID 的密码
    public String selectpassword(String id){
        String s = "";
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 密码 from password where 账号='"+id+"'");
            // 获取结果集中的密码
            while(rs.next()){
                s = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }

    // 获取给定 ID 的职位
    public String selectposition(String id){
        String s = "";
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 职位 from member where 账号='"+id+"'");
            // 获取结果集中的职位
            while(rs.next()){
                s = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }
}
