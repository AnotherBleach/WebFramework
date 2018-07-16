package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.service.CustomerService;
import org.smart4j.chapter2.service.CustomerServiceImpl;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DatabaseHelper {

    public static final String JDBC_DRIVER = "jdbc.driver";
    public static final String JDBC_URL = "jdbc.url";
    public static final String JDBC_USERNAME = "jdbc.username";
    public static final String JDBC_PASSWORD = "jdbc.password";
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    static {

        Properties properties = PropsUtil.loadProps("config.properties");
        DRIVER = properties.getProperty(JDBC_DRIVER);
        URL = properties.getProperty(JDBC_URL);
        USERNAME = properties.getProperty(JDBC_USERNAME);
        PASSWORD = properties.getProperty(JDBC_PASSWORD);


        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.info("Cannot load jdbc driver:" + DRIVER, e);
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           } catch (SQLException e) {
                e.printStackTrace();
            } finally {

                CONNECTION_HOLDER.set(connection);
            }


        }
        System.out.println(connection);

        return connection;
    }

    public static void closeConnection() {

        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                CONNECTION_HOLDER.remove();
            }

        }


    }

    public static <T> List<T> queryEntityList(Class<T> entityClass ,String sql, Object... params) {

        List<T> entitylist = null;
        Connection connection = getConnection();

        try {
            entitylist = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();

        }

        return entitylist;
    }


}
