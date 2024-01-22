package de.fh_kiel.iue.mob;

import android.os.AsyncTask;
import android.util.Log;
import java.net.URL;
import java.util.ArrayList;

//Async Task für Download der synthetischen Daten.
public class DownloadFilesTask extends AsyncTask <URL, Integer, Long> {

    GlobalVariables gv = GlobalVariables.getInstance();
    private static ArrayList<DataSetChangedListener> DataSetListener = new ArrayList();

    //Arrays für simulierte Datensätze.
    public String [] simulatedCountries = {"Germany","France","Italy","Maldives","Sudan","Zimbabwe","Mauritania","Mozambique","Nigeria","Swaziland","Tanzania","Iraq","Guyana","Namibia","Senegal","Turkmenistan","Afghanistan","Andorra","Fiji","Gabon","Uzbekistan","Cameroon","Cuba","Faroe Islands","El Salvador","Caribbean","Ethiopia","Mongolia","Puerto Rico","Samoa","Myanmar","Nicaragua","Seychelles","Tajikistan","Dominican Republic","Guinea","Barbados","CI","Laos","Libya","Panama","Bahrain","Benin","Ghana","Haiti","Montenegro","Somalia","Syria","Ecuador","Honduras"};
    public int [] simulatedInfections = {4895918,5664018,15042113,28395916,12142257,8285054,12043463,15411687,4860013,27838914,11427566,4406427,946870,22371295,19540511,5774287,16046060,26705106,6960591,5399840,22163474,2033,2953787,
                                        14274592,27501158,394935,23126009,3574877,13276060,18046451,28558410,18636942,12692705,308017,3680959,28175440,4803851,10751023,22963579,7943347,14018910,20745214,26470616,17558603,21604922,20690284,24382834,15161180,11395394,29739896};
    public int[] simulatedNewConfirmed = {1303,3540,7649,9204,6162,7536,3584,7007,1524,8260,2487,7781,4213,7548,9641,2088,7850,6025,2344,5940,5695,7756,5983,3803,3819,564,8602,3990,2376,4257,1389,3347,378,8040,1223,6945,6742,7719,3010,3063,6377,7733,9983,3244,7694,1687,6115,7287,9979,2192};


    long nrDataAdded = 1;
    boolean finished = false;

    public DownloadFilesTask() {
    }

    @Override
    protected Long doInBackground(URL... urls) {
        try {
                DataContainer.clearData();
                //Daten werden in doppelter Form hinzugefügt. Gesamtanzahl Daten ist 100.
                addDemoData();
                addDemoData();
                finished = true;
                sleepAndUpdate(200);
                return nrDataAdded;
        }
        catch (Exception e){
                nrDataAdded = -1;
                return nrDataAdded;
        }
    }

    @Override
    protected void onPostExecute(Long aLong) {
        Log.i("DownloadTask", "Task has been executed succesfully.");
        super.onPostExecute(aLong);
    }

    //Funktion um Verzögerung durchzuführen und registrierte Listener darüber zu informieren, dass sich Datensatz geändert hat.
    private void sleepAndUpdate(int time){

        for (DataSetChangedListener listener : DataSetListener){
            listener.DataChanged(nrDataAdded, finished);

        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addDemoData(){

        for (int i = 0; i < 50; i++) {
            //Bedingung zum Hinzufügen eines Datensatz ist, dass Infektionswert unter dem gewünschten Wert liegt.
            if(gv.isMaxInfectionsSet() && simulatedInfections[i] < gv.getMaxInfections()) {
                DataContainer.addData(new Data(simulatedCountries[i], simulatedInfections[i], simulatedNewConfirmed[i]));
            }
            nrDataAdded++;
            sleepAndUpdate(200);
        }

    }

     public static void register(DataSetChangedListener listener){
        DataSetListener.add(listener);
    }
}
