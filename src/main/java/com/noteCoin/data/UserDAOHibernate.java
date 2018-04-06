package com.noteCoin.data;

import com.noteCoin.data.dao.UserDAO;
import com.noteCoin.data.factories.ConnectionDataBase_Factory;
import com.noteCoin.models.Transaction;
import com.noteCoin.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserDAOHibernate implements UserDAO{

    public Boolean save(User user) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        Boolean status;

        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
            status = true;
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = false;
        }finally {
            connection.closeConnect();
        }
        return status;
    }

    public Boolean remove(User user) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        Boolean status;
        Long userId = user.getId();

        em.getTransaction().begin();
        try{
            Transaction tr = em.find(Transaction.class, userId);
            if (tr != null) {
                em.remove(tr);
                status = true;
            }else{
                status = false;
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = false;
        }finally {
            connection.closeConnect();
        }
        return status;
    }

    public User update(User user) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        try{
            return em.merge(user);
        }finally {
            connection.closeConnect();
        }
    }

    public List<User> getList(String strQuery) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        try {
//            Query objQuery = em.createQuery(strQuery);
            Query objQuery = em.createNativeQuery(strQuery);
            List list = objQuery.getResultList();
            List<User> userList = new ArrayList<User>();
            for (Object obj : list){
                User user = (User)obj;
                userList.add(user);
            }
            return userList;
        }catch (Exception ex){
            return null;
        }finally {
            connection.closeConnect();
        }
    }
}
