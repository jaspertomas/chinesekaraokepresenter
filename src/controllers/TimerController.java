/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import utils.AssParser;

public class TimerController implements Runnable {
   public final Integer PAUSED=0;
   public final Integer PLAYING=1;
   public final Integer STOPPED=2;
    
   private Thread t;
//   private Boolean stopped=false;
   private Integer status=0;
   private Integer speed=100;
   public static Integer time=0;
   
   PlayerController playerController;
   public TimerController(PlayerController playerController){
       this.playerController=playerController;
       start();
   }
   public void run() {
      try 
      {
          while(true)
          {
//              System.out.println(status);
              if(status==PAUSED)
              {
                  //do nothing
              }
              else if(status==PLAYING)
              {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                          playerController.play(time);
                    }
                });        
                  time+=speed;
              }
              else if(status==STOPPED)
              {
                  break;
              }
//              System.out.println(time);
              Thread.sleep(100);
          }
      } catch (InterruptedException e) {}
   }
   
   public void start ()
   {
      if (t == null)
      {
         t = new Thread (this);
      }
      t.start ();
   }
   public void stop ()
   {
       status=STOPPED;
   }
   public void pause ()
   {
       status=PAUSED;
   }
   public void play ()
   {
       status=PLAYING;
   }
   public Boolean isPlaying ()
   {
       return status==PLAYING;
   }
   public Boolean isPaused ()
   {
       return status==PAUSED;
   }
   public Boolean isStopped ()
   {
       return status==STOPPED;
   }
   public void setSpeed (Integer speed)
   {
       this.speed=speed;
   }
   public void setTime (Integer time)
   {
       this.time=time;
   }
   public Integer getTime ()
   {
       return time;
   }
}