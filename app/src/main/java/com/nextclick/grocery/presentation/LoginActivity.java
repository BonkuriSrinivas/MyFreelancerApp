package com.nextclick.grocery.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nextclick.grocery.Database.DatabaseClient;
import com.nextclick.grocery.HomeActivity;
import com.nextclick.grocery.R;
import com.nextclick.grocery.modal.Productdetails;
import com.nextclick.grocery.common.MyCustomDialog;
import com.nextclick.grocery.modal.Login;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements MyCustomDialog.onSubmitListener, View.OnClickListener {
    TextView uname_edit_text, password_edit_text, tv_fp, tv_signup;
    Button next_button, cancel_button;
    String str_uname, str_pwd;
    private List<Login> loginLists = new ArrayList<>();
    MyCustomDialog fragment_dialog;
    String str_match = "false";

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fragment_dialog = new MyCustomDialog();
        fragment_dialog.mListener = LoginActivity.this;

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        uname_edit_text = (TextView) findViewById(R.id.uname_edit_text);
        password_edit_text = (TextView) findViewById(R.id.password_edit_text);
        tv_fp = findViewById(R.id.tv_fp);
        tv_signup = findViewById(R.id.tv_signup);
        next_button = (Button) findViewById(R.id.next_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);

        tv_fp.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        next_button.setOnClickListener(this);

        LoginDataInsertion loginDataInsertion = new LoginDataInsertion();
        loginDataInsertion.execute();
    }

    @Override
    public void setOnSubmitListener(int flag, int flag1) {
        if (flag == 1) {
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fp:
                Intent ii = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(ii);
                break;
            case R.id.tv_signup:
                Intent intent_reg = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent_reg);
                break;
            case R.id.next_button:
                login();
                break;
            case R.id.cancel_button:
                finish();
                break;
        }
    }

    class LoginDataInsertion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int count,prod_count;
                count = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().logincount();
                prod_count = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().productscount();
                if (count == 0) {
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().insertAll(Login.InsertData());
                }

                if (prod_count == 0) {
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().insertAllProducts(Productdetails.ProductsData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GetData getData = new GetData();
            getData.execute();
        }
    }

    class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                loginLists = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().getLoginDatils();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    /* Detect when the back button is pressed */
    @Override
    public void onBackPressed() {
        fragment_dialog.setDialog(R.layout.custom_dialog2, LoginActivity.this, 1, 0, "Alert!", "Are you sure want to close this application", "Yes", "No");
        fragment_dialog.show(LoginActivity.this.getFragmentManager(), "");
    }

    private void login() {
        str_uname = uname_edit_text.getText().toString();
        str_pwd = password_edit_text.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, str_uname);
        editor.commit();

        if (str_uname.length() == 0) {
            uname_edit_text.setError("Please enter username");
        } else if (str_pwd.length() == 0) {
            password_edit_text.setError("Please enter password");
        } else {
            for (int i = 0; i < loginLists.size(); i++) {
                if (str_uname.trim().equals(loginLists.get(i).getUserid().trim()) && str_pwd.equals(loginLists.get(i).getPassword())) {
                    str_match = "true";
                    Intent ii = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(ii);
                }
            }
            if (str_match.equalsIgnoreCase("false")) {
                fragment_dialog.setDialog(R.layout.custom_dialog, LoginActivity.this, 0, 0, "Alert!", "Please enter valid username/password", "Ok", "");
                fragment_dialog.show(getFragmentManager(), "");
            }
        }
    }
}
