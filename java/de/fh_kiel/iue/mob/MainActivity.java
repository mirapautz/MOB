package de.fh_kiel.iue.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Main Activity beschreibt die Activity mit der RecyclerView Ansicht.
public class MainActivity extends AppCompatActivity implements ButtonListener, DataSetChangedListener {

    GlobalVariables gv = GlobalVariables.getInstance();
    RecyclerView rcView; //Recycler View aus dem Main Acitivity Layout.
    Adapter mAdapter; //Adapter um die Daten an einen Viewholder zu binden.
    DetailsFragment detailsFragment; //Fragment Detailansicht für Landscape Modus.
    String detailsCountry;
    RecyclerView.LayoutManager mLayoutManager; //Layout Manager um Abfolge und Anordnung der RecyclerView Elemente zu organisieren.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanziierung des LayoutManager, Adapter, Recycler View aus Layout wird instanziiert.
        mLayoutManager = new LinearLayoutManager(this);
        //Dem Adapter wird die Activity als btListener übergeben. Klassen die Button Listener implementieren benötigen eine Methode ButtonClicked().
        mAdapter = new Adapter(DataContainer.getData(), this);
        rcView = findViewById(R.id.rcView);

        //LayoutManager und Adapter für RecyclerView werden gesetzt.
        rcView.setLayoutManager(mLayoutManager);
        rcView.setAdapter(mAdapter);

        //MainActivity wird bei VolleyDownloader&DownloadFilesTask als DataSetChanged Listener registriert.
        VolleyDownloader.register(this);
        DownloadFilesTask.register(this);

        //Falls simulierter Download nicht gesetzt ist, werden reale Daten runter geladen. Somit werden Daten bei jedem Aufruf der Activity aktualisiert.
        if(gv.simulatedDownloadStatus() == false){
            VolleyDownloader.completeVolleyDownload(getApplicationContext());
        }
    }


    @Override
        protected void onResume() {
        super.onResume();
        detailsCountry = "";
        //Wenn sich die App im Landscape Modus befindet wird ein DetailsFragment instanziiert. Solange kein Eintrag angeklickt wird, ist das Fragment nicht sichtbar.
        if(getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE) {
            detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fgDetails);
            detailsFragment.getView().setVisibility(View.GONE);
        }
    }

    @Override
    //Speichert den Namen des Landes, welches für die Detailansicht angeklickt wurde ab.
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("detailsCountry", detailsCountry);

    }

    @Override
    //Wenn ein Detailscountry abgespeichert wurde, wird eine Detailansicht in einer Detailsactivity geöffnet. Tritt ein, wenn in Landscape Ansicht eine Detailansicht geöffnet ist, und in Potraitansicht gewechselt wird.
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedCountryDetails = savedInstanceState.getString("detailsCountry");
        if(savedCountryDetails != "") {

            this.startDetailsActivity(savedCountryDetails);
        }
    }

    //Wird aufgerufen, wenn ein Listeneintrag aus dem RecyclerView angeklickt wird.
    public void ButtonClicked(String countryName){

        //Wenn mit realen Daten gearbeitet wird, werden die Details des jeweiligen Landes geupdated.
        if(gv.simulatedDownloadStatus() == false){
            VolleyDownloader.volleyUpdateDetailsByCountry(countryName, this);
        }

        //Wenn ein Eintrag angeklickt wird, und sich die App im Potrait Modus befindet, wird eine Activity mit einem Details Fragment geöffnet. Im Landscape Modus wird das Fragment mit Daten befüllt und sichtbar gemacht.
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            detailsCountry = countryName;
            detailsFragment.getView().setVisibility(View.VISIBLE);
            ArrayList<Data> data = DataContainer.getData();

            //For Schleife durchsucht Daten nach den Daten des angeklickten Eintrags.
            for (Data mData : data) {
                String currentCountry = mData.getCountry();
                if (currentCountry.equals(countryName)) {
                    Log.i("ButtonInfo", "Country Found!");
                    detailsFragment.setTvInfections(mData.getValue());
                    detailsFragment.setTvName(mData.getCountry());
                    detailsFragment.setTvInzidenz(mData.getNewConfirmed());
                    break;

                } else {
                    Log.i("ButtonInfo", "Country wasnt found!");
                }
            }
        }
        else {
            this.startDetailsActivity(countryName);
        }

    }

    //Wird von VolleyDownloader & DownloadFilesTask aufgerufen, wenn Datensatz sich geändert hat.
    public void DataChanged(final long position, final boolean finished) {
        //Position von -1 bedeutet VolleyDownload fehlgeschlagen.
        if(position == -1){

            this.addErrorMessageToView();
        }

        Log.i("DataSetChanged", "Data has been changed.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                if(finished && position == 0){
                    Toast feedbackToast = Toast.makeText(getApplicationContext(), "Loading Data succeeded!", Toast.LENGTH_SHORT);
                    feedbackToast.show();
                }
            }
        });

    }

    //Funktion startet DetailsActivity mit entsprechende Daten.
    public void startDetailsActivity(String countryName){

        Bundle b = new Bundle();
        b.putString("CountryName", countryName);
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //Sorgt dafür das maximal eine DetailsActivity gleichzeitig geöffnet ist. Gibt es bereits eine offene wird diese in den Vordergrund gebracht.
        intent.putExtras(b);

        startActivity(intent);
    }

    //Fügt bei Fehler im VolleyDownload Error Nachricht zum Bildschirm hinzu.
    public void addErrorMessageToView(){
        DataContainer.clearData();
        Log.i("VolleyDownload", "Volley Download failed!");
        View constraintLayout = findViewById(R.id.tableLayout);
        TextView downloadFailed = new TextView(this);
        downloadFailed.setText("Volley Download went wrong! \nCheck if https://api.covid19api.com/summary is available \nand restart app.");
        downloadFailed.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        ((ConstraintLayout) constraintLayout).addView(downloadFailed);
        downloadFailed.setId((100));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) constraintLayout);
        constraintSet.connect(downloadFailed.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 18);
        constraintSet.connect(downloadFailed.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 18);
        constraintSet.connect(downloadFailed.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 18);
        constraintSet.connect(downloadFailed.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 18);

        constraintSet.applyTo((ConstraintLayout) constraintLayout);

    }
}