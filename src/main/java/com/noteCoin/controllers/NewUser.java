package com.noteCoin.controllers;

import com.noteCoin.data.dao.UserDAO;
import com.noteCoin.data.UserDAOHibernate;
import com.noteCoin.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class NewUser extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String pass = req.getParameter("key");
        Date date = new Date();

        HttpSession session = req.getSession();
        String sessionId = session.getId();

        User newUser = new User(name, pass);
        newUser.setLastEnter(date);

        UserDAO userDAO = new UserDAOHibernate();
        if (userDAO.save(newUser)){
            resp.getWriter().println("success//" + sessionId);
        }else{
            resp.getWriter().println("Fail, try again//" + sessionId);
        }
    }
}
