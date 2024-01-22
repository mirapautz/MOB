package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

//Activity für das Menu der App.
public class StartMenuActivity extends AppCompatActivity implements DataSetChangedListener, PopupMenu.OnMenuItemClickListener {
    GlobalVariables gv = GlobalVariables.getInstance();
    Button btExecute;
    Switch simulatedDownload;
    ProgressBar mProgressBar;
    DownloadFilesTask mDownloadFilesTask;
    SeekBar maxInfectionsBar;
    long progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        btExecute = findViewById(R.id.actionsButton);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        simulatedDownload = findViewById(R.id.simDownSwitch);
        maxInfectionsBar = findViewById(R.id.seekBar5);
        maxInfectionsBar.setProgress(15000000);
        DownloadFilesTask.register(this);


        //Execute Button öffnet ein PopUpMenu mit den Optionen Refresh und Reset.
        btExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(StartMenuActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popupmenu, popup.getMenu());
                popup.setOnMenuItemClickListener(StartMenuActivity.this);
                popup.show();

            }
        });
    }


        public void DataChanged(long position, boolean finishedChange){
            progress = position;
            mProgressBar.setProgress((int)progress);

            if(finishedChange){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        Toast feedbackToast = Toast.makeText(getApplicationContext(), "Simulated Data has been updated succesfully! Max Infections = " + maxInfectionsBar.getProgress(), Toast.LENGTH_SHORT);
                        feedbackToast.show();
                    }
                });
            }
            if(position == -1){
                mProgressBar.setVisibility(View.GONE);
                Toast feedbackToast = Toast.makeText(getApplicationContext(), "Updating simulated Data went wrong!", Toast.LENGTH_SHORT);
                feedbackToast.show();
            }
        }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //Refresh Button aktualisiert den Datensatz entsprechend der angegebenen Werte.
        if(item.getItemId() == R.id.refreshMenu){

            boolean stateOfSwitch = simulatedDownload.isChecked();
            DataContainer.clearData();
            if (stateOfSwitch) {
                Log.i("StateSwitch", (String.valueOf(stateOfSwitch)));
                mProgressBar.setVisibility(View.VISIBLE);
                mDownloadFilesTask = new DownloadFilesTask();
                mDownloadFilesTask.execute();
                gv.setSimulatedDownload(true);
                gv.setMaxInfections(maxInfectionsBar.getProgress());
                gv.setMaxInfectionsSet(true);
            }
            else{
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(50);
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.GONE);
                Toast feedbackToast = Toast.makeText(getApplicationContext(), "Succesfully switched to real data! Max Infections =" + maxInfectionsBar.getProgress(), Toast.LENGTH_SHORT);
                feedbackToast.show();
                gv.setSimulatedDownload(false);
                gv.setMaxInfectionsSet(true);
                gv.setMaxInfections(maxInfectionsBar.getProgress());
            }
        }
        //Reset Button setzt auf Defaultwerte zurück.
        if(item.getItemId() == R.id.resetMenu){
            simulatedDownload.setChecked(false);
            maxInfectionsBar.setProgress(15000000);
            gv.setMaxInfections(0);
            gv.setMaxInfectionsSet(false);
        }

            return false;
    }
    }
