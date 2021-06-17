package com.example.miskaatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miskaatest.Offline.SavedMatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<Countries> countriesArrayList;
RecyclerView recyclerView;
CountriesAdapter countriesAdapter;
    RecyclerView.LayoutManager layoutManager;GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Country Tray");
        countriesArrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        refresh("https://restcountries.eu/rest/v2/region/asia");
        gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    void refresh(String url){
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){

                                JSONObject jsonObject=response.getJSONObject(i);
                               ArrayList<String> borders=new ArrayList<>();
                                for(int j=0;j<jsonObject.getJSONArray("borders").length();j++)
                                    borders.add(jsonObject.getJSONArray("borders").getString(j));

                                ArrayList<String> languages=new ArrayList<>();
                                for(int j=0;j<jsonObject.getJSONArray("languages").length();j++){
                                    Log.i("lang",""+jsonObject.getJSONArray("languages").getJSONObject(j).getString("name"));
                                    languages.add(jsonObject.getJSONArray("languages").getJSONObject(j).getString("name"));
                                }
                                Countries countries=new Countries(jsonObject.getString("name"),
                                        jsonObject.getString("capital"),
                                        jsonObject.getString("flag"),
                                        jsonObject.getString("region"),
                                                jsonObject.getString("subregion"),
                                                jsonObject.getString("population"),
                                        borders,
                                        languages
                                        );

                                countriesArrayList.add(countries);

                            }
                            if (countriesArrayList!=null) {
                                countriesAdapter = new CountriesAdapter(countriesArrayList, getApplicationContext());
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(countriesAdapter);
                            }else{

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("myapp",error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void takeToSaved(View view) {
        startActivity(new Intent(MainActivity.this, SavedMatter.class));
    }

}