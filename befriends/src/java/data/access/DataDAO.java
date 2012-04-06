/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.access;

import data.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * General class for data access object
 */
public class DataDAO {
    protected static void freeDbResouce(PreparedStatement ps, Statement st,
            Connection c, ConnectionPool pool) {
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        if (st != null) 
            try {
                st.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        
        pool.freeConnection(c);
    }
}
