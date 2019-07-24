package br.com.miller.farmaciaatendente.mainMenu.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Store;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.presenters.StorePerfilPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.StorePerfilTasks;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;
import br.com.miller.farmaciaatendente.utils.alerts.ImageDialogFragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class StorePerfilFragment extends Fragment implements StorePerfilTasks.Presenter,
        EditTextDialogFragment.AlertOptionsResult,
        ImageDialogFragment.ImageDialogFragmentListener {

    private OnFragmentInteractionListener mListener;
    private StorePerfilPresenter storePerfilPresenter;
    private User user;
    private TextView storeName, storeDescription, storeCity, storeTime, storeClassification,storeSendValue;
    private ImageView imageStore, editImageStorePerfil;
    public static final int ID = 221;

    public StorePerfilFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            user = getArguments().getParcelable("user");
        }

        storePerfilPresenter = new StorePerfilPresenter(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_perfil, container, false);

        storeName = view.findViewById(R.id.store_name);
        storeCity = view.findViewById(R.id.store_city);
        storeDescription = view.findViewById(R.id.store_description);
        storeTime = view.findViewById(R.id.store_time);
        imageStore = view.findViewById(R.id.image_store);
        storeClassification = view.findViewById(R.id.store_classification);
        ScrollView scrollView = view.findViewById(R.id.main_layout);
        storeSendValue = view.findViewById(R.id.store_send_value);
        editImageStorePerfil = view.findViewById(R.id.edit_image_store_perfil);

        bindViews();

        if(user.getStoreId() != null) {

            if(!user.getStoreId().isEmpty()) {
                storePerfilPresenter.getStore(user.getStoreId(), user.getCity());
                scrollView.setVisibility(View.VISIBLE);
            }else{
                scrollView.setVisibility(View.INVISIBLE);
            }
        }else{
            scrollView.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private void bindViews(){

        editImageStorePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("type", 3);

                mListener.onFragmentInteraction(bundle);

            }
        });

        storeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                bundle.putString("text", storePerfilPresenter.getStore().getName());

                bundle.putString("type", Constants.STORE_NAME);

                bundle.putString("hint", "Nome da loja");

                openAlert(bundle);

            }
        });

        storeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                bundle.putString("hint", "Descrição");

                bundle.putString("type", Constants.STORE_SESCRIPTION);

                bundle.putString("text", storePerfilPresenter.getStore().getDescription());

                openAlert(bundle);

            }
        });

        storeSendValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_CLASS_NUMBER);

                bundle.putString("hint", "Valor de envio");

                bundle.putBoolean("isMoney", true);

                bundle.putString("type", Constants.STORE_SEND_VALUE);

                bundle.putString("text", String.valueOf(storePerfilPresenter.getStore().getSendValue()));

                openAlert(bundle);

            }
        });

        storeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_CLASS_TEXT);

                bundle.putString("hint", "Horário de funcionamento");

                bundle.putString("type", Constants.STORE_TIME);

                bundle.putString("text", storePerfilPresenter.getStore().getTime());

                openAlert(bundle);

            }
        });
    }


    public void openAlert(Bundle bundle){

        EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);

        editTextDialogFragment.setListener(this);

        editTextDialogFragment.openDialog(getFragmentManager());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setStoreData(Store store){

        if(this.isVisible()){

            storeCity.setText(store.getCity());
            storeTime.setText(store.getTime());
            storeDescription.setText(store.getDescription());
            storeName.setText(store.getName());
            storeSendValue.setText(StringUtils.doubleToMonetaryString(store.getSendValue()));
            storeClassification.setText(String.valueOf(store.getClassification()));

            storePerfilPresenter.downloadImage("stores", store.getCity(), store.getImage());
        }
    }

    public void setImageAlert(Intent intent){

        Bundle bundle = new Bundle();

        ImageDialogFragment  imageDialogFragment = ImageDialogFragment.newInstance(bundle);

        imageDialogFragment.setListener(this, intent);

        imageDialogFragment.openDialog(getFragmentManager());
    }


    @Override
    public void onStoreDataSuccess(Store store) { setStoreData(store);}

    @Override
    public void onStoreDataFailed() { Toast.makeText(getContext(), "Você não possui lojas vinculadas", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { imageStore.setImageBitmap(bitmap); }

    @Override
    public void onImageDownloadFailed() { }

    @Override
    public void onUploadImageSuccess(Bitmap bitmap) { imageStore.setImageBitmap(bitmap); }

    @Override
    public void onUploadImageFailed() { }

    @Override
    public void onUpdateStoreSuccess(Store store) { setStoreData(store);}

    @Override
    public void onUpdateStoreFailed() { }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) { storePerfilPresenter.updateStore(bundle);}

    @Override
    public void onImageDialogFragmentListener(Bitmap bitmap, int result) {

        if(result == RESULT_OK)
            storePerfilPresenter.uploadImage("stores", storePerfilPresenter.getStore().getCity(), storePerfilPresenter.getStore().getImage(), bitmap);

        else if(result == RESULT_CANCELED)
            Toast.makeText(getContext(), "Erro a obter a imagem, tente novament", Toast.LENGTH_SHORT).show();

    }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
