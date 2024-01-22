package de.fh_kiel.iue.mob;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

//Klasse öffnet Karte mit Hilfe von WebView aus Assets Ordner.
public class MapActivity extends AppCompatActivity implements DataSetChangedListener {

    WebView webView;
    GlobalVariables gv = GlobalVariables.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        VolleyDownloader.register(this);

        //Führt VolleyDownload aus, wenn reale Daten ausgewählt sind. Daten werden somit bei jedem Aufruf aktualisiert.
        if(gv.simulatedDownloadStatus() == false){
            VolleyDownloader.completeVolleyDownload(getApplicationContext());
        }
        else if(gv.simulatedDownloadStatus() == true){
            createMap();
        }
    }

    @Override
    //Fügt, wenn Datensatzänderung fehlgeschlagen ist Fehlermeldung hinzu. Wenn nicht wird Map erstellt.
    public void DataChanged(long position, boolean finishedChange) {
        //Position -1 -> VolleyDownload fehlgeschlagen.
        if(position == -1){
            addErrorMessageToView();
        }
        else if(finishedChange) {
            createMap();
        }
    }

    //Erstellt Map mit Java Script Datei & Assets Ordner.
    public void createMap(){

        final String convertedData = DataConverter.arrayToString(DataContainer.getData());
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/html.html");
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript: tableData('" + convertedData + "')");

            }
        });
    }

    //Fügt Fehlernachricht hinzu.
    public void addErrorMessageToView(){

        DataContainer.clearData();
        View frameLayout = (FrameLayout) this.findViewById(R.id.mapLayout);
        TextView downloadFailedMap = new TextView(this);
        downloadFailedMap.setText("Volley Download went wrong! \nCheck if https://api.covid19api.com/summary is available \nand restart app.");
        downloadFailedMap.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        ((FrameLayout) frameLayout).addView(downloadFailedMap);
    }
}