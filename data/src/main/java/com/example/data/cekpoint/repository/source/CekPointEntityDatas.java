package com.example.data.cekpoint.repository.source;

import com.example.data.cekpoint.CekPointEntity;

import rx.Observable;

public interface CekPointEntityDatas {

    Observable<CekPointEntity> getCekPoint();
}
