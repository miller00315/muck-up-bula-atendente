package br.com.miller.farmaciaatendente.saleManager.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.saleManager.adapters.recyclerAdapters.ManipulateBuyRecyclerAdapter;
import br.com.miller.farmaciaatendente.saleManager.presenters.ManipulateBuyPresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.ManipulateBuyTask;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;

public class ManipulateBuy extends AppCompatActivity implements
        ManipulateBuyTask.Presenter,
        RecyclerItem.OnAdapterInteract,
        EditTextDialogFragment.AlertOptionsResult {

    private Bundle bundle;
    private ManipulateBuyPresenter manipulateBuyPresenter;
    private TextView payMode, observationClient, addressClient, clientName, totalValue, phoneCliente, transshipment;
    private ManipulateBuyRecyclerAdapter manipulateBuyRecyclerAdapter;
    private RecyclerView recyclerViewManipulateBuy;
    private Button sendBuy, receiveBuy, cancelBuy;
    private ScrollView mainLayout;
    private RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_buy);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Pedido");

        manipulateBuyPresenter = new ManipulateBuyPresenter(this);

        manipulateBuyRecyclerAdapter = new ManipulateBuyRecyclerAdapter(this, this);

        bundle = getIntent().getBundleExtra("params");

        payMode = findViewById(R.id.pay_mode);
        clientName = findViewById(R.id.name_client);
        addressClient = findViewById(R.id.address_client);
        observationClient = findViewById(R.id.observation_client);
        recyclerViewManipulateBuy = findViewById(R.id.recycler_view_manipulate_buys);
        totalValue = findViewById(R.id.total_value);
        sendBuy = findViewById(R.id.button_sended);
        receiveBuy = findViewById(R.id.button_received);
        cancelBuy = findViewById(R.id.button_cancel);
        phoneCliente = findViewById(R.id.phone_client);
        loadingLayout = findViewById(R.id.layout_loading);
        mainLayout = findViewById(R.id.main_layout);
        transshipment = findViewById(R.id.transshipment);

        bindViews();
    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
    }


    private void bindViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerViewManipulateBuy.setLayoutManager(linearLayoutManager);

        recyclerViewManipulateBuy.setHasFixedSize(true);

        recyclerViewManipulateBuy.setAdapter(manipulateBuyRecyclerAdapter);

        showLoading();

        if (bundle != null)
            manipulateBuyPresenter.getBuy(bundle.getString("city"), bundle.getString("storeId"), bundle.getString("buyId"), bundle.getString("status"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.general_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.chat_icon: {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                bundle.putString("hint", "Fale conosco");

                EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);
                editTextDialogFragment.setListener(this);
                editTextDialogFragment.openDialog(getSupportFragmentManager());

                break;
            }

            case android.R.id.home: {

                finish();

                break;
            }

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBuyDataSuccess(Buy buy) {

        if (!this.isDestroyed()) {

            hideLoading();

            addressClient.setText(buy.getAddress().concat(", ").concat(buy.getUserCity()));
            clientName.setText(buy.getUserName());
            observationClient.setText(buy.getObservations() != null ? buy.getObservations() : "");
            totalValue.setText(StringUtils.doubleToMonetaryString(buy.getTotalValue()));
            phoneCliente.setText(buy.getUserPhone() != null ? buy.getUserPhone() : "");
            transshipment.setText(StringUtils.doubleToMonetaryString(buy.getTroco()));

            if (buy.getPayMode() == 1) {

                payMode.setText(getResources().getString(R.string.money));
                payMode.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_notes,0);

            } else if (buy.getPayMode() == 2) {

                payMode.setText(getResources().getString(R.string.card));

                switch (buy.getCardFlag()){
                    case 1:

                        payMode.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.master_card_logo,0);
                        break;

                    case(2):
                        payMode.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visa_logo,0);
                        break;

                    case 3:
                        payMode.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.elo_logo,0);
                        break;

                        default:
                            payMode.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_credit_card,0);
                            break;
                }
            }

            manipulateBuyRecyclerAdapter.setOffers(buy.getOffers());

            switch (buy.getStatus()) {

                case "news": {

                    sendBuy.setVisibility(View.VISIBLE);
                    cancelBuy.setVisibility(View.VISIBLE);
                    receiveBuy.setVisibility(View.VISIBLE);

                    break;
                }

                case "sended": {

                    sendBuy.setVisibility(View.INVISIBLE);
                    cancelBuy.setVisibility(View.VISIBLE);
                    receiveBuy.setVisibility(View.VISIBLE);

                    break;
                }

                case "received": {

                    sendBuy.setVisibility(View.INVISIBLE);
                    cancelBuy.setVisibility(View.INVISIBLE);
                    receiveBuy.setVisibility(View.INVISIBLE);

                    break;
                }

                case "canceled": {

                    sendBuy.setVisibility(View.INVISIBLE);
                    cancelBuy.setVisibility(View.INVISIBLE);
                    receiveBuy.setVisibility(View.INVISIBLE);

                    break;
                }

                default:
                    break;
            }

        }
    }

    @Override
    public void onBuyDataFailed() {

        hideLoading();
        Toast.makeText(this, "Erro ao obter dados de compra", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onChangeSucces(Buy buy) {

        hideLoading();
        Toast.makeText(this, "Compra atualizada com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onChangeFailed() {
        hideLoading();
        Toast.makeText(this, "Algum erro ocorreu, tente novamente", Toast.LENGTH_SHORT).show();
    }

    public void sendBuy(View view) {
        showLoading();
        manipulateBuyPresenter.sendBuyToSended();
    }

    public void cancelBuy(View view) {
        showLoading();
        manipulateBuyPresenter.sendBuyToCanceled();
    }

    @Override
    public void onAdapterInteract(Bundle bundle) {
    }

    public void receiveBuy(View view) {
        showLoading();

        manipulateBuyPresenter.sendBuyToReceived();
    }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) {

    }

    public void callClient(View view) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:".concat(phoneCliente.getText().toString())));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Não temos permissão para acessar o telefone", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(intent);
        }
    }

    public void goToMap(View view) {

        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", manipulateBuyPresenter.getBuy()
                        .getAddress()
                        .concat(", ")
                        .concat(manipulateBuyPresenter.getBuy().getUserCity()));

        Intent intent = new Intent(Intent.ACTION_VIEW, uriBuilder.build());

        if (intent.resolveActivity(this.getPackageManager()) != null) {
            this.startActivity(intent);
        }
    }
}
