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
import com.navadev.pruebanative.core.utils.DialogModel;
import com.navadev.pruebanative.core.utils.Utils;
import com.navadev.pruebanative.databinding.FragmentListBinding;
import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.feature.list.adapter.IncidenteAdapter;
import com.navadev.pruebanative.feature.list.presenter.ListViewmodel;
import com.navadev.pruebanative.feature.list.presenter.ViewModelFactory;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private ListViewmodel viewModel;

    private Utils utils;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        utils = new Utils(requireActivity());

        ViewModelFactory factory = new ViewModelFactory( requireContext());
        viewModel = new ViewModelProvider(this, factory).get(ListViewmodel.class);


        viewModel.statusView.observe(requireActivity(),status->{

            switch (status){

                case None:
                    utils.showProgressDialog(false);
                    break;

                case InProgress:
                    utils.showProgressDialog(true);
                    break ;

                case ErrorToSave:
                    showError();
                    break;
            }

        });



        IncidenteAdapter adapter = new IncidenteAdapter();
        adapter.setListener(incidente -> {

            utils.showImage(incidente);
        });

        viewModel.incidentes.observe(getViewLifecycleOwner(), adapter::setData);


        binding.lista.setAdapter(adapter);


        return binding.getRoot();

    }


    public void showError(){
        DialogModel model = new DialogModel();
        model.title = "Error getting data";
        model.description = "An unexpected error occurred while getting the data";
        model.btnAceptar = "Closed";
        utils.showTextDialog(model);

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