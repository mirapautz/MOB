package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

//Klasse die geöffnet wird wenn im Potraitmodus eine Detailansicht gezeigt wird.
public class DetailsActivity extends AppCompatActivity {

    DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Bekommt Namen des Landes auf das geklickt wurde als Bundle übergeben.
        Bundle b = getIntent().getExtras();
        String name = b.getString("CountryName");
        setContentView(R.layout.activity_details);
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsfragment);
        ArrayList<Data> data = DataContainer.getData();

        //Durchsucht Datensatz um Fragment mit Daten des Landes zu befüllen.
        for (Data mData : data) {
            String currentCountry = mData.getCountry();
            if (currentCountry.equals(name)) {
                detailsFragment.setTvInfections(mData.getValue());
                detailsFragment.setTvName(mData.getCountry());
                detailsFragment.setTvInzidenz(mData.getNewConfirmed());
                break;
            }
    }
    }


}