package com.nextclick.grocery.presentation;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nextclick.grocery.Database.DatabaseClient;
import com.nextclick.grocery.R;
import com.nextclick.grocery.common.LocationAddress;
import com.nextclick.grocery.common.LocationTrack;
import com.nextclick.grocery.modal.Login;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText fn, pwd, email_edit_text, mbl_edit_text, et_cur_addrs;
    Spinner sp_gendar;
    Button next_signup, back;
    String str_fn, str_pwd, str_gendar, str_ph, str_mail;
    Switch switch1;

    Boolean Checked = false;
    String str_locator_chk, trip;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    Login login;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Address = "addressKey";
    SharedPreferences sharedpreferences;

    private AdView adView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fn = (EditText) findViewById(R.id.fn);
        pwd = (EditText) findViewById(R.id.pwd);
        email_edit_text = (EditText) findViewById(R.id.email_edit_text);
        mbl_edit_text = (EditText) findViewById(R.id.mbl_edit_text);
        sp_gendar = findViewById(R.id.sp_gendar);
        next_signup = findViewById(R.id.next_signup);
        et_cur_addrs = findViewById(R.id.et_cur_addrs);
        back = findViewById(R.id.back);
        switch1 = findViewById(R.id.switch1);

        adView = (AdView) findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        back.setOnClickListener(this);
        next_signup.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        /*switch1.setChecked(true);
        curr_addrs();*/

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Checked = true;
                    et_cur_addrs.setEnabled(false);
                    et_cur_addrs.setInputType(InputType.TYPE_NULL);
                    et_cur_addrs.setFocusable(false);
                    curr_addrs();
                } else {
                    Checked = false;
                    et_cur_addrs.setText("");
                    et_cur_addrs.setEnabled(true);
                    et_cur_addrs.setFocusable(true);
                }
            }
        });
    }

    private void curr_addrs() {
        str_locator_chk = String.valueOf(Checked);
        Log.v("str_locator_chk>>>", "" + str_locator_chk);
        if (str_locator_chk.equalsIgnoreCase("true")) {
            locationTrack = new LocationTrack(RegistrationActivity.this);
            if (locationTrack.canGetLocation()) {
                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();

                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        this.getApplicationContext(), new GeocoderHandler());

                //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            } else {
                locationTrack.showSettingsAlert();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_signup:
                register();
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private void register() {
        str_fn = fn.getText().toString();
        str_pwd = pwd.getText().toString();
        str_ph = mbl_edit_text.getText().toString();
        str_mail = email_edit_text.getText().toString();
        str_gendar = sp_gendar.getSelectedItem().toString();

        if (str_fn.length() == 0) {
            fn.setError("Please enter first name");
        } else if (str_mail.length() == 0) {
            email_edit_text.setError("Please enter Email ID");
        } else if (str_ph.length() == 0) {
            mbl_edit_text.setError("Please enter phone number");
        } else if (str_pwd.length() == 0) {
            pwd.setError("Please enter password");
        } else {
            SaveTask saveTask = new SaveTask();
            saveTask.execute();
        }
    }

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int count = 0;
                count = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().logincount();
                count++;
                login = new Login(count,str_fn,str_pwd);
                DatabaseClient.getInstance(RegistrationActivity.this).getAppDatabase().loginDao().insertUsers(login);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent ii = new Intent(RegistrationActivity.this, LoginActivity.class);
            ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ii);
        }
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Address, locationAddress);
            editor.commit();
            et_cur_addrs.setText(" " + locationAddress);
            //Toast.makeText(getApplicationContext(), locationAddress, Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(RegistrationActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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