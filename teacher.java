import java.sql.*;

public class teacher extends Connect{
    // 声明 SQL 语句、结果集、连接和用户
    Statement sql;
    ResultSet rs;
    Connection conn;
    String user;

    // 连接到数据库的方法
    public void connect(Connection con){
        conn = con;
    }

    // 检查给定的姓名是否是社长或副社长
    public boolean is_leader(String name){
        boolean s = false;
        String w = "";
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 职位 from member where 姓名='"+name+"'");
            // 获取结果集中的职位
            while(rs.next()){
                w = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        // 如果职位是"社长"或"副社长"，则返回 true
        if(w.equals("社长") | w.equals("副社长"))
            s = true;
        return s;
    }

    // 检查给定的姓名是否存在于数据库中
    public Boolean selectname(String name){
        boolean s = false;
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select * from member where 姓名='"+name+"'");
            // 如果结果集中有数据，说明姓名存在
            while(rs.next()){
                s = true;
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }

    // 更改职位
    public void change(String old, String thenew){
        String oldposition = "", newposition = "";
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 职位 from member where 姓名='"+old+"'");
            // 获取结果集中的职位
            while(rs.next()){
                oldposition = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 职位 from member where 姓名='"+thenew+"'");
            // 获取结果集中的职位
            while(rs.next()){
                newposition = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("update member set 职位='"+oldposition+"' where 姓名='"+thenew+"'");
        } catch(SQLException e){
            System.out.println(e);
        }
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("update member set 职位='"+newposition+"' where 姓名='"+old+"'");
        } catch(SQLException e){
            System.out.println(e);
        }
        // 如果旧职位是"社长"，则更新社团的社长
        if(oldposition.equals("社长")){
            try{
                // 创建一个 Statement 对象来执行 SQL 更新
                sql = conn.createStatement();
                // 执行 SQL 更新
                sql.executeUpdate("update team set 社长='"+thenew+"' where 社长='"+old+"'");
            } catch(SQLException e){
                System.out.println(e);
            }
        }
    }

    // 检查给定的名称是否是旧的社团名称
    public boolean is_oldname(String name){
        boolean s = false;
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select * from team where 社团名称='"+name+"'");
            // 如果结果集中有数据，说明名称是旧的
            while(rs.next()){
                s = true;
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }

    // 更改社团名称
    public void change_teamname(String name){
        String oldname = null;
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 社团名称 from team limit 1");
            // 获取结果集中的社团名称
            while(rs.next()){
                oldname = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("update team set 社团名称='"+name+"' where 社团名称='"+oldname+"'");
        } catch(SQLException e){
            System.out.println(e);
        }  
    }

    // 更改密码
    public void changepassword(String id, String password){
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("update password set 密码='"+password+"' where 账号='"+id+"'");
        } catch(SQLException e){
            System.out.println(e);
        }  
    }
}
