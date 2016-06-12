package com.example.romina.fufogranja;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.romina.fufogranja.model.Fruit;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by romina on 08/03/16.
 */
public class FruitListAdapter extends ArrayAdapter<Fruit> {

    private int mResourceId;

    public FruitListAdapter(Context context, int resource, List<Fruit> fruits) {
        super(context, resource,fruits);
        this.mResourceId = resource;
    }

    static class FruitsViewHolder {
        @Bind(R.id.img_fruit)
        ImageView imgFruit;

        @Bind(R.id.title_fruit)
        TextView txTitle;

        public FruitsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FruitsViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(mResourceId, parent, false);
            viewHolder = new FruitsViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (FruitsViewHolder) convertView.getTag();
        }
        Fruit currentFruit = getItem(position);
        if (currentFruit.getImgSmall() != null){


            //Loading image from below url into imageView

            Picasso.with(getContext()).setLoggingEnabled(true);
            Picasso.with(getContext())
                    .load(currentFruit.getImgSmall())
                    .into(viewHolder.imgFruit, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("Picasso","Ok");
                        }

                        @Override
                        public void onError() {
                            Log.d("Picasso","Error");
                        }
                    });

        }

        if (currentFruit.getTitle() != null){
            viewHolder.txTitle.setText(currentFruit.getTitle());
        }

        return convertView;
    }
}
