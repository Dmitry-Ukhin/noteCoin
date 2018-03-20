package com.noteCoin.controllers;

import com.noteCoin.data.WorkWithMySQL;
import com.noteCoin.data.interfaces.WorkWithDB;
import com.noteCoin.models.KeyWord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandLine extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        String typeCommand = getTypeOfCommand(command);
        System.out.println("COMMAND:" + command);


    }

    private String getTypeOfCommand(String command){
        command += " ";
        Integer start = 0, end = 0;
        List<String> listWords = new ArrayList<String>();

        for (;start < command.length();start = end + 1){
            if (end < command.length()) {
                end = command.indexOf(" ", start);
            }else{
                listWords.add(command.substring(start, command.length()));
            }
            listWords.add(command.substring(start, end));
        }

        WorkWithDB dataBaseKeyWords = new WorkWithMySQL();
        try {
            for (int i = 0; i < listWords.size(); i++){
                KeyWord keyWord = (KeyWord) dataBaseKeyWords.find(listWords.get(i));
                return keyWord.getKey();
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        System.out.println(listWords.toString());
        return null;
    }
}
