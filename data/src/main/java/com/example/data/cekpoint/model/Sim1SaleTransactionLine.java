package com.example.data.cekpoint.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sim1SaleTransactionLine {

    @SerializedName("sim1:LineValue")
    @Expose
    private Integer sim1LineValue;
    @SerializedName("sim1:ProductBrand")
    @Expose
    private String sim1ProductBrand;
    @SerializedName("sim1:ProductCode")
    @Expose
    private String sim1ProductCode;
    @SerializedName("sim1:Quantity")
    @Expose
    private Integer sim1Quantity;

    public Integer getSim1LineValue() {
        return sim1LineValue;
    }

    public void setSim1LineValue(Integer sim1LineValue) {
        this.sim1LineValue = sim1LineValue;
    }

    public String getSim1ProductBrand() {
        return sim1ProductBrand;
    }

    public void setSim1ProductBrand(String sim1ProductBrand) {
        this.sim1ProductBrand = sim1ProductBrand;
    }

    public String getSim1ProductCode() {
        return sim1ProductCode;
    }

    public void setSim1ProductCode(String sim1ProductCode) {
        this.sim1ProductCode = sim1ProductCode;
    }

    public Integer getSim1Quantity() {
        return sim1Quantity;
    }

    public void setSim1Quantity(Integer sim1Quantity) {
        this.sim1Quantity = sim1Quantity;
    }
}
