/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.fileaccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaspertomas
 */
public class DateFormat {
    public static SimpleDateFormat sfin = new SimpleDateFormat("MMMM dd, yyyy");
    public static SimpleDateFormat sfout = new SimpleDateFormat("yyyy/MM/dd");
    public static String[] nullresult={"","",""};
    public static String[] parse(String datestring)
    {
        try {
            Date date = sfin.parse(datestring);
            String[] segments=sfout.format(date).split("/");
//            String year=segments[0];
//            String month=segments[1];
//            String day=segments[2];
//            System.out.println(year+" "+month+" "+day);
            return segments;
        } catch (ParseException ex) {
            Logger.getLogger(DateFormat.class.getName()).log(Level.SEVERE, null, ex);
            return nullresult;
        }
    
    }
    
}
