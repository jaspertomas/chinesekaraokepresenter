/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import controllers.PlayerController;
import java.io.File;
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

/**
 *
 * @author jaspertomas
 */
public class MyAegisub extends Application {
    WebView browser = new WebView();
    WebEngine webEngine = browser.getEngine();;
    Label label = new Label();
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
        
        PlayerController playerController=new PlayerController(webEngine);
        playerController.play(50000);
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
        label.setText("Play");
    }
    private void pause()
    {
        label.setText("Pause");
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
            case 110:speed=110;break;
        }
        label.setText("Speed: "+speed);
    }
    private void slowDown()
    {
        switch(speed)
        {
            case 90:speed=90;break;
            case 95:speed=90;break;
            case 98:speed=95;break;
            case 100:speed=98;break;
            case 102:speed=100;break;
            case 105:speed=102;break;
            case 110:speed=105;break;
        }
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
// 