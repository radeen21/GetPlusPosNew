package com.example.domain;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by singgihsaputro on 12/14/16.
 */

public abstract class UseCase<T, Params> {

    private CompositeSubscription compositeSubscription;

    protected UseCase() {
        initCompositeSubscription();
    }


    private void initCompositeSubscription() {
        compositeSubscription = new CompositeSubscription();
    }

    protected Subscription addSubscription(Subscription subscription) {
        if (null != compositeSubscription && compositeSubscription.isUnsubscribed()) {
            initCompositeSubscription();
        }

        if (subscription != null && !subscription.isUnsubscribed()) {
            assert compositeSubscription != null;
            compositeSubscription.remove(subscription);
        }

        assert subscription != null;
        assert compositeSubscription != null;
        compositeSubscription.add(subscription);
        return compositeSubscription;
    }

    public void clearAllSubscription() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    protected abstract Observable<T> buildUseCaseObservable(Params params);

    public Subscription execute(Subscriber<T> subscriber) {
        return execute(subscriber, null);
    }

    public Subscription execute(Subscriber<T> subscriber, Params params) {
        return addSubscription(
            buildUseCaseObservable(params)
                .subscribe(subscriber)
        );
    }
}