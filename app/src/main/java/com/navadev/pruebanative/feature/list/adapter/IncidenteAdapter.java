package com.navadev.pruebanative.feature.list.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navadev.pruebanative.R;
import com.navadev.pruebanative.feature.add.model.Incidente;

import java.io.File;
import java.util.List;

public class IncidenteAdapter extends RecyclerView.Adapter<IncidenteAdapter.IncidenteViewHolder> {


    List<Incidente> incidenteList;

    IncidenteAdapter.OnItemCLick listener;


    @NonNull
    @Override
    public IncidenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incidente, parent, false);
        return new IncidenteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidenteViewHolder holder, int position) {

        holder.bind(incidenteList.get(position),listener);

    }

    @Override
    public int getItemCount() {
        if (incidenteList==null){
           return  0;
        }else{
          return   incidenteList.size();
        }
    }


    public void setData(List<Incidente> incidenteList){
        this.incidenteList = incidenteList;
        notifyDataSetChanged();

    }

    public void setListener(IncidenteAdapter.OnItemCLick listener){
        this.listener = listener;
    }



    public static class IncidenteViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textFecha;
        private TextView textViewCiudad;
        private TextView textDescripcion;

        private TextView textId;

        public IncidenteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textFecha = itemView.findViewById(R.id.textFecha);
            textViewCiudad = itemView.findViewById(R.id.textViewCiudad);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            textId = itemView.findViewById(R.id.textViewId);
        }

        public void bind(final Incidente incidente, final OnItemCLick listener) {
            textFecha.setText(incidente.getFecha());
            textViewCiudad.setText(incidente.getUbicacion());
            textDescripcion.setText(incidente.getDescripcion());
            textId.setText("Id: "+incidente.getId());
            imageView.setImageURI(getImageFileUri(incidente.getFoto()));


            itemView.setOnClickListener(v -> listener.onItemClick(incidente));
        }
    }


    private static Uri getImageFileUri(String currentPhotoPath) {
        File imageFile = new File( currentPhotoPath);
        Uri imageUri = Uri.fromFile(imageFile);
        return imageUri;
    }


    public interface OnItemCLick{

        void onItemClick(Incidente incidente);

    }


}
