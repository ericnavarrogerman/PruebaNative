package com.navadev.pruebanative.core.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.navadev.pruebanative.R;
import com.navadev.pruebanative.feature.add.model.Incidente;

import java.io.File;

public class Utils {


    Activity context;
    private Dialog progressDialog;

    private Dialog customDialog;

    public Utils(Activity context) {
        this.context = context;
        configureProgress();
    }

    public void configureProgress(){
        progressDialog = new Dialog(context);
        customDialog = new Dialog(context);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void showProgressDialog(boolean isVisible){
    if (isVisible){
        progressDialog.show();
    }else {
        progressDialog.dismiss();
    }

    }


    public void showTextDialog(DialogModel model){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(model.title);
        builder.setMessage(model.description);

        if (model.btnAceptar != null) {
            builder.setPositiveButton(model.btnAceptar, (dialog, which) -> model.listener.onPositiveClick());
        }

        if (model.btnCancelar != null) {
            builder.setNegativeButton(model.btnCancelar, (dialog, which) -> model.listener.onNegativeClick());
        }

        if (model.btnNeutro != null) {
            builder.setNeutralButton(model.btnNeutro, (dialog, which) -> model.listener.onNeutroClick());
        }

        AlertDialog dialog = builder.create();

        if (model.listener==null){
            model.listener = new DefaultDialogListener(dialog);
            };

        dialog.show();

        }

        public void showImage(Incidente incidente){

            customDialog.setContentView(R.layout.custom_dialog_layout);
            customDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (context.getWindowManager().getDefaultDisplay().getHeight() * 0.8));

            ImageView ivImage = customDialog.findViewById(R.id.iv_image);

            ivImage.setImageURI(getImageFileUri(incidente.getFoto()));


            TextView tvTitle = customDialog.findViewById(R.id.tvTitle);
            tvTitle.setText("ID: "+incidente.getId());


            TextView tvDescription = customDialog.findViewById(R.id.tv_description);
            tvDescription.setText("Description: "+incidente.getDescripcion());

            customDialog.show();

        }

    public static Uri getImageFileUri(String currentPhotoPath) {
        File imageFile = new File( currentPhotoPath);
        Uri imageUri = Uri.fromFile(imageFile);
        return imageUri;
    }

}






class DefaultDialogListener implements DialogButtonClickListener{

    AlertDialog alertDialog;

    public DefaultDialogListener(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    @Override
    public void onPositiveClick() {
        alertDialog.dismiss();
    }

    @Override
    public void onNegativeClick() {
        alertDialog.dismiss();
    }

    @Override
    public void onNeutroClick() {
        alertDialog.dismiss();
    }
}