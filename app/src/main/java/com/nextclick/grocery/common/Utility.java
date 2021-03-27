package com.nextclick.grocery.common;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String EmpId = "";
    public static String gender = "";
    public static String empname = "";
    public static String emptype = "";
    public static String date = "";
    public static final String TAG = "DETAILS";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String REQUIRED_MSG = "Required";
    private static final String EMAIL_MSG = "invalid email";
 //   private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String FNAME_PATTERN = "[A-Z][a-zA-Z]*";

    private static final String LNAME_PATTERN = "[a-zA-z]+([ '-][a-zA-Z]+)*";

    private static final String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z]).{6,10}$";

    private static final String pancardPattern = "[a-zA-Z]{5}+[0-9]{4}+[a-zA-Z]{1}";

    private static final String empIdPattern = "[a-zA-Z]{3}+[0-9]{3}";

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public static String authorization = getAuthToken("admin", "arezzo7");

    /* Common validation for email. */
    public static boolean emailValidate(String email) {

        if (email != null && email.trim().length() > 0) {

            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /* Common validation for fname. */
    public static boolean fnameValidate(String name) {

        if (name != null && name.trim().length() > 0) {

            pattern = Pattern.compile(FNAME_PATTERN);
            matcher = pattern.matcher(name);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /* Common validation for lname. */
    public static boolean lnameValidate(String name) {

        if (name != null && name.trim().length() > 0) {

            pattern = Pattern.compile(LNAME_PATTERN);
            matcher = pattern.matcher(name);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /* Common validation for password. */
    public static boolean passwordValidation(String password) {
        if (!TextUtils.isEmpty(password) && password != null) {
            pattern = Pattern.compile(passwordPattern);
            matcher = pattern.matcher(password);

            return matcher.matches();
        } else
            return false;
    }

    /* Common validation for Phonenumber. */
    public static boolean PhoneValidation(String phone) {
        if (phone != null && phone.length() >= 10) {

            return true;
        }
        return false;
    }

    /* Common validation for PAN number. */
    public static boolean isValidPAN(String pan) {
        if (!TextUtils.isEmpty(pan) && pan != null) {
            pattern = Pattern.compile(pancardPattern);
            matcher = pattern.matcher(pan);
            return matcher.matches();
        } else
            return false;
    }

    /* Common validation for Zipcode. */
    public static boolean ZipcodeValidation(String zipcode) {
        if (zipcode != null && zipcode.length() >= 6) {

            return true;
        }
        return false;
    }

    /* Common ProgressDialog. */
    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }

    /* Common CurrentDate in dd-MM-yyyy format. */
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    /* Common CurrentDate in MM/dd/yyyy format. */
    public static String getCurrentDate1() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    /* Common validation for email. */
    public static boolean CommonValidation(String common_name) {
        if (common_name != null && common_name.length() != 0) {
            return true;
        } else
            return false;
    }

    /* Common method for DateDialog. */
    public static void getDateDialog(Activity activity, final EditText et_inv_date) {
        int year, month, day;
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        et_inv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /* Common method for CurrentDateTime. */
    public String getCurrentDateTime() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateValue = dateFormat.format(date);
        // System.out.println("Current Time: " + dateValue);
        return dateValue;
    }

    /* Common method for app Permissions. */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /* Get Authentication for arezzo service. */
    public static String getAuthToken(String user, String pwd) {
        byte[] data = new byte[0];
        try {
            data = (user + ":" + pwd).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

    /* Check Network Connectivity. */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.w("INTERNET:", "connected!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValidEmail(editText,EMAIL_PATTERN , EMAIL_MSG, required);
    }
    public static boolean isValidEmail(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasEmail(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }
    public static boolean hasEmail(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        else{
            return true;
        }


    }

}