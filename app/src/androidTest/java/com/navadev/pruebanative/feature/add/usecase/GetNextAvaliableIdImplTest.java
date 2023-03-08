package com.navadev.pruebanative.feature.add.usecase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navadev.pruebanative.feature.add.repository.IncidenteRepository;
import com.navadev.pruebanative.feature.add.usecase.GetNextAvailableId;
import com.navadev.pruebanative.feature.add.usecase.GetNextAvaliableIdImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetNextAvaliableIdImplTest {

    @Mock
    private IncidenteRepository mockRepositorio;

    private GetNextAvailableId useCase;

    @Before
    public void setUp() {
        useCase = new GetNextAvaliableIdImpl(mockRepositorio);
    }

    @Test
    public void testExecute() {
        int expectedId = 123;
        when(mockRepositorio.getNextAvailableId()).thenReturn(expectedId);

        int result = useCase.execute();

        assertEquals(expectedId, result);
        verify(mockRepositorio).getNextAvailableId();
    }
}
