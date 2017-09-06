package com.swein.framework.rxjava2;

import android.app.Activity;
import android.os.Bundle;

import com.swein.framework.rxjava2.observalbe.ObservableManager;
import com.swein.framework.rxjava2.observer.ObserverManager;
import com.swein.framework.rxjava2.observerinterface.ObserverInterface;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import io.reactivex.disposables.Disposable;

public class RxJava2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2);

        ObservableManager.getInstance().createObservableWithT("1", "2", "3")
                .subscribe(
                        ObserverManager.getInstance().createObserverWithObserverInterface(new ObserverInterface() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object value) {
                                ILog.iLogDebug("???", value.toString());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                ILog.iLogDebug("???", "onComplete");
                            }
                        }));


    }

    public void test1() {

    }


}
