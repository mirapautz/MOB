package de.fh_kiel.iue.mob;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewHolder extends RecyclerView.ViewHolder
{
    //Klasse die Layout eines Datensatzes enthält.
    private Button btLeft;
    private TextView tvRight;
    private ArrayList<ButtonListener> btListener = new ArrayList<>(); 

    public ViewHolder( @NonNull View itemView )
    {
        super(itemView);
        btLeft = itemView.findViewById( R.id.tvCountryname);
        tvRight = itemView.findViewById( R.id.tvInfectionsvalue);

        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ButtonMessage", "I have been pressed");
                for (ButtonListener Listener: btListener) {
                    //Gibt Text des Buttons an registrierte ButtonListener weiter.
                    Listener.ButtonClicked((String) btLeft.getText());
                }
            }
        });
    }

    public Button getTvLeft()
    {
        return btLeft;
    }

    public TextView getTvRight()
    {
        return tvRight;
    }
    
    public void registerBtListener(ButtonListener newListener){
        btListener.add(newListener);
    }
}
