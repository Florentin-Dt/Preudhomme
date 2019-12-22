package epsi.tma.service;

import epsi.tma.dao.CommandeDAO;
import epsi.tma.dao.CommandeStatutLogDAO;
import org.junit.Before;
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
        CommandeStatutLogDAO commandeStatutLogDAO = mock(CommandeStatutLogDAO.class);
        //CommandeService commandeService = mock(CommandeService.class);
        CommandeService commandeService = new CommandeService();
        CommandeDAO commandeDAO = mock(CommandeDAO.class);
        //String tst = commandeService.simulateMagasinCommande(1, 1, 1);

        when(commandeDAO.create(1,1,1, 1)).thenReturn(1);
        when(commandeStatutLogDAO.create("Magasin_1","create",1, new Timestamp(2019-12-22),1,1,"test")).thenReturn("create log entry successfully");
        //when(commandeService.simulateMagasinCommande(1, 2, 3)).thenReturn("SIMULATE SUCCESSFULLY");

        assertEquals("SIMULATE SUCCESSFULLY", commandeService.simulateMagasinCommande(1, 1, 1));
    }




}
