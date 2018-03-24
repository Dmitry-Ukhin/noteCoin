package com.noteCoin.models;

import jdk.nashorn.internal.objects.annotations.Constructor;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "command")
    private String command;

    public Command() {}

    public Command(String type, String command) {
        if (command != null) {
//            if (command.contains(".")) {
//                command = command.replaceAll(". ", " ");
//                System.out.println("Command30");
//            } else if (command.contains(",")) {
//                command = command.replaceAll(", ", " ");
//                System.out.println("Command33");
//            }
            this.command = command;
        }else{
            System.out.println("command is null");
            this.command = null;
        }
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }

    public List<String> commandToArray(){
        return new ArrayList<String>(Arrays.asList(command.split(" ")));

    }
}
