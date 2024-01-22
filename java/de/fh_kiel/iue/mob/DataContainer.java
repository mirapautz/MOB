package de.fh_kiel.iue.mob;
import java.util.ArrayList;

public final class DataContainer {
    /*Klasse welche die Daten in Liste data hält.
    Final, damit sie nicht erweitert werden kann.
    Konstruktor ist privat, damit sie nicht instanziiert werden kann.*/

    private DataContainer(){}

   private static ArrayList<Data> data = new ArrayList<>();

    //Datensatz zum Container hinzufügen.
   static void addData(Data newData){
       data.add(newData);
   }

   static ArrayList getData () {
       return data;
   }


    //Daten aus Container löschen.
   static void clearData(){
       data.clear();
   }


    //Datensatz mit Countrynamen = namen zurück geben.
   static Data getDataByName(String name){
       for(Data mdata : data){
           if (mdata.getCountry().equals(name)){
               return mdata;
           }
       }
       return null;
   }

   //Wenn Container leer wird true zurück gegeben.
   static boolean isEmpty(){
       if(data.size() == 0){
           return true;
       }
       else{
           return false;
       }
   }
}
