package com.navadev.pruebanative.feature.add.repository;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.feature.add.repository.dao.IncidenteDao;

import java.util.List;


public class IncidenteRepositoryImpl implements IncidenteRepository {

    private final IncidenteDao dao;

    public IncidenteRepositoryImpl(IncidenteDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(Incidente entidad) {
        return dao.insert(entidad);
    }
    @Override
    public boolean update(Incidente entidad) {
        return dao.update(entidad);
    }

    @Override
    public boolean delete(Incidente entidad) {
        return dao.delete(entidad);
    }

    @Override
    public List<Incidente> getAll() {
        return dao.getAll();
    }

    public int getNextAvailableId(){return dao.getNextAvailableId();}}

