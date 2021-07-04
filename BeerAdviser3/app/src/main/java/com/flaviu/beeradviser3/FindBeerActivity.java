package com.flaviu.beeradviser3;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class FindBeerActivity extends AppCompatActivity {
    BeerExpert beerExpert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickFindBeer(View view) {
        TextView brands = (TextView) findViewById(R.id.brands);
        Spinner color = (Spinner) findViewById(R.id.color);
        String beer_type = String.valueOf(color.getSelectedItem());
        brands.setText(beer_type);
        List<String> brandList = beerExpert.getBrands(beer_type);
        StringBuilder brandFormatted = new StringBuilder();
        for (String brand : brandList) {
            brandFormatted.append(brand).append("\n");
        }
        brands.setText(brandFormatted);
    }
}