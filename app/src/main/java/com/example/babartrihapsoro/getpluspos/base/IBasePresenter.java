package com.example.babartrihapsoro.getpluspos.base;

public interface IBasePresenter<T extends IBaseView> {

    void onAttach(T view);
    void onDetach();
    boolean isAttached();
}
