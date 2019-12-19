package epsi.tma.dao;

import epsi.tma.database.DatabaseSpring;
import epsi.tma.entity.CommandeStatutLog;
import epsi.tma.factory.IFactoryCommandeStatutLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cDelage
 */
@Repository
public class CommandeStatutLogDAO implements ICommandeStatutLogDAO {

    private static final Logger LOG = LogManager.getLogger(CommandeStatutLogDAO.class);

    @Autowired
    private DatabaseSpring databaseSpring;

    @Autowired
    private IFactoryCommandeStatutLog factoryCommandeStatutLog;

    public List<CommandeStatutLog> read() {
        LOG.debug("READ - CommandStatutLogDAO");
        List<CommandeStatutLog> listeLog = new ArrayList();
        StringBuilder query = new StringBuilder();
        query.append("SELECT `idLog`,`idCommande`,`idEtat`,`horodatage`,`emmeteur`,`action`,`type` FROM CommandeStatutLog ORDER BY `idLog` DESC;");
        Connection connection = this.databaseSpring.connect();

        try {
            PreparedStatement prestat = connection.prepareStatement(query.toString());
            ResultSet resultSet = prestat.executeQuery();
            while (resultSet.next()) {
                listeLog.add(this.loadCommandSttLogFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            LOG.error("Fail log-list loading, exception : ", exception);
        }
        return listeLog;
    }

    @Override
    public CommandeStatutLog loadCommandSttLogFromResultSet(ResultSet rs) throws SQLException {
        Integer idLog = rs.getInt("idLog");
        Integer idEtat = rs.getInt("idEtat");
        Integer idCommande = rs.getInt("idCommande");
        Timestamp horodatage = rs.getTimestamp("horodatage");
        String emmeteur = rs.getString("emmeteur");
        String action = rs.getString("action");
        String type = rs.getString("type");

        return factoryCommandeStatutLog.create(idLog, idCommande, idEtat, horodatage, emmeteur, action, type);
    }

    @Override
    public String create(String emmeteur, String action, int idCommande, Timestamp horodatage, int idProduit, int idEtat, String type) {

        LOG.debug("CREATE - CommandeStatutLogDAO");
        String response = new String();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `CommandeStatutLog` (`idCommande`,`idEtat`,`horodatage`,`emmeteur`,`action`,`type`) VALUES (?, ?, ?, ?, ?, ?);");
        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            preStat.setInt(1, idCommande);
            preStat.setInt(2, idEtat);
            preStat.setTimestamp(3, horodatage);
            preStat.setString(4, emmeteur);
            preStat.setString(5, action);
            preStat.setString(6, type);

            preStat.executeUpdate();
            response = "create log entry successfully";
        } catch (SQLException exception) {
            LOG.error("Catch exception during create Commande status log :", exception);
            response = " Failed to create log entry, catch exception : " + exception.getMessage();
        }
        return response;
    }
}
