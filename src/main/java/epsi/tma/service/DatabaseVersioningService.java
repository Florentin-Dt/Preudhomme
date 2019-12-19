/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.service;

import epsi.tma.config.Property;
import epsi.tma.entity.DatabaseVersioning;
import epsi.tma.dao.IDatabaseVersioningDAO;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cDelage
 */
@Service
public class DatabaseVersioningService implements IDatabaseVersioningService {

    private static final Logger LOG = LogManager.getLogger(DatabaseVersioningService.class);

    @Autowired
    IDatabaseVersioningDAO databaseversioningDAO;

    @Autowired
    ICommandeStatutLogService commandeStatutLogService;

    /*
     * update database version
     * call compare method and update new version of database
     * Versioning based on number of sql command in sql-list (this.getSQLScript -> List)
     * 
     */
    @Override
    public String updateDatabaseVersion() {
        LOG.info("DATABASE UPDATE START");
        String response = new String();
        DatabaseVersioning currentDatabaseVersion = findMyVersionByKey("DatabaseVersion");
        ArrayList<String> sqlList = this.getSQLScript();
        try {
            int i = 0;
            LOG.debug("IS UP TO DATE : " + this.isDatabaseUptodate());
            if (!this.isDatabaseUptodate()) {
                for (String sqlUpdate : sqlList) {
                    if (currentDatabaseVersion.getVersion() - 1 < i || currentDatabaseVersion.getVersion() == 0) {
                        this.updateSingleVersion(sqlList.get(i));
                    }
                    i++;
                }

                this.updateNumberVersion(sqlList.size());
                LOG.debug("DATABASE VERSION IS UPDATE FROM {} TO {}", currentDatabaseVersion.getVersion(), sqlList.size());
                response = "DATABASE VERSION IS UPDATE FROM " + currentDatabaseVersion.getVersion() + " TO " + sqlList.size();
            } else {
                response = "DATABASE ALREADY UPTODATE";
            }
        } catch (Exception exception) {
            LOG.error("Catch exception during database update : ", exception);
            response = "Catch exception during database update : " + exception;
        }

        return response;
    }

    /*
     * is database up to date
     * will compare back-end version to database stored version
     * @return true if database is up to date and false in reverse case
     */
    @Override
    public boolean isDatabaseUptodate() {
        // Getting the Full script to update the database.
        ArrayList<String> SQLList;
        SQLList = this.getSQLScript();
        // Get version from the database
        DatabaseVersioning curentDatabaseVersion = findMyVersionByKey("DatabaseVersion");
        if (curentDatabaseVersion != null) {
            // compare both to see if version is uptodate.
            if (SQLList.size() == curentDatabaseVersion.getVersion()) {
                return true;
            }
            LOG.info("Database needs an upgrade - Script : " + SQLList.size() + " Database : " + curentDatabaseVersion.getVersion());
        }
        return false;
    }

    @Override
    public DatabaseVersioning findMyVersionByKey(String key) {
        return databaseversioningDAO.findMyVersionByKey(key);
    }

    @Override
    public String updateSingleVersion(String SQLString) {
        return databaseversioningDAO.updateSingleVersion(SQLString);
    }

    @Override
    public void updateNumberVersion(Integer version) {
        this.databaseversioningDAO.updateNumberVersion(version);
    }

    /*
     * read multiple information of Database and Back
     * for database use this.readDatabaseInformation
     * for project information from maven use MavenXpp3Reader
     */
    @Override
    public Map<String, Object> readGenericInformation() {
        Map<String, Object> map = this.readDatabaseInformation();
        try {
            map.put("Project_version", Property.VERSION);
            map.put("Project_name", Property.NAME);
            map.put("Project_group_id", Property.GROUP_ID);
            map.put("Target_DB", this.getSQLScript().size());
        } catch (Exception e) {
            LOG.error("Failed to read mvn information, catch exception : ", e);
        }
        return map;
    }

    @Override
    public Map<String, Object> readDatabaseInformation() {
        return databaseversioningDAO.readDatabaseInformation();
    }

