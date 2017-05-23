package com.esprit.nolife.carsstore.utils.html_parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Souhaib on 09/11/2016.
 */

public class DataFromTheWeb extends AsyncTask<Void, Void, Void> {

    String question;
    WebView wv;
    String URL;
    Context context;
    String carName;
    ProgressDialog progressDialog;


    public DataFromTheWeb(WebView wv) {
        this.wv = wv;
        progressDialog = new ProgressDialog(context);
    }

    public DataFromTheWeb(WebView wv, String URL) {
        this.wv = wv;
        this.URL = URL;
        progressDialog = new ProgressDialog(context);
    }

    public DataFromTheWeb(WebView wv, String URL, Context context) {
        this.wv = wv;
        this.URL = URL;
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public DataFromTheWeb(WebView wv, String URL, Context context, String carName) {
        this.wv = wv;
        this.URL = URL;
        this.context = context;
        this.carName = carName;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("loading data from server");
        progressDialog.setTitle("Loading");
//        progressDialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {

        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                builder.append(line.trim());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException logOrIgnore) {
                logOrIgnore.printStackTrace();
            }
        }

        String start = "<div class=\"technical-details\">" +
                "<div class=\"bloc-title\">" +
                "<h3>fiche technique" +
                "<span>" + carName;
        String end = "<div class=\"bloc-title\">" + "<h3>vos commentaires";
        String part = builder.substring(builder.indexOf(start) + start.length());
        System.out.println("******************************************");
        System.out.println(part.length());

        try {
            question = part.substring(0, part.indexOf(end));
        } catch (StringIndexOutOfBoundsException e) {

        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (question != null && question.length() < 17000){
            wv.loadData(question, "text/html; charset=UTF-8", null);
            progressDialog.dismiss();
        }


    }

    public String getResult(String s) {
        return s;
    }


}
