package com.example.warhammer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String id_SRM = "-104169151";
    private String necronId = "222401877";
    private String wah30Id = "236412223";
    private String serviceKey = "ef32ef1cef32ef1cef32ef1c19ef5baea3eef32ef32ef1cb3bbcedb07d470665f02863d";
    ArrayList<Post> temp_album = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        List<String> alb = new ArrayList<String>(); alb.add(necronId); alb.add(wah30Id);
        parsePhotos(alb); //загрузка данных из вк

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // HTTP GET request
    private void makeVkRequest(String method, HashMap<String, String> args, Callback func) throws Exception {
        String access_token = serviceKey;
        args.put("access_token", access_token);
        args.put("v", "5.92");
        StringBuilder res_args = new StringBuilder();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            res_args.append(entry.getKey());
            res_args.append("=");
            res_args.append(entry.getValue());
            res_args.append("&");
        }

        String url = "https://api.vk.com/method/" + method + "?" + res_args;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(func);

    }

    /*private void initGridArray() {
        GridView gridView = (GridView) findViewById(R.id.gridview);
        PostAdapter postsAdapter = new PostAdapter(this, posts);
        gridView.setAdapter(postsAdapter);
        TextView text = findViewById(R.id.defaultText);
        text.setText(String.valueOf(posts.size()));
    }*/

    private void parsePhotos(List<String> albums)
    {
       for (int i=0; i < albums.size(); i++){
            HashMap<String, String> args = new HashMap<String, String>();
            args.put("album_id", albums.get(i));
            getPhotos(args);
        }
    }
    private void getPhotos(HashMap<String, String> args) { //передавать id_альбомов, которые нужно брать в массиве temp_alb будет висеть результат
        try{
            args.put("owner_id", "-104169151");
            args.put("count", "1000");

            makeVkRequest("photos.get", args, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    e.printStackTrace();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//обработка ошибки
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String my_response = response.body().string();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject ob = (new JSONObject(my_response)).getJSONObject("response");
                                    JSONArray ar = ob.getJSONArray("items");
                                    for (int i = 0; i < ob.getInt("count"); i++) {
                                        JSONObject object = (JSONObject) ar.get(i);
                                        int max = object.getJSONArray("sizes").length();
                                        JSONObject picture_big = object.getJSONArray("sizes").getJSONObject(max - 1); //фоток всгеда 9 берем самую последнюю
                                        JSONObject picture_small = new JSONObject();
                                        for (int j=0; j < max; j++) {
                                            picture_small = object.getJSONArray("sizes").getJSONObject(j);
                                            if(picture_small.getString("type") == "q")
                                                j = max+1;
                                        }
                                        //String name = take_name(picture.getString("text"));
                                        //String price = take_price(picture.getString("text"));
                                        //String a = object.getString("album_id") + "_" + object.getString("id");

                                        Post good = new Post(object.getString("id"), object.getString("text"), picture_big.getString("url"),
                                                picture_small.getString("url"));
                                        good.makeNameAndPrice("");
                                        temp_album.add(good);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
