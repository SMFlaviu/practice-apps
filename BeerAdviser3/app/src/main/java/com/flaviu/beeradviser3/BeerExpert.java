package com.flaviu.beeradviser3;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBrands(String color) {
        List<String> brands = new ArrayList<>();
        if (color.equals(String.valueOf(R.string.beer_color))) {
            brands.add(String.valueOf(R.string.ursus_beer));
            brands.add(String.valueOf(R.string.ciuc_beer));
        } else {
            brands.add(String.valueOf(R.string.beer_bergenbier));
            brands.add(String.valueOf(R.string.beer_stela));
        }
        return brands;
    }
}
