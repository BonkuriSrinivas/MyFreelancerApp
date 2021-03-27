package com.nextclick.grocery;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextclick.grocery.presentation.MapsActivity;

public class Shipping extends Fragment {
    TextView order;
    LinearLayout lay_Map;
    MyEditText name2,secondname2,phoneno2;
    String str_name2,str_secondname2,str_phoneno2;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shipping, container, false);

        name2 = view.findViewById(R.id.name2);
        lay_Map = view.findViewById(R.id.lay_map);
        secondname2 = view.findViewById(R.id.secondname2);
        phoneno2 = view.findViewById(R.id.phoneno2);
        order = view.findViewById(R.id.order);

        lay_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getActivity(), MapsActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name2 = name2.getText().toString();
                str_secondname2 = secondname2.getText().toString();
                str_phoneno2 = phoneno2.getText().toString();

                if (str_name2.length() == 0) {
                    name2.setError("Please enter first name");
                } else if (str_phoneno2.length() == 0) {
                    phoneno2.setError("Please enter phone number");
                } else {
                    ShippigPayment.viewPager.arrowScroll(View.FOCUS_RIGHT);
                }
            }
        });

        return view;
    }
}