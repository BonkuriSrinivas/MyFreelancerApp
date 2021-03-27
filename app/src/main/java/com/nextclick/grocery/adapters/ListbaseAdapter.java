package com.nextclick.grocery.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextclick.grocery.ProductCategories;
import com.nextclick.grocery.R;
import com.nextclick.grocery.modal.Beanclasses;
import com.nextclick.grocery.presentation.AddProducts;
import com.nextclick.grocery.presentation.LoginActivity;

import java.util.ArrayList;


public class ListbaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Beanclasses>bean;

    public ListbaseAdapter(Context context, ArrayList<Beanclasses> bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list, null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.subtitle = (TextView)convertView.findViewById(R.id.subtitle);
            viewHolder.shop = (TextView)convertView.findViewById(R.id.shop);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        Beanclasses bean = (Beanclasses)getItem(position);
        viewHolder.image.setImageResource(bean.getImage());
        viewHolder.title.setText(bean.getTitle());
        viewHolder.subtitle.setText(bean.getSubtitle());
        viewHolder.shop.setText(bean.getShop());

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 0) {
                    Intent ii = new Intent(context, AddProducts.class);
                    context.startActivity(ii);
                } else if (position == 1) {
                    Intent ii = new Intent(context, ProductCategories.class);
                    context.startActivity(ii);
                } else if (position == 2) {
                    logout();
                }
            }

        });

        return convertView;
    }

    private void logout() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.txt_message);
        text.setText("Are you sure you want to logout?");
        ImageView close = (ImageView) dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, LoginActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(it);
            }
        });

        dialog.show();
    }

    class ViewHolder {
        ImageView image;
        TextView title;
        TextView subtitle;
        TextView shop;

    }
}