package com.example.romina.fufogranja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.romina.fufogranja.model.Fruit;
import com.example.romina.fufogranja.network.FruitService;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FruitDetailFragment extends FruitBaseFragment {

    private static final String ARG_FRUIT_ID = "fruitId";
    private static final String FRUIT_KEY = "fruit";

    private String fruitId;

    @Bind(R.id.img_detail)
    ImageView img;

    @Bind(R.id.text_detail)
    TextView txt;

    private Fruit fruit;

    public FruitDetailFragment() {
        // Required empty public constructor
    }

    public static FruitDetailFragment newInstance(String fruitId) {
        FruitDetailFragment fragment = new FruitDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRUIT_ID, fruitId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fruitId = getArguments().getString(ARG_FRUIT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fruit_detail, container, false);

        ButterKnife.bind(this, v);

        return v; 
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Picasso.with(getActivity()).cancelTag(FruitDetailFragment.this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            loadFruit(fruitId);
        } else {
            fruit = (Fruit) savedInstanceState.getSerializable(FRUIT_KEY);
            showFruit(fruit);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FRUIT_KEY, fruit);
    }

    private void loadFruit(String id) {
        FruitService service = new FruitService();

        service.getFruit(id, new Callback<Fruit>() {
            @Override
            public void success(Fruit fruit, Response response) {
                FruitDetailFragment.this.fruit = fruit;
                showFruit(fruit);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void showFruit(Fruit fruit) {
        Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext())
                .load(fruit.getImgBig())
                .tag(FruitDetailFragment.this)
                .into(img);
        txt.setText(fruit.getDescription());
    }
}
