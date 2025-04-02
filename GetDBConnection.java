import java.sql.*;
public class GetDBConnection{
public static Connection connectDB(String DBName,String user,String mm){
 Connection con = null;
 try{Class.forName("com.mysql.cj.jdbc.Driver");
 }
 catch(Exception e){}
 String uri="jdbc:mysql://localhost:3306/"+DBName+"?useSSL=true&serverTimezone=CST&chatacterEncoding=utf-8";
 try{
 con=DriverManager.getConnection(uri,user,mm);
 }
 catch(SQLException e){
System.out.println(e.getMessage());

 }
 return con;
}
}
//javac -encoding UTF-8 Main.java
//java -cp mc.jar; Main