/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqliteexample;

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
public class SQLiteJDBC {

    private Connection c = null;
    private Statement stmt = null;

    public boolean close()
    {
        try {
            c.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void main(String args[]) {
        SQLiteJDBC jdbc = new SQLiteJDBC();
        if (jdbc.connect("database.db")) {
            System.out.println("Opened database successfully");
        }
        else
            System.out.println("Cannot open database");

        if (jdbc.createTable()) {
            System.out.println("Table created successfully");
        }
        
        if (jdbc.insert()) {
            System.out.println("Records created successfully");
        }
        
        ArrayList<Employee> employees=jdbc.select();
        if (employees!=null) {
            for(Employee e:employees)
            {
                System.out.println( "ID = " + e.id );
                System.out.println( "NAME = " + e.name );
                System.out.println( "AGE = " + e.age );
                System.out.println( "ADDRESS = " + e.address );
                System.out.println( "SALARY = " + e.salary );
                System.out.println();            
            }
            
            System.out.println("select successful");
        }
        
        if (jdbc.update()) {
            System.out.println("update successful");
        }
        
        if (jdbc.delete()) {
            System.out.println("delete successful");
        }
        jdbc.close();
    }

    public boolean createTable() {
        try {

            stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           TEXT    NOT NULL, "
                    + " AGE            INT     NOT NULL, "
                    + " ADDRESS        CHAR(50), "
                    + " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    
    
  public boolean insert()
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                   "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
      stmt.executeUpdate(sql);

      stmt.close();
      c.commit();
      
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }    
  

  public ArrayList<Employee> select( )
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
      
      ArrayList<Employee> employees=new ArrayList<Employee>();
      Employee e;
      
      while ( rs.next() ) {
          employees.add(new Employee(rs));
      }
      rs.close();
      stmt.close();
      
      return employees;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
    }
  }  
  

  public boolean update()
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
      stmt.executeUpdate(sql);
      c.commit();

      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  
  

  public boolean delete()
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "DELETE from COMPANY where ID=2;";
      stmt.executeUpdate(sql);
      c.commit();

      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  
  
}
