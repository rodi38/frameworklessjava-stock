package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.UserDao;
import org.example.exception.GlobalExceptionHandler;
import org.example.service.UserService;
import org.example.util.propertie.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class Configuration {


    private static class ObjectMapperHolder {
        public static final ObjectMapper INSTANCE = new ObjectMapper();
    }

    private static class UserServiceHolder{

        public static final UserService INSTANCE = new UserService(getUserDao());
    }

    private static class UserDaoHolder {
        private static final Properties properties;

        static {
            try {
                properties = PropertiesLoader.loadProperties();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public static final UserDao INSTANCE = new UserDao(properties.getProperty("db.url"),  properties.getProperty("db.username"),  properties.getProperty("db.password"));

    }

    private static class GlobalExceptionHandlerHolder{
        public static final GlobalExceptionHandler INSTANCE = new GlobalExceptionHandler(getObjectMapper());
    }



    public static ObjectMapper getObjectMapper() {
        return ObjectMapperHolder.INSTANCE;
    }

    public static UserService getUserService() {
        return UserServiceHolder.INSTANCE;
    }

    public static UserDao getUserDao() {
        return UserDaoHolder.INSTANCE;
    }

    public static GlobalExceptionHandler getGlobalExceptionHandler() {
        return GlobalExceptionHandlerHolder.INSTANCE;
    }


}
