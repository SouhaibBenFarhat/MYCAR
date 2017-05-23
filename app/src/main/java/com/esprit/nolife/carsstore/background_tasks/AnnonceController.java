package com.esprit.nolife.carsstore.background_tasks;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Firas on 20/12/2016.
 */


    public class AnnonceController extends Application {

        private static Context mContext;

        private RequestQueue requestQueue;

        private static AnnonceController mInstance;

    public AnnonceController(Context context) {
        this.mContext=context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            }
            return requestQueue;
        }
    public static synchronized AnnonceController getmInstance(Context context){
        if (mInstance==null){
            mInstance=new AnnonceController(context);
        }
        return mInstance;

    }
    public <T>void addToRequestQue(Request<T> request){
        requestQueue.add(request);
    }

    }

