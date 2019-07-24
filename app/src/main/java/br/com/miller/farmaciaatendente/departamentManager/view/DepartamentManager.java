package br.com.miller.farmaciaatendente.departamentManager.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.departamentManager.presenter.DepartamentManagerPresenter;
import br.com.miller.farmaciaatendente.departamentManager.tasks.DepartamentManagerTasks;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters.RecyclerAdapterOffers;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.alerts.AlertOptions;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;

public class DepartamentManager extends AppCompatActivity implements
        RecyclerItem.OnAdapterInteract,
        DepartamentManagerTasks.Presenter,
        AlertOptions.AlertOptionsResult,
        EditTextDialogFragment.AlertOptionsResult{

    private RecyclerView recyclerView;
    private RecyclerAdapterOffers recyclerAdapterOffers;
    private DepartamentManagerPresenter departamentManagerPresenter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departament_manager);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        bundle = getIntent().getBundleExtra("params");

        departamentManagerPresenter = new DepartamentManagerPresenter(this);
        recyclerAdapterOffers = new RecyclerAdapterOffers(this, getApplicationContext());
        recyclerView = findViewById(R.id.recycler_departament_manager);

        setResult(RESULT_CANCELED);

        bindView();
    }

    private void bindView(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(recyclerAdapterOffers);

        if(bundle != null)
            departamentManagerPresenter.getDepartament(bundle.getString("departamentId"), bundle.getString("storeId"), bundle.getString("city"));
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
    public void onAdapterInteract(Bundle bundle) {

      AlertOptions alertOptions = AlertOptions.newInstance(bundle);
      alertOptions.setListener(this);
      alertOptions.openDialog(getSupportFragmentManager());

    }


    @Override
    public void onDepartamentDataSuccess(Departament departament) {

        if(!this.isDestroyed()) {

            Objects.requireNonNull(getSupportActionBar()).setTitle(departament.getTitle());
            recyclerAdapterOffers.setOffers(departament.getOffers() != null ? departament.getOffers() : new ArrayList<Offer>());

        }
    }

    @Override
    public void onDepartamentDataFailed() {
        Toast.makeText(this, "Erro ao obter o departamento, tente novamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onMedicineDeleteSucces(Offer offer) {

        Toast.makeText(this, "O medicamento ".concat(offer.getTitle()).concat(" foi removido"), Toast.LENGTH_SHORT).show();

        departamentManagerPresenter.getDepartament(bundle.getString("departamentId"), bundle.getString("storeId"), bundle.getString("city"));

        setResult(RESULT_OK);

    }

    @Override
    public void onMedicineDeleteFailed() { Toast.makeText(this, "Erro ao excluir o medicamenot, tente novamente", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onDepublishSuccess(Offer offer) { Toast.makeText(this, "O produto foi removido das opções de busca", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onDepublishFailed() { Toast.makeText(this, "Erro ao remover produto das buscas, tente novemente", Toast.LENGTH_SHORT).show();}

    @Override
    public void onMedicinePublishFailed() { Toast.makeText(this, "Erro ao publicar produto, tente novamente", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onMedicinePublishSucces(Offer offer) { Toast.makeText(this, "Produto publicado com sucesso", Toast.LENGTH_SHORT).show();}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.MEDICINE_MANAGER){

            if(resultCode == RESULT_OK){

                setResult(resultCode);

                departamentManagerPresenter.getDepartament(bundle.getString("departamentId"), bundle.getString("storeId"), bundle.getString("city"));
            }
        }
    }

    public void addMedicine(View view) {

        Intent intent = new Intent(this, MedicineManager.class);
        intent.putExtra("params", bundle);
        startActivityForResult(intent, Constants.MEDICINE_MANAGER);
    }

    @Override
    public void onAlertOptionResult(Bundle bundle, int type) {

        switch (type){

            case 1:{

                  Intent intent = new Intent(this, MedicineManager.class);
                  intent.putExtra("params", bundle);
                  startActivityForResult(intent, Constants.MEDICINE_MANAGER);

                break;
            }

            case 2: {

                departamentManagerPresenter.deleteOffer(recyclerAdapterOffers.getOffer(bundle.getInt("adapterPosition")));

                break;
            }

            case 3:{

                departamentManagerPresenter.publishOffer(recyclerAdapterOffers.getOffer(bundle.getInt("adapterPosition")));

                break;
            }

            case 4: {

                departamentManagerPresenter.dePublishOffer(recyclerAdapterOffers.getOffer(bundle.getInt("adapterPosition")));

                break;

            }
        }
    }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) {

    }
}
