package com.esprit.nolife.carsstore.utils.xml_parsers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.NewsMagazineRecyclerAdapter;
import com.esprit.nolife.carsstore.entities.CarMagazine;

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

public class CarsMagazineRssReader extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog loadingNews;
    String address = "http://www.automobile.tn/rss/magazine.php";
    URL url;
    ArrayList<CarMagazine> carMagazines;
    RecyclerView recyclerView;
    String orientation = "";

    public CarsMagazineRssReader(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        loadingNews = new ProgressDialog(context);
        loadingNews.setTitle("Loading");
        loadingNews.setMessage("Retreiving data From the web...");

    }

    public CarsMagazineRssReader(Context context, RecyclerView recyclerView, String orientation) {
        this.context = context;
        this.recyclerView = recyclerView;
        loadingNews = new ProgressDialog(context);
        loadingNews.setTitle("Loading");
        loadingNews.setMessage("Retreiving data From the web...");
        this.orientation = orientation;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        loadingNews.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (orientation.equals("")) {
            NewsMagazineRecyclerAdapter newsMagazineRecyclerAdapter
                    = new NewsMagazineRecyclerAdapter(context, carMagazines);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(newsMagazineRecyclerAdapter);
        }
        if (!orientation.equals("")) {
            NewsMagazineRecyclerAdapter newsMagazineRecyclerAdapter
                    = new NewsMagazineRecyclerAdapter(context, carMagazines);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            recyclerView.setAdapter(newsMagazineRecyclerAdapter);
        }
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
            carMagazines = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currChild = items.item(i);
                if (currChild.getNodeName().equalsIgnoreCase("item")) {
                    CarMagazine carMagazine = new CarMagazine();
                    NodeList itemChilds = currChild.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            carMagazine.setTitle(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("link")) {
                            carMagazine.setLink(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("description")) {
                            carMagazine.setDescription(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("author")) {
                            carMagazine.setAuthor(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("category")) {
                            carMagazine.setCategory(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            carMagazine.setPubDate(current.getTextContent());
                        }
                        if (current.getNodeName().equalsIgnoreCase("enclosure")) {
                            carMagazine.setThumbnailUrl(current.getAttributes().getNamedItem("url").getNodeValue());
                        }

                    }
                    carMagazines.add(carMagazine);


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
