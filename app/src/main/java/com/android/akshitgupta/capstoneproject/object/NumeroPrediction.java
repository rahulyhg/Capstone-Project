package com.android.akshitgupta.capstoneproject.object;

import java.io.Serializable;

/**
 * Created by akshitgupta on 09/10/16.
 */

public class NumeroPrediction implements Serializable {
    String title;
    String description;

    public NumeroPrediction()
    {

    }

    public NumeroPrediction(String title,String description)
    {
        this.title=title;
        this.description=description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
