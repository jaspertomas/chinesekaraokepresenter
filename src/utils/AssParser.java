/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import myaegisub.DbMan1;
import utils.fileaccess.FileReader;

/**
 *
 * @author jaspertomas
 */
public class AssParser {
    public static String parse(String filename)
    {
        String output="";
        String input=FileReader.read(filename).replace("\\N", "");
        String[] sections=input.split("\\[Events\\]");
        if(sections.length<2)return "";
        
        //sections[1] is [Events]
        String[] lines=sections[1].split("\n");
        String[] segments;
        String[] columns;
        for(String line:lines)
        {
            //ignore lines not starting with "Dialogue: "
            if(!line.contains("Dialogue: "))continue;
            
            columns=line.split(",");
            segments=columns[9].split("[{}]");
            for(String segment:segments)
            {
                //possible values for segment: "","\k195","zhòng "
                if(segment.trim().isEmpty());//do nothing
                else if(segment.contains("\\k"));//do nothing
                else
                {
//                    System.out.println(segment);
//                    output.concat(" "+segment);
                    output+=" "+segment;
                }
            }
        }
        return output;
    }
    public static void parseSong(String filename) 
    {
        DbMan1 jdbc = new DbMan1();
        if (jdbc.connect("database.db")) {
            System.out.println("Opened database successfully");
        }else{
            System.out.println("Error opening database");
            return;
        }

        if (jdbc.createTable()) {
            System.out.println("Table created successfully");
        }
        
        
        
        String input=FileReader.read(filename);
        
        //sections[1] is [Events]
        ArrayList<String> line1=new ArrayList<String>();
        ArrayList<String> line2=new ArrayList<String>();
        ArrayList<String> line3=new ArrayList<String>();
        ArrayList<String> linearrays[]=new ArrayList[3];
        linearrays[0]=line1;
        linearrays[1]=line2;
        linearrays[2]=line3;
        Integer linecounter=0;
        String[] lines=input.split("\n");
        String[] segments;
        for(String line:lines)
        {
            linecounter++;
            if(linecounter>4)linecounter=1;
            segments=line.split("\t");
            for(String segment:segments)
            {
                //possible values for segment: "","\k195","zhòng "
                if(segment.trim().isEmpty());//do nothing
                else if(segment.contains("。"));//do nothing
                else
                {
//                    System.out.println(segment.trim());
//                    output.concat(" "+segment);
//                    output+=" "+segment;
//                    jdbc.insert();
                    linearrays[linecounter-1].add(segment);
                }
            }
        }
        for(int i=0;i<line1.size();i++)
        {
            jdbc.insert(filename.replace(".txt", ""),line1.get(i),line2.get(i),line3.get(i),"");
        }
        jdbc.close();
    }
}
