package com.swein.framework.rxjava.activity.subject;

import java.util.List;

import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by seokho on 26/07/2017.
 */

public class SubJectManager<T> {

    private SubJectManager() {}

    private static SubJectManager instance = new SubJectManager();

    public static SubJectManager getInstance() {

        return instance;
    }

    /**
     * this class just call last thing in list when onCompleted called
     * @param list
     * @return
     */
    public AsyncSubject<T> createAsyncSubject(List<T> list) {

        AsyncSubject<T> asyncSubject = AsyncSubject.create();

        for(T t : list) {
            asyncSubject.onNext(t);
        }
        asyncSubject.onCompleted();

        return asyncSubject;
    }

    /**
     * this class just call last thing in list before subscribe first, than will call other thing
     * @param list
     * @return
     */
    public BehaviorSubject<T> createBehaviorSubject(List<T> list, T defaultObject) {

        BehaviorSubject<T> behaviorSubject = BehaviorSubject.create(defaultObject);

        if(list != null) {
            for (T t : list) {
                behaviorSubject.onNext(t);
            }
        }

        return behaviorSubject;
    }

    /**
     * this class just call things after subscribe
     * @param list
     * @return
     */
    public PublishSubject<T> createPublishSubject(List<T> list) {

        PublishSubject<T> publishSubject = PublishSubject.create();

        if(list != null) {
            for (T t : list) {
                publishSubject.onNext(t);
            }
        }

        return publishSubject;
    }

    /**
     * this class call all things before & after subscribe but only when list size under 16. if big than 16, will clear
     * @param list
     * @return
     */
    public ReplaySubject<T> createReplaySubject(List<T> list) {

        ReplaySubject<T> replaySubject = ReplaySubject.create();

        if(list != null) {
            for (T t : list) {
                replaySubject.onNext(t);
            }
        }

        return replaySubject;
    }

    /**
     * this class call all things before & after subscribe but only when list size under cache. if big than cache, will clear
     * @param list
     * @return
     */
    public ReplaySubject<T> createReplaySubjectWithCache(List<T> list, int cache) {

        ReplaySubject<T> replaySubject = ReplaySubject.create(cache);

        if(list != null) {
            for (T t : list) {
                replaySubject.onNext(t);
            }
        }

        return replaySubject;
    }

    /**
     * this class call dataSize things before subscribe
     * @param list
     * @return
     */
    public ReplaySubject<T> createReplaySubjectWithDataSize(List<T> list, int dataSize) {

        ReplaySubject<T> replaySubject = ReplaySubject.createWithSize(dataSize);

        if(list != null) {
            for (T t : list) {
                replaySubject.onNext(t);
            }
        }

        return replaySubject;
    }

}
