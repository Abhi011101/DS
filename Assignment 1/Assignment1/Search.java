import java.rmi.Remote;
import java.rmi.RemoteException;

interface Search extends Remote{
    public String query(String search) throws RemoteException;
}