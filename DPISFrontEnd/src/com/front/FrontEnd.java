package com.front;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class FrontEnd {

	public static void main(String[] args) throws InvalidName, ServantAlreadyActive, WrongPolicy, ObjectNotActive, FileNotFoundException, AdapterInactive{
		
		org.omg.CORBA.ORB orb = ORB.init(args, null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		FrontEndInterfaceImpl frontEndInterfaceImpl = new FrontEndInterfaceImpl();
		byte[] id = rootPOA.activate_object(frontEndInterfaceImpl);
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
		
		String ior = orb.object_to_string(ref);
		System.out.println(ior);
		
		PrintWriter file = new PrintWriter("FE_contact.txt");
		file.println(ior);
		file.close();
		
		System.out.println("CORBA Front End ready...");
		
		rootPOA.the_POAManager().activate();
		orb.run();
		
	}
	
}
