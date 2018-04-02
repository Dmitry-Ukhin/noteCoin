package com.noteCoin.controllers;

import com.noteCoin.data.UserDAOHibernate;
import com.noteCoin.data.dao.UserDAO;
import com.noteCoin.models.User;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authorization extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String pass = req.getParameter("key");

        HttpSession session = req.getSession();
        String sessionId = session.getId();

        Integer hashPass = pass.hashCode();

        Date date = new Date();

        Map<String, String> args = new HashMap<String, String>();
        args.put("name", name);
        args.put("pass", hashPass.toString());

        String query = "From Users ";
        query = query.concat("WHERE ");
        for (String key : args.keySet()) {
            query = query.concat(key + " LIKE \'" + args.get(key) + "%\' AND ");
        }
        Integer lastIndexOf = query.lastIndexOf("AND");
        if (lastIndexOf < query.length() - 3) {
            query = query.substring(0, lastIndexOf);
        }
        query = query.concat("ORDER BY date DESC");

        UserDAO userDAO = new UserDAOHibernate();
        List<User> userList = userDAO.getList(query);
        if (null != userList && userList.size() == 1){
            userList.get(0).setLastEnter(date);
            User update = userDAO.update(userList.get(0));
            if (null != update) {
                resp.getWriter().println("STAT: 200//" + sessionId);
            }else{
                resp.getWriter().println("STAT: 501//" + sessionId);
            }
        }else{
            resp.getWriter().println("STAT: 403//" + sessionId);
        }


    }
}
