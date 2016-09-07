package com.learning.sanchellios.rxjavacaptech;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.learning.sanchellios.rxjavacaptech.databinding.ActivityMainBinding;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dBinding.nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
                startActivity(intent);
            }
        });
        integerObservable.subscribe(mIntegerSubscriber);
    }

    Observable integerObservable = Observable.create(new Observable.OnSubscribe<Subscriber>() {
        @Override
        public void call(Subscriber subscriber) {
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
            subscriber.onCompleted();
        }
    });

    Subscriber<Integer> mIntegerSubscriber = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
            dBinding.textView1.setText("Complete!");
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Integer integer) {
            dBinding.textView2.append("onNext: " + integer + "\n");
        }
    };
}
