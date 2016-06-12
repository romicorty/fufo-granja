package com.example.romina.fufogranja;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

public class FruitBaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = FruitApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
