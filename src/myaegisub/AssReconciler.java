/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.AssTimeParser;
import utils.SongParser;

/**
 *
 * @author jaspertomas
 */
public class AssReconciler extends Application {
    public static final Integer linesperpage=4;
    public static final Integer zeropageadjustment=3;

//    String songname="song13";
//    String assfilename="4final.ass";
//    Integer[] forremoval={14,18,27,37,40,49,57,66,73,76,79,88,96,105,112,115,118,127,134,143,150,159,164,164,165,165,165,165};

//    String songname="song14";
//    String assfilename="5final.ass";
//    Integer[] forremoval={5,9,13,18,22,26,30,31,32,33,34};

    String songname="song15";
    String assfilename="6final.ass";
    Integer[] forremoval={2,2,2,3,4,4,5,6,6,6,7,8,8,8,8,8,8,9,9,9,9,9,9,9,10,10,10,10,11,11,11,12,12,12,13,13,13,13,13,13,13,14,14,14,15,15,15,15,16,16,16,16,16,16,16,16,16,16,16,17,18,18,18,18,18,18,19,19,19,19,20,20,20,21,21,22,22,22,22,23,23,23,24,24,25,25,27,28,29,29,29,30,31,31,31,31,31,32,33,33,33,33,33,33,34,34,34,35,35,35,36,36,37,38,38,38,40,41,41,41,41,42,42,42,43,43,43,44,44,44,46,47,48,48,48,49,49,49,50,50,50,50,50,50,50,50,51,54,54,54,55,55,55,55,55,55,55,55,55,55,55,56,56,56,56,56,57,58,59,60,61,62,63,63,63,64,64,65,66,66,68,69,69,69,69,70,70,70,71,71,71,71,71,71,71,71,72,72,72,72,72,72,72,73,73,74,75,76,76,76,77,77,77,77,77,77,77,77,77,77,78,78,78,79,80,81,81,81,81,81,81,81,82,82,82,83,83,83,83,83,83,83,83,84,84,84,84,84,84,84,85,85,85,85,85,86,86,86,86,86,86,86,87,87,87,87,88,89,90,90,90,90,90,90,90,91,91,91,92,92,92,92,92,92,92,92,93,93,93,93,93,93,93,94,94,94,94,94,95,95,95,95,95,95,95,96,96,96,97,98,99,99,99,99,99,99,99,99,100,100,100,101,101,101,101,101,101,101,101,102,102,102,102,102,102,102,103,103,103,103,103,103,103,103,103,104,104,104,104,104,104,104};
//    Integer[] forremoval={};

    WebView browser = new WebView();
    WebEngine webEngine = browser.getEngine();;
    Label label = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    Label label5 = new Label();
    
    Integer speed=100;
    int linecounter=1;
    Integer karaokecounter=1;
    
    @Override
    public void start(Stage primaryStage) {

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
        AssTimeParser.words.remove(linecounter);
        AssTimeParser.times.remove(linecounter);
        play();
        System.out.print(","+linecounter);
            }
        });        
        
        btn.requestFocus();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));        

        grid.add(browser, 0, 0);        

        grid.add(label, 0, 1);        
        grid.add(btn, 0, 2);        
        grid.add(label3, 0, 3);        
        grid.add(label4, 0, 4);        
        grid.add(label5, 0, 5);        
        label4.setText("Line counter: "+String.valueOf(linecounter));
        label5.setText("Karaoke counter: "+karaokecounter.toString());
        
        Scene scene = new Scene(grid, 500, 500);

        btn.addEventFilter(KeyEvent.KEY_PRESSED, 
                    new EventHandler<KeyEvent>() {
                        public void handle(KeyEvent event) { 
//        System.out.println("Filtering out event " + event.getEventType()); 
        String s=event.getText();
        for(int i=0;i<s.length();i++)
        {
            int j=(int)s.charAt(i);
            switch(j)
            {
                //space
                case 13://play
//                    System.out.println("enter");
                    play();
                    break;
                case 32://pause
//                    System.out.println("space");
                    pause();
                    break;
                case 44://slow down
//                    System.out.println("<");
                    slowDown();
                    break;
                case 46://speed up
//                    System.out.println(">");
                    speedUp();
                    break;
                case 91://decrement line counter
                    decrementLineCounter();
                    break;
                case 93://increment line counter
                    incrementLineCounter();
                    break;
                default:
                    System.out.println(j);
                    /*
                    reset=
                        114
                        101
                        115
                        101
                        116
                    */
            }
        }
        event.consume();                            
                        };
                    });

        primaryStage.setScene(scene);
        primaryStage.show();

        File imagefile=new File("bananaman.jpg");
        String backgroundstring="<html><body background=\""+imagefile.toURI()+"\"><font size=10>";
//        String backgroundstring="<html><body>";
//        System.out.println(backgroundstring);
//        webEngine.loadContent(AssParser.parse("Xiang zan.ass"));
//        webEngine.loadContent(backgroundstring+AssParser.parse("3.ass"));

        //step 1
        //insert song to database
//        SongParser.parse(songname+".txt");
        
        //step 2
        //this is for adjustment mode:
        //load .ass file
        //then edit ids of karaoke nodes to merge
