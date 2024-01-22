package de.fh_kiel.iue.mob;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//Klasse downloaded die realen Daten mit Hilfe von Volley.
public class VolleyDownloader {

    private static ArrayList<DataSetChangedListener> DataSetListener = new ArrayList();
    private static GlobalVariables gv = GlobalVariables.getInstance();

    //Downloaded alle in der API zur Verfügung stehenden Daten.
    public static void completeVolleyDownload(Context appContext){

        final boolean maxInfectionsSet = gv.isMaxInfectionsSet();
        final int maxInfections = gv.getMaxInfections();
        String url = "https://api.covid19api.com/summary";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray countryArray = (JSONArray) response.get("Countries");
                    Log.i("VolleyResponse", countryArray.toString());
                    for (int i = 0; i < countryArray.length(); i++) {
                        Log.i("VolleyResponse", countryArray.getJSONObject(i).toString());
                        //Downloaded, wenn maxInfections Wert gesetzt ist, nur bestimmte Daten.
                        if(Integer.parseInt(countryArray.getJSONObject(i).get("TotalConfirmed").toString()) <= maxInfections && maxInfectionsSet) {

                            DataContainer.addData(new Data(countryArray.getJSONObject(i).get("Country").toString(), countryArray.getJSONObject(i).getInt("TotalConfirmed"), countryArray.getJSONObject(i).getInt("NewConfirmed")));
                        }
                        else if(!maxInfectionsSet){

                            DataContainer.addData(new Data(countryArray.getJSONObject(i).get("Country").toString(), countryArray.getJSONObject(i).getInt("TotalConfirmed"), countryArray.getJSONObject(i).getInt("NewConfirmed")));
                        }
                        notifyListener(0, false);
                    }
                    notifyListener(0, true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyResponse", "Volley Download went wrong!");
                        notifyListener(-1, true); //Informiert Listener bei Fehlschlag des Download.

                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(1), 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(appContext).add(jsonObjectRequest);

    }

    //Updated Daten eines bestimmten Landes.
    public static void volleyUpdateDetailsByCountry(final String countryName, Context appContext){

        String url = "https://api.covid19api.com/summary";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray countryArray = (JSONArray) response.get("Countries");
                    Log.i("VolleyResponse", countryArray.toString());
                    for (int i = 0; i < countryArray.length(); i++) {
                        if(countryArray.getJSONObject(i).get("Country").toString().equals(countryName)){
                            Log.i("VolleyResponse", "have found" + countryName);
                            DataContainer.getDataByName(countryName).setNewConfirmed(countryArray.getJSONObject(i).getInt("NewConfirmed"));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyResponse", "Volley Download went wrong!");
                    }
                });
        Volley.newRequestQueue(appContext).add(jsonObjectRequest);

    }

    public static void register(DataSetChangedListener listener){
        DataSetListener.add(listener);
    }

    public static void notifyListener(long position, boolean finished){

        for(DataSetChangedListener mListener : DataSetListener){
            mListener.DataChanged(position,finished);
        }
    }


}

