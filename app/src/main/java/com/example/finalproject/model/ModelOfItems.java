package com.example.finalproject.model;

import com.example.finalproject.R;

import java.util.ArrayList;

public class ModelOfItems {

    public ArrayList<ListArrayItem> setListOfArrayItems() {
        ArrayList<ListArrayItem> listArrayItem = new ArrayList<>();

        listArrayItem.add(new ListArrayItem("Pingo", 15000));
        listArrayItem.add(new ListArrayItem("Cactus", 1000));

        return listArrayItem;
    }
}
