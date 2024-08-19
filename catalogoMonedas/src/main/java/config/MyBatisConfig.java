package config;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConfig {

    private String resource = "mybatis-config.xml";

    public SqlSession getSession() {
        System.out.println("Ejecutando [getSession]...");

        SqlSession session = null;
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
            System.out.println("SqlSession creado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear el SqlSession: " + e.getMessage());
        }

        if (session == null) {
            System.out.println("SqlSession es null.");
        }

        return session;
    }
}
