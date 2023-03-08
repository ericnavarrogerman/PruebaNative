package com.navadev.pruebanative.feature.add;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.navadev.pruebanative.R;
import com.navadev.pruebanative.core.utils.DialogButtonClickListener;
import com.navadev.pruebanative.core.utils.DialogModel;
import com.navadev.pruebanative.core.utils.Utils;
import com.navadev.pruebanative.databinding.FragmentAddBinding;
import com.navadev.pruebanative.feature.add.presenter.AddViewModel;
import com.navadev.pruebanative.feature.add.presenter.ViewModelFactory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private AddViewModel viewModel;

    private String currentPhotoPath;

    private Utils utils;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddBinding.inflate(inflater, container, false);
        utils = new Utils(requireActivity());

        ViewModelFactory factory = new ViewModelFactory( requireContext());
        viewModel = new ViewModelProvider(this, factory).get(AddViewModel.class);
        binding.setViewModel(viewModel);





        viewModel.statusView.observe(requireActivity(),status->{

            switch (status){

                case None:
                    utils.showProgressDialog(false);
                break;

                case InProgress:
                    utils.showProgressDialog(true);
                break ;

                case TakeAPhoto:
                    dispatchTakePictureIntent();
                    break;

                case ImagePresent:
                    showImagePresentDialog();
                    break;

                case DialogNoDescriptionValid:
                    showErrorDialog(false);
                    break;

                case DialogNoPhotoPresent:
                    showErrorDialog(true);
                    break;
                case ErrorToSave:
                    showSaveError();
                    break;

                case GoList:
                    goBack();
                    break;

            }


        });


        return binding.getRoot();


    }

    private void showErrorDialog(boolean isPhotoMissing) {
        String errorMessage = "";
        if (isPhotoMissing) {
            errorMessage = "The photo is missing.";
        }else{
            errorMessage = "The description is missing.";
        }

        DialogModel model = new DialogModel();
        model.title = "There is an error in the fields.";
        model.description = errorMessage ;
        model.btnAceptar = "OK";
        utils.showTextDialog(model);
    }

    private void showSaveError(){
        DialogModel model = new DialogModel();
        model.title = "Error while saving";
        model.description = "An unexpected error occurred and could not be saved";
        model.btnAceptar = "Closed";
        utils.showTextDialog(model);

    }

    private void showImagePresentDialog() {

        DialogModel model = new DialogModel();
        model.title = "Image present";
        model.description = "An image has already been taken. Do you want to take a new one?";
        model.btnAceptar = "Take new photo";
        model.btnCancelar = "Delete";
        model.btnNeutro = "Cancel";
        model.listener = new DialogButtonClickListener() {
            @Override
            public void onPositiveClick() {
                dispatchTakePictureIntent();
            }

            @Override
            public void onNegativeClick() {
                deleteImageFile();
            }

            @Override
            public void onNeutroClick() {

            }
        };
        utils.showTextDialog(model);

    }



    private ActivityResultLauncher<Uri> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicture(), result -> {
                if (result) {

                    viewModel.setPhoto(currentPhotoPath);
                } else {
                    showSaveError();
                }
            });


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.navadev.pruebanative.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureLauncher.launch(photoURI);
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void deleteImageFile() {
        File imageFile = new File(currentPhotoPath);
        if (imageFile.exists()) {
            imageFile.delete();
            viewModel.setPhoto(null);
        }
    }





    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    public void goBack(){
        NavHostFragment.findNavController(AddFragment.this).navigate(R.id.action_AddFragment_to_ListFragment);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}