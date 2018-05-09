package com.eyo.bethel.gitnaija.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DevList {
    @SerializedName("items")
    @Expose
    private List<NaijaDevelopers> items = null;

    public List<NaijaDevelopers> getItems() {
        return items;
    }

    public void setItems(List<NaijaDevelopers> items) {
        this.items = items;
    }
}
