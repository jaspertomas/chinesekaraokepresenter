/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import javafx.scene.web.WebEngine;
import myaegisub.AssReconciler;
import myaegisub.DbMan1;
import static myaegisub.DbMan1.lines;

/**
 *
 * @author jaspertomas
 */
public class PlayerController {
    
   WebEngine webEngine;
   public PlayerController(WebEngine webEngine){
       this.webEngine=webEngine;
   }
    public void play(Integer time)
    {
        webEngine.loadContent(gen(time));
        
        
        
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

            jdbc.selectPage(time);
        Integer pageno=DbMan1.pageno;
        System.out.println("page:"+pageno.toString());
        
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
                output+="<td>"+DbMan1.characters.get(wordcounter1)+"</td>";
                wordcounter1++;
            }
            output+="</tr><tr>";
            for(int j=0;j<wordsperline[i];j++)
            {
                output+="<td>"+DbMan1.sounds.get(wordcounter2)+"</td>";
                wordcounter2++;
            }
            output+="</tr><tr>";
            for(int j=0;j<wordsperline[i];j++)
            {
                output+="<td>"+DbMan1.englishes.get(wordcounter3)+"</td>";
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
