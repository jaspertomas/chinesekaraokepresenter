/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import static controllers.TimerController.time;
import javafx.application.Platform;

public class Timer implements Runnable {
   private Thread t;
   private Integer seconds;
   private String name;
   private Boolean stopped=false;
   Timer(){
   }
   public void run() {
      try 
      {
          Thread.sleep(500);
//          System.out.println("timer action next page");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                      MyAegisub.getInstance().gotoNext();
                    }
                });        
          
      } catch (InterruptedException e) {}
   }
   
   public void start ()
   {
      if (t == null)
      {
         t = new Thread (this);
         t.start ();
      }
   }
   public void stop ()
   {
       stopped=true;
   }
}