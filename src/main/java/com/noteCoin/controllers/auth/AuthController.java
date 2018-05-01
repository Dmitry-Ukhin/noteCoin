package com.noteCoin.controllers.auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GoogleAuthHelper helper = new GoogleAuthHelper();

        HttpSession session = request.getSession();

        if (request.getParameter("code") == null
                || request.getParameter("state") == null) {

            /*
             * set the secure state token in session to be able to track what we sent to google
             */
            session = request.getSession();
            session.setAttribute("state", helper.getStateToken());

            /*
             * initial visit to the page
             */
            response.getWriter().println("<a href=\"" + helper.buildLoginURI() + "\">log in with google</a>");

        } else if (request.getParameter("code") != null && request.getParameter("state") != null
                && request.getParameter("state").equals(session.getAttribute("state"))) {

            System.out.println("CODE: " + request.getParameter("code"));
            System.out.println("STATE: " + request.getParameter("state"));
            session.removeAttribute("state");

            /*
             * Executes after google redirects to the callback url.
             * Please note that the state request parameter is for convenience to differentiate
             * between authentication methods (ex. facebook oauth, google oauth, twitter, in-house).
             *
             * GoogleAuthHelper()#getUserInfoJson(String) method returns a String containing
             * the json representation of the authenticated user's information.
             * At this point you should parse and persist the info.
             */

            response.getWriter().println(helper.getUserInfoJson(request.getParameter("code")));
        }
    }
}
