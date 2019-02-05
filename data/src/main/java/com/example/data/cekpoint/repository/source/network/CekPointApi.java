package com.example.data.cekpoint.repository.source.network;

import com.example.data.cekpoint.repository.source.network.response.CekPointInquiryResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface CekPointApi {

    @GET("transaction/pointredemption")
    Observable<CekPointInquiryResponse> getCekPoint();
}
