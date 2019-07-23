package br.com.miller.farmaciaatendente.mainMenu.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.departamentManager.view.DepartamentManager;
import br.com.miller.farmaciaatendente.departamentManager.view.MedicineManager;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.adapters.MainMenuPageAdapter;
import br.com.miller.farmaciaatendente.mainMenu.presenters.MainMenuPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.MainMenuTasks;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.DepartamentFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.PersonalPerfilFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.SolicitationFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.StorePerfilFragment;
import br.com.miller.farmaciaatendente.saleManager.views.activities.ManipulateBuy;
import br.com.miller.farmaciaatendente.saleManager.views.activities.SalesManager;
import br.com.miller.farmaciaatendente.utils.Constants;

public class MainMenuActivity extends AppCompatActivity implements MainMenuTasks.Presenter,
        SolicitationFragment.OnFragmentInteractionListener,
        PersonalPerfilFragment.OnFragmentInteractionListener,
        DepartamentFragment.OnFragmentInteractionListener,
        StorePerfilFragment.OnFragmentInteractionListener {

    private MainMenuPresenter mainMenuPresenter;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_store:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Bula");

        mainMenuPresenter = new MainMenuPresenter(this);

        viewPager = findViewById(R.id.pager);

       BottomNavigationView navigation = findViewById(R.id.navigation);
       navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MainMenuPageAdapter mainMenuPageAdapter = new MainMenuPageAdapter(getSupportFragmentManager(), 4, this, getUserData(sharedPreferences));

        viewPager.setAdapter(mainMenuPageAdapter);
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

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.chat_icon :{

            }

            case R.id.logout:{

            }

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PersonalPerfilFragment.ID){

            if(resultCode == RESULT_OK){

                if(data != null){

                    List<Fragment> fragments = getSupportFragmentManager().getFragments();

                    for (Fragment fragment : fragments){

                        if(fragment instanceof PersonalPerfilFragment){

                            PersonalPerfilFragment personalPerfilFragment = (PersonalPerfilFragment) fragment;
                            personalPerfilFragment.setImageAlert(data);

                            break;
                        }
                    }
                }
            }
        }else if(requestCode == StorePerfilFragment.ID){

            if(resultCode == RESULT_OK){

                if(data != null){

                    List<Fragment> fragments = getSupportFragmentManager().getFragments();

                    for (Fragment fragment : fragments){

                        if(fragment instanceof StorePerfilFragment){

                            StorePerfilFragment storePerfilFragment = (StorePerfilFragment) fragment;
                            storePerfilFragment.setImageAlert(data);

                            break;
                        }
                    }
                }
            }
        }else if(requestCode == Constants.MEDICINE_MANAGER){

            if(resultCode == RESULT_OK){

                List<Fragment> fragments = getSupportFragmentManager().getFragments();

                for(Fragment fragment: fragments){

                    if(fragment instanceof DepartamentFragment){

                        DepartamentFragment departamentFragment = (DepartamentFragment) fragment;
                        departamentFragment.getDepartaments();
                    }
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

        switch (bundle.getInt("type")){

            case 1:{

                Intent intent = new Intent(this, SalesManager.class);
                startActivity(intent);

                break;
            }

            case 2:{

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PersonalPerfilFragment.ID);

                break;
            }

            case 3:{

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, StorePerfilFragment.ID);

                break;
            }

            case 4 : {

                Intent intent = new Intent(this, ManipulateBuy.class);
                intent.putExtra("params", bundle);
                startActivity(intent);

                break;
            }

            case 5 : {

                Intent intent = new Intent(this, DepartamentManager.class);
                intent.putExtra("params", bundle);
                startActivityForResult(intent, Constants.MEDICINE_MANAGER);

                break;
            }

            case 6 : {

                Intent intent = new Intent(this, MedicineManager.class);
                intent.putExtra("params", bundle);
                startActivityForResult(intent, Constants.MEDICINE_MANAGER);

                break;
            }

            default:
                break;
        }

    }
}
