package com.nextclick.grocery.presentation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nextclick.grocery.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText fp_edit_text;
    Button next_button,back;
    String str_fp;

    private AdView adView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fp_edit_text = (EditText) findViewById(R.id.fp_edit_text);
        next_button = findViewById(R.id.next_button);
        back = findViewById(R.id.back);

        adView = (AdView) findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_fp = fp_edit_text.getText().toString();
                if (str_fp.length() == 0) {
                    fp_edit_text.setError("Please enter Email ID");
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Password has sent to your Email ID", Toast.LENGTH_SHORT).show();
                    Intent ii = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ii);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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