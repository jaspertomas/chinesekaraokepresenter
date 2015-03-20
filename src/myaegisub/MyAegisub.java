/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import controllers.PlayerController;
import controllers.TimerController;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.WindowEvent;

/**
 *
 * @author jaspertomas
 */
public class MyAegisub extends Application {
    WebView browser = new WebView();
    WebEngine webEngine = browser.getEngine();;
    PlayerController playerController=new PlayerController(webEngine);
    TimerController timerController=new TimerController(playerController);
    Label label = new Label();
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timerController.stop();
//                com.sun.javafx.application.PlatformImpl.tkExit();
                Platform.exit();
            }
        });        
        
        browser.requestFocus();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));        

        grid.add(browser, 0, 0);        

        grid.add(label, 0, 1);        
        
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
                case 13://enter
                    play();
                    break;
                case 32://space
                    pause();
                    break;
                case 44://<
                    slowDown();
                    break;
                case 46://>
                    speedUp();
                    break;
                case 91://[
                    gotoPrevious();
                    break;
                case 93://]
                    gotoNext();
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
//        AssTimeParser.parse("4final.ass","song13");


//        play();
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
    
    private void gotoNext()
    {
        System.out.println("goto next");
        Integer wordid=DbMan1.wordid;
        System.out.println("word id="+wordid.toString());
        Integer index=DbMan1.ids.indexOf(DbMan1.wordid);
        System.out.println("index="+index.toString());
        Integer milliseconds=DbMan1.milliseconds.get(index);
        System.out.println("milliseconds="+milliseconds.toString());
        System.out.println("time="+timerController.getTime());
//        playerController.play(20000);
        timerController.setTime(timerController.getTime()+milliseconds);
        if(timerController.isPaused())
            playerController.play(timerController.getTime());
    }
    private void gotoPrevious()
    {
        System.out.println("goto previous");
//        playerController.play(10000);
        Integer index=DbMan1.ids.indexOf(DbMan1.wordid);
        Integer milliseconds=DbMan1.milliseconds.get(index);
        timerController.setTime(timerController.getTime()-milliseconds);
        if(timerController.isPaused())
            playerController.play(timerController.getTime());
    }
    private void play()
    {
        label.setText("Play");
        timerController.play();
    }
    private void pause()
    {
        label.setText("Pause");
        timerController.pause();
    }
    Integer speed=100;
    private void speedUp()
    {
        switch(speed)
        {
            case 90:speed=95;break;
            case 95:speed=98;break;
            case 98:speed=100;break;
            case 100:speed=102;break;
            case 102:speed=105;break;
            case 105:speed=110;break;
//            case 110:speed=110;break;
            default:speed=speed+5;
        }
        timerController.setSpeed(speed);
        label.setText("Speed: "+speed);
    }
    private void slowDown()
    {
        switch(speed)
        {
//            case 90:speed=90;break;
            case 95:speed=90;break;
            case 98:speed=95;break;
            case 100:speed=98;break;
            case 102:speed=100;break;
            case 105:speed=102;break;
            case 110:speed=105;break;
            default:speed=speed-5;
        }
        timerController.setSpeed(speed);
        label.setText("Speed: "+speed);
    }
}
//
//    @Override
//    public void start(Stage stage) {
//        stage.setTitle("HTML");
//        stage.setWidth(500);
//        stage.setHeight(500);
//        Scene scene = new Scene(new Group());
//    
//        VBox root = new VBox();     
// 
//        final WebView browser = new WebView();
//        final WebEngine webEngine = browser.getEngine();
//        
//     
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setStyle("-fx-background-color: white");
//        
//        scrollPane.setContent(browser);
//        webEngine.loadContent("<b>asdf</b>");
//         
//        
//        root.getChildren().addAll(scrollPane);
//        scene.setRoot(root);
// 
//        stage.setScene(scene);
//        stage.show();
//    }
// com.sun.javafx.application.PlatformImpl.tkExit()