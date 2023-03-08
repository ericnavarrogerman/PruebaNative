package com.navadev.pruebanative.feature.list.presenter;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.navadev.pruebanative.core.repository.IncidenteRepository;
import com.navadev.pruebanative.core.repository.IncidenteRepositoryImpl;
import com.navadev.pruebanative.core.repository.dao.IncidenteDao;
import com.navadev.pruebanative.core.repository.dao.IncidenteDaoImpl;
import com.navadev.pruebanative.feature.list.usecase.GetAllIncidentesUseCaseImpl;

public class ViewModelFactory implements ViewModelProvider.Factory {

    IncidenteDao dao;

    public ViewModelFactory(Context context) {
        this.dao = new IncidenteDaoImpl(context);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewmodel.class)) {

            IncidenteRepository repository = new IncidenteRepositoryImpl(dao);
            GetAllIncidentesUseCaseImpl useCaseCreate = new GetAllIncidentesUseCaseImpl(repository);

            return (T) new ListViewmodel(useCaseCreate);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
