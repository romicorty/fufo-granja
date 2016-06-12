package com.example.romina.fufogranja;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FruitDetailActivity extends AppCompatActivity{

    public static final String EXTRA_FRUIT_ID = "FruitId";
    private static final String LOG_TAG = "FruitDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);
        String id = getIntent().getStringExtra(EXTRA_FRUIT_ID);
        Log.d(LOG_TAG,"onCreate");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameLayout);

        if (fragment == null) {
            FruitDetailFragment fruitDetailFragment = FruitDetailFragment.newInstance(id);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, fruitDetailFragment);
            ft.commit();
        }
    }
}
