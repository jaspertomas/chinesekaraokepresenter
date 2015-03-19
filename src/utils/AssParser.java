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
}
