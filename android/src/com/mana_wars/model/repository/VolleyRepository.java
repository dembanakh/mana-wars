package com.mana_wars.model.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyRepository {

    private static VolleyRepository instance;
    private RequestQueue requestQueue;

    public static synchronized VolleyRepository getInstance(Context context){
        if (instance == null) instance = new VolleyRepository(context);
        return instance;
    }

    private VolleyRepository(Context context){
        this.requestQueue = Volley.newRequestQueue(context);
    }

    private synchronized <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }


    //TODO delete temp solution
    private static final String gss_link = "https://script.google.com/macros/s/AKfycbxkCNcnUbanJsE_4iPaLmEB11yJDj46Rk6ICY8btXOxuvPMWqg/exec";

    public void postFCMUserTokenToServer(String token){
        StringRequest request = new StringRequest(Request.Method.POST, gss_link,
                    response -> {Log.i("FCM Valley response", response);},
                    error -> {
                        Log.e("FCM Volley FCM error", error.toString());
                    })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", "add_user");
                params.put("fcm_token", token);

                return params;
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        addToRequestQueue(request);
    }

}
