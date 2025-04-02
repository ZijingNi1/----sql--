import java.sql.*;

// leader 类继承自 teacher 类
public class leader extends teacher{
    // 声明 SQL 语句、结果集、连接和用户
    Statement sql;
    ResultSet rs;
    Connection conn;
    String user;

    // 连接到数据库的方法
    public void connect(Connection con){
        conn = con;
    }

    // 将给定的 ID 和姓名添加到 member 和 password 表中
    public void enlist(String id, String name){
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("insert into member values ('"+id+"','"+name+"','社员')");
        } catch(SQLException e){
            System.out.println(e);
        }
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("insert into password values ('"+id+"','123456')");
        } catch(SQLException e){
            System.out.println(e);
        }
    }

    // 检查给定的 ID 是否是社员
    public boolean is_member(String id){
        boolean s = false;
        String w = "";
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 职位 from member where 账号='"+id+"'");
            // 获取结果集中的职位
            while(rs.next()){
                w = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        // 如果职位是"社员"，则返回 true
        if(w.equals("社员"))
            s = true;
        return s;
    }

    // 从 member 和 password 表中删除给定的 ID
    public void kick(String id){
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("delete from member where 账号='"+id+"'");
        } catch(SQLException e){
            System.out.println(e);
        }
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("delete from password where 账号='"+id+"'");
        } catch(SQLException e){
            System.out.println(e);
        }
    }

    // 将给定的姓名和内容添加到 notice 表中
    public void baba(String name, String content){
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("insert into notice values ('"+name+"','"+content+"')");
        } catch(SQLException e){
            System.out.println(e);
        }
    }

    // 根据给定的 ID 从 member 表中选择姓名
    public String selectmyname(String id){
        String w = "";
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select 姓名 from member where 账号='"+id+"'");
            // 获取结果集中的姓名
            while(rs.next()){
                w = rs.getString(1);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return w;
    }

    public boolean  selectcontent(String content){
        boolean s = false;
        try{
            // 创建一个 Statement 对象来执行 SQL 查询
            sql = conn.createStatement();
            // 执行 SQL 查询并获取结果集
            rs = sql.executeQuery("select * from notice where 公告内容='"+content+"'");
            while(rs.next()){
                 s = true;
            }
        } catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }

    // 从 notice 表中删除给定的内容
    public void drop(String content){
        try{
            // 创建一个 Statement 对象来执行 SQL 更新
            sql = conn.createStatement();
            // 执行 SQL 更新
            sql.executeUpdate("delete from notice where 公告内容='"+content+"'");
        } catch(SQLException e){
            System.out.println(e);
        }
    }  
}
