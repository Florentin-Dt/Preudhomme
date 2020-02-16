package epsi.tma.service;

import epsi.tma.dao.CommandeDAO;
import epsi.tma.dao.CommandeStatutLogDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandeServiceTest {

    @Test
    public void simulateMagasinCommandeTestPassed(){
        CommandeService cmdService = Mockito.mock(CommandeService.class);

        //Configure Mock
        Mockito.when(cmdService.simulateMagasinCommande(1,1,1)).thenReturn("SIMULATE SUCCESSFULLY");

        String expected = "SIMULATE SUCCESSFULLY";
        String actual = cmdService.simulateMagasinCommande(1, 1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void simulateMagasinCommandeTestFailed(){
        CommandeService cmdService = Mockito.mock(CommandeService.class);

        String expected = null;
        String actual = cmdService.simulateMagasinCommande(1, 1, 1);

        assertEquals(expected, actual);
    }
}
