package com.navadev.pruebanative.feature.list;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.navadev.pruebanative.R;
import com.navadev.pruebanative.databinding.FragmentListBinding;
import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.feature.list.adapter.IncidenteAdapter;
import com.navadev.pruebanative.feature.list.presenter.ListViewmodel;
import com.navadev.pruebanative.feature.list.presenter.ViewModelFactory;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private ListViewmodel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListBinding.inflate(inflater, container, false);


        ViewModelFactory factory = new ViewModelFactory( requireContext());
        viewModel = new ViewModelProvider(this, factory).get(ListViewmodel.class);


        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Cargando...");

        viewModel.statusView.observe(requireActivity(),status->{

            switch (status){

                case None:
                    progressDialog.dismiss();
                    break;

                case InProgress:
                    progressDialog.show();
                    break ;

                case ErrorToSave:
                    showError();
                    break;
            }

        });



        IncidenteAdapter adapter = new IncidenteAdapter();
        adapter.setListener(incidente -> {

        });

        viewModel.incidentes.observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });


        binding.lista.setAdapter(adapter);


        return binding.getRoot();

    }


    public void showError(){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("No se pudieron obtener los datos. Ocurrió un error inesperado.");
        builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        AlertDialog errorDialog = builder.create();


        errorDialog.show();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonAdd.setOnClickListener(view1 -> NavHostFragment.findNavController(ListFragment.this)
                .navigate(R.id.action_ListFragment_to_AddFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}