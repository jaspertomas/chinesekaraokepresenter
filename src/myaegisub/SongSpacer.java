/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myaegisub;

import java.util.ArrayList;
import utils.fileaccess.FileReader;
import utils.fileaccess.FileWriter;

/**
 *
 * @author jaspertomas
 */
public class SongSpacer {
    static String songfilename="";
    static String[] songfilenumbers={"_morning","_afternoon","14a"};
//    static String[] songfilenumbers={"00","01","02","03","04","05","06","07","08","09","10","11","12a","12b","12c","12d","12e","12f","13","14","15","16","17","18","19","20","21","22b"};
    public static void main(String args[])    
    {
        for(String number:songfilenumbers)
        {
            songfilename="song"+number+".txt";
            convert();
        }
//        merge();
    }    
    public static void convert()
    {
        String s=FileReader.read(songfilename);
        String output="";
        String[] lines=s.split("\n");
        for(int i=0;i<lines.length;i++)
        {
            if(i%4==0)
            {
                output+="x\n.\n.\n\n";
            }
            output+=lines[i]+"\n";
        }
        FileWriter.write(songfilename, output);
    }
    public static void merge()
    {
        String output="";
        for(String number:songfilenumbers)
        {
            System.out.println(number);
            songfilename="song"+number+".txt";
            String input=FileReader.read(songfilename);
            String segments[]=input.split("\n");
            ArrayList<String> segmentslist=new ArrayList<String>();
            for(String s:segments)
            {
                segmentslist.add(s);
            }
            while(segmentslist.size()%16!=0)
            {
                segmentslist.add("");
            }
            for(String s:segmentslist)
            {
                output+=s+"\n";
            }
        }
        FileWriter.write("song001.txt", output);
    }
}
