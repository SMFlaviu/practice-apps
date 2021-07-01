package com.flaviu.beeradviser3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URL;
import java.util.List;


public class Find_Beer_Activity extends AppCompatActivity {
private BeerExpert beerExpert = new BeerExpert();
    public void onClickFindBeer(View view){
        TextView brands =(TextView) findViewById(R.id.brands);
        Spinner color = (Spinner) findViewById(R.id.color);
        String beer_type = String.valueOf(color.getSelectedItem());
        brands.setText(beer_type);
        List<String> brandList = beerExpert.getBrands(beer_type);
        StringBuilder brandFormatted = new StringBuilder();
        for(String brand : brandList){
            brandFormatted.append(brand).append("\n");
        }
        brands.setText(brandFormatted);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}