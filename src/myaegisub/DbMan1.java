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
    
    private static DbMan1 jdbc=null;
    public static DbMan1 getInstance()
    {
        if(jdbc==null)
        {
            initialize();
        }
        return jdbc;
    }
    private static void initialize()
    {
        //connect to database
        jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
        }
    }

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
//                && jdbc.selectBySongname()) {
//            System.out.println("selectBySongname successful");
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
      public static ArrayList<Integer> ids=new ArrayList<Integer>();
      public static ArrayList<String> songs=new ArrayList<String>();
      public static ArrayList<String> characters=new ArrayList<String>();
      public static ArrayList<String> sounds=new ArrayList<String>();
      public static ArrayList<String> englishes=new ArrayList<String>();
      public static ArrayList<String> syllables=new ArrayList<String>();
      public static ArrayList<Integer> milliseconds=new ArrayList<Integer>();
      public static ArrayList<Integer> times=new ArrayList<Integer>();
      public static ArrayList<Integer> lines=new ArrayList<Integer>();
      public static ArrayList<Integer> pages=new ArrayList<Integer>();

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
                    + " ,line         integer default 0"
                    + " ,page         integer default 0"
                    + " ,milliseconds         integer"
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
    
    
  public boolean insert(String song,String character,String sound,String english,String syllables, Integer line)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "INSERT INTO words (song,character,sound,english,syllables,milliseconds,line) " +
                   "VALUES ('"+song+"','"+character+"', '"+sound+"', '"+english+"', '"+syllables+"',0, '"+line+"' );"; 