    @Override
    public ArrayList<String> getSQLScript() {
        // Temporary string that will store the SQL Command before putting in the array.
        StringBuilder b;
        // Full script that create the cerberus database.
        ArrayList<String> a;

        // Start to build the SQL Script here.
        a = new ArrayList();

        // ***********************************************
        // ***********************************************
        // SQL Script Instructions.
        // ***********************************************
        // ***********************************************
        // - Every Query must be independant.<ul>
        //    - Drop and Create index of the table / columns inside the same SQL
        //    - Drop and creation of Foreign Key inside the same SQL
        // - SQL must be fast (even on big tables)
        //    - 1 Index or Foreign Key at a time.
        //    - Beware of big tables that may result a timeout on the GUI side.
        // - Limit the number of SQL required in this class.
        //    - When inserting some data in table, group them inside the same SQL
        // - Never introduce an SQL between 2 SQL.
        //    - it messup the seq of SQL to execute in all users that moved to
        //      earlier version
        // - Only modify the SQL to fix an SQL issue but not to change a
        //   structure or enrich some data on an existing SQL. You need to
        //   create a new one to secure that it gets executed in all env.
        // ***********************************************
        // ***********************************************
        b = new StringBuilder();
        b.append("CREATE TABLE Magasin (");
        b.append("idMagasin INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("nomMagasin VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Magasin` (`nomMagasin`) VALUES ('magasin1');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Magasin` (`nomMagasin`) VALUES ('magasin2');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Magasin` (`nomMagasin`) VALUES ('magasin3');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE Produit (");
        b.append("idProduit INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("nomProduit VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Produit` (`nomProduit`) VALUES ('produit1');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Produit` (`nomProduit`) VALUES ('produit2');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Produit` (`nomProduit`) VALUES ('produit3');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE Entrepot (");
        b.append("idEntrepot INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("nomEntrepot VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Entrepot` (`nomEntrepot`) VALUES ('entrepot1');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE Etat (");
        b.append("idEtat INT PRIMARY KEY NOT NULL,");
        b.append("descriptionEtat VARCHAR(50)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (1, 'Demande de préparation');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (2, 'En cours de préparation');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (3, 'Commande finie');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("INSERT INTO `Etat` (`idEtat`,`descriptionEtat`) VALUES (4, 'Commande du jour');");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE Commande (");
        b.append("idCommande INT PRIMARY KEY NOT NULL,");
        b.append("idMagasin  INT NOT NULL,");
        b.append("idProduit  INT NOT NULL,");
        b.append("idEntrepot INT NOT NULL,");
        b.append("CONSTRAINT fk_idMagasin_magasin FOREIGN KEY (idMagasin) REFERENCES Magasin(idMagasin),");
        b.append("CONSTRAINT fk_idProduit_produit FOREIGN KEY (idProduit)  REFERENCES Produit(idProduit),");
        b.append("CONSTRAINT fk_idEntrepot_entrepot FOREIGN KEY (identrepot) REFERENCES Entrepot(idEntrepot)");
        b.append(");");
        a.add(b.toString());

        b = new StringBuilder();
        b.append("CREATE TABLE CommandeStatutLog (");
        b.append("idLog      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("idCommande INT             NOT NULL,");
        b.append("idEtat     INT             NOT NULL,");
        b.append("horodatage TIMESTAMP,");
        b.append("emmeteur   VARCHAR(50),");
        b.append("action     VARCHAR(50),");
        b.append("CONSTRAINT fk_idCommande_commande FOREIGN KEY (idCommande) REFERENCES Commande(idCommande),");
        b.append("CONSTRAINT fk_idEtat_etat         FOREIGN KEY (idEtat)     REFERENCES Etat(idEtat)");
        b.append(");");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("ALTER TABLE Commande ");
        b.append("ADD COLUMN idEtat INT NOT NULL,");
        b.append("ADD CONSTRAINT fk_idEtat_etatCmd FOREIGN KEY (idEtat) REFERENCES Etat(idEtat);");
        a.add(b.toString()); 

        b = new StringBuilder();
        b.append("ALTER TABLE CommandeStatutLog ");
        b.append("DROP FOREIGN KEY fk_idCommande_commande;");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("ALTER TABLE Commande ");
        b.append("MODIFY idCommande INT AUTO_INCREMENT;");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("ALTER TABLE CommandeStatutLog ");
        b.append("ADD CONSTRAINT fk_idCommande_commande FOREIGN KEY (idCommande) REFERENCES Commande(idCommande);");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("DROP TABLE CommandeStatutLog");
        a.add(b.toString());
        
        b = new StringBuilder();
        b.append("CREATE TABLE CommandeStatutLog (");
        b.append("idLog      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,");
        b.append("idCommande INT             NOT NULL,");
        b.append("idEtat     INT             NOT NULL,");
        b.append("horodatage TIMESTAMP,");
        b.append("emmeteur   VARCHAR(50),");
        b.append("action     VARCHAR(50),");
        b.append("type     VARCHAR(50)");
        b.append(");");
        a.add(b.toString());
        
        return a;
    }

}
