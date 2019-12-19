/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.dao;

import epsi.tma.database.DatabaseSpring;
import epsi.tma.entity.Commande;
import epsi.tma.factory.IFactoryCommande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author florentin
 */
@Repository
public class CommandeDAO implements ICommandeDAO {

    private static final Logger LOG = LogManager.getLogger(CommandeDAO.class);

    @Autowired
    private DatabaseSpring databaseSpring;

    @Autowired
    private IFactoryCommande factoryCommande;

    @Override
    public int create(int idProduit, int idMagasin, int idEntrepot, int idEtat) {
        int result = 0;
        final String query = "INSERT INTO Commande(idProduit, idMagasin, IdEntrepot, idEtat) VALUES(?, ?, ?, ?);";
        LOG.debug("CREATE COMMAND ENTRY - COMMANDDAO");
        try {
            Connection connection = this.databaseSpring.connect();
            try {
                PreparedStatement preStat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                try {
                    preStat.setInt(1, idProduit);
                    preStat.setInt(2, idMagasin);
                    preStat.setInt(3, idEntrepot);
                    preStat.setInt(4, idEtat);
                    preStat.execute();
                    ResultSet resultSet = preStat.getGeneratedKeys();
                    try {
                        if (resultSet.first()) {
                            LOG.debug("ID of new create magasin" + resultSet.getInt(1));
                            result = resultSet.getInt(1);
                        }
                    } catch (Exception e) {
                        LOG.debug("Exception catch :", e);
                    } finally {
                        resultSet.close();
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    preStat.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    LOG.warn(e.toString());
                }
            }
        } catch (Exception exception) {
            LOG.error("Failed to connect to database, catched Exception : ", exception);
        }
        return result;
    }

    @Override
    public String update(int newState, int idCommand) {
        String response = "UPDATE SUCCESSFULLY";
        final String query = "UPDATE Commande SET idEtat = ? WHERE idCommande = ?";
        try {
            Connection connection = this.databaseSpring.connect();
            try {
                PreparedStatement preStat = connection.prepareStatement(query);

                preStat.setInt(1, newState);
                preStat.setInt(2, idCommand);
                int updatedrow = preStat.executeUpdate();
                if (updatedrow == 0) {
                    response = ("0 row updated, idCommande or idEtat don't exist");
                }
            } catch (SQLException exception) {
                response = "SQL EXCEPTION DURING QUERY EXECUTING";
                LOG.warn("Unable to execute query : " + exception.toString());

            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    LOG.warn("Failed to close database connection, caused by exception : ", e.toString());
                }
            }
        } catch (Exception exception) {
            LOG.error("Failed to connect to database, catched Exception : ", exception);
        }
        return response;
    }

    @Override
    public String updateAll(int oldState, int newState) {
        String response = "UPDATE SUCCESSFULLY";
        final String query = "UPDATE Commande SET idEtat = ? WHERE idEtat = ?";
        try {
            Connection connection = this.databaseSpring.connect();
            try {
                PreparedStatement preStat = connection.prepareStatement(query);

                preStat.setInt(1, newState);
                preStat.setInt(2, oldState);
                int updatedrow = preStat.executeUpdate();
                if (updatedrow == 0) {
                    response = ("0 row updated, idEtat don't exist or no line needs update");
                }
            } catch (SQLException exception) {
                response = "SQL EXCEPTION DURING QUERY EXECUTING";
                LOG.warn("Unable to execute query : " + exception.toString());

            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    LOG.warn("Failed to close database connection, caused by exception : ", e.toString());
                }
            }
        } catch (Exception exception) {
            LOG.error("Failed to connect to database, catched Exception : ", exception);
        }
        return response;
    }

    @Override
    public List<Commande> read() {
        LOG.debug("READ - COMMANDE DAO");
        List<Commande> response = new ArrayList();
        StringBuilder query = new StringBuilder();
        query.append("SELECT `idCommande`,`idMagasin`,`idProduit`,`idEntrepot`,`idEtat` FROM Commande ORDER BY idEtat, IdMagasin;");
        try {
            Connection connection = this.databaseSpring.connect();
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            ResultSet resultSet = preStat.executeQuery();
            while (resultSet.next()) {
                response.add(this.loadFromCommandResultSet(resultSet));
            }
        } catch (Exception e) {
            LOG.debug("READ - COMMANDE DAO exception catch : ", e);
        }
        LOG.debug("READ LIST COMMAND : {} ", response.toString());
        for (Commande response1 : response) {
            LOG.debug("READ COMMAND NUMBER {}", response1.getIdCommande());
        }

        return response;
    }

    @Override
    public List<Commande> readByStatus(int status) {
        List<Commande> response = new ArrayList();
        StringBuilder query = new StringBuilder();
        LOG.debug("READ COMMAND FOR STATUS {}", status);
        query.append("SELECT `idCommande`,`idMagasin`,`idProduit`,`idEntrepot`,`idEtat` FROM Commande WHERE idEtat = ? ORDER BY IdMagasin;");
        try {
            Connection connection = this.databaseSpring.connect();
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            preStat.setInt(1, status);
            ResultSet rs = preStat.executeQuery();

            while (rs.next()) {
                response.add(loadFromCommandResultSet(rs));
            }
        } catch (SQLException e) {
            LOG.error("CATCH SQL EXCEPTION DURING EXECUTION :", e);
        } catch (Exception e) {
            LOG.error("CATCH EXCEPTION DURING EXECUTION :", e);

        }

        return response;
    }

    @Override
    public void clear() {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM Commande WHERE 1=1;");
        try {
            Connection connection = this.databaseSpring.connect();
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            preStat.execute();
            LOG.debug("CLEAR COMMAND SUCCESSFULL");
        } catch (SQLException exception) {
            LOG.error("CLEAR COMMAND FAIL, SQL exception : ", exception);
        } catch (Exception exception) {
            LOG.error("CLEAR COMMAND FAIL, exception : ", exception);
        }
    }

    @Override
    public void clearTodayStatus() {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM Commande WHERE idEtat=4;");
        try {
            Connection connection = this.databaseSpring.connect();
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            preStat.execute();
            LOG.debug("CLEAR TODAY COMMAND SUCCESSFULL");
        } catch (SQLException exception) {
            LOG.error("CLEAR COMMAND FAIL, SQL exception : ", exception);
        } catch (Exception exception) {
            LOG.error("CLEAR COMMAND FAIL, exception : ", exception);
        }
    }

    @Override
    public Commande loadFromCommandResultSet(ResultSet rs) throws SQLException {
        int idCommande = rs.getInt("idCommande");
        int idProduit = rs.getInt("idProduit");
        int idEtat = rs.getInt("idEtat");
        int idEntrepot = rs.getInt("idEntrepot");
        int idMagasin = rs.getInt("idMagasin");
        return factoryCommande.create(idCommande, idProduit, idMagasin, idEntrepot, idEtat);
    }
}
