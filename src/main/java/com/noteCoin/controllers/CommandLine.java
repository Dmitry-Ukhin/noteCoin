package com.noteCoin.controllers;

import com.noteCoin.data.WorkWithMySQL;
import com.noteCoin.data.interfaces.WorkWithDB;
import com.noteCoin.models.Command;
import com.noteCoin.models.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Status:  0 - success;
 *          1 - type of command is null;
 *          2 - sum of transaction is null;
 *          3 - command is null
 *          100 - save command is fail;
 */
public class CommandLine extends HttpServlet{
    private Integer status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();
        Transaction transaction;
        PrintWriter writer = resp.getWriter();
        String descriptionOfTran;
        String paramCommand = req.getParameter("command");
        String paramType = req.getParameter("type");

        Command command = new Command(paramType, paramCommand);
        if (command.getCommand() == null){
            status = 3;
            writer.println(status);
            return;
        }

        if (command.getType() == null){
            command = new Command(getTypeOfCommand(command), command.getCommand());
            if (command.getType() == null){
                status = 1;
                writer.println(status);
                return;
            }
        }else if (command.getType().equals("undefined")){
            status = 1;
            writer.println(status);
            return;
        }

        if(command.getType().equals("income") || command.getType().equals("expense")){
            String sum = checkSum(command.commandToArray());
            if (sum == null){
                status = 2;
                writer.println(status);
                return;
            }
            descriptionOfTran = getDescriptionOfTransaction(command.getCommand());
            transaction = getTransaction(command.getType(), Integer.parseInt(sum), date, descriptionOfTran);
        }

        WorkWithDB dataBase = new WorkWithMySQL();
        status = dataBase.save(command);
        if (status == 0){
            status = 100;
        }else{
            status = 0;
        }
        writer.println(status);
        writer.close();
    }

    private String getDescriptionOfTransaction(String command) {

        return null;
    }

    private String checkSum(List<String> words) {
        for (String word : words){
            Pattern pattern = Pattern.compile(".?[0-9]+\\.[0-9]+.?");
            Matcher matcher = pattern.matcher(word);
            if (matcher.matches()){
                return word;
            }else{
                Pattern pattern1 = Pattern.compile(".?[0-9]+");
                matcher = pattern1.matcher(word);
                if (matcher.matches()) {
                    return word;
                }
            }
        }
        return null;
    }

    /**
     * TODO check by key words, because first word get type and maybe wrong. Example "I" get type income
     * @param command is command from request user
     * @return type(income, expense, show), null
     */
    private String getTypeOfCommand(Command command){
        Integer income = 0, expense = 0;
        List<String> listWords;

        listWords = command.commandToArray();

        WorkWithDB dataBaseKeyWords = new WorkWithMySQL();
        try {
            for (int i = 0; i < listWords.size(); i++) {
                String collocation = listWords.get(i) + " " + listWords.get(i + 1);
                System.out.println("DATA_STORE FIND:" + collocation);
                List resultList = dataBaseKeyWords.find(Command.class, "command", collocation);
                if (resultList != null) {
                    for (Object obj : resultList) {
                        if (((Command) obj).getType().equals("income")) {
                            income++;
                        } else if (((Command) obj).getType().equals("expense")) {
                            expense++;
                        }
                    }
                }
                dataBaseKeyWords.reloadConnectWithDB();
            }
        }catch (Exception ex){
            System.out.println("CommandLine120");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("INCOME:" + income + " EXPENSE:" + expense);
        if (income > expense) {
            return "income";
        }else if(expense < income){
            return "expense";
        }else{
            return null;
        }
    }

    private Transaction getTransaction(String type, Integer sum, Date date, String description){
        return new Transaction(type, sum, date, description);
    }
}
