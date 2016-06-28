package com.example.romina.fufogranja;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.romina.fufogranja.model.Fruit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FruitListActivity extends AppCompatActivity implements FruitListFragment.FruitListFragmentListener {
    
    @Nullable
    @Bind(R.id.frameLayout)
    View frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inyecto vistas
        ButterKnife.bind(this);
    }

    public void showFruitDetail(Fruit fruit) {
        if (frameLayout != null) {
            // Estoy en large-land
            FruitDetailFragment fruitDetailFragment = FruitDetailFragment.newInstance(fruit.getId());
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, fruitDetailFragment);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            // No estoy en large-land
            Intent intent = new Intent(this, FruitDetailActivity.class);
            intent.putExtra(FruitDetailActivity.EXTRA_FRUIT_ID, fruit.getId());
            startActivity(intent);
        }
    }
}
