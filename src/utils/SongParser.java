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
public class SongParser {
    public static void parse(String filename) 
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
        ArrayList<String> pinyinlist=new ArrayList<String>();
        ArrayList<String> kokinlist=new ArrayList<String>();
        ArrayList<String> characterlist=new ArrayList<String>();
        ArrayList<String> linearrays[]=new ArrayList[3];
        linearrays[0]=pinyinlist;
        linearrays[1]=kokinlist;
        linearrays[2]=characterlist;
        ArrayList<Integer> linelist=new ArrayList<Integer>();
        Integer linemarker=0;
        Integer linecounter=0;
        String[] lines=input.split("\n");
        String[] segments;
        for(String line:lines)
        {
            linemarker++;
            if(linemarker>4)
            {
                linemarker=1;
                linecounter++;        
            }
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
                    linearrays[linemarker-1].add(segment);
                    if(linemarker-1==0)linelist.add(linecounter);
                }
            }
        }
        for(int i=0;i<pinyinlist.size();i++)
        {
            jdbc.insert(filename.replace(".txt", ""),pinyinlist.get(i),kokinlist.get(i),characterlist.get(i),"",linelist.get(i));
        }
        jdbc.close();
    }    
}
