/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import utils.fileaccess.FileReader;

/**
 *
 * @author jaspertomas
 */
public class AssParser {
    public static void parse(String filename)
    {
        String input=FileReader.read(filename);
        String[] sections=input.split("\\[Events\\]");
        if(sections.length<2)return;
        
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
                System.out.println(segment);
            }
        }
    }
}
