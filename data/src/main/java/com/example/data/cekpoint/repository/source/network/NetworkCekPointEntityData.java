package com.example.data.cekpoint.repository.source.network;

import com.example.data.cekpoint.CekPointEntity;
import com.example.data.cekpoint.repository.source.CekPointEntityDatas;
import com.example.data.cekpoint.repository.source.network.response.CekPointInquiryResponse;
import com.example.data.network.BaseNetwork;

import rx.Observable;

public class NetworkCekPointEntityData extends BaseNetwork<CekPointApi> implements
        CekPointEntityDatas {

    public NetworkCekPointEntityData() {

    }

    @Override
    protected Class<CekPointApi> getNetworkClass() {
        return CekPointApi.class;
    }

    @Override
    protected String getBaseUrl() {
        return "https://mygetplus-staging.azurewebsites.net/mobile/v1/201812/";
    }

    @Override
    public Observable<CekPointEntity> getCekPoint() {
        return getNetworkService()
                .getCekPoint().map(CekPointInquiryResponse::getCekPointEntity);
    }
}
