package br.com.miller.farmaciaatendente.mainMenu.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.miller.farmaciaatendente.mainMenu.views.fragments.DepartamentFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.PersonalPerfilFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.SolicitationFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.StorePerfilFragment;

public class MainMenuPageAdapter extends FragmentPagerAdapter {

    private int numTabs;
    private Context context;
    private Bundle bundle;

    public MainMenuPageAdapter(FragmentManager fm, int numTabs, Context context, Bundle bundle) {
        super(fm);

        this.numTabs = numTabs;
        this.context = context;
        this.bundle = bundle;

    }


    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0 : {

                Fragment fragment = Fragment.instantiate(context, SolicitationFragment.class.getName());
                fragment.setArguments(this.bundle);

                return fragment;
            }

            case 1 : {

                Fragment fragment = Fragment.instantiate(context, DepartamentFragment.class.getName());

                fragment.setArguments(this.bundle);

                return  fragment;
            }

            case 2 : {

                Fragment fragment = Fragment.instantiate(context, PersonalPerfilFragment.class.getName());

                fragment.setArguments(this.bundle);

                return fragment;
            }

            case 3 : {
                Fragment fragment = Fragment.instantiate(context, StorePerfilFragment.class.getName());
                fragment.setArguments(this.bundle);
                return fragment;
            }

            default: return  null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
