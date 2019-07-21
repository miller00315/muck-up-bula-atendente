package br.com.miller.farmaciaatendente.saleManager.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.miller.farmaciaatendente.saleManager.views.fragments.CanceledSales;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.NewsSales;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.ReceivedSales;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.SendedSales;

public class SalesTabPagerAdapter extends FragmentPagerAdapter {

    private int numTabs;
    private Context context;
    private Bundle bundle;

    public SalesTabPagerAdapter(FragmentManager fm, int numTabs, Context context, Bundle bundle) {
        super(fm);

        this.numTabs = numTabs;
        this.context = context;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){

            case 0 : {
                Fragment fragment = Fragment.instantiate(context, NewsSales.class.getName());
                fragment.setArguments(bundle);

                return fragment;
            }

            case 1 : {
                Fragment fragment = Fragment.instantiate(context, SendedSales.class.getName());
                fragment.setArguments(bundle);

                return fragment;
            }

            case 2 : {
                Fragment fragment = Fragment.instantiate(context, ReceivedSales.class.getName());
                fragment.setArguments(bundle);

                return  fragment;

            }

            case 3 : {

                Fragment fragment = Fragment.instantiate(context, CanceledSales.class.getName());
                fragment.setArguments(bundle);

                return  fragment;

            }

            default: return  null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
