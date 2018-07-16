package org.smart4j.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;
import org.smart4j.chapter2.service.CustomerServiceImpl;
import org.smart4j.chapter2.util.CollectionUtils;
import org.smart4j.chapter2.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private static final BasicDataSource DATA_SOURCE;

    static {

        Properties properties = PropsUtil.loadProps("test.config.properties");
        DRIVER = properties.getProperty(JDBC_DRIVER);
        URL = properties.getProperty(JDBC_URL);
        USERNAME = properties.getProperty(JDBC_USERNAME);
        PASSWORD = properties.getProperty(JDBC_PASSWORD);
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
        DATA_SOURCE.setDriverClassName(DRIVER);


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
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {

                CONNECTION_HOLDER.set(connection);
            }


        }
      //  System.out.println(connection);

        return connection;
    }

    public static void closeConnection() {

//        Connection connection = CONNECTION_HOLDER.get();
//        if (connection != null) {
//
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                CONNECTION_HOLDER.remove();
//            }
//
//        }


    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {

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

    public static <T> T queryEntity(Class<T> entityClass, Object... params) {

        T entity = null;
        String sql = "SELECT * FROM "+getTableName(entityClass)+" WHERE id=?";
        Connection connection = getConnection();

        try {
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<T>(entityClass), params);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return entity;

    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {

        List<Map<String, Object>> result = new ArrayList<>();
        Connection connection = getConnection();
        try {
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
    }
    public static void executeSqlFile(String file) throws IOException
    {



        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String sql;
        while ((sql = bufferedReader.readLine())!= null)
        {
            DatabaseHelper.executeUpdate(sql);


        }
    }
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        Connection connection = getConnection();
        try {
            rows = QUERY_RUNNER.update(connection, sql, params);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rows;

    }

    public static <T> String getTableName(Class<T> clazz) {
        return clazz.getSimpleName().toLowerCase();

    }

    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtils.isEmpty(fieldMap)) {

            return false;

        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder conlumns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");


        for (String fieldName : fieldMap.keySet()) {
            Object fieldValue = fieldMap.get(fieldName);
            conlumns.append(fieldName).append(",");
            values.append("?,");


        }
        conlumns.replace(conlumns.lastIndexOf(","), conlumns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += conlumns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql, params) == 1;


    }


    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtils.isEmpty(fieldMap)) {
            LOGGER.info("can not update entity,fieldMap is empty");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {

            columns.append(fieldName).append("=?, ");

        }
        sql +=columns.toString().substring(0,columns.lastIndexOf(","))+" WHERE id=?";
        List<Object>params = new ArrayList<>();
        params.addAll(fieldMap.values());
        params.add(id);
        Object[]paramsArray = params.toArray();

        return executeUpdate(sql, paramsArray) == 1;

    }

    public static <T> boolean deleteEntity(Class<T>entityClass,long id)
    {

        String sql = "DELETE FROM "+getTableName(entityClass)+" WHERE id=?";
        return executeUpdate(sql,id) == 1;


    }


}
