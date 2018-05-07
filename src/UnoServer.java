import java.rmi.Naming;
import java.rmi.RemoteException;

class UnoServer {

	

	public static void main (String[] args) {

		if	(args.length != 1) {
			System.err.println("UnoServer <server host>\n  ERRO: Nome de dominio ou IP nao fornecido!");
			System.exit(1);
		}

		System.setProperty("java.rmi.server.hostname",args[0]);
		System.setProperty("java.security.policy","UnoServer.policy");
		
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready.");			
		} catch (RemoteException e) {
			System.out.println("RMI registry already running.");			
		}

		try {
			Naming.rebind ("Uno", new UnoImpl ("Uno, woadjaodjaod asda da d adrld!"));
			System.out.println ("UnoServer is ready.");
		} catch (Exception e) {
			System.out.println ("UnoServer failed:");
			e.printStackTrace();
		}



	}
}

