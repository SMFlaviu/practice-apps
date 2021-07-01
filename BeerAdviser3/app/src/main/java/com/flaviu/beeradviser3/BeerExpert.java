package com.flaviu.beeradviser3;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBrands(String color){
        List<String> brands= new ArrayList<String>();
        if(color.equals("amber")){
            brands.add("Ursus");
            brands.add("Ciuc");
        }
        else {
        brands.add("Bergenbier");
        brands.add("Stela");
        }
        return brands;
    }
}
