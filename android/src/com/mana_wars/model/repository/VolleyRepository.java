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

import io.reactivex.Single;

public class VolleyRepository {

    private static VolleyRepository instance;
    private static final RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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

    public Single<String> doGetRequest(String url) {
        return Single.create(emitter -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    emitter::onSuccess,
                    error -> {
                        if (error.getMessage() != null) {
                            Log.e("RESPONCE ERROR", error.getMessage());
                        } else {
                            Log.e("RESPONCE ERROR", "error");
                        }
                        emitter.onError(error);
                    }
            );
            stringRequest.setRetryPolicy(retryPolicy);
            addToRequestQueue(stringRequest);
        });
    }

}
