package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Activity des Start Bildschirm.
public class StartActivity extends AppCompatActivity{

    GlobalVariables gv = GlobalVariables.getInstance();
    Button btTable;
    Button btMap;
    Button btSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Button für den Recyclerview mit den Einträgen.
        btTable = findViewById(R.id.btTabelle);
        //Button für die Karte.
        btMap = findViewById(R.id.btKarte);
        //Button für die Einstellungen.
        btSettings = findViewById(R.id.btSettings);


        btTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Öffnet Tabellenansicht der App.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //Sorgt dafür, dass nur eine Main Activity geöffnet ist.
                startActivity(intent);
            }
        });

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Öffnet Kartenansicht der App.
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //Sorgt dafür, dass nur eine MapActivity geöffnet ist.
                startActivity(intent);
            }
        });

        btSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Öffnet Settingsansicht der App.
                Intent intent = new Intent(getApplicationContext(), StartMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //Sorgt dafür, dass nur eine Main Activity geöffnet ist.
                startActivity(intent);
            }
        });


    }


}