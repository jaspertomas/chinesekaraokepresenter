/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.scene.web.WebEngine;
import myaegisub.Constants;
import myaegisub.DbMan1;

/**
 *
 * @author jaspertomas
 */
public class PlayerController {
   WebEngine webEngine;
   String title="";
   
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
        
        //determine if this word is a title, 
        //act accordingly
        Integer wordindex=DbMan1.ids.indexOf(DbMan1.wordid);
        if(DbMan1.characters.get(wordindex).contentEquals("."))//is title
        {
            
        }
        
        //determine number of words per line
        Integer[] wordsperline={0,0};
        Integer linesperpage=0;//lines on this page
        //initialize words per line to 0
        Integer currentline=0;
        for(int i=0,j=0;i<DbMan1.lines.size();i++)
        {
            //line change
            if(!currentline.equals(DbMan1.lines.get(i)))
            {
                j++;
                linesperpage++;
                currentline=DbMan1.lines.get(i);
            }
            wordsperline[j-1]++;
        }

        String pinyinfontopenred="<font size=6 color="+Constants.textcoloractive+">";
        String pinyinfontopenblack="<font size=6 color="+Constants.textcolorpassive+">";

        String chinesefontopenred="<font size=10 color="+Constants.textcoloractive+">";
        String chinesefontopenblack="<font size=10 color="+Constants.textcolorpassive+">";

        String kokinfontopenred="<font size=5 color="+Constants.textcoloractive+">";
        String kokinfontopenblack="<font size=5 color="+Constants.textcolorpassive+">";

        String titlefontopen="<font size=6 color="+Constants.textcolorpassive+">";
        String spacerfontopen="<font size=1 >";
        
        String fontopen;
        String fontclose="</font>";
        
        //formulate html
        output+="<html><body bgcolor="+Constants.bgcolor+"><table width=100%>";
        //for each line...
        Integer wordcounter1=0,wordcounter2=0,wordcounter3=0;
        for(int i=0;i<linesperpage;i++)
        {
            if(wordsperline[i]==1)
            {
                if(DbMan1.sounds.get(wordcounter2).length()!=1)
                {
                    title=DbMan1.sounds.get(wordcounter2);
                }
                //skip
                wordcounter1++;
                wordcounter2++;
                wordcounter3++;
            }
            else
            {
                //title row
                output+="<tr>";
                output+="<td align=center colspan=8>"+titlefontopen+title+fontclose+"</td>";
                output+="<tr><td><font size=1>&nbsp;</font></td>";
                output+="</tr>";
                
                
                //lyrics
                //row 1
                output+="<tr>";//open row
                for(int j=0;j<wordsperline[i]&&j<4;j++)
                {
                    if(DbMan1.lines.get(wordcounter1)<lineno)fontopen=pinyinfontopenred;
                    else if(DbMan1.lines.get(wordcounter1)>lineno)fontopen=pinyinfontopenblack;
                    else if(DbMan1.ids.get(wordcounter1)<=DbMan1.wordid)fontopen=pinyinfontopenred;
                    else fontopen=pinyinfontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.characters.get(wordcounter1)+fontclose+"</td>";
                    wordcounter1++;
                }
                output+="</tr><tr>";
                for(int j=0;j<wordsperline[i]&&j<4;j++)
                {
                    if(DbMan1.lines.get(wordcounter2)<lineno)fontopen=kokinfontopenred;
                    else if(DbMan1.lines.get(wordcounter2)>lineno)fontopen=kokinfontopenblack;
                    else if(DbMan1.ids.get(wordcounter2)<=DbMan1.wordid)fontopen=kokinfontopenred;
                    else fontopen=kokinfontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.sounds.get(wordcounter2)+fontclose+"</td>";
                    wordcounter2++;
                }
                output+="</tr><tr>";
                for(int j=0;j<wordsperline[i]&&j<4;j++)
                {
                    if(DbMan1.lines.get(wordcounter3)<lineno)fontopen=chinesefontopenred;
                    else if(DbMan1.lines.get(wordcounter3)>lineno)fontopen=chinesefontopenblack;
                    else if(DbMan1.ids.get(wordcounter3)<=DbMan1.wordid)fontopen=chinesefontopenred;
                    else fontopen=chinesefontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.englishes.get(wordcounter3)+fontclose+"</td>";
                    wordcounter3++;
                }
                output+="</tr>";//close row

                output+="<tr><td><font size=1>&nbsp;</font></td></tr>";//spacer
            
                //row 2
                output+="<tr>";//open row
                for(int j=4;j<wordsperline[i];j++)
                {
                    if(DbMan1.lines.get(wordcounter1)<lineno)fontopen=pinyinfontopenred;
                    else if(DbMan1.lines.get(wordcounter1)>lineno)fontopen=pinyinfontopenblack;
                    else if(DbMan1.ids.get(wordcounter1)<=DbMan1.wordid)fontopen=pinyinfontopenred;
                    else fontopen=pinyinfontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.characters.get(wordcounter1)+fontclose+"</td>";
                    wordcounter1++;
                }
                output+="</tr><tr>";
                for(int j=4;j<wordsperline[i];j++)
                {
                    if(DbMan1.lines.get(wordcounter2)<lineno)fontopen=kokinfontopenred;
                    else if(DbMan1.lines.get(wordcounter2)>lineno)fontopen=kokinfontopenblack;
                    else if(DbMan1.ids.get(wordcounter2)<=DbMan1.wordid)fontopen=kokinfontopenred;
                    else fontopen=kokinfontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.sounds.get(wordcounter2)+fontclose+"</td>";
                    wordcounter2++;
                }
                output+="</tr><tr>";
                for(int j=4;j<wordsperline[i];j++)
                {
                    if(DbMan1.lines.get(wordcounter3)<lineno)fontopen=chinesefontopenred;
                    else if(DbMan1.lines.get(wordcounter3)>lineno)fontopen=chinesefontopenblack;
                    else if(DbMan1.ids.get(wordcounter3)<=DbMan1.wordid)fontopen=chinesefontopenred;
                    else fontopen=chinesefontopenblack;
                    output+="<td align=center>"+fontopen+DbMan1.englishes.get(wordcounter3)+fontclose+"</td>";
                    wordcounter3++;
                }
                output+="</tr>";//close row
            }

        }
        output+="</table>";
//        System.out.println(output);
        
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
