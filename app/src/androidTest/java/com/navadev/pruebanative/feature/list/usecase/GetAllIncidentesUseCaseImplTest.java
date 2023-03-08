package com.navadev.pruebanative.feature.list.usecase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navadev.pruebanative.core.repository.IncidenteRepository;
import com.navadev.pruebanative.feature.add.model.Incidente;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GetAllIncidentesUseCaseImplTest {

    @Mock
    private IncidenteRepository mockRepositorio;

    private GetAllIncidentesUseCase useCase;

    @Before
    public void setUp() {
        useCase = new GetAllIncidentesUseCaseImpl(mockRepositorio);
    }

    @Test
    public void testExecute() {

        List<Incidente> mockIncidentes = new ArrayList<>();
        mockIncidentes.add(new Incidente());
        mockIncidentes.add(new Incidente());
        mockIncidentes.add(new Incidente());
        when(mockRepositorio.getAll()).thenReturn(mockIncidentes);


        List<Incidente> result = useCase.execute();

        assertEquals(mockIncidentes, result);
        verify(mockRepositorio).getAll();
    }
}

