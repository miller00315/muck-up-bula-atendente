package br.com.miller.farmaciaatendente.departamentManager.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.utils.alerts.AlertOptionsMedicineManager;
import br.com.miller.farmaciaatendente.departamentManager.presenter.MedicineManagerPresenter;
import br.com.miller.farmaciaatendente.departamentManager.tasks.MedicineManagerTasks;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;
import br.com.miller.farmaciaatendente.utils.alerts.ImageDialogFragment;
import br.com.miller.farmaciaatendente.utils.images.ImageUtils;
import br.com.miller.farmaciaatendente.utils.MoneyTextWatcher;

public class MedicineManager extends AppCompatActivity implements
        MedicineManagerTasks.Presenter,
        AlertOptionsMedicineManager.AlertOptionsResult,
        ImageDialogFragment.ImageDialogFragmentListener,
        EditTextDialogFragment.AlertOptionsResult{

    private Bundle bundle;
    private MedicineManagerPresenter medicineManagerPresenter;
    private ImageView medicineImage;
    private EditText medicineName, medicineDescription, medicineIndication, medicineNoIndication, medicineValue, medicineActive;
    private boolean isNewMedicine = false;
    private RelativeLayout layoutLoading;
    private ScrollView mainLayout;

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

        layoutLoading = findViewById(R.id.layout_loading);
        mainLayout = findViewById(R.id.main_layout);

        showLoading();

        medicineManagerPresenter.setData(bundle);

    }

    private void showLoading(){
        layoutLoading.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
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

        hideLoading();

        setView(offer);

        medicineManagerPresenter.downloadImage("offers", offer.getCity(), offer.getImage());

    }

    void setView(Offer offer){

        hideLoading();

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
        hideLoading();
        Toast.makeText(this, "Erro ao obter os dados sobre o produto", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdateMedicineSuccess(Offer offer) {
        hideLoading();
        isNewMedicine = false;
        this.setView(offer);
        medicineManagerPresenter.uploadImage("offers", offer.getCity(), offer.getImage(), ImageUtils.getImageFormImageView(medicineImage));
    }

    @Override
    public void onUpdateMedicineFailed() {
        hideLoading();
        Toast.makeText(this, "Erro ao tentar atualizar o produto, tente novamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateDataFailed(int type) {

        hideLoading();

        Toast.makeText(this, "Verifique os dados", Toast.LENGTH_SHORT).show();

        switch (type){

            case 0:{
                Toast.makeText(this, "Erro ao tratar dados, tente novamente", Toast.LENGTH_SHORT).show();
                break;
            }

            case 1: {
                medicineName.setHintTextColor(getResources().getColor(R.color.colorRed));
                break;
            }

            case 2:{
                medicineDescription.setHintTextColor(getResources().getColor(R.color.colorRed));
                break;
            }

            case 3: {
                medicineIndication.setHintTextColor(getResources().getColor(R.color.colorRed));
                break;
            }

            case 4:{
                medicineNoIndication.setHintTextColor(getResources().getColor(R.color.colorRed));
                break;
            }

            case 5: {
                medicineValue.setHintTextColor(getResources().getColor(R.color.colorRed));
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
    public void onPublishMedicineSuccess(Offer offer) {
        hideLoading();
        Toast.makeText( this, "Medicamento publicado com sucesso", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onPublishMedicineFailed() {
        hideLoading();
        Toast.makeText(this, "Erro ao publicar o medicamento, tente novament", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onImageDownloadFailed() { }

    @Override
    public void onImageUploadFailed() { }

    @Override
    public void newMedicine() {
        hideLoading();
        isNewMedicine = true;
    }

    public void save(View view) {

        AlertOptionsMedicineManager alertOptionsMedicineManager = AlertOptionsMedicineManager.newInstance(bundle);

        alertOptionsMedicineManager.setListener(this);

        alertOptionsMedicineManager.openDialog(getSupportFragmentManager());

    }

    @Override
    public void onAlertOptionResult(Bundle bundle, int type) {

        showLoading();

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

    public void getImage(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, Constants.INTERNAL_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.INTERNAL_IMAGE){

            if(resultCode == RESULT_OK){

                if(data != null){

                    Bundle bundle = new Bundle();

                    ImageDialogFragment imageDialogFragment = ImageDialogFragment.newInstance(bundle);

                    imageDialogFragment.setListener(this, data);

                    imageDialogFragment.openDialog(getSupportFragmentManager());
                }
            }
        }

    }

    @Override
    public void onImageDialogFragmentListener(Bitmap bitmap, int result) {

        if(result == RESULT_OK) medicineImage.setImageBitmap(bitmap);

        else if(result == RESULT_CANCELED) Toast.makeText(this, "Erro a obter a imagem, tente novamente", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) {

    }
}
