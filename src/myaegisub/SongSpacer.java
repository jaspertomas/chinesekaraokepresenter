/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import utils.fileaccess.FileReader;
import utils.fileaccess.FileWriter;

/**
 *
 * @author jaspertomas
 */
public class SongSpacer {
    static String songfilename="song000.txt";
    public static void main(String args[])    
    {
        String s=FileReader.read(songfilename);
        String output="";
        String[] lines=s.split("\n");
        for(int i=0;i<lines.length;i++)
        {
            if(i%12==0)
            {
                output+="x\n.\n.\n\n";
            }
            output+=lines[i]+"\n";
        }
        FileWriter.write(songfilename, output);
    }    
}
