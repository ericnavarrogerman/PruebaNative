package com.navadev.pruebanative.feature.add.usecase;

import com.navadev.pruebanative.core.repository.IncidenteRepository;

public class GetNextAvaliableIdImpl implements GetNextAvailableId {


    private final IncidenteRepository repositorio;

    public GetNextAvaliableIdImpl(IncidenteRepository repositorio) {
        this.repositorio = repositorio;
    }


    @Override
    public int execute() {
        return repositorio.getNextAvailableId();
    }
}
