package com.example.data.cekpoint.repository.source;

import com.example.data.AbstractEntityDataFactory;
import com.example.data.cekpoint.repository.source.network.NetworkCekPointEntityData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CekPointEntityDataFactory  extends AbstractEntityDataFactory<CekPointEntityDatas> {

    @Inject
    public CekPointEntityDataFactory(){

    }

    @Override
    public CekPointEntityDatas createData() {
        return new NetworkCekPointEntityData();
    }
}
