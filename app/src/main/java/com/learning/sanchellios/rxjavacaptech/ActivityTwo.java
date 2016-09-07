package com.learning.sanchellios.rxjavacaptech;

import android.databinding.DataBindingUtil;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learning.sanchellios.rxjavacaptech.databinding.ActivityActivityTwoBinding;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class ActivityTwo extends AppCompatActivity {
    private ActivityActivityTwoBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_activity_two);
        Observable.just(1, 2, 3, 4, 5, 6)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 1;
                    }
                })
                .map(new Func1<Integer, Double>() {
                    @Override
                    public Double call(Integer integer) {
                        return Math.sqrt(integer);
                    }
                })
                .subscribe(new Subscriber<Double>() {
                    @Override
                    public void onCompleted() {
                        mBinding.textView3.append("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Double aDouble) {
                        mBinding.textView3.append("OnNext: " + aDouble + "\n");
                    }
                });
    }


}
