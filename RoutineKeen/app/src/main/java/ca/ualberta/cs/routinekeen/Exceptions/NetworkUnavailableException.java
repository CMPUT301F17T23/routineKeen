package ca.ualberta.cs.routinekeen.Exceptions;

/**
 * Created by hughc on 2017-11-21.
 */

public class NetworkUnavailableException extends Exception {
    public NetworkUnavailableException(){}
    public NetworkUnavailableException(String exceptionMessage){
        super(exceptionMessage);
    }
}