//      System.out.println(sql);
      stmt.executeUpdate(sql);

      stmt.close();
      c.commit();
      
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }    
  

  public ArrayList<Integer> selectBySongname(String song)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM words where song=\""+song+"\";" );
      ids.clear();
      characters.clear();
      sounds.clear();
      songs.clear();
      englishes.clear();
      times.clear();
      milliseconds.clear();
      syllables.clear();
      lines.clear();
      pages.clear();
      while ( rs.next() ) {
          ids.add(rs.getInt("id"));
          characters.add(rs.getString("character"));
          englishes.add(rs.getString("english"));
          syllables.add(rs.getString("syllables"));
          milliseconds.add(rs.getInt("milliseconds"));
          times.add(rs.getInt("time"));
          songs.add(rs.getString("song"));
          sounds.add(rs.getString("sound"));
          lines.add(rs.getInt("line"));
          pages.add(rs.getInt("page"));
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
  
  public ArrayList<Integer> select(String criteria)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM words "+criteria+";" );
      ids.clear();
      characters.clear();
      sounds.clear();
      songs.clear();
      englishes.clear();
      times.clear();
      milliseconds.clear();
      syllables.clear();
      lines.clear();
      pages.clear();
      while ( rs.next() ) {
          ids.add(rs.getInt("id"));
          characters.add(rs.getString("character"));
          englishes.add(rs.getString("english"));
          syllables.add(rs.getString("syllables"));
          milliseconds.add(rs.getInt("milliseconds"));
          times.add(rs.getInt("time"));
          songs.add(rs.getString("song"));
          sounds.add(rs.getString("sound"));
          lines.add(rs.getInt("line"));
          pages.add(rs.getInt("page"));
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

  public boolean update(Integer id,String milliseconds)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "UPDATE words set milliseconds = "+milliseconds+"0 where ID="+id.toString()+";";
      stmt.executeUpdate(sql);
      c.commit();
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  

  public boolean updateTime(Integer id,Integer time)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "UPDATE words set time = "+time+" where ID="+id.toString()+";";
      stmt.executeUpdate(sql);
      c.commit();
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }   
  public boolean updatePage(Integer id,Integer page)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "UPDATE words set page = "+page+" where ID="+id.toString()+";";
      stmt.executeUpdate(sql);
      c.commit();
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }     

  public boolean deletePages()
  {
    try {
      stmt = c.createStatement();
      String sql = "DELETE from pages;";
      stmt.executeUpdate(sql);
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  
  public boolean deleteLines()
  {
    try {
      stmt = c.createStatement();
      String sql = "DELETE from lines;";
      stmt.executeUpdate(sql);
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  
  public boolean deleteWords()
  {
    try {
      stmt = c.createStatement();
      String sql = "DELETE from words;";
      stmt.executeUpdate(sql);
      stmt.close();
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }  
  
//---------
  public boolean insertPage(Integer page,Integer timestart, Integer timeend)
  {
    try {
/*            sql = "CREATE TABLE pages "
                    + "(ID INTEGER PRIMARY KEY     NOT NULL,"
                    + " page           integer    NOT NULL, "
                    + " timestart            integer, "
                    + " timeend        integer "
                    + ")";
  
  */  
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "INSERT INTO pages (page,timestart,timeend) " +
                   "VALUES ('"+page+"','"+timestart+"', '"+timeend+"');"; 
//      System.out.println(sql);
      stmt.executeUpdate(sql);

      stmt.close();
      c.commit();
      
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }    
  public boolean insertLine(Integer line,Integer page,Integer timestart, Integer timeend)
  {
    try {
/*            sql = "CREATE TABLE pages "
                    + "(ID INTEGER PRIMARY KEY     NOT NULL,"
                    + " page           integer    NOT NULL, "
                    + " timestart            integer, "
                    + " timeend        integer "
                    + ")";
  
  */  
      c.setAutoCommit(false);

      stmt = c.createStatement();
      String sql = "INSERT INTO lines (line,page,timestart,timeend) " +
                   "VALUES ('"+line+"','"+page+"','"+timestart+"', '"+timeend+"');"; 
//      System.out.println(sql);
      stmt.executeUpdate(sql);

      stmt.close();
      c.commit();
      
      return true;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return false;
    }
  }      
/*
            sql = "CREATE TABLE lines "
                    + "(ID INTEGER PRIMARY KEY     NOT NULL,"
                    + " line           integer    NOT NULL, "
                    + " page           integer    NOT NULL, "
                    + " timestart            integer, "
                    + " timeend        integer "
                    + ")";
*/

  public ArrayList<Integer> selectLineByTime(Integer time)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM lines where timestart<="+time+" and timeend>="+time+"" );
      while ( rs.next() ) {
          lineno=rs.getInt("line");
          linepageno=rs.getInt("page");
          linetimestart=rs.getInt("timestart");
          linetimeend=rs.getInt("timeend");
      }
      rs.close();
      stmt.close();
      
      return ids;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
    }
  }  
  
  public ArrayList<Integer> selectPageByTime(Integer time)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM pages where timestart<="+time+" and timeend>="+time+"" );
      while ( rs.next() ) {
          pageno=rs.getInt("page");
          pagetimestart=rs.getInt("timestart");
          pagetimeend=rs.getInt("timeend");
      }
      rs.close();
      stmt.close();

        getNextPageWordTime();
        getPreviousPageWordTime();
      
      return ids;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
    }
  }  
  public Integer[] selectPageByPageNo(Integer pageno)
  {
      Integer[] times={0,0};
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM pages where page="+pageno+"" );
      while ( rs.next() ) {
          times[0]=rs.getInt("timestart");
          times[1]=rs.getInt("timeend");
      }
      rs.close();
      stmt.close();

      return times;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
    }
  }  
  public Integer selectWordByTime(Integer time)
  {
    try {
      c.setAutoCommit(false);

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM words where time <= "+time.toString()+" order by id desc limit 1"  );
      while ( rs.next() ) {
          wordid=rs.getInt("id");
      }
      rs.close();
      stmt.close();
      
      return wordid;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
    }
  }    
  public void getPreviousPageWordTime()
  {
      previouspagewordtime=null;
    try {
      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "select * from words where page < "+pageno+" order by id desc limit 1");
      while ( rs.next() ) {
          previouspagewordtime=rs.getInt("time");
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    }
  }    
  public void getNextPageWordTime()
  {
      nextpagewordtime=null;
    try {
      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "select * from words where page > "+pageno+" limit 1");
      while ( rs.next() ) {
          nextpagewordtime=rs.getInt("time");
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    }
  }    
//  public ArrayList<Integer> selectPageByTime(Integer pageno)
//  {
//    try {
//      c.setAutoCommit(false);
//
//      stmt = c.createStatement();
//      ResultSet rs = stmt.executeQuery( "SELECT * FROM pages where page = "+pageno+";" );
//      while ( rs.next() ) {
//          pageno=rs.getInt("page");
//          pagetimestart=rs.getInt("timestart");
//          pagetimeend=rs.getInt("timeend");
//      }
//      rs.close();
//      stmt.close();
//      
//      return ids;
//    } catch ( Exception e ) {
//      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//      return null;
//    }
//  }  
      public static Integer lineno=0;
      public static Integer linepageno=0;
      public static Integer linetimestart=0;
      public static Integer linetimeend=0;
      public static Integer pageno=0;
      public static Integer pagetimestart=0;
      public static Integer pagetimeend=0;
      public static Integer wordid=0;
      public static Integer previouspagewordtime;
      public static Integer nextpagewordtime;
}
