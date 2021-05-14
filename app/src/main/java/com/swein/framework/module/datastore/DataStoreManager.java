package com.swein.framework.module.datastore;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.swein.framework.tools.util.debug.log.ILog;

import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DataStoreManager {

    public interface DataStoreManagerDelegate {
        void onGetValue(Object value);
    }

    private static final Handler dataStoreManagerHandler = new Handler(Looper.getMainLooper());

    public static final DataStoreManager instance = new DataStoreManager();

    private DataStoreManager() {}

    private RxDataStore<Preferences> dataStore;


    public void init(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, /*name=*/ "whatever_name.datastore").build();
    }

    public void saveValue(String keyName, String value) {

        Preferences.Key<String> key = new Preferences.Key<>(keyName);

        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            String currentStringKey = preferences.get(key);
            mutablePreferences.set(key, currentStringKey != null ? value : ""); // default value
            return Single.just(mutablePreferences);
        }).subscribeOn(Schedulers.newThread()).subscribe();

    }

    public void getValue(String keyName, DataStoreManagerDelegate dataStoreManagerDelegate) {

        Preferences.Key<String> key = new Preferences.Key<>(keyName);

        dataStore.data().map(preferences -> preferences.get(key))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        ILog.iLogDebug("???", "onNext " + s);

                        dataStoreManagerHandler.post(() -> {
                            dataStoreManagerDelegate.onGetValue(s);
                        });
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        ILog.iLogDebug("???", "onComplete");
                    }
                });
    }


}
