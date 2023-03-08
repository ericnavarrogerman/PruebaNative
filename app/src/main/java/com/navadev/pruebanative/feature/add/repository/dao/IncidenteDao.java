package com.navadev.pruebanative.feature.add.repository.dao;

import com.navadev.pruebanative.feature.add.model.Incidente;

import java.util.List;

public interface IncidenteDao {


    boolean insert(Incidente entidad);

    boolean update(Incidente entidad);

    boolean delete(Incidente entidad);

    List<Incidente> getAll();

    int getNextAvailableId();

    boolean deleteAll();

}
