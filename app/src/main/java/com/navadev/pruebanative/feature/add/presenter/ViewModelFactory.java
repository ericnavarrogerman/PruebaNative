package com.navadev.pruebanative.feature.add.presenter;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.navadev.pruebanative.feature.add.repository.IncidenteRepository;
import com.navadev.pruebanative.feature.add.repository.IncidenteRepositoryImpl;
import com.navadev.pruebanative.feature.add.repository.dao.IncidenteDao;
import com.navadev.pruebanative.feature.add.repository.dao.IncidenteDaoImpl;
import com.navadev.pruebanative.feature.add.usecase.CrearIncidenteUseCase;
import com.navadev.pruebanative.feature.add.usecase.CrearIncidenteUseCaseImpl;
import com.navadev.pruebanative.feature.add.usecase.GetNextAvailableId;
import com.navadev.pruebanative.feature.add.usecase.GetNextAvaliableIdImpl;

public class ViewModelFactory implements ViewModelProvider.Factory {

   IncidenteDao dao;

    public ViewModelFactory(Context context) {
       this.dao = new IncidenteDaoImpl(context);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddViewModel.class)) {

            IncidenteRepository repository = new IncidenteRepositoryImpl(dao);
            CrearIncidenteUseCase useCaseCreate = new CrearIncidenteUseCaseImpl(repository);
            GetNextAvailableId  getNextAvailableId = new GetNextAvaliableIdImpl(repository);

            return (T) new AddViewModel(useCaseCreate,getNextAvailableId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
