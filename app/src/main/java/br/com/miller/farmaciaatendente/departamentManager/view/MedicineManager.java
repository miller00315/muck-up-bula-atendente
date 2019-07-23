package br.com.miller.farmaciaatendente.departamentManager.view;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.departamentManager.helpers.AlertOptionsMedicineManager;
import br.com.miller.farmaciaatendente.departamentManager.presenter.MedicineManagerPresenter;
import br.com.miller.farmaciaatendente.departamentManager.tasks.MedicineManagerTasks;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.utils.ImageUtils;
import br.com.miller.farmaciaatendente.utils.MoneyTextWatcher;

public class MedicineManager extends AppCompatActivity implements MedicineManagerTasks.Presenter, AlertOptionsMedicineManager.AlertOptionsResult {

    private Bundle bundle;
    private MedicineManagerPresenter medicineManagerPresenter;
    private ImageView medicineImage;
    private EditText medicineName, medicineDescription, medicineIndication, medicineNoIndication, medicineValue, medicineActive;
    private boolean isNewMedicine = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_manager);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        medicineManagerPresenter = new MedicineManagerPresenter(this);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        bundle = getIntent().getBundleExtra("params");

        medicineImage = findViewById(R.id.medicine_image);
        medicineName = findViewById(R.id.medicine_name);
        medicineDescription = findViewById(R.id.medicine_description);
        medicineIndication = findViewById(R.id.medicine_indication);
        medicineNoIndication = findViewById(R.id.medicine_no_indication);
        medicineValue = findViewById(R.id.medicine_value);
        medicineActive = findViewById(R.id.medicine_active);

        bindView();

    }

    public void bindView(){


        medicineValue.addTextChangedListener(new MoneyTextWatcher(medicineValue, Locale.getDefault()));

        medicineManagerPresenter.setData(bundle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.general_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.chat_icon :{ break; }

            case android.R.id.home:{

                setResult(RESULT_CANCELED);
                finish();
                break;
            }

            default: break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMedicineDataSuccess(Offer offer) {

        isNewMedicine = false;

        setView(offer);

        medicineManagerPresenter.downloadImage("offers", offer.getCity(), offer.getImage());

    }

    void setView(Offer offer){

        medicineIndication.setText(offer.getIndication());
        medicineName.setText(offer.getTitle());
        medicineDescription.setText(offer.getDescription());
        medicineNoIndication.setText(offer.getNoIndication());
        medicineValue.setText(String.format(Locale.getDefault(),"%.2f", offer.getValue()));
        medicineActive.setText(offer.getActive());

        Objects.requireNonNull(getSupportActionBar()).setTitle(offer.getTitle());
    }

    @Override
    public void onMedicineDataFailed() {
        Toast.makeText(this, "Erro ao obter os dados sobre o produto", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdateMedicineSuccess(Offer offer) {
        isNewMedicine = false;
        this.setView(offer);
        medicineManagerPresenter.uploadImage("offers", offer.getCity(), offer.getImage(), ImageUtils.getImageUser(medicineImage));
    }

    @Override
    public void onUpdateMedicineFailed() {
        Toast.makeText(this, "Erro ao tentar atualizar o produto, tente novamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateDataFailed(int type) {

        switch (type){

            case 0:{

                break;
            }

            case 1: {

                break;
            }

            case 2:{

                break;
            }

            case 3: {

                break;
            }

            case 4:{

                break;
            }

            case 5: {

                break;
            }
            case 6:{

                break;
            }

            default:
                break;
        }
    }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) {
        medicineImage.setImageBitmap(bitmap);

        setResult(RESULT_OK);

        Toast.makeText(this, "Atualização ocorrida com sucesso",Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { medicineImage.setImageBitmap(bitmap); }

    @Override
    public void onPublishMedicineSuccess(Offer offer) { Toast.makeText( this, "Medicamento publicado com sucesso", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onPublishMedicineFailed() { Toast.makeText(this, "Erro ao publicar o medicamento, tente novament", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onImageDownloadFailed() { }

    @Override
    public void onImageUploadFailed() { }

    @Override
    public void newMedicine() { isNewMedicine = true; }

    public void save(View view) {

        AlertOptionsMedicineManager alertOptionsMedicineManager = AlertOptionsMedicineManager.newInstance(bundle);

        alertOptionsMedicineManager.setListener(this);

        alertOptionsMedicineManager.openDialog(getSupportFragmentManager());

    }

    @Override
    public void onAlertOptionResult(Bundle bundle, int type) {

        switch (type){

            case 1: {

                if(isNewMedicine) {
                    medicineManagerPresenter.createMedicine(

                            medicineName.getText().toString(),
                            medicineDescription.getText().toString(),
                            medicineIndication.getText().toString(),
                            medicineNoIndication.getText().toString(),
                            bundle,
                            medicineValue.getText().toString(),
                            medicineActive.getText().toString(),
                            false
                    );

                }else
                    medicineManagerPresenter.updateMedicine(
                            medicineName.getText().toString(),
                            medicineDescription.getText().toString(),
                            medicineIndication.getText().toString(),
                            medicineNoIndication.getText().toString(),
                            medicineValue.getText().toString(),
                            medicineActive.getText().toString(),
                            false
                    );

                break;
            }

            case 2: {

                if(isNewMedicine)
                    medicineManagerPresenter.createMedicine(
                            medicineName.getText().toString(),
                            medicineDescription.getText().toString(),
                            medicineIndication.getText().toString(),
                            medicineNoIndication.getText().toString(),
                            bundle,
                            medicineValue.getText().toString(),
                            medicineActive.getText().toString(),
                            true
                    );
                else
                    medicineManagerPresenter.updateMedicine(
                            medicineName.getText().toString(),
                            medicineDescription.getText().toString(),
                            medicineIndication.getText().toString(),
                            medicineNoIndication.getText().toString(),
                            medicineValue.getText().toString(),
                            medicineActive.getText().toString(),
                            true
                    );

                break;
            }
        }
    }
}
