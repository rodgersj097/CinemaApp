package com.example.helix.cinema;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class south extends AppCompatActivity {
    List movies = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south);

        new getMovies2().execute();

    }

    public class getMovies2 extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            // super.onPostExecute(s);

            ArrayAdapter adapter = new ArrayAdapter(south.this, android.R.layout.simple_list_item_1 ,movies );
            ListView listView = findViewById(R.id.listViewSouth);
            listView.setAdapter(adapter);

        }
        @TargetApi(Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... strings)  {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.now();
            String formattedString = localDate.format(formatter);
            String url = "https://www.cineplex.com/Showtimes/any-movie/galaxy-cinemas-barrie?Date=";
            String fullUrl = url+formattedString;


            Document doc = null;
            try {
                doc = Jsoup.connect(fullUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements movieTitle = doc.getElementsByClass("grid__item no-page-break-inside");

            for(int i=0; i<movieTitle.size(); i++){
                movies.add(movieTitle.get(i).text());

            }
            return movies.toString();
        }
    }
}
