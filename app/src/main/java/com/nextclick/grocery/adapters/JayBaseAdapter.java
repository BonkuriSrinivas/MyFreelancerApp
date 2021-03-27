package com.nextclick.grocery.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextclick.grocery.R;
import com.nextclick.grocery.modal.BeanShipping;
import com.nextclick.grocery.modal.Beanclass;

import java.util.ArrayList;


public class JayBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Beanclass> bean;
    Typeface fonts1, fonts2;

    public JayBaseAdapter(Context context, ArrayList<Beanclass> bean) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        fonts1 = Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");
        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listes, null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.discription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.title.setTypeface(fonts1);
            viewHolder.discription.setTypeface(fonts1);
            viewHolder.text.setTypeface(fonts1);
            viewHolder.date.setTypeface(fonts1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Beanclass bean = (Beanclass) getItem(position);
        String name = bean.getDiscription1();
        viewHolder.title.setText(name);
        viewHolder.discription.setText(bean.getDiscount()+"off");
        viewHolder.date.setText("₹ "+ String.valueOf(bean.getPrice1()));

        if (name.equalsIgnoreCase("Daal")) {
            viewHolder.image.setImageResource(R.drawable.daal);
        } else if (name.equalsIgnoreCase("Soaps")) {
            viewHolder.image.setImageResource(R.drawable.soaps);
        } else if (name.equalsIgnoreCase("Sunflower OIL")) {
            viewHolder.image.setImageResource(R.drawable.oil);
        } else if (name.equalsIgnoreCase("Apples")) {
            viewHolder.image.setImageResource(R.drawable.apple);
        } else if (name.equalsIgnoreCase("Grapes")) {
            viewHolder.image.setImageResource(R.drawable.grapes);
        } else {
            viewHolder.image.setImageResource(R.drawable.oil);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView title;
        TextView discription;
        TextView date;
        ImageView min;
        TextView text;
        ImageView plus;
    }
}