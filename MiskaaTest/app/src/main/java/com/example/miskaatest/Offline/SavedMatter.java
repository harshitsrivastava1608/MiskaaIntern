package com.example.miskaatest.Offline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.miskaatest.Countries;
import com.example.miskaatest.CountriesAdapter;
import com.example.miskaatest.MainActivity;
import com.example.miskaatest.R;

import java.util.ArrayList;
import java.util.List;

public class SavedMatter extends AppCompatActivity {
RoomDB database;
List<SaveData> saveDataArrayList;
ArrayList<Countries> countriesArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    CountriesAdapter countriesAdapter;
    RecyclerView.LayoutManager layoutManager;GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_matter);
        recyclerView=findViewById(R.id.recycler);
        getSupportActionBar().setTitle("Favorites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        database=RoomDB.getInstance(this);
        saveDataArrayList=new ArrayList<>();
        saveDataArrayList=database.mainDao().getAll();
        for(int i=0;i<saveDataArrayList.size();i++){
            ArrayList<String> borders,languages;
            borders=new ArrayList<>();
            languages=new ArrayList<>();
            borders.add(saveDataArrayList.get(i).getBorders());languages.add(saveDataArrayList.get(i).getLanguages());
            Countries countries=new Countries(saveDataArrayList.get(i).getName(),
                    saveDataArrayList.get(i).getCapital(),
                    saveDataArrayList.get(i).getFlag(),
                    saveDataArrayList.get(i).getRegion(),
                    saveDataArrayList.get(i).getSubregion(),
                    saveDataArrayList.get(i).getPopulation(),
                    borders,
                    languages);
            countriesArrayList.add(countries);
            Log.i("room23",""+countries.getFlag().toString());
        }
        try {
            Log.i("room23", "" + countriesArrayList);

                countriesAdapter = new CountriesAdapter(countriesArrayList, this);
                recyclerView.setAdapter(countriesAdapter);

        }catch (Exception e){
            Log.i("roome",""+e.getMessage());
        }

    }

    public void takeToHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}