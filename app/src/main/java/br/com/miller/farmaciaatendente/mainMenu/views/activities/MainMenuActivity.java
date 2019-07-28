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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.departamentManager.view.DepartamentManager;
import br.com.miller.farmaciaatendente.departamentManager.view.MedicineManager;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.jobs.FirebaseJobs;
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
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;
import br.com.miller.farmaciaatendente.utils.alerts.SpinnerDialogFragment;

public class MainMenuActivity extends AppCompatActivity implements MainMenuTasks.Presenter,
        SolicitationFragment.OnFragmentInteractionListener,
        PersonalPerfilFragment.OnFragmentInteractionListener,
        DepartamentFragment.OnFragmentInteractionListener,
        StorePerfilFragment.OnFragmentInteractionListener,
        EditTextDialogFragment.AlertOptionsResult {

    private MainMenuPresenter mainMenuPresenter;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private FirebaseJobDispatcher dispatcher;

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

        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));

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

        startJob();
    }

    public void startJob(){

        Bundle bundle = new Bundle();

        bundle.putString("city", getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).getString(Constants.USER_CITY, ""));
        bundle.putString("storeId", getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).getString(Constants.STORE_ID, ""));

        Job job = dispatcher.newJobBuilder()
                .setService(FirebaseJobs.class)
                .setTag(FirebaseJobs.ID)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(false)
                .setExtras(bundle)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        dispatcher.mustSchedule(job);

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

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                bundle.putString("hint", "Fale conosco");

                EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);
                editTextDialogFragment.setListener(this);
                editTextDialogFragment.openDialog(getSupportFragmentManager());

            }

            case R.id.logout:{

                dispatcher.cancelAll();

                SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE).edit();

                editor.clear();

                editor.apply();

                FirebaseAuth.getInstance().signOut();

                finish();
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


    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) {

    }
}
