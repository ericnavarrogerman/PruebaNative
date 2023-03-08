package com.navadev.pruebanative.feature.add.repository.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.feature.add.repository.dao.IncidenteDao;
import com.navadev.pruebanative.feature.add.repository.dao.IncidenteDaoImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IncidenteDaoImplTest {

    private IncidenteDao dao;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dao = new IncidenteDaoImpl(context);
    }

    @After
    public void tearDown() {

        dao = null;
    }

    @Test
    public void testInsert() {
        dao.deleteAll();
        Incidente incidente = new Incidente();
        incidente.setFecha("2022-10-15");
        incidente.setUbicacion("Bogotá");
        incidente.setDescripcion("Incidente de prueba");
        incidente.setFoto("ruta/de/prueba/foto.jpg");

        boolean result = dao.insert(incidente);
        assertTrue(result);
    }


    @Test
    public void testGetAllWhenAddSomeIncident() {
        dao.deleteAll();
        List<Incidente> lista = null;
        for (int i = 0; i <= 10; i++) {
            Incidente incidente = new Incidente();
            incidente.setFecha("2022-10-15");
            incidente.setUbicacion("Bogotá");
            incidente.setDescripcion("Incidente de prueba");
            incidente.setFoto("ruta/de/prueba/foto.jpg");
            dao.insert(incidente);
        }
        lista = dao.getAll();
        assertTrue(lista.size() == 11);
    }

    @Test
    public void testGetAllWhenNoData() {
        dao.deleteAll();
        List<Incidente> lista = dao.getAll();

        assertTrue(lista.size()==0);
    }

    @Test
    public void testGetNextAvailableIdWhenNoData() {
        dao.deleteAll();
        int siguienteId = dao.getNextAvailableId();
        assertEquals(1, siguienteId);
    }



}
