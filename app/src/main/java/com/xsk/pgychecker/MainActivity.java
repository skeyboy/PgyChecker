package com.xsk.pgychecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xsk.pgyercheckhelper.PgyerCheckper;
import com.xsk.pgyercheckhelper.PgyerShorCutImp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PgyerCheckper pgyerCheckper = new PgyerCheckper(this);
        pgyerCheckper.check(new PgyerShorCutImp() {
            @Override
            public void onCheckStart() {

            }

            @Override
            public void onCheckFinished() {

            }

            @Override
            public void onCheckFailure(String msg) {

            }
        });
    }

    public void check(View view) {

    }
}
