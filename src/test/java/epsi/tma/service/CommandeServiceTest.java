package epsi.tma.service;

import epsi.tma.dao.CommandeDAO;
import epsi.tma.dao.CommandeStatutLogDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandeServiceTest {
    //private ICommandeService commandeService;

    @Test
    public void simulateMagasinCommandeTest(){
        //CommandeStatutLogDAO commandeStatutLogDAO = mock(CommandeStatutLogDAO.class);
        CommandeStatutLogDAO commandeStatutLogDAO = spy(new CommandeStatutLogDAO());
        CommandeStatutLogService commandeStatutLogService = spy(new CommandeStatutLogService());
        //CommandeService commandeService = mock(CommandeService.class);
        CommandeService commandeService = new CommandeService();
        CommandeService cmdService = spy(new CommandeService());
        //CommandeDAO commandeDAO = mock(CommandeDAO.class);

        //String tst = commandeService.simulateMagasinCommande(1, 1, 1);

        //Configure Mock
        doReturn(1).when(cmdService).create(1,1,1,1);
        //doReturn(1).when(commandeDAO).create(1,1,1, 1);
        //when(commandeDAO.create(1,1,1, 1)).thenReturn(1);
        doReturn("create log entry successfully").when(commandeStatutLogService).create("Magasin_1"," create order 1",1, new Timestamp(2019),1,1,"CREATE");
        //when(commandeStatutLogDAO.create("Magasin_1","create",1, new Timestamp(2019-12-22),1,1,"test")).thenReturn("create log entry successfully");

        String expected = "SIMULATE SUCCESSFULLY";
        String actual = cmdService.simulateMagasinCommande(1, 1, 1);

        //when(commandeService.simulateMagasinCommande(1, 2, 3)).thenReturn("SIMULATE SUCCESSFULLY");

        assertEquals(expected, actual);
    }
}
