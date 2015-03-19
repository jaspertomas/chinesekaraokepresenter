/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.AssParser;
import utils.AssTimeParser;

/**
 *
 * @author jaspertomas
 */
public class AssReconciler extends Application {
    WebView browser = new WebView();
    WebEngine webEngine = browser.getEngine();;
    Label label = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    Label label5 = new Label();
    
    Integer speed=100;
    Integer linecounter=1;
    Integer karaokecounter=1;
    
    @Override
    public void start(Stage primaryStage) {

        browser.requestFocus();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));        

        grid.add(browser, 0, 0);        

        grid.add(label, 0, 1);        
        grid.add(label2, 0, 2);        
        grid.add(label3, 0, 3);        
        grid.add(label4, 0, 4);        
        grid.add(label5, 0, 5);        
        label4.setText("Line counter: "+linecounter.toString());
        label5.setText("Karaoke counter: "+karaokecounter.toString());
        
        Scene scene = new Scene(grid, 500, 500);
// scene.getStylesheets().add(cssfile.toURI().toString());        
        
//--as

browser.addEventFilter(KeyEvent.KEY_PRESSED, 
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

//        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
//        webEngine.load("http://www.google.com");   

        File imagefile=new File("bananaman.jpg");
        String backgroundstring="<html><body background=\""+imagefile.toURI()+"\"><font size=10>";
//        String backgroundstring="<html><body>";
//        System.out.println(backgroundstring);
//        webEngine.loadContent(AssParser.parse("Xiang zan.ass"));
//        webEngine.loadContent(backgroundstring+AssParser.parse("3.ass"));

        //insert song to database
//        SongParser.parse("song00.txt");
//        SongParser.parse("song01.txt");
//        SongParser.parse("song13.txt");
        AssTimeParser.parse("4final.ass","song13");
        ArrayList<String> times=AssTimeParser.times;
        ArrayList<String> words=AssTimeParser.words;
        
        
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
//        label.setText("Play");
        //display 
    }
    private void pause()
    {
        label.setText("Pause");
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
        label4.setText("Line counter: "+linecounter.toString());
    }    
    private void decrementLineCounter()
    {
        linecounter--;
        label4.setText("Line counter: "+linecounter.toString());
    }    
}
