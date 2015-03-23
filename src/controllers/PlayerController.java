/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
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
    public void play(Integer time)
    {
        webEngine.loadContent(gen(time));
    }
    private String gen(Integer time)
    {
        String output="";
        DbMan1 jdbc=DbMan1.getInstance();

        jdbc.selectWordByTime(time);
        Integer wordid=DbMan1.wordid;
//        System.out.println("word:"+wordid.toString());

        jdbc.selectPageByTime(time);
        Integer pageno=DbMan1.pageno;
        
//        System.out.println("page:"+pageno.toString());

        jdbc.selectLineByTime(time);
        Integer lineno=DbMan1.lineno;
//        System.out.println("line:"+lineno.toString());
        
        //select words that belong to this page 
//        jdbc.select(" where page = "+pageno.toString()+" or page = "+Integer.valueOf(pageno-1).toString()+" or page = "+Integer.valueOf(pageno+1).toString()+" ");
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
        
        String pinyinfontopenred="<font size=6 color=magenta>";
        String pinyinfontopenblack="<font size=6 >";

        String chinesefontopenred="<font size=10 color=magenta>";
        String chinesefontopenblack="<font size=10 >";

        String kokinfontopenred="<font size=5 color=magenta>";
        String kokinfontopenblack="<font size=5 >";

        String titlefontopen="<font size=6 >";
        String spacerfontopen="<font size=6 >";
        
        String fontopen;
        String fontclose="</font>";
        
        //formulate html
        output+="<table width=100%>";
        //for each line...
        Integer wordcounter1=0,wordcounter2=0,wordcounter3=0;
        for(int i=0;i<AssReconciler.linesperpage;i++)
        {
            if(wordsperline[i]==1)
            {
                //title row
                //row 1
//                output+="<tr><td>&nbsp;</td></tr>";
                //row 2
                File imagefile=new File("kwanyin.png");
                String backgroundstring="<html><body background=\""+imagefile.toURI()+"\"><font size=10>";
//                output+=backgroundstring;
                output+="<tr>";
                for(int j=0;j<wordsperline[i];j++)
                {
                    output+="<td align=center colspan=8>"+titlefontopen+DbMan1.sounds.get(wordcounter2)+fontclose+"</td>";
                    wordcounter1++;
                    wordcounter2++;
                    wordcounter3++;
                }
                //row 3
//                output+="<tr><td>&nbsp;</td></tr>";
                //spacer
                output+="<tr><td><font size=1>&nbsp;</font></td>";
                output+="</tr>";
            }
            else
            {
                //lyrics
                output+="<tr>";
                for(int j=0;j<wordsperline[i];j++)
                {
                    if(DbMan1.lines.get(wordcounter1)<lineno)fontopen=pinyinfontopenred;
                    else if(DbMan1.lines.get(wordcounter1)>lineno)fontopen=pinyinfontopenblack;
                    else if(DbMan1.ids.get(wordcounter1)<=DbMan1.wordid)fontopen=pinyinfontopenred;
                    else fontopen=pinyinfontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.characters.get(wordcounter1)+fontclose+"</td>";
                    wordcounter1++;
                }
                output+="</tr><tr>";
                for(int j=0;j<wordsperline[i];j++)
                {
                    if(DbMan1.lines.get(wordcounter2)<lineno)fontopen=kokinfontopenred;
                    else if(DbMan1.lines.get(wordcounter2)>lineno)fontopen=kokinfontopenblack;
                    else if(DbMan1.ids.get(wordcounter2)<=DbMan1.wordid)fontopen=kokinfontopenred;
                    else fontopen=kokinfontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.sounds.get(wordcounter2)+fontclose+"</td>";
                    wordcounter2++;
                }
                output+="</tr><tr>";
                for(int j=0;j<wordsperline[i];j++)
                {
                    if(DbMan1.lines.get(wordcounter3)<lineno)fontopen=chinesefontopenred;
                    else if(DbMan1.lines.get(wordcounter3)>lineno)fontopen=chinesefontopenblack;
                    else if(DbMan1.ids.get(wordcounter3)<=DbMan1.wordid)fontopen=chinesefontopenred;
                    else fontopen=chinesefontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.englishes.get(wordcounter3)+fontclose+"</td>";
                    wordcounter3++;
                }
                output+="</tr><tr><td><font size=1>&nbsp;</font></td>";
                output+="</tr>";
            }

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
        return output;
    }


}
