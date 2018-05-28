package com.swein.framework.tools.util.thread;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class AsyncUtil {

    private static AsyncUtil instance = new AsyncUtil();

    public static AsyncUtil getInstance() {
        return instance;
    }

    private AsyncUtil(){}

    private SHAsyncTask shAsyncTask = new SHAsyncTask();

    public void run(Runnable back, Runnable updateUI) {
        shAsyncTask.setBackAndUpdateUI(back, updateUI);
        shAsyncTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class SHAsyncTask extends AsyncTask<Void, Integer, Void> {

        private Runnable back;
        private Runnable updateUI;

        public void setBackAndUpdateUI(Runnable back, Runnable updateUI) {
            this.back = back;
            this.updateUI = updateUI;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            back.run();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void result) {

            updateUI.run();

            if(back != null) {
                back = null;
            }

            if(updateUI != null) {
                updateUI = null;
            }
        }
    }


}
