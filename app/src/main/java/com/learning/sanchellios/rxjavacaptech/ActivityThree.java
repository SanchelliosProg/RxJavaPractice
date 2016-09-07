package com.learning.sanchellios.rxjavacaptech;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.learning.sanchellios.rxjavacaptech.databinding.ActivityThreeBinding;
import com.nispok.snackbar.Snackbar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityThree extends AppCompatActivity {

    private ActivityThreeBinding mBinding;
    final Observable operationObservable = Observable.create(new Observable.OnSubscribe<Subscriber>() {
        @Override
        public void call(Subscriber subscriber) {
            subscriber.onNext(longRunningOperation());
            subscriber.onCompleted();
        }
    })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_three);
        setClickListenerToStartBtn();
        SampleAsyncTask sampleAsyncTask = new SampleAsyncTask();
        sampleAsyncTask.execute();
    }

    private void setClickListenerToStartBtn(){
        mBinding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                operationObservable.subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        v.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        callToast();
                    }
                });
            }
        });
    }

    public String longRunningOperation() {
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Complete!";
    }

    private class SampleAsyncTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            return longRunningOperation();
        }

        @Override
        protected void onPostExecute(String s) {
            callToast();
            mBinding.startBtn.setEnabled(true);
        }
    }

    private void callToast(){
        Toast.makeText(getApplicationContext(), "TEXT", Toast.LENGTH_LONG).show();
    }

}
