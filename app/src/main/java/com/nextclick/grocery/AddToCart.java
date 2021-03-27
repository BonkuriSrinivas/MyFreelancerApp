package com.nextclick.grocery;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nextclick.grocery.presentation.MapsActivity;

public class AddToCart extends AppCompatActivity {
    ImageView addtocart;
    MyTextView est,price,itemname,but_now;
    LinearLayout lay_price;
    String str_name,str_disc;
    int price_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtocart);
        addtocart = (ImageView) findViewById(R.id.addtocart);
        est = findViewById(R.id.est);
        price = findViewById(R.id.price);
        itemname = findViewById(R.id.itemname);
        but_now = findViewById(R.id.but_now);
        lay_price = findViewById(R.id.lay_price);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_name = extras.getString("name");
            str_disc = extras.getString("discrip");
            price_val = extras.getInt("price");
        }
        Log.v("str_disc22>>>",""+str_name+"&&&"+str_disc+"$$"+price);
        est.setText(str_name);
        itemname.setText(str_disc);
        price.setText("₹ "+price_val);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(ii);
            }
        });

        /*lay_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), ShippigPayment.class);
                startActivity(ii);
            }
        });*/

        but_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), ShippigPayment.class);
                startActivity(ii);
            }
        });
    }
}
