package ca.ualberta.cs.routinekeen.Exceptions;

/**
 * A class to represent the case when the user executes an action
 * that issues a request through the network to elastic search when the
 * user is not currently connected to any networks (Wifi or Mobile).
 *
 * @author Hugh Craig
 * @see ca.ualberta.cs.routinekeen.Controllers.IOManager
 * @version 1.0.0
 */
public class NetworkUnavailableException extends Exception {
    public NetworkUnavailableException(){}
    public NetworkUnavailableException(String exceptionMessage){
        super(exceptionMessage);
    }
}
