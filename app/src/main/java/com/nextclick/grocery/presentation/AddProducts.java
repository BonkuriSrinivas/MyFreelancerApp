package com.nextclick.grocery.presentation;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nextclick.grocery.Database.DatabaseClient;
import com.nextclick.grocery.HomeActivity;
import com.nextclick.grocery.R;
import com.nextclick.grocery.common.MyCustomDialog;
import com.nextclick.grocery.modal.Productdetails;

public class AddProducts extends AppCompatActivity implements MyCustomDialog.onSubmitListener{

    Spinner sp_category,sp_qty,sp_discount;
    EditText et_name,et_price,et_disc;
    Button btn_add;
    String str_cate,str_name,str_disc,str_qty,str_discount,str_price;
    int qty,price;
    MyCustomDialog fragment_dialog;
    Productdetails productDet;

    private AdView adView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);

        fragment_dialog = new MyCustomDialog();
        fragment_dialog.mListener = AddProducts.this;

        sp_category = (Spinner)findViewById(R.id.sp_category);
        sp_qty = findViewById(R.id.sp_qty);
        sp_discount = findViewById(R.id.sp_discount);
        et_name = (EditText)findViewById(R.id.et_name);
        et_disc = (EditText)findViewById(R.id.et_disc);
        et_price = (EditText)findViewById(R.id.et_price);
        btn_add = (Button)findViewById(R.id.btn_add);

        adView = (AdView) findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidData();
            }
        });
    }

    boolean isValidData() {
        boolean valid = true;

        str_cate = sp_category.getSelectedItem().toString();
        str_qty = sp_qty.getSelectedItem().toString();
        str_discount = sp_discount.getSelectedItem().toString();
        str_name = et_name.getText().toString();
        str_disc = et_disc.getText().toString();
        str_price = et_price.getText().toString();
        price = Integer.parseInt(str_price);

        if (str_cate.equals("Select")) {
            valid = false;
            fragment_dialog.setDialog(R.layout.custom_dialog, AddProducts.this, 0, 0, "Alert!", "Please select Category", "Ok", "");
            fragment_dialog.show(getFragmentManager(), "");
        }  else if (str_name.length() == 0) {
            valid = false;
            fragment_dialog.setDialog(R.layout.custom_dialog, AddProducts.this, 0, 0, "Alert!", "Please enter Product Name", "Ok", "");
            fragment_dialog.show(getFragmentManager(), "");
        } else if (str_cate.length() == 0) {
            valid = false;
            fragment_dialog.setDialog(R.layout.custom_dialog, AddProducts.this, 0, 0, "Alert!", "Please enter Discription", "Ok", "");
            fragment_dialog.show(getFragmentManager(), "");
        } else{
            SaveTask saveTask = new SaveTask();
            saveTask.execute();
        }

        return valid;
    }

    @Override
    public void setOnSubmitListener(int flag, int flag1) {

    }

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                productDet = new Productdetails(str_cate,str_name,str_disc,price,str_qty,str_discount,0);

                DatabaseClient.getInstance(AddProducts.this).getAppDatabase().loginDao().insertProducts(productDet);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddProducts.this, "Product added sucessfully", Toast.LENGTH_LONG).show();
            Intent ii = new Intent(AddProducts.this, HomeActivity.class);
            ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ii);
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
