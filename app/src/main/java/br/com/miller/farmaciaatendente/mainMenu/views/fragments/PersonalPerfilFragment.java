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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.presenters.PersonalPerfilPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.PersonalPerfilTasks;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.alerts.EditTextDialogFragment;
import br.com.miller.farmaciaatendente.utils.alerts.ImageDialogFragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PersonalPerfilFragment extends Fragment implements PersonalPerfilTasks.Presenter,
        EditTextDialogFragment.AlertOptionsResult,
        ImageDialogFragment.ImageDialogFragmentListener {

    private OnFragmentInteractionListener mListener;
    private PersonalPerfilPresenter personalPerfilPresenter;
    private ImageView imageViewUser, editImagePerfil;
    private TextView name, email, phone, address;
    public static final int ID = 222;
    private RelativeLayout loadingLayout;
    private ScrollView mainLayout;

    public PersonalPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personalPerfilPresenter = new PersonalPerfilPresenter(this);
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
        loadingLayout = view.findViewById(R.id.layout_loading);
        mainLayout = view.findViewById(R.id.main_layout);

        if (getArguments() != null) {

            User user = getArguments().getParcelable("user");

            if(user != null) {
                personalPerfilPresenter.getUserData(user.getId_firebase(), user.getCity());
                personalPerfilPresenter.downloadImage("users", user.getCity(), user.getId_firebase());
                showLoading();
            }

        }

        bindViews();

        return view;
    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
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

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_double_edit_text_alert_fragment);

                bundle.putString("type", Constants.USER_NAME);

                bundle.putInt("firstInputType", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                bundle.putInt("secondInputType", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                bundle.putString("firstText", personalPerfilPresenter.getUser().getName());

                bundle.putString("secondText", personalPerfilPresenter.getUser().getSurname());

                bundle.putString("firstHint", "Nome");

                bundle.putString("secondHint", "Sobrenome");

                openAlert(bundle);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                bundle.putString("type", Constants.USER_EMAIL);

                bundle.putString("hint", "Email");

                bundle.putString("text", personalPerfilPresenter.getUser().getEmail());

                openAlert(bundle);

            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_CLASS_PHONE);

                bundle.putString("type", Constants.USER_PHONE);

                bundle.putString("hint", "Telefone");

                bundle.putString("text", personalPerfilPresenter.getUser().getPhone());

                openAlert(bundle);

            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);

                bundle.putString("type", Constants.USER_ADDRESS);

                bundle.putString("hint", "Endereço");

                bundle.putString("text", personalPerfilPresenter.getUser().getAddress() != null ? personalPerfilPresenter.getUser().getAddress().getAddress() : "");

                openAlert(bundle);

            }
        });
    }

    public void openAlert(Bundle bundle){

        EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);

        editTextDialogFragment.setListener(this);

        editTextDialogFragment.openDialog(getFragmentManager());
    }

    public void setImageAlert(Intent intent){

        Bundle bundle = new Bundle();

        ImageDialogFragment  imageDialogFragment = ImageDialogFragment.newInstance(bundle);

        imageDialogFragment.setListener(this, intent);

        imageDialogFragment.openDialog(getFragmentManager());

    }

    private void setUserdata(User user){

        hideLoading();

        if(this.isVisible()) {
            name.setText(user.getName().concat(" ").concat(user.getSurname()));
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
    public void onUserDataSuccess(User user) { setUserdata(user); }

    @Override
    public void onUserDataFailed() { }

    @Override
    public void onUpdateUserSuccess(User user) { setUserdata(user); }

    @Override
    public void onUpdateUserFailed() { }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) { personalPerfilPresenter.updateUser(bundle);}

    @Override
    public void onImageDialogFragmentListener(Bitmap bitmap, int result) {

        if(result == RESULT_OK)personalPerfilPresenter.uploadImage("users",
                personalPerfilPresenter.getUser().getCity(),
                personalPerfilPresenter.getUser().getId_firebase(),
                bitmap);

        else if(result == RESULT_CANCELED)
            Toast.makeText(getContext(), "Erro a obter a imagem, tente novamente", Toast.LENGTH_SHORT).show();

    }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
