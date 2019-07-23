package br.com.miller.farmaciaatendente.mainMenu.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Store;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.presenters.StorePerfilPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.StorePerfilTasks;
import br.com.miller.farmaciaatendente.utils.AlertDialogUtils;
import br.com.miller.farmaciaatendente.utils.ImageUtils;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.tasks.AlertDialogUtilsTask;

public class StorePerfilFragment extends Fragment implements StorePerfilTasks.Presenter, AlertDialogUtilsTask.Presenter {
    private OnFragmentInteractionListener mListener;
    private StorePerfilPresenter storePerfilPresenter;
    private User user;
    private TextView storeName, storeDescription, storeCity, storeTime, storeClassification,storeSendValue;
    private ImageView imageStore;
    private ScrollView scrollView;
    private AlertDialogUtils alertDialogUtils;
    private Store store;
    public static final int ID = 221;

    public StorePerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            user = getArguments().getParcelable("user");
        }

        storePerfilPresenter = new StorePerfilPresenter(this);
        alertDialogUtils = new AlertDialogUtils(this);

        alertDialogUtils.setContext(getContext());
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
        scrollView = view.findViewById(R.id.main_layout);
        storeSendValue = view.findViewById(R.id.store_send_value);

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

            this.store = store;

            storePerfilPresenter.downloadImage("stores", store.getCity(), store.getImage());
        }
    }

    public void setImageAlert(Intent intent){

        ViewGroup viewGroup = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);

        View view = getLayoutInflater().inflate(R.layout.layout_alert_image_view, viewGroup , false);

        ImageView imageView = view.findViewById(R.id.image_memory);

        ImageUtils.getImageFromMwmory(intent, imageView);

        alertDialogUtils.creatAlertImageView(view, 0);
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
    public void onAlertPositive(Object o, int type) {
        if(o instanceof Bitmap){ storePerfilPresenter.uploadImage("stores", store.getCity(), store.getImage(), (Bitmap) o ); }
    }

    @Override
    public void onALertNegative() {

    }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
