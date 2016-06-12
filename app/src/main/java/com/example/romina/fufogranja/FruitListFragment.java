package com.example.romina.fufogranja;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.romina.fufogranja.model.Fruit;
import com.example.romina.fufogranja.network.FruitService;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FruitListFragment extends FruitBaseFragment implements AdapterView.OnItemClickListener {

    private static final String FRUITS_KEY = "fruits";

    @Bind(R.id.listView)
    ListView list;

    @Bind(R.id.progressBar)
    ProgressBar bar;

    private FruitListAdapter adapter;
    private FruitListFragmentListener mListener;
    private List<Fruit> fruits;

    public FruitListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fruit_list, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
            loadFruits();
        } else {
            fruits = (List<Fruit>) savedInstanceState.getSerializable(FRUITS_KEY);
            showFruits(fruits);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FruitListFragmentListener) {
            mListener = (FruitListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FruitListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FRUITS_KEY, (Serializable) fruits);
    }

    @OnItemClick(R.id.listView)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fruit fruit = adapter.getItem(position);
        if (mListener != null) {
            mListener.showFruitDetail(fruit);
        }
    }

    private void loadFruits() {
        bar.setVisibility(View.VISIBLE);
        FruitService service = new FruitService();
        service.getFruits(new Callback<List<Fruit>>() {
            @Override
            public void success(List<Fruit> fruits, Response response) {
                FruitListFragment.this.fruits = fruits;
                showFruits(fruits);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                bar.setVisibility(View.GONE);
            }
        });

    }

    private void showFruits(List<Fruit> fruits) {
        adapter = new FruitListAdapter(getActivity(), R.layout.item_fruit, fruits);
        list.setAdapter(adapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface FruitListFragmentListener {
        void showFruitDetail(Fruit fruit);
    }
}
