/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.AssParser;

/**
 *
 * @author jaspertomas
 */
public class MyAegisub extends Application {
    WebEngine webEngine;
    @Override
    public void start(Stage primaryStage) {

        WebView browser = new WebView();
        webEngine = browser.getEngine();
        
        StackPane root = new StackPane();
        root.getChildren().add(browser);

        browser.requestFocus();
//        File cssfile=new File("background.css");
        
//        browser.setStyle("body {background-color: yellow;}");
        Scene scene = new Scene(root, 500, 500);
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
                    System.out.println("enter");
                    break;
                case 32://pause
                    System.out.println("space");
                    break;
                case 44://slow down
                    System.out.println("<");
                    break;
                case 46://speed up
                    System.out.println(">");
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

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
//        webEngine.load("http://www.google.com");   

        File imagefile=new File("bananaman.jpg");
        String backgroundstring="<html><body background=\""+imagefile.toURI()+"\"><font size=10>";
//        String backgroundstring="<html><body>";
        System.out.println(backgroundstring);
//        webEngine.loadContent(AssParser.parse("3.ass"));
//        webEngine.loadContent(backgroundstring+AssParser.parse("3.ass"));

        AssParser.parseSong("song0.txt");
        AssParser.parseSong("song1.txt");
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
    }
    private void pause()
    {
    }
    private void speedUp()
    {
    }
    private void slowDown()
    {
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
// 