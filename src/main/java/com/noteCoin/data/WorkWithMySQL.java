/**
 * last change 20.03.18 01:52
 */
package com.noteCoin.data;

import com.noteCoin.data.interfaces.WorkWithDB;
import com.noteCoin.models.KeyWord;
import com.noteCoin.models.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

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
     * TODO: overload for upload list Transactions to DB
     * @param object
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
            }else if (object.getClass() == KeyWord.class){
                KeyWord keyWord = (KeyWord) object;
                em.persist(keyWord);//I save my model of KeyWord to db
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

    public Object find(String searchWord) {
        try{
            KeyWord keyWord = em.find(KeyWord.class, searchWord);
            if (keyWord != null){
                return keyWord;
            }else{
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
