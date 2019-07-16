package br.com.miller.farmaciaatendente.mainMenu.views.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.mainMenu.presenters.MainMenuPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.MainMenuTasks;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.DepartamentFragment;
import br.com.miller.farmaciaatendente.mainMenu.views.fragments.PerfilFragment;

public class MainMenuAtivity extends AppCompatActivity implements MainMenuTasks.Presenter,
        PerfilFragment.OnFragmentInteractionListener,
        DepartamentFragment.OnFragmentInteractionListener {

    private MainMenuPresenter mainMenuPresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_ativity);

        mainMenuPresenter = new MainMenuPresenter(this);

       BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }
}
