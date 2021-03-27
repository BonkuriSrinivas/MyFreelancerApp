package com.nextclick.grocery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextclick.grocery.modal.Beanclass;

import java.util.ArrayList;


public class GridviewAdapter extends BaseAdapter {

    Context context;

    ArrayList<Beanclass> bean;
    ArrayList<String> names_list = new ArrayList<String>();

    public GridviewAdapter(Context context, ArrayList<Beanclass> bean) {
        this.bean = bean;
        this.context = context;
    }

    public void filterList(ArrayList<String> filterdNames) {
        this.names_list = filterdNames;
        notifyDataSetChanged();
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
            convertView = layoutInflater.inflate(R.layout.gridview, null);

            viewHolder = new ViewHolder();
            viewHolder.lay_item = (LinearLayout) convertView.findViewById(R.id.lay_item);
            viewHolder.discription1 = (TextView) convertView.findViewById(R.id.description1);
            viewHolder.date1 = (TextView) convertView.findViewById(R.id.date1);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.discount = (TextView) convertView.findViewById(R.id.discount);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String upToNDisc;
        //final Beanclass bean = (Beanclass) getItem(position);
        String Disc = bean.get(position).getDate1();
        if (Disc.length() > 12) {
            upToNDisc = Disc.substring(0,12);
        } else {
            upToNDisc = Disc;
        }
        Log.v("upToNDisc>>>>>>",""+upToNDisc);
        viewHolder.discription1.setText(bean.get(position).getDiscription1());
        viewHolder.date1.setText(upToNDisc + "...");
        viewHolder.price.setText("₹ "+ String.valueOf(bean.get(position).getPrice1()));
        viewHolder.discount.setText("  "+bean.get(position).getDiscount()+"off");

        viewHolder.lay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(context, ProductDetails.class);
                Bundle extras = new Bundle();
                extras.putString("name", bean.get(position).getDiscription1());
                extras.putString("discrip",  bean.get(position).getDate1());
                extras.putInt("price", bean.get(position).getPrice1());
                //extras.putString("discrount",  bean.get(position).getDate1());
                ii.putExtras(extras);
                context.startActivity(ii);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image1;
        TextView discription1;
        TextView price;
        TextView discount;
        TextView date1;
        LinearLayout lay_item;
    }
}