package org.smart4j.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties pros = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null)
            {

                throw  new FileNotFoundException(fileName+" file not found!");

            }
            pros = new Properties();
            pros.load(is);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.info("load properties failed!",e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(is!=null)
            {

                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.info("close stream failed",e);
                    e.printStackTrace();
                }

            }

        }

        return  pros;
    }

    public static  String getString(Properties properties,String key)
    {

            return getString(properties,key,"");


    }

    private static String getString(Properties properties, String key, String s) {
            String value = s;
            if(properties.contains(key))
            {

                value = properties.getProperty(key);

            }
            return value;


    }

    public static int getInt(Properties properties,String key)
    {


        return getInt(properties,key,0);


    }

    private static int getInt(Properties properties, String key, int i) {

        int value = i;
        if(properties.contains(key))
        {

            value =  CastUtil.castInt(properties.getProperty(key));

        }
        return value;
    }

    public static boolean getBoolean(Properties properties,String key)
    {

        return getBoolean(properties,key,false);
        
    }

    private static boolean getBoolean(Properties properties, String key, boolean b) {

        boolean value = b;
        if(properties.contains(key))
        {

            value =  CastUtil.castBoolean(properties.getProperty(key));

        }
        return value;
    }


}
