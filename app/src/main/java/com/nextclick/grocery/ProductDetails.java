package com.nextclick.grocery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.nextclick.grocery.Database.DatabaseClient;
import com.nextclick.grocery.modal.Bean;
import com.nextclick.grocery.presentation.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    SliderLayout mDemoSlider;
    LinearLayout adsds;
    TextView itemname,discription1;
    private ExpandableHeightGridView gridview;
    private ArrayList<Bean> beanclassArrayList;
    private GridviewDetailsAdapter gridviewAdapter;
    private int[] IMAGEgrid = {R.drawable.bag1, R.drawable.bag3, R.drawable.bag6, R.drawable.bag4};
    private String[] DIscriptiongrid = {"Office bag", "Travel bag", "Causal bag","Class bag"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!"};
    String str_name,str_disc;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_name = extras.getString("name");
            str_disc = extras.getString("discrip");
            price = extras.getInt("price");
        }
        Log.v("str_disc>>>",""+str_name+"&&&"+str_disc+"$$"+price);

        itemname = (TextView)findViewById(R.id.itemname);
        discription1 = (TextView)findViewById(R.id.discription1);
        itemname.setText(str_name);
        discription1.setText(str_disc);
        gridview = (ExpandableHeightGridView)findViewById(R.id.gridview);
        adsds = (LinearLayout)findViewById(R.id.adsds);
        adsds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCart update = new updateCart();
                update.execute();
            }
        });
        beanclassArrayList= new ArrayList<Bean>();

        for (int i= 0; i< IMAGEgrid.length; i++) {
            Bean beanclass = new Bean(IMAGEgrid[i], DIscriptiongrid[i], Dategrid[i]);
            beanclassArrayList.add(beanclass);
        }
        gridviewAdapter = new GridviewDetailsAdapter(ProductDetails.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.s1);
        file_maps.put("2",R.drawable.s2);
        file_maps.put("3",R.drawable.s3);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new ChildAnimationExample());
        mDemoSlider.setDuration(4000);
        //mDemoSlider.addOnPageChangeListener(this);
    }

    // Update Cart value
    public class updateCart extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().updateCart(1, str_name);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent ii = new Intent(getApplicationContext(), AddToCart.class);
            Bundle extras = new Bundle();
            extras.putString("name", str_name);
            extras.putString("discrip",  str_disc);
            extras.putInt("price",  price);
            ii.putExtras(extras);
            startActivity(ii);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}