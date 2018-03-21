package com.noteCoin.data;

import com.noteCoin.data.interfaces.WorkWithDB;
import com.noteCoin.models.Command;
import com.noteCoin.models.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkWithMySQL implements WorkWithDB {

    private EntityManagerFactory emf;
    private EntityManager em;

    public WorkWithMySQL() {
        emf = Persistence.createEntityManagerFactory("noteCoinDB");
        em = emf.createEntityManager();
    }

    public void reloadConnectWithDB() {
        emf = Persistence.createEntityManagerFactory("noteCoinDB");
        em = emf.createEntityManager();
    }

    /**
     * TODO: overload for upload list Transactions to DB, to do for Object
     * @param object object that you save
     * @return '0' save is fail and '1' save is success
     */
    public Integer save(Object object) {
        Integer status;

        em.getTransaction().begin();
        try {
            if (object.getClass() == Transaction.class) {
                Transaction transaction = (Transaction) object;
                em.persist(transaction);//I save my model of Transaction to db
                em.getTransaction().commit();
                status = 1;
            }else if(object.getClass() == Command.class) {
                Command command = (Command) object;
                em.persist(command);//I save my model of Command to db
                em.getTransaction().commit();
                status = 1;
            }else{
                status = 0;
            }
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = 0;
        }finally {
            em.close();
            emf.close();
        }
        return status;
    }

    public List load(String requestToDB) {
        try {
            Query query = em.createQuery(requestToDB);
            List list = query.getResultList();
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            em.close();
            emf.close();
        }
    }

    /**
     * @param nameClass class of Object
     * @param key key of value
     * @param value search by value of key
     * @return object from DB
     */
    public List find(Class nameClass, String key, String value) {
        String requestToDB = "FROM " + nameClass.getName() + " WHERE " + key +
                " LIKE \'%" + value + "%\'";
        try{
            System.out.println("REQUEST TO STORE:" + requestToDB);
            Query query = em.createQuery(requestToDB);
            List resultList = query.getResultList();
            if (resultList != null){
                System.out.println("WorkWithMySQL88");
                return resultList;
            }else{
                System.out.println("WorkWithMySQL91");
                return null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            em.close();
            emf.close();
        }
        return null;
    }

    public Integer remove(Object object) {
        Integer status;

        em.getTransaction().begin();
        try{
            Transaction transaction = (Transaction)object;
            Long transactionId = transaction.getId();
            Transaction tr = em.find(Transaction.class, transactionId);
            if (tr != null) {
                em.remove(tr);
                status = 1;
            }else{
                status = 0;
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = 0;
        }finally {
            em.close();
            emf.close();
        }
        return status;
    }
}
