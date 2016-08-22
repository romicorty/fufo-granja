package com.example.romina.fufogranja;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.romina.fufogranja.model.Fruit;
import com.example.romina.fufogranja.network.FruitService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

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

    @Bind(R.id.btn_save_data)
    Button btn_save;

    @Bind(R.id.detail_imageView)
    ImageView imageView;

    @Bind(R.id.fruit_detail_container)
    View fruitDetailContainer;

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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot(fruitDetailContainer);
            }
        });

        return v; 
    }

    private void takeScreenshot(View v) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            String mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/micopia.jpg";
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            Bitmap bitmap = screenShot(v);
           // imageView.setImageBitmap(bitmap);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            galleryAddPic(mPath);
        }catch (IOException e){
            String error = e.getMessage();
            Log.e("FruitDetailFragment",error);
        }

    }

    private void galleryAddPic(String photoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.getActivity().sendBroadcast(mediaScanIntent);
    }


    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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
