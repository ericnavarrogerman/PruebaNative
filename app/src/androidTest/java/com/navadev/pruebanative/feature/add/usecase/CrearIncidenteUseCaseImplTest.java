package com.navadev.pruebanative.feature.add.usecase;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.core.repository.IncidenteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CrearIncidenteUseCaseImplTest {

    @Mock
    private IncidenteRepository mockRepositorio;

    private CrearIncidenteUseCase useCase;

    @Before
    public void setUp() {
        useCase = new CrearIncidenteUseCaseImpl(mockRepositorio);
    }

    @Test
    public void testExecute() {
        Incidente incidente = new Incidente(/* datos de prueba */);
        when(mockRepositorio.insert(incidente)).thenReturn(true);

        boolean resultado = useCase.execute(incidente);

        assertTrue(resultado);
        verify(mockRepositorio).insert(incidente);
    }
}