//        AssTimeParser.parse(assfilename,songname);
//        adjust();
        
        //step 3
        //this is to write adjustments to database
        AssTimeParser.parse(assfilename,songname);
        update();
        
        setPages();
        setTimes();
        insertLines();
        insertPages();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void play()
    {
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }
        jdbc.selectBySongname(songname);
        
//        label.setText("Play");
        //display 
        String output="<table border=1>";
        for(Integer i=linecounter;i<DbMan1.characters.size();i++)
        {
            output+="<tr>";
            output+="<td>"+i.toString()+"</td>";
            output+="<td><font size=7>"+DbMan1.characters.get(i)+"</font></td>";
            output+="<td>"+DbMan1.englishes.get(i)+"</td>";
            output+="<td>"+DbMan1.milliseconds.get(i)+"</td>";
            output+="<td>"+DbMan1.syllables.get(i)+"</td>";
            output+="<td>"+DbMan1.times.get(i)+"</td>";
            output+="<td><font size=7>"+(AssTimeParser.words.size()>i?AssTimeParser.words.get(i):"")+"</font></td>";
            output+="<td>"+(AssTimeParser.times.size()>i?AssTimeParser.times.get(i):"")+"</td>";

            output+="</tr>";
        }
        output+="</table>";
        webEngine.loadContent(output);
        jdbc.close();
    }
    private void pause()
    {
//        label.setText("Pause");
    }
    private void speedUp()
    {
        //increment linecounter
        karaokecounter++;
        label5.setText("Karaoke counter: "+karaokecounter.toString());
    }
    private void slowDown()
    {
        karaokecounter--;
        label5.setText("Karaoke counter: "+karaokecounter.toString());
    }
    private void incrementLineCounter()
    {
        linecounter++;
        label4.setText("Line counter: "+String.valueOf(linecounter));
    }    
    private void decrementLineCounter()
    {
        linecounter--;
        label4.setText("Line counter: "+String.valueOf(linecounter));
    }    
    private void adjust()
    {
        for(int i:forremoval)
        {
            AssTimeParser.words.remove(i);
            AssTimeParser.times.remove(i);
        }
        
        play();
    }
    private void update()
    {
        Integer timeprevious,timeremoved;
        
        //prepare karaoke data - merge some nodes to match database data
        for(int i:forremoval)
        {
            if(i>0)
            {
                timeprevious=Integer.parseInt(AssTimeParser.times.get(i-1));
                timeremoved=Integer.parseInt(AssTimeParser.times.get(i));
                AssTimeParser.times.set(i-1,String.valueOf(timeremoved+timeprevious));
            }
            AssTimeParser.words.remove(i);
            AssTimeParser.times.remove(i);
        }
        
        //update database
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }
        jdbc.selectBySongname(songname);
        
//        label.setText("Play");
        //display 
        for(Integer i=0;i<DbMan1.characters.size();i++)
        {
            jdbc.update(DbMan1.ids.get(i), AssTimeParser.times.get(i).toString());
        }
        jdbc.close();
    }    
    private void setPages()
    {
        
        //update database
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }
        jdbc.selectBySongname(songname);
        
//        label.setText("Play");
        //display 
        Integer page;
        for(Integer i=0;i<DbMan1.characters.size();i++)
        {
            page=Double.valueOf(Math.floor((DbMan1.lines.get(i)+Double.valueOf(zeropageadjustment))/linesperpage)).intValue();
//            System.out.println(page);
            jdbc.updatePage(DbMan1.ids.get(i), page);
        }
        jdbc.close();
    }
    private void setTimes()
    {
        
        //update database
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }
        jdbc.selectBySongname(songname);
        
//        label.setText("Play");
        //display 
        Integer time=0;
        for(Integer i=0;i<DbMan1.characters.size();i++)
        {
//            System.out.println(time);
            jdbc.updateTime(DbMan1.ids.get(i), time);
            time+=DbMan1.milliseconds.get(i);
        }
        jdbc.close();
    }
    private void insertPages()
    {
        
        //update database
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }
        
        jdbc.deletePages();
        
        Integer starttime=0;
        Integer endtime=0;
        Integer pageno=1;
        jdbc.select("where page = "+pageno.toString()+"");
        while(!DbMan1.ids.isEmpty())
        {
            starttime=DbMan1.times.get(0);
            endtime=DbMan1.times.get(DbMan1.ids.size()-1);
            
            jdbc.insertPage(pageno, starttime, endtime);
            
            //-next iteration------
            pageno++;
            jdbc.select("where page = "+pageno.toString()+"");
        }
        

        
        
        jdbc.close();
    }
    private void insertLines()
    {
        
        //update database
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }
        
        jdbc.deleteLines();
        
        Integer starttime=0;
        Integer endtime=0;
        Integer lineno=1;
        Integer pageno=1;
        jdbc.select("where line = "+lineno.toString()+"");
        while(!DbMan1.ids.isEmpty())
        {
            starttime=DbMan1.times.get(0);
            endtime=DbMan1.times.get(DbMan1.ids.size()-1);
            pageno=DbMan1.pages.get(0);
            jdbc.insertLine(lineno, pageno, starttime, endtime);
            
            //-next iteration------
            lineno++;
            jdbc.select("where line = "+lineno.toString()+"");
        }
        

        
        
        jdbc.close();
    }
}
