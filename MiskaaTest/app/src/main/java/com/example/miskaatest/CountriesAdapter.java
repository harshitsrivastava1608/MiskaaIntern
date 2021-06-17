package com.example.miskaatest;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miskaatest.Offline.RoomDB;
import com.example.miskaatest.Offline.SaveData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    ArrayList<Countries> arrayList;
    Context context;

    public CountriesAdapter(ArrayList<Countries> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_countries,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Countries countries=arrayList.get(position);
        holder.name.setText(countries.getName());
        holder.capital.setText(countries.getCapital());
        holder.region.setText(countries.getRegion());
        holder.subregion.setText(countries.getSubregion());
        holder.population.setText(countries.getPopulation());
        holder.borders.setText(countries.getBorders().toString());
        holder.languages.setText(countries.getLanguages().toString());

        Picasso.get().load(""+countries.getFlag().toString()).into(holder.flag);

        String userAvatarUrl = ""+countries.getFlag();
        Utils.fetchSvg(context, userAvatarUrl, holder.flag);
        holder.saveStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveData saveData=new SaveData();
                saveData.setName(countries.getName());
                saveData.setCapital(countries.getCapital());
                saveData.setRegion(countries.getRegion());
                saveData.setSubregion(countries.getSubregion());
                saveData.setPopulation(countries.getPopulation());
                saveData.setFlag(countries.getFlag());
                saveData.setBorders(countries.getBorders().toString());
                saveData.setLanguages(countries.getLanguages().toString());

                RoomDB database ;
                Log.i("room12",""+saveData.getBorders().toString());
                database=RoomDB.getInstance(context);
                database.mainDao().insert(saveData);
                Toast.makeText(context,""+countries.getName()+ " Added To Favorites",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,  capital, region, subregion, population,borders, languages;
        ImageButton flag,saveStar;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.name);
            flag=itemView.findViewById(R.id.flag);
            capital=itemView.findViewById(R.id.capital);
            region=itemView.findViewById(R.id.region);
            subregion=itemView.findViewById(R.id.subregion);
            population=itemView.findViewById(R.id.population);
            borders=itemView.findViewById(R.id.borders);
            languages=itemView.findViewById(R.id.languages);
            saveStar=itemView.findViewById(R.id.saveStar);
        }
    }
}
