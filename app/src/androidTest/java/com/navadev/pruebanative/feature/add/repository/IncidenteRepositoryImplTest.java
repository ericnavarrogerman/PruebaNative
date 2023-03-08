package com.navadev.pruebanative.feature.add.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.core.repository.IncidenteRepository;
import com.navadev.pruebanative.core.repository.IncidenteRepositoryImpl;
import com.navadev.pruebanative.core.repository.dao.IncidenteDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IncidenteRepositoryImplTest {

    @Mock
    private IncidenteDao mockDao;

    private IncidenteRepository repository;

    @Before
    public void setUp() {
        repository = new IncidenteRepositoryImpl(mockDao);
    }

    @Test
    public void testInsert() {
        Incidente entidad = new Incidente(/* datos de prueba */);
        when(mockDao.insert(entidad)).thenReturn(true);

        boolean resultado = repository.insert(entidad);

        assertTrue(resultado);
        verify(mockDao).insert(entidad);
    }

    @Test
    public void testUpdate() {
        Incidente entidad = new Incidente(/* datos de prueba */);
        when(mockDao.update(entidad)).thenReturn(true);

        boolean resultado = repository.update(entidad);

        assertTrue(resultado);
        verify(mockDao).update(entidad);
    }

    @Test
    public void testDelete() {
        Incidente entidad = new Incidente(/* datos de prueba */);
        when(mockDao.delete(entidad)).thenReturn(true);

        boolean resultado = repository.delete(entidad);

        assertTrue(resultado);
        verify(mockDao).delete(entidad);
    }

    @Test
    public void testGetAll() {
        List<Incidente> lista = new ArrayList<>();
        lista.add(new Incidente(/* datos de prueba */));
        when(mockDao.getAll()).thenReturn(lista);

        List<Incidente> resultado = repository.getAll();

        assertEquals(lista, resultado);
        verify(mockDao).getAll();
    }

    @Test
    public void testGetNextAvailableId() {
        int siguienteId = 1;
        when(mockDao.getNextAvailableId()).thenReturn(siguienteId);

        int resultado = repository.getNextAvailableId();

        assertEquals(siguienteId, resultado);
        verify(mockDao).getNextAvailableId();
    }
}

