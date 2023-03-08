package com.navadev.pruebanative.feature.add.usecase;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.core.repository.IncidenteRepository;

public class CrearIncidenteUseCaseImpl implements CrearIncidenteUseCase {

    private final IncidenteRepository repositorio;

    public CrearIncidenteUseCaseImpl(IncidenteRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public boolean execute(Incidente incidente) {

        return repositorio.insert(incidente);
    }
}

