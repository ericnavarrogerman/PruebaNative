package com.navadev.pruebanative.feature.list.usecase;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.core.repository.IncidenteRepository;

import java.util.List;

public class GetAllIncidentesUseCaseImpl implements GetAllIncidentesUseCase{

    private final IncidenteRepository repositorio;

    public GetAllIncidentesUseCaseImpl(IncidenteRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Incidente> execute() {
        return repositorio.getAll();
    }
}
