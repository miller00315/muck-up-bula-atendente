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
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.presenters.PersonalPerfilPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.PersonalPerfilTasks;
import br.com.miller.farmaciaatendente.utils.AlertDialogUtils;
import br.com.miller.farmaciaatendente.utils.ImageUtils;
import br.com.miller.farmaciaatendente.utils.tasks.AlertDialogUtilsTask;

public class PersonalPerfilFragment extends Fragment implements PersonalPerfilTasks.Presenter, AlertDialogUtilsTask.Presenter {

    private OnFragmentInteractionListener mListener;
    private PersonalPerfilPresenter personalPerfilPresenter;
    private User user;
    private ImageView imageViewUser, editImagePerfil;
    private TextView name, email, phone, address;
    private AlertDialogUtils alertDialogUtils;
    public static final int ID = 222;

    public PersonalPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) { user = getArguments().getParcelable("user"); }

        alertDialogUtils = new AlertDialogUtils(this);
        personalPerfilPresenter = new PersonalPerfilPresenter(this);

        alertDialogUtils.setContext(getContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        name = view.findViewById(R.id.name_perfil);
        email = view.findViewById(R.id.email_perfil);
        phone = view.findViewById(R.id.phone_perfil);
        address = view.findViewById(R.id.address_perfil);
        imageViewUser = view.findViewById(R.id.image_perfil);
        editImagePerfil = view.findViewById(R.id.edit_image_perfil);

        personalPerfilPresenter.getUserData(user.getId_firebase(), user.getCity());
        personalPerfilPresenter.downloadImage("users", user.getCity(), user.getId_firebase());

        bindViews();

        return view;
    }

    private void bindViews(){

        editImagePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putInt("type", 2);

                mListener.onFragmentInteraction(bundle);
            }
        });
    }

    public void setImageAlert(Intent intent){

        ViewGroup viewGroup = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);

        View view = getLayoutInflater().inflate(R.layout.layout_alert_image_view, viewGroup , false);

        ImageView imageView = view.findViewById(R.id.image_memory);

        ImageUtils.getImageFromMwmory(intent, imageView);

        alertDialogUtils.creatAlertImageView(view, 0);
    }

    private void setUserdata(User user){

        if(this.isVisible()) {
            name.setText(user.getName().concat(user.getSurname()));
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            address.setText(user.getAddress() != null ? user.getAddress().getAddress() : "");
        }
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

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { imageViewUser.setImageBitmap(bitmap); }

    @Override
    public void onImageUploadFailed() { }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { imageViewUser.setImageBitmap(bitmap); }

    @Override
    public void onImageDownloadFailed() { }

    @Override
    public void onUserDataSuccess(User user) {

        this.user = user;
        setUserdata(user);
    }

    @Override
    public void onUserDataFailed() { }

    @Override
    public void onUpdateUserSuccess(User user) {

        this.user = user;
        setUserdata(user);
    }

    @Override
    public void onUpdateUserFailed() { }

    @Override
    public void onAlertPositive(Object o, int type) {

        if(o instanceof Bitmap){ personalPerfilPresenter.uploadImage("users", user.getCity(), user.getId_firebase(), (Bitmap) o); }
    }

    @Override
    public void onALertNegative() { }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
