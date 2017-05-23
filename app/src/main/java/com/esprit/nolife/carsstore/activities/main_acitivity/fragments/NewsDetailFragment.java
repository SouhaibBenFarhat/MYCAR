package com.esprit.nolife.carsstore.activities.main_acitivity.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.list_adapters.AlbumGridAdapter;
import com.esprit.nolife.carsstore.entities.CarMagazine;
import com.esprit.nolife.carsstore.entities.Picture;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class NewsDetailFragment extends Fragment {

    TextView tvContentNews, tvAuthor, tvNewsTitle;
    ImageView imgNewdThumbnail;
    GridView imagesNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CarMagazine carMagazine = new CarMagazine();
        Bundle arg = getArguments();
        if (arg != null) {
            carMagazine = (CarMagazine) arg.getSerializable("carMagazine");
        }

        Document doc1 = null;
        try {
            doc1 = Jsoup.connect(carMagazine.getLink()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tvContentNews = (TextView) getActivity().findViewById(R.id.news_content);
        Element contentDiv = doc1.select("div[id=detail_news]").first();
        String text = contentDiv.getElementsByTag("div").text();
        tvContentNews.setText(text);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);


        tvNewsTitle = (TextView) getActivity().findViewById(R.id.news_detail_title);
        tvNewsTitle.setText(carMagazine.getTitle());
        tvAuthor = (TextView) getActivity().findViewById(R.id.news_detail_author);
        tvAuthor.setText(carMagazine.getAuthor());

        imgNewdThumbnail = (ImageView) getActivity().findViewById(R.id.news_detail_thumbnail);
        Picasso.with(getContext()).load(Uri.parse(carMagazine.getThumbnailUrl().replace("min", "max")))
                .into(imgNewdThumbnail);


        imagesNews = (GridView) getActivity().findViewById(R.id.gv_img_news);
        ArrayList<Picture> pictures = new ArrayList<>();

        Elements elements = doc1.getAllElements();
        for (Element e : elements) {
            if (e.hasClass("grid-gallery")) {
                Elements elts = e.getAllElements();
                for (Element e2 : elts) {
                    if (e2.hasClass("grid-item ") || e.hasClass("grid-item st-2x")) {
                        Picture picture = new Picture();
                        picture.setUrl(e2.attr("style").replace("background-image:url(", "").replace(")", ""));
                        pictures.add(picture);
//                        System.out.println(e2.attr("style").replace("background-image:url(", "").replace(")", "") + "     here");
//                        String imgUrl = e2.attr("style").replace("background-image:url(", "").replace(")", "");
//                        System.out.println(imgUrl);
                    }
                }

            }
        }
        imagesNews.setAdapter(new AlbumGridAdapter(getContext(), R.layout.album_item, pictures));
        imagesNews.setNumColumns(5);



    }
}



