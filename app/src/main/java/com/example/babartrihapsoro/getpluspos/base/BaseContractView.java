package com.example.babartrihapsoro.getpluspos.base;

public interface BaseContractView {

    void showAuthExpiredDialog(Throwable throwable);

    void showInsecureConnectionDialog(Throwable throwable);

}