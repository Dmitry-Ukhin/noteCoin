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
        Integer int_hashPass = pass.hashCode();
        String str_hashPass = int_hashPass.toString();

        HttpSession session = req.getSession();
        String sessionId = session.getId();

        Date date = new Date();

        String query = "Select * From Users WHERE name=\'" + name + "\' AND pass=" + str_hashPass;

        UserDAO userDAO = new UserDAOHibernate();
        List<User> userList = userDAO.getList(query);
        if (null != userList && userList.size() == 1){
            userList.get(0).setLastEnter(date);
            User update = userDAO.update(userList.get(0));
            if (null != update) {
                resp.getWriter().println("STAT: 200");
            }else{
                resp.getWriter().println("STAT: 501");
            }
        }else{
            Integer size;
            if (userList != null){
                size = userList.size();
            }else{
                size = null;
            }

            resp.getWriter().println("STAT: 403//" + size);
        }


    }
}
