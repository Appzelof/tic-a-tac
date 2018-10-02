package no.woact.bordan16.ticatac.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import no.woact.bordan16.ticatac.fragments.OptionsFragment;
import no.woact.bordan16.ticatac.fragments.PlayerFragment;
import no.woact.bordan16.ticatac.interfaces.RequestListener;

/**
 * Created by daniel on 21/04/2018.
 */

/**
 * Class that perform a HTTP call and request a JSONObject. We are using the pokeApi for free access.
 * To get the correct pokemon we combine our url base with our textview. That way we can make the correct
 * request to the REST API.
 */
public class RequestManager {
    private final static String URL_BASE = "https://pokeapi.co/api/v2/pokemon/";
    private String url;
    private String pokeUrl;
    private TextView textView;
    private RequestListener requestListener;

    public RequestManager(PlayerFragment playerFragment, TextView textView) {
        requestListener = playerFragment;
        this.textView = textView;
    }

    /**
     * Method that uses the VOLLEY library recommended by Google!
     * We simply combine our URL with playerName to get the correct url so we can make
     * a GET request.
     * @param context
     */
    public void getJsonObject(Context context) {
        //Creates a complete url.
        url = URL_BASE + textView.getText().toString().toLowerCase() + "/";
        //Creates a new jsonOjectRequest with the correct parameters.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //We use a try and catch block so we can get the correct info from our object.
                try {
                    JSONObject sprites = response.getJSONObject("sprites");
                    pokeUrl = sprites.getString("front_default");
                    System.out.println(pokeUrl);
                    requestListener.getString(pokeUrl);
                } catch (JSONException e){ // if it fails we will get a JSONException.
                    e.getMessage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("TIC", "Error " + error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }


}
