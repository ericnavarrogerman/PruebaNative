package com.navadev.pruebanative.feature.add.repository;

import com.navadev.pruebanative.feature.add.model.Incidente;

import java.util.List;

public interface IncidenteRepository  {

    List<Incidente> getAll();
    boolean insert(Incidente entidad);
    boolean update(Incidente entidad);
    boolean delete(Incidente entidad);
    int getNextAvailableId();
}

