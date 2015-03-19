/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import javafx.scene.web.WebEngine;
import utils.AssParser;

class TimerController implements Runnable {
   public final Integer PAUSED=0;
   public final Integer PLAYING=1;
    
   private Thread t;
//   private Boolean stopped=false;
   private Integer status=0;
   private Integer speed=10;
   private Integer time=0;
   
   PlayerController playerController;
   TimerController(PlayerController playerController){
       this.playerController=playerController;
   }
   public void run() {
      try 
      {
          while(true)
          {
              if(status==PAUSED)
              {
                  //do nothing
              }
              else if(status==PLAYING)
              {
                  playerController.play(speed);
                  time+=speed;
              }
              Thread.sleep(100);
          }
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
//   public void stop ()
//   {
//       status=STOPPED;
//   }
   public void pause ()
   {
       status=PAUSED;
   }
   public void play ()
   {
       status=PLAYING;
   }
//   public void slowDown ()
//   {
//       switch(speed)
//       {
//           case 9:break;//if already at lowest setting, do nothing;
//           default: speed--;
//       }
//   }
//   Integer topspeed=11;
//   public void speedUp ()
//   {
//       switch(speed)
//       {
//           case 11:break;//if already at lowest setting, do nothing;
//           default: speed--;
//       }
//   }
   public void setSpeed (Integer speed)
   {
       this.speed=speed;
   }
}