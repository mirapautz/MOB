package de.fh_kiel.iue.mob;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//Klasse die Datensatz des DataContainer in String für die Karte umwandelt.
public class DataConverter {

    public static String arrayToString(ArrayList<Data> data) {
        String returnString = "";
        //Filterung von Zeichen die nicht ASCII entsprechen.
        for (Data mdata : data){
            String countryName = mdata.getCountry();
            countryName = countryName.replace('\'', '-');
            byte[] nameBytes = new byte[0];

            try {
                nameBytes = countryName.getBytes("ASCII");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String countryNameASCII = new String(nameBytes);

            //Umwandlung der API Daten für Russland / USA in Daten die Karte benötigt.
            if(countryNameASCII.equals("Russian Federation")){
                countryNameASCII = "RU";
            }
            if(countryNameASCII.equals("United States of America")){
                countryNameASCII="United States";
            }
            returnString +="#"+countryNameASCII+ "+"+mdata.getValue(); //Zwischen Ländern # als Trennzeichen. Zwischen Land und Infektionswert +.
        }
        returnString = returnString.substring(1);
        return returnString;

    }
}
