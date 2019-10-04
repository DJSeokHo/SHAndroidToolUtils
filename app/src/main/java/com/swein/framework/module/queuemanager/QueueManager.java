package com.swein.framework.module.queuemanager;

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

    private boolean isQueueEmpty()
    {
        return linkedList.isEmpty();
    }

    public void addObjectListToQueue(List<Object> objectList, Runnable runnable)
    {
        for(Object object : objectList) {
            ILog.iLogDebug("===>", object.toString());
            linkedList.addLast(object);
        }

        readyToProcessQueueObject(runnable);
    }

    private void readyToProcessQueueObject(Runnable runnable) {

        // 初始化处理线程
        if(threadProcess == null) {
            threadProcess = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 处理线程开始工作，直到队列为空时自动跳出，单线程处理
                    while (true) {

                        if(isQueueEmpty()) {
                            continue;
                        }

                        ILog.iLogDebug("???", "\n");
                        ILog.iLogDebug("before size===========================", getQueueLength());

                        ILog.iLogDebug("doing ", getFirstObject().toString());

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
                        runnable.run();
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
