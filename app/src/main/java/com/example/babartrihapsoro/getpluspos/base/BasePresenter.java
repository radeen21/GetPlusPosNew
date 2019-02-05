package com.example.babartrihapsoro.getpluspos.base;

public abstract class BasePresenter<T extends BaseContractView> {

    private T view;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    protected abstract void onDestroy();
}
