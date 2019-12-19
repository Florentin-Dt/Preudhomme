/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.database;

import epsi.tma.config.Property;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cDelage
 */
@Repository
public class DatabaseSpring {

    private static final Logger LOG = LogManager.getLogger(DatabaseSpring.class);

    @Autowired
    private DataSource dataSource;
    private boolean onTransaction = false;
    private Connection conn;

    /**
     * Create connection.
     * <p/>
     * If the connection doesn't exist, one will be created through the
     * DataSource object. If the connection exists, is reused without the
     * necessity of creating another. Then update the status.
     *
     * @return Connection Object with the connection created by DataSource
     * object.
     */
    public Connection connect() {
        try {
            if (onTransaction) { //if the connection is in a transaction, it will return the current connection
                return this.conn;
            }
            return this.dataSource.getConnection();
        } catch (SQLException exception) {
            LOG.warn("Cannot connect to datasource jdbc/ TMA" + System.getProperty(Property.ENVIRONMENT) + " : " + exception.toString());
        }

        return null;
    }

    public void closeConnection() {
        //if the connection is in a transaction, it will not be close, it 
        //will be closed when the user calls the endTransaction
        if (onTransaction) {
            try {
                //automatically commits the changes
                this.conn.commit();
            } catch (SQLException ex) {
                LOG.warn("Exception closing connection :" + ex);
            }
        }
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException ex) {
                LOG.warn("Can't end/close the connection to datasource jdbc/ tma" + System.getProperty(Property.ENVIRONMENT) + " : " + ex.toString());
            }
        }

    }

    public void beginTransaction() {
        onTransaction = true;
        try {
            this.conn = this.dataSource.getConnection();
            this.conn.setAutoCommit(false);
        } catch (SQLException exception) {
            LOG.warn("Cannot connect to datasource jdbc/tma" + System.getProperty(Property.ENVIRONMENT) + " : " + exception.toString());
        }
    }

    private void endTransaction(boolean success) {
        onTransaction = false;
        try {
            if (success) {
                this.conn.commit();
            } else {
                this.conn.rollback();
            }
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException ex) {
            LOG.warn("Can't end/close the connection to datasource jdbc/tma" + System.getProperty(Property.ENVIRONMENT) + " : " + ex.toString());
        }
    }

    public void commitTransaction() {
        endTransaction(true);

    }

    public void abortTransaction() {
        endTransaction(false);
    }

    public Connection connect(final String connection) {
        try {
            InitialContext ic = new InitialContext();
            String conName = "jdbc/" + connection;
            LOG.info("connecting to '" + conName + "'");
            DataSource ds = (DataSource) ic.lookup(conName);
            return ds.getConnection();
        } catch (SQLException ex) {
            LOG.warn(ex.toString());
        } catch (NamingException ex) {
            LOG.warn(ex.toString());
            InitialContext ic;
            try {
                ic = new InitialContext();
                String conName = "java:/comp/env/jdbc/" + connection;
                LOG.info("connecting to '" + conName + "'");
                DataSource ds = (DataSource) ic.lookup(conName);
                return ds.getConnection();
            } catch (NamingException ex1) {
                LOG.warn(ex.toString());
            } catch (SQLException ex1) {
                LOG.warn(ex.toString());
            }
        }
        return null;
    }

    public boolean isOnTransaction() {
        return onTransaction;
    }
}
