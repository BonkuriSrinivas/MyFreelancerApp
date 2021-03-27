package com.nextclick.grocery.common;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextclick.grocery.R;

/**
 * Created by srinivas.bonkuri on 23/10/2018.
 */

public class MyCustomDialog extends DialogFragment {
    Button buttonOk,buttonYes,buttonNo;
    ImageView btnCancel;
    TextView txtTitle, txtMessage;
    public onSubmitListener mListener;
    int mFlag,mFlag1, layout;
    String strTitle, strMessage, strPositiveButton, strNegative;
    Context context;
    Activity objActivity;

    // ClickListener for button actions
    public interface onSubmitListener {
        void setOnSubmitListener(int flag, int flag1);
    }

    public void setDialog(int mlayout, Context ctx, int flag_posi,int flag_negi, String Title, String Message, String PositiveButton, String NegativeButton) {
        layout = mlayout;
        mFlag = flag_posi;
        mFlag1 = flag_negi;
        strTitle = Title;
        strMessage = Message;
        strPositiveButton = PositiveButton;
        strNegative = NegativeButton;
        context = ctx;
        objActivity = (Activity) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogFragment = new Dialog(getActivity());

        dialogFragment.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogFragment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFragment.setContentView(layout); //R.layout.custom_dialog
        dialogFragment.setCanceledOnTouchOutside(false);
        dialogFragment.show();

        // dialog with one buuton(OK)
        if (layout == R.layout.custom_dialog) {
            txtTitle = (TextView) dialogFragment.findViewById(R.id.title);
            txtMessage = (TextView) dialogFragment.findViewById(R.id.txt_message);
            buttonOk = (Button) dialogFragment.findViewById(R.id.btn_ok);
            btnCancel = (ImageView) dialogFragment.findViewById(R.id.iv_close);

            buttonOk.setText(strPositiveButton);
            txtTitle.setText(strTitle);
            txtMessage.setText(strMessage);

            buttonOk.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.setOnSubmitListener(mFlag,0);
                    dismiss();
                }
            });
            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        } else if (layout == R.layout.custom_dialog2) {
            // dialog with two buuton(Yes,No)
            txtTitle = (TextView) dialogFragment.findViewById(R.id.title);
            txtMessage = (TextView) dialogFragment.findViewById(R.id.txt_message);
            buttonYes = (Button) dialogFragment.findViewById(R.id.btn_yes);
            buttonNo = (Button) dialogFragment.findViewById(R.id.btn_no);
            btnCancel = (ImageView) dialogFragment.findViewById(R.id.iv_close);

            buttonYes.setText(strPositiveButton);
            buttonNo.setText(strNegative);
            txtTitle.setText(strTitle);
            txtMessage.setText(strMessage);

            buttonYes.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.setOnSubmitListener(mFlag,0);
                    dismiss();
                }
            });
            buttonNo.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.setOnSubmitListener(0,mFlag1);
                    dismiss();
                }
            });
            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        return dialogFragment;
    }
}