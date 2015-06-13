package hibernateUtil;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.util.HashMap;

public class ConnectDB {

    private static EntityManagerFactory emFactory;

    public static void conecta() {
        try{
            HashMap map = new HashMap();
            emFactory = Persistence.createEntityManagerFactory("persistencia", map);
        }catch (Throwable t){
            t.printStackTrace();
            throw new ExceptionInInitializerError(t);
        }
    }

    public static Session getInstance(){
        Session session = (SessionImpl) emFactory.createEntityManager().getDelegate();
        return session;
    }

    public static Connection getConnection(Session s){
        SessionImpl si = (SessionImpl) s;
        return si.connection();
    }

}
