package com.nextclick.grocery;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextclick.grocery.Database.DatabaseClient;
import com.nextclick.grocery.modal.Beanclass;
import com.nextclick.grocery.modal.Productdetails;

import java.util.ArrayList;
import java.util.List;

public class Eapp5 extends Fragment {
    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;

    ArrayList<String> names_list = new ArrayList<String>();
    ArrayList<String> discps_list = new ArrayList<String>();
    ArrayList<Integer> price_list = new ArrayList<Integer>();
    ArrayList<String> discount_list = new ArrayList<String>();

    private List<Productdetails> productLists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab1, container, false);

        gridview = (ExpandableHeightGridView)view.findViewById(R.id.gridview);
        beanclassArrayList= new ArrayList<Beanclass>();

        DisplayData displayData = new DisplayData();
        displayData.execute();

        return view;

    }

    private class DisplayData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                productLists = DatabaseClient.getInstance(getActivity().getApplicationContext()).getAppDatabase().loginDao().getElectronicsProducts();
                if (productLists.size() > 0) {
                    for (int i = 0; i < productLists.size(); i++) {
                        names_list.add(productLists.get(i).getName());
                        discps_list.add(productLists.get(i).getDiscription());
                        price_list.add(productLists.get(i).getPrice());
                        discount_list.add(productLists.get(i).getDiscount());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i= 0; i< names_list.size(); i++) {
                Beanclass beanclass = new Beanclass(names_list.get(i), discps_list.get(i), price_list.get(i), discount_list.get(i));
                beanclassArrayList.add(beanclass);
            }
            gridviewAdapter = new GridviewAdapter(getActivity(), beanclassArrayList);
            gridview.setExpanded(true);
            gridview.setAdapter(gridviewAdapter);
        }
    }
}