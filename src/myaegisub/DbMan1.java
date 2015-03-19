/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaspertomas sqlite-jdbc-3.7.2.jar 
 * downloaded from
 * https://bitbucket.org/xerial/sqlite-jdbc/downloads 
 * tutorial from
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */
public class DbMan1 {

    private Connection c = null;
    private Statement stmt = null;

    public boolean close()
    {
        try {
            c.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DbMan1.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean connect(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + filename);
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
//
//    public static void main(String args[]) {
//        DbMan1 jdbc = new DbMan1();
//        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
//        }
//
//        if (jdbc.c!=null && jdbc.createTable()) {
//            System.out.println("Table created successfully");
//        }
//        
//        if (jdbc.c!=null 
//                && jdbc.insert()) {
//            System.out.println("Records created successfully");
//        }
//        
//        if (jdbc.c!=null 
//                && jdbc.select()) {
//            System.out.println("select successful");
//        }
//        
//        if (jdbc.c!=null 
//                && jdbc.update()) {
//            System.out.println("update successful");
//        }
//        
//        if (jdbc.c!=null 
//                && jdbc.delete()) {
//            System.out.println("delete successful");
//        }
//        
//        
//        
//        jdbc.close();
//
//
//    }

    public boolean createTable() {
        try {
            String sql;
            stmt = c.createStatement();
            sql = "CREATE TABLE lines "
                    + "(ID INTEGER PRIMARY KEY     NOT NULL,"
                    + " line           integer    NOT NULL, "
                    + " page           integer    NOT NULL, "
                    + " timestart            integer, "
                    + " timeend        integer "
                    + ")";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE pages "
                    + "(ID INTEGER PRIMARY KEY     NOT NULL,"
                    + " page           integer    NOT NULL, "
                    + " timestart            integer, "
                    + " timeend        integer "
                    + ")";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE words "
                    + "(ID INTEGER PRIMARY KEY     NOT NULL,"
                    + " song           VARCHAR(10)    NOT NULL, "
                    + " character           VARCHAR(5)    NOT NULL, "
                    + " sound            VARCHAR(5), "
                    + " english        VARCHAR(5), "
                    + " syllables         VARCHAR(50)"
                    + " ,centiseconds         integer"
                    + " ,time         integer default 0"
                    + ")";
            stmt.executeUpdate(sql);
            stmt.close();
            
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    
    
  public boolean insert(String song,String character,String sound,String english,String syllables)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "INSERT INTO words (song,character,sound,english,syllables,centiseconds) " +
                   "VALUES ('"+song+"','"+character+"', '"+sound+"', '"+english+"', '"+syllables+"',0 );"; 
      System.out.println(sql);
      stmt.executeUpdate(sql);

      stmt.close();
      c.commit();
      
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }    
  

  public ArrayList<Integer> select(String song)
  {
      ArrayList<Integer> ids=new ArrayList<Integer>();
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM words where song="+song+";" );
      while ( rs.next() ) {
          ids.add(rs.getInt("id"));
//         int id = rs.getInt("id");
//         String  name = rs.getString("name");
//         int age  = rs.getInt("age");
//         String  address = rs.getString("address");
//         float salary = rs.getFloat("salary");
//         System.out.println( "ID = " + id );
//         System.out.println( "NAME = " + name );
//         System.out.println( "AGE = " + age );
//         System.out.println( "ADDRESS = " + address );
//         System.out.println( "SALARY = " + salary );
//         System.out.println();
      }
      rs.close();
      stmt.close();
      
      return ids;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
    }
  }  
  

  public boolean update(Integer id,String centiseconds)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "UPDATE words set centiseconds = "+centiseconds+"0 where ID="+id.toString()+";";
      stmt.executeUpdate(sql);
      c.commit();
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  
  
//
//  public boolean delete()
//  {
//    try {
//      c.setAutoCommit(false);
//
//      stmt = c.createStatement();
//      String sql = "DELETE from COMPANY where ID=2;";
//      stmt.executeUpdate(sql);
//      c.commit();
//
//      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
//      while ( rs.next() ) {
//         int id = rs.getInt("id");
//         String  name = rs.getString("name");
//         int age  = rs.getInt("age");
//         String  address = rs.getString("address");
//         float salary = rs.getFloat("salary");
//         System.out.println( "ID = " + id );
//         System.out.println( "NAME = " + name );
//         System.out.println( "AGE = " + age );
//         System.out.println( "ADDRESS = " + address );
//         System.out.println( "SALARY = " + salary );
//         System.out.println();
//      }
//      rs.close();
//      stmt.close();
//      return true;
//    } catch ( Exception e ) {
//      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//      return false;
//    }
//  }  
  
}
