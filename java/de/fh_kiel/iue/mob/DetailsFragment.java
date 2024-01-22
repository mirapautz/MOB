package de.fh_kiel.iue.mob;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


//Fragment, dass erscheint, wenn im Landscape Modus auf einen Eintrag geklickt wird.
public class DetailsFragment extends Fragment {

   private TextView tvInfections;
   private TextView tvName;
   private TextView tvInzidenz;
   private int InzidenzValue;
   private int InfectionsText;
   private String NameText = "Choose a country";

    public DetailsFragment() {

        super(R.layout.fragment_details);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState )
    {
        super.onViewCreated( view, savedInstanceState );
        tvInfections = view.findViewById(R.id.tv_countryInfections);
        tvName = view.findViewById(R.id.tv_countryName);
        tvInzidenz = view.findViewById(R.id.tv_oneweekvalue);
        tvInzidenz.setText("New Confirmed:" + " " + InzidenzValue);
        tvInfections.setText("Infections:" + " " + InfectionsText);
        tvName.setText(" " + NameText);
    }

    public void setTvInfections(int text){
        //Setzt die Infektionen im TextView TvInfections.
        InfectionsText = text;
        if(tvInfections != null) {
            tvInfections.setText(" " + InfectionsText);
        }
    }

    public void setTvName(String text){
        //Setzt den Ländernamen im Textview TvName.
        NameText = text;
        if(tvName != null){
            tvName.setText(" " + NameText);
        }
    }

    public void setTvInzidenz(int value){
        //Setzt den Inzidenzwert im Textview TvInzidenz.
        InzidenzValue = value;
        if(tvInzidenz != null){
            tvInzidenz.setText(" " + InzidenzValue);
        }
    }


}