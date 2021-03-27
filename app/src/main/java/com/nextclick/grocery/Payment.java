package com.nextclick.grocery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nextclick.grocery.common.Api;
import com.nextclick.grocery.common.Constants;
import com.nextclick.grocery.modal.Checksum;
import com.nextclick.grocery.modal.Paytm;
import com.nextclick.grocery.presentation.ForgotPasswordActivity;
import com.nextclick.grocery.presentation.LoginActivity;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Payment extends Fragment implements View.OnClickListener, PaytmPaymentTransactionCallback {
    TextView order, deli_addrs, total_amount;
    MyEditText cardno2, date2, cvv2, name2;
    LinearLayout linear_paytm, linear_cod, lay_cards, lay_cod;
    String str_cardno2, str_date2, str_cvv2, str_name2, addrs, str_prie;
    View view;

    SharedPreferences preferences;
    public static final String Address = "addressKey";
    public static final String Price = "priceKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.payment, container, false);

        linear_paytm = view.findViewById(R.id.linear_paytm);
        linear_cod = view.findViewById(R.id.linear_cod);
        lay_cards = view.findViewById(R.id.lay_cards);
        lay_cod = view.findViewById(R.id.lay_cod);
        cardno2 = view.findViewById(R.id.cardno2);
        date2 = view.findViewById(R.id.date2);
        cvv2 = view.findViewById(R.id.cvv2);
        name2 = view.findViewById(R.id.name2);
        order = view.findViewById(R.id.order);
        deli_addrs = view.findViewById(R.id.deli_addrs);
        total_amount = view.findViewById(R.id.total2);

        linear_paytm.setOnClickListener(this);
        linear_cod.setOnClickListener(this);
        order.setOnClickListener(this);

        preferences = getActivity().getSharedPreferences("MyPrefs", 0);
        addrs = preferences.getString(Address, null);
        if (addrs != null) {
            deli_addrs.setText(addrs);
        }

        /*str_prie = preferences.getString(Price, null);
        total_amount.setText(str_prie);*/

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order:
                str_cardno2 = cardno2.getText().toString();
                str_date2 = date2.getText().toString();
                str_cvv2 = cvv2.getText().toString();
                str_name2 = name2.getText().toString();

                /*if(linear_paytm.getVisibility() == View.VISIBLE){
                    if (str_cardno2.length() == 0) {
                        cardno2.setError("Please enter card number");
                    } else if (str_date2.length() == 0) {
                        date2.setError("Please enter expiry date");
                    } else if (str_cvv2.length() == 0) {
                        cvv2.setError("Please enter cvv number");
                    } else if (str_name2.length() == 0) {
                        name2.setError("Please enter cardholder name");
                    } else {
                        Toast.makeText(getActivity(), "Your Order has been successfully placed", Toast.LENGTH_SHORT).show();
                        Intent ii = new Intent(getActivity(), HomeActivity.class);
                        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ii);
                    }
                } else {*/
                Toast.makeText(getActivity(), "Your Order has been successfully placed", Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(getActivity(), HomeActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                //}

                break;
            case R.id.linear_paytm:
                lay_cards.setVisibility(View.VISIBLE);
                lay_cod.setVisibility(View.GONE);
                paytmCall();
                break;
            case R.id.linear_cod:
                lay_cards.setVisibility(View.GONE);
                lay_cod.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void paytmCall() {
        //getting the tax amount first.
        String txnAmount = total_amount.getText().toString().trim();

        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });
    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(getActivity(), true, true, this);
    }


    @Override
    public void onTransactionResponse(Bundle bundle) {
        Toast.makeText(getActivity(), bundle.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(getActivity(), s + bundle.toString(), Toast.LENGTH_LONG).show();
    }
}