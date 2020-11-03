package com.example.gym;

import java.util.Date;

public class Dimensions {

    int height;
    int weight;
    int adiposeTissue;
    int muscleTissue;
    int bodyWaterPercentage;
    Date date;
    int dimensionId;

    public Dimensions(int height, int weight, int adiposeTissue, int muscleTissue, int bodyWaterPercentage, Date date, int dimensionId) {
        this.height = height;
        this.weight = weight;
        this.adiposeTissue = adiposeTissue;
        this.muscleTissue = muscleTissue;
        this.bodyWaterPercentage = bodyWaterPercentage;
        this.date = date;
        this.dimensionId = dimensionId;
    }

    public Dimensions() {
    }

    public Dimensions(Date date) {
        this.date = date;
    }
}
