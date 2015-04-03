/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import controllers.PlayerController;
import controllers.TimerController;
import javafx.scene.input.MouseEvent;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
    
    Integer resetmode=0;

    Scene scene;
    Stage primaryStage;
    
    Integer maxpages;
    Boolean pageturnenabled=true;
    
    static MyAegisub instance=null;
    public static MyAegisub getInstance()
    {
        return instance;
    }
    
    @Override
    public void start(Stage primaryStage) {
        instance=this;
        
        this.primaryStage=primaryStage;
                
        DbMan1.getInstance();
        
        maxpages=DbMan1.getInstance().getMaxPages();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timerController.stop();
                DbMan1.getInstance().close();
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
        
        scene = new Scene(grid, 619, 469);// on 800x640
//        scene = new Scene(grid, 697, 488);
// scene.getStylesheets().add(cssfile.toURI().toString());        
        
//--as
        EventHandler mouseEventHandler=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY)
                {
                    gotoPrevious();
                }
                else
                {
                    gotoNext();
                }
            }
        };
        browser.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
        scene.setOnMousePressed(mouseEventHandler);

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

        playerController.play(0);
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
    
    public void gotoNext()
    {
        Integer index=DbMan1.ids.indexOf(DbMan1.wordid);
        
        if(index>=DbMan1.ids.size()-1)//last character in page
        {
            nextPage();
        }
        else
        {
            timerController.setTime(DbMan1.times.get(index+1));
            playerController.play(timerController.getTime());
            
            //if now, is last character
            index=DbMan1.ids.indexOf(DbMan1.wordid);
            if(index>=DbMan1.ids.size()-1 && pageturnenabled)//last character in page
            {
                new Timer().start();
//                System.out.println("timer started");
            }            
        }
    }
    Integer pagecounter=1;
    private void previousPage()
    {
            if(DbMan1.previouspagewordtime==null)
            {
                //do nothing
            }
            else
            {
                pagecounter--;
                label.setText("Page "+pagecounter.toString());
                timerController.setTime(DbMan1.previouspagewordtime);
                playerController.play(timerController.getTime());
            }
    }
    private void nextPage()
    {
            if(DbMan1.nextpagewordtime==null)
            {
                //do nothing
            }
            else
            {
                pagecounter++;
                label.setText("Page "+pagecounter.toString());
                timerController.setTime(DbMan1.nextpagewordtime);
                playerController.play(timerController.getTime());
            }
    }
    private void previous10Page()
    {
            if(pagecounter<11)
            {
                pagecounter=1;
            }
            else
            {
                pagecounter-=10;
            }
                label.setText("Page "+pagecounter.toString());
                Integer[] times=DbMan1.getInstance().selectPageByPageNo(pagecounter);
                Integer starttime=times[0];
                timerController.setTime(starttime);
                playerController.play(timerController.getTime());
    }
    private void next10Page()
    {
            if(pagecounter+10>maxpages)
            {
                pagecounter=maxpages;
            }
            else
            {
                pagecounter+=10;
            }
                label.setText("Page "+pagecounter.toString());
                Integer[] times=DbMan1.getInstance().selectPageByPageNo(pagecounter);
                Integer starttime=times[0];
                timerController.setTime(starttime);
                playerController.play(timerController.getTime());
    }
    
    private void gotoPrevious()
    {
        Integer index=DbMan1.ids.indexOf(DbMan1.wordid);
        
        if(index<=0)//first character in page
        {
            previousPage();
        }
        else
        {
            timerController.setTime(DbMan1.times.get(index-1));
            playerController.play(timerController.getTime());
        }
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
    private void reset()
    {
        pagecounter=0;
        //press 'R' 3 times to activate reset
        if(resetmode==2)
        {
            timerController.setTime(0);
            playerController.play(timerController.getTime());
            resetmode=0;
        }
        else 
            resetmode++;
    }
EventHandler eventHandler=
                    new EventHandler<KeyEvent>() {
                        public void handle(KeyEvent event) { 
                            
//        System.out.println("Filtering out event " + event.getEventType()); 
        String s=event.getText();
        for(int i=0;i<s.length();i++)
        {
            int j=(int)s.charAt(i);

            //bring reset mode back to 0
            //if key pressed is not "R"
            if(j!=114)resetmode=0;

            switch(j)
            {
                case 13://enterx    
                    play();
                    break;
                case 32://space
                    pause();

//            timerController.setTime(1970000);
//            playerController.play(timerController.getTime());
                    
        System.out.print("scene width: ");
        System.out.println(scene.getWidth());
        System.out.print("scene height: ");
        System.out.println(scene.getHeight());
        System.out.print("primaryStage width: ");
        System.out.println(primaryStage.getWidth());
        System.out.print("primaryStage height: ");
        System.out.println(primaryStage.getHeight());
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
                case 114://R
                    reset();
                    break;
                case 45://-
                    previousPage();
                    break;
                case 61://=
                    nextPage();
                    break;
                case 57://(
                    previous10Page();
                    break;
                case 48://)
                    next10Page();
                    break;
                case 122://Z
                    enableAutoPageTurn();
                    break;
                case 120://X
                    disableAutoPageTurn();
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
                    };
        public void enableAutoPageTurn()
        {
            pageturnenabled=true;
            label.setText("Auto Page Turn Enabled");
        }
        public void disableAutoPageTurn()
        {
            pageturnenabled=false;
            label.setText("Auto Page Turn Disabled");
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