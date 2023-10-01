package Exceptions;

public class CreationException extends Exception {
    public CreationException(){
        super("This name is already used.");
    }
    
}
