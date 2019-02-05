package com.example.data.cekpoint.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sim1TransactionLines {

    @SerializedName("sim1:SaleTransactionLine")
    @Expose
    private Sim1SaleTransactionLine sim1SaleTransactionLine;

    public Sim1SaleTransactionLine getSim1SaleTransactionLine() {
        return sim1SaleTransactionLine;
    }

    public void setSim1SaleTransactionLine(Sim1SaleTransactionLine sim1SaleTransactionLine) {
        this.sim1SaleTransactionLine = sim1SaleTransactionLine;
    }
}
