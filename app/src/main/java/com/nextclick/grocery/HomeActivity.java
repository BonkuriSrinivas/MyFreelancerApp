package com.nextclick.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.nextclick.grocery.adapters.ListbaseAdapter;
import com.nextclick.grocery.common.MyCustomDialog;
import com.nextclick.grocery.modal.Beanclasses;
import com.nextclick.grocery.presentation.LoginActivity;
import com.nextclick.grocery.presentation.RegistrationActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,MyCustomDialog.onSubmitListener {
    SliderLayout mDemoSlider;

    private ListView list;
    private ArrayList<Beanclasses> Bean;
    private ListbaseAdapter baseAdapter;

    private int[] IMAGE = {R.drawable.slidertea, R.drawable.slider2, R.drawable.slider3,};

    private String[] TITLE = {"", "", ""};

    private String[] SUBTITLE = {"", "", ""};

    private String[] SHOP = {"Add Product", "Search Product", "Log Out"};

    MyCustomDialog fragment_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        list = (ListView)findViewById(R.id.list);
        fragment_dialog = new MyCustomDialog();
        fragment_dialog.mListener = HomeActivity.this;

        //*****listview*******
        Bean = new ArrayList<Beanclasses>();

        for (int i= 0; i< TITLE.length; i++){

            Beanclasses bean = new Beanclasses(IMAGE[i], TITLE[i], SUBTITLE[i], SHOP[i]);
            Bean.add(bean);

        }

        baseAdapter = new ListbaseAdapter(HomeActivity.this, Bean) {

        };

        list.setAdapter(baseAdapter);

        //******slider***********
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
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void setOnSubmitListener(int flag, int flag1) {
        if (flag == 1) {
            Intent intent_reg = new Intent(HomeActivity.this, LoginActivity.class);
            intent_reg.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent_reg);
        }
    }

    /* Detect when the back button is pressed */
    @Override
    public void onBackPressed() {
        fragment_dialog.setDialog(R.layout.custom_dialog2, HomeActivity.this, 1, 0, "Alert!", "Are you sure you want to logout?", "Yes", "No");
        fragment_dialog.show(HomeActivity.this.getFragmentManager(), "");
    }
}