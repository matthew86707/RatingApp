package com.example.coolkidsdev.ratingapp;

import android.app.Application;
import android.app.DownloadManager;
import android.os.Debug;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Matthew on 9/30/2015.
 */
public class DataBaseFunctions {

    public static String[] getCurrentTeachers() throws Exception{
        GetTeachers g = new GetTeachers();
        Thread t = new Thread(g);
        t.start();
        t.join();
      return g.getTeachersArray();
    }



    RequestQueue requestQueue;



    public void pushRatings(final float ratingFun, final float ratingLearn, final String teacher){

        requestQueue = Volley.newRequestQueue(MainActivity.context);
        StringRequest request = new StringRequest(Request.Method.POST, "http://feedback.jointheleague.org/addRating.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("informationRating",ratingLearn +"");
                parameters.put("funRating",ratingFun + "");
                parameters.put("teacher", teacher);


                return parameters;
            }
        };
        requestQueue.add(request);
        ToastStuff.createToast("Rating Submitted Successfully!", MainActivity.context);
    }


}

