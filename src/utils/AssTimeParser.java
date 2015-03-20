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
public class AssTimeParser {
    public static ArrayList<String> times=new ArrayList<String>();
    public static ArrayList<String> words=new ArrayList<String>();
    public static void parse(String filename,String songname)
    {
        System.out.println("AssTimeParser:parsing");
//        DbMan1 jdbc = new DbMan1();
//        if (jdbc.connect("database.db")) {
//            System.out.println("Opened database successfully");
//        }else{
//            System.out.println("Error opening database");
//            return;
//        }
        
//        ArrayList<Integer> ids=jdbc.select(songname);
        
        String input=FileReader.read(filename).replace("\\N", "");
        String[] sections=input.split("\\[Events\\]");
        if(sections.length<2)return;
        
        //sections[1] is [Events]
        String[] lines=sections[1].split("\n");
        String[] segments,segments2;
        String[] columns;
        
        for(String line:lines)
        {
            //ignore lines not starting with "Dialogue: "
            if(!line.contains("Dialogue: "))continue;
            
            columns=line.split(",");
//            if(columns.length<9)continue;//if no data, do nothing
            segments=columns[9].split("[{]");
            for(String segment:segments)
            {
                
                if(segment.trim().isEmpty());//do nothing
                else if(segment.contains("}"))
                {
                    System.out.println(segment);
                    //possible values for segment: "","\k195","zhòng "
                    segments2=segment.split("[}]");
                        times.add(segments2[0].replace("\\k", ""));
                        if(segments2.length==1)
                            words.add("");
                        else
                            words.add(segments2[1]);
                }
                
                
//                if(segment.trim().isEmpty());//do nothing
//                else if(segment.contains("\\k"))
//                {
//                    times.add(segment.replace("\\k", ""));
//                }
//                else
//                {
////                    System.out.println(segment);
////                    output.concat(" "+segment);
//                    words.add(segment);
//                }
            }
        }
//        for(String s:times)System.out.println(s);
//        for(String s:words)System.out.println(s);
//        for(int i=0;i<times.size();i++)
//        {
//            jdbc.update( ids.get(i), times.get(i));
//        }
//        jdbc.close();
    }
}
