/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import myaegisub.AssReconciler;
import myaegisub.DbMan1;

/**
 *
 * @author jaspertomas
 */
public class PlayerController {
    
   WebEngine webEngine;
   public PlayerController(WebEngine webEngine){
       this.webEngine=webEngine;
   }
    public void play()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.loadContent(gen(TimerController.time));
            }
        });        
    }
    private String gen(Integer time)
    {
        String output="";
        
        //connect to database
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return "";
        }

        jdbc.selectWord(time);
        Integer wordid=DbMan1.wordid;
        System.out.println("word:"+wordid.toString());

        jdbc.selectPage(time);
        Integer pageno=DbMan1.pageno;
        System.out.println("page:"+pageno.toString());

        jdbc.selectLine(time);
        Integer lineno=DbMan1.lineno;
        System.out.println("line:"+lineno.toString());
        
        //select words that belong to this page 
        jdbc.select(" where page = "+pageno.toString());
        
        //determine number of words per line
        Integer[] wordsperline=new Integer[AssReconciler.linesperpage];
        //initialize words per line to 0
        for(int i=0;i<wordsperline.length;i++)
        {
            wordsperline[i]=0;
        }
        for(Integer line:DbMan1.lines)
        {
            wordsperline[(line+AssReconciler.zeropageadjustment)%4]++;
        }
//        for(int i=0;i<wordsperline.length;i++)
//        {
//            System.out.println(wordsperline[i]);
//        }
        
        String fontopenred="<font size=4 color=red>";
        String fontopenblack="<font size=4 >";
        String fontopenbigred="<font size=7 color=red>";
        String fontopenbigblack="<font size=7>";
        String fontopen;
        String fontclose="</font>";
        
        //formulate html
        output+="<table>";
        //for each line...
        Integer wordcounter1=0,wordcounter2=0,wordcounter3=0;
        for(int i=0;i<AssReconciler.linesperpage;i++)
        {
            //
            output+="<tr>";
            for(int j=0;j<wordsperline[i];j++)
            {
                if(DbMan1.lines.get(wordcounter1)<lineno)fontopen=fontopenred;
                else if(DbMan1.lines.get(wordcounter1)>lineno)fontopen=fontopenblack;
                else if(DbMan1.ids.get(wordcounter1)<=DbMan1.wordid)fontopen=fontopenred;
                else fontopen=fontopenblack;
                output+="<td>"+fontopen+DbMan1.characters.get(wordcounter1)+fontclose+"</td>";
                wordcounter1++;
            }
            output+="</tr><tr>";
            for(int j=0;j<wordsperline[i];j++)
            {
                if(DbMan1.lines.get(wordcounter2)<lineno)fontopen=fontopenred;
                else if(DbMan1.lines.get(wordcounter2)>lineno)fontopen=fontopenblack;
                else if(DbMan1.ids.get(wordcounter2)<=DbMan1.wordid)fontopen=fontopenred;
                else fontopen=fontopenblack;
                output+="<td>"+fontopen+DbMan1.sounds.get(wordcounter2)+fontclose+"</td>";
                wordcounter2++;
            }
            output+="</tr><tr>";
            for(int j=0;j<wordsperline[i];j++)
            {
                if(DbMan1.lines.get(wordcounter3)<lineno)fontopen=fontopenbigred;
                else if(DbMan1.lines.get(wordcounter3)>lineno)fontopen=fontopenbigblack;
                else if(DbMan1.ids.get(wordcounter3)<=DbMan1.wordid)fontopen=fontopenbigred;
                else fontopen=fontopenbigblack;
                output+="<td>"+fontopen+DbMan1.englishes.get(wordcounter3)+fontclose+"</td>";
                wordcounter3++;
            }
            output+="</tr><tr><td>&nbsp;</td>";
            output+="</tr>";
        }
        output+="</table>";
        
//        
        
        
//        Integer starttime=0;
//        Integer endtime=0;
//        Integer lineno=0;
//        Integer pageno=0;
//        jdbc.select("where line = "+lineno.toString()+"");
//        while(!DbMan1.ids.isEmpty())
//        {
//            starttime=DbMan1.times.get(0);
//            endtime=DbMan1.times.get(DbMan1.ids.size()-1);
//            pageno=DbMan1.pages.get(0);
//            jdbc.insertLine(lineno, pageno, starttime, endtime);
//            
//            //-next iteration------
//            lineno++;
//            jdbc.select("where line = "+lineno.toString()+"");
//        }
//        
//
//        
//        
        jdbc.close();
        return output;
    }


}
