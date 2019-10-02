package com.swein.framework.module.queuemanager;

import android.os.Handler;
import android.os.Looper;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.LinkedList;
import java.util.List;

public class QueueManager {

    private static QueueManager instance = new QueueManager();
    public static QueueManager getInstance() {
        return instance;
    }

    private QueueManager() {}

    private Thread threadProcess;

    private LinkedList linkedList = new LinkedList<>();

    public void clear()
    {
        linkedList.clear();
    }

    public boolean isQueueEmpty()
    {
        return linkedList.isEmpty();
    }

    public void addObjectToQueue(Object object)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                linkedList.addLast(object);
            }
        }).start();

        ILog.iLogDebug("===>", object.toString());
        if(threadProcess == null) {
            threadProcess = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        ILog.iLogDebug("???", "\n");
                        ILog.iLogDebug("before size===========================", getQueueLength());

                        try {
                            Thread.sleep(1000);
                            ILog.iLogDebug("doing ", getFirstObject().toString());
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Object object = removeObjectFromQueue();
                        ILog.iLogDebug("remove", object.toString());

                        ILog.iLogDebug("after size==============================", getQueueLength());
                        ILog.iLogDebug("???", "\n");
                        if(isQueueEmpty()) {
                            ILog.iLogDebug("???", "finish");
                            break;
                        }
                    }

                    if(threadProcess != null) {
                        ILog.iLogDebug("???", "finish thread");
                        threadProcess = null;
                    }
                }
            });

            threadProcess.start();
        }

    }

    public void addObjectListToQueue(List<Object> objectList)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Object object : objectList) {
                    ILog.iLogDebug("===>", object.toString());
                    linkedList.addLast(object);
                }
            }
        }).start();


        if(threadProcess == null) {
            threadProcess = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!isQueueEmpty()) {

                        ILog.iLogDebug("before size===========================", getQueueLength());

                        try {
                            Thread.sleep(1000);
                            ILog.iLogDebug("doing ", getFirstObject().toString());
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Object object = removeObjectFromQueue();
                        ILog.iLogDebug("remove", object.toString());

                        ILog.iLogDebug("after size==============================", getQueueLength());

                        if(isQueueEmpty()) {
                            ILog.iLogDebug("???", "finish");
                            break;
                        }
                    }

                    if(threadProcess != null) {
                        ILog.iLogDebug("???", "finish thread");
                        threadProcess = null;
                    }
                }
            });

            threadProcess.start();
        }

    }

    private Object removeObjectFromQueue()
    {
        if(!linkedList.isEmpty())
        {
            return linkedList.removeFirst();
        }

        return null;
    }

    public int getQueueLength()
    {
        return linkedList.size();
    }

    public Object getFirstObject()
    {
        return linkedList.getFirst();
    }

    public Object getLastObject()
    {
        return linkedList.getLast();
    }

}
