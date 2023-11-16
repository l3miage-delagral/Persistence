package fr.uga.miage.m1.commands;

public interface Command{


    // execute the command
    void execute();// implemented by children

    void undo();

}
