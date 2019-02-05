package com.example.data.cekpoint;

import com.example.data.cekpoint.model.SimValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CekPointEntity {

    @SerializedName("sim:Token")
    @Expose
    private String simToken;
    @SerializedName("sim:Value")
    @Expose
    private SimValue simValue;

    public String getSimToken() {
        return simToken;
    }

    public void setSimToken(String simToken) {
        this.simToken = simToken;
    }

    public SimValue getSimValue() {
        return simValue;
    }

    public void setSimValue(SimValue simValue) {
        this.simValue = simValue;
    }


}
