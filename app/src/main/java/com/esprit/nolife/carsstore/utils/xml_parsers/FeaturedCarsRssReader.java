package com.esprit.nolife.carsstore.utils.xml_parsers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.FeaturedCarsRecyclerAdapter;
import com.esprit.nolife.carsstore.dao.FeaturedCarDao;
import com.esprit.nolife.carsstore.entities.FeaturedCar;
import com.esprit.nolife.carsstore.myapp.MyApplication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Souhaib on 07/11/2016.
 */

public class FeaturedCarsRssReader extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog loadingNews;
    String address = "http://www.automobile.tn/rss/neuf.php";
    URL url;
    ArrayList<FeaturedCar> featuredCars;
    public ArrayList<FeaturedCar> featuredCarsFromDataBase;
    ;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    int nbElement = 0;
    String carName;
    FeaturedCarDao featuredCarDao;
    Activity activity;
    MyApplication app;

    public FeaturedCarsRssReader(Context context, RecyclerView recyclerView, LinearLayoutManager layoutManager, Activity activity) {
        this.context = context;
        this.recyclerView = recyclerView;
        loadingNews = new ProgressDialog(context);
        loadingNews.setTitle("Loading");
        loadingNews.setMessage("Retreiving data From the web...");
        this.layoutManager = layoutManager;
        carName = "";
        this.activity = activity;
        featuredCarsFromDataBase = new ArrayList<>();


    }


    public FeaturedCarsRssReader(Context context, RecyclerView recyclerView, LinearLayoutManager layoutManager, Activity activity, int nbElement) {
        this.context = context;
        this.recyclerView = recyclerView;
        loadingNews = new ProgressDialog(context);
        loadingNews.setTitle("Loading");
        loadingNews.setMessage("Retreiving data From the web...");
        this.layoutManager = layoutManager;
        carName = "";
        this.activity = activity;
        featuredCarsFromDataBase = new ArrayList<>();
        this.nbElement = nbElement;

    }

    public FeaturedCarsRssReader(Context context, RecyclerView recyclerView, LinearLayoutManager layoutManager, String carName, Activity activity) {
        this.context = context;
        this.recyclerView = recyclerView;
        loadingNews = new ProgressDialog(context);
        loadingNews.setTitle("Loading");
        loadingNews.setMessage("Retreiving data From the web...");
        this.layoutManager = layoutManager;
        this.carName = carName;
        this.activity = activity;
        featuredCarsFromDataBase = new ArrayList<>();


    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        featuredCarDao = new FeaturedCarDao();
        featuredCarDao.getFeaturedCars(featuredCarsFromDataBase);


//        loadingNews.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {


        FeaturedCarsRecyclerAdapter featuredCarsRecyclerAdapter
                = new FeaturedCarsRecyclerAdapter(context, featuredCars);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(featuredCarsRecyclerAdapter);
        super.onPostExecute(aVoid);
        return;
//        loadingNews.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(getData());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {

            featuredCars = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();
            if (nbElement == 0) {
                nbElement = items.getLength();
            }
            for (int i = 0; i < nbElement; i++) {
                Node currChild = items.item(i);
                if (currChild.getNodeName().equalsIgnoreCase("item")) {
                    FeaturedCar featuredCar = new FeaturedCar();
                    NodeList itemChilds = currChild.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            featuredCar.setTitle(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("link")) {
                            featuredCar.setLink(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("description")) {
                            featuredCar.setDescription(current.getTextContent().replace("<br>", ""));
                        }
                        if (current.getNodeName().equalsIgnoreCase("author")) {
                            featuredCar.setAuthor(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("category")) {
                            featuredCar.setCategory(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            featuredCar.setPubDate(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("enclosure")) {
                            featuredCar.setThumbnailUrl(current.getAttributes().getNamedItem("url").getNodeValue().replace("min", "big"));

                        }

                    }
                    if (carName.equals("")) {
                        featuredCars.add(featuredCar);
                    }
                    if ((!carName.equals("")) && featuredCar.getTitle().toLowerCase().contains(carName.toLowerCase())) {
                        featuredCars.add(featuredCar);
                    }


                }
            }

        }
    }

    public Document getData() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(inputStream);
            return xmlDocument;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

