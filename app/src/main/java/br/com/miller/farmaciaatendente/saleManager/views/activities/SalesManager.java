package br.com.miller.farmaciaatendente.saleManager.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.saleManager.adapters.SalesTabPagerAdapter;
import br.com.miller.farmaciaatendente.saleManager.presenters.SaleManagerPresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.SaleManagerTasks;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.CanceledSales;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.NewsSales;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.ReceivedSales;
import br.com.miller.farmaciaatendente.saleManager.views.fragments.SendedSales;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;

public class SalesManager extends AppCompatActivity implements SaleManagerTasks.Presenter,
        NewsSales.OnFragmentInteractionListener,
        SendedSales.OnFragmentInteractionListener,
        ReceivedSales.OnFragmentInteractionListener,
        CanceledSales.OnFragmentInteractionListener,
        EditTextDialogFragment.AlertOptionsResult{

    private SaleManagerPresenter saleManagerPresenter;
    private ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){

                        case R.id.navigation_news: {
                            viewPager.setCurrentItem(0);
                            return true;
                        }

                        case R.id.navigation_sended: {
                            viewPager.setCurrentItem(1);
                            return true;
                        }

                        case R.id.navigation_received:{
                            viewPager.setCurrentItem(2);
                            return true;
                        }

                        case R.id.navigation_canceled: {

                            viewPager.setCurrentItem(3);
                            return true;
                        }

                        default:
                            return false;
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_manager);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Pedidos");

        saleManagerPresenter = new SaleManagerPresenter(this);

        viewPager = findViewById(R.id.pager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SalesTabPagerAdapter salesTabPagerAdapter = new SalesTabPagerAdapter(getSupportFragmentManager(), 4,
                this, getUserData(getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)));

        viewPager.setAdapter(salesTabPagerAdapter);

    }

    private Bundle getUserData(SharedPreferences sharedPreferences){

        Bundle bundle = new Bundle();

        User user = new User();

        user.setName(sharedPreferences.getString(Constants.USER_NAME, ""));
        user.setSurname(sharedPreferences.getString(Constants.USER_SURNAME, ""));
        user.setId_firebase(sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));
        user.setCity(sharedPreferences.getString(Constants.USER_CITY, ""));
        user.setEmail(sharedPreferences.getString(Constants.USER_EMAIL,""));
        user.setStoreId(sharedPreferences.getString(Constants.STORE_ID, ""));

        bundle.putParcelable("user", user);

        return bundle;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.general_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.chat_icon :{

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                bundle.putString("hint", "Fale conosco");

                EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);
                editTextDialogFragment.setListener(this);
                editTextDialogFragment.openDialog(getSupportFragmentManager());

                break;
            }

            case android.R.id.home:{

                finish();

                break;
            }

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

        switch (bundle.getInt("type")){

            case 4 : {

                Intent intent = new Intent(this, ManipulateBuy.class);
                intent.putExtra("params", bundle);
                startActivity(intent);

                break;
            }

            default:
                break;

        }
    }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) {
        
    }
}
