package com.nextclick.grocery;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nextclick.grocery.Database.DatabaseClient;
import com.nextclick.grocery.adapters.JayBaseAdapter;
import com.nextclick.grocery.modal.BeanShipping;
import com.nextclick.grocery.modal.Beanclass;
import com.nextclick.grocery.modal.Productdetails;
import com.nextclick.grocery.presentation.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class Confirmation extends Fragment {

    private View view;
    MyTextView tv_name, tv_addrs, pay, total2;
    private ListView listview;
    Typeface fonts1, fonts2;
    String name, addrs;
    public static int tot_price = 0;
    private int[] IMAGE = {R.drawable.daal, R.drawable.soaps, R.drawable.oil, R.drawable.apple, R.drawable.grapes};
    private String[] TITLE = {"Daal", "Soaps", "Sunflower OIL", "Apples", "Grapes"};
    private String[] DESCRIPTION = {"Split pulse..", "cleansing and lubricating..", "harm your health..", "edible fruit..", "deliious fruit.."};
    private String[] DATE = {"₹ 120.00", "₹ 49.00", "₹ 320.00", "₹ 130.00", "₹ 49.00"};
    private JayBaseAdapter baseAdapter;

    SharedPreferences preferences;
    public static final String Address = "addressKey";
    public static final String Name = "nameKey";
    public static final String Price = "priceKey";

    private List<Productdetails> productLists = new ArrayList<>();
    ArrayList<String> names_list = new ArrayList<String>();
    ArrayList<String> discps_list = new ArrayList<String>();
    ArrayList<Integer> price_list = new ArrayList<Integer>();
    ArrayList<String> discount_list = new ArrayList<String>();
    private ArrayList<Beanclass> beanclassArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.confirmation, container, false);

        tv_name = (MyTextView) view.findViewById(R.id.Mytv_name);
        tv_addrs = (MyTextView) view.findViewById(R.id.Mytv_addrs);
        pay = view.findViewById(R.id.pay);
        total2 = view.findViewById(R.id.total2);
        listview = (ListView) view.findViewById(R.id.listview);
        beanclassArrayList= new ArrayList<Beanclass>();

        preferences = getActivity().getSharedPreferences("MyPrefs", 0);
        name = preferences.getString(Name, null);
        addrs = preferences.getString(Address, null);
        Log.v("name_addrs>>>", "" + name + "&&" + addrs);

        if(name!=null){
            tv_name.setText(name);
        }
        if(addrs!=null) {
            tv_addrs.setText(addrs);
        }

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShippigPayment.viewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        CartData cartData = new CartData();
        cartData.execute();

        return view;
    }

    private class CartData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                productLists = DatabaseClient.getInstance(getActivity().getApplicationContext()).getAppDatabase().loginDao().getCartProducts();
                if (productLists.size() > 0) {
                    for (int i = 0; i < productLists.size(); i++) {
                        names_list.add(productLists.get(i).getName());
                        discps_list.add(productLists.get(i).getDiscription());
                        price_list.add(productLists.get(i).getPrice());
                        discount_list.add(productLists.get(i).getDiscount());

                        tot_price = tot_price+ productLists.get(i).getPrice();
                        Log.v("SAS##",""+tot_price);
                    }
                }
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Price, String.valueOf(tot_price));
                total2.setText(String.valueOf(tot_price));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i = 0; i < names_list.size(); i++) {
                Beanclass beanclass = new Beanclass(names_list.get(i), discps_list.get(i), price_list.get(i), discount_list.get(i));
                beanclassArrayList.add(beanclass);
            }

            if (beanclassArrayList != null) {
                baseAdapter = new JayBaseAdapter(getActivity(), beanclassArrayList);
                listview.setAdapter(baseAdapter);
            }
        }
    }
}