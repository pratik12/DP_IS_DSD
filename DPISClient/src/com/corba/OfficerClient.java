package com.corba;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.ORB;

import FrontEnd.FrontEndInterface;
import FrontEnd.FrontEndInterfaceHelper;


public class OfficerClient extends Thread{

	private int testMode;
	private String badgeID;
	private String recordID;
	private String firstName;
	private String lastName;
	private String description;
	private String status;
	private String lastDate;
	private String lastLocation;
	private String address;
	private String remoteServerName;
	private static int userChoice;
	
	/**
	 * constructor for initializing new object
	 * @param testMode
	 * @param badgeID
	 * @param recordID
	 * @param firstName
	 * @param lastName
	 * @param description
	 * @param status
	 * @param lastDate
	 * @param lastLocation
	 * @param address
	 * @param remoteServerName
	 */
	public OfficerClient(int testMode, String badgeID, String recordID,
			String firstName, String lastName, String description,
			String status, String lastDate, String lastLocation, String address, String remoteServerName) {
		super();
		this.testMode = testMode;
		this.badgeID = badgeID;
		this.recordID = recordID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.status = status;
		this.lastDate = lastDate;
		this.lastLocation = lastLocation;
		this.address = address;
		this.remoteServerName =remoteServerName;
		start();
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			// display client menu
			showMenu();
			while(true){
				Boolean valid = false;
				
				// get the user choice
				while (!valid) {
					try {
						System.out.print("Enter your choice: ");
						userChoice = Integer.parseInt(br.readLine());
						valid = true;
					} catch (Exception e) {
						System.out.println("Invalid Input, please enter an integer: ");
						valid = false;
					}
				}
				
				// start the test cased based on user choice
				switch(userChoice){
					case 1:
						new OfficerClient(userChoice, "SPVM1111", "","JOHN", "DILINGER", "MURDER", "CAPTURED","","","","");
						new OfficerClient(userChoice, "SPVM1112", "","JOHN", "DILINGER", "MURDER", "CAPTURED","","","","");
						new OfficerClient(userChoice, "SPVM1113", "","JOHN", "DILINGER", "MURDER", "CAPTURED","","","","");
						break;
					case 2:
						new OfficerClient(userChoice, "SPVM1111", "","FERNANDO", "SUCRE", "", "FOUND","01/01/2009","QUEBEC","QUEBEC","");
						new OfficerClient(userChoice, "SPL1111", "","THEODARE", "BAGWELL", "", "MISSING","01/01/2009","QUEBEC","QUEBEC","");
						new OfficerClient(userChoice, "SPB1111", "","SARA", "TRENCREDI", "", "FOUND","01/01/2009","QUEBEC","QUEBEC","");
						break;
					case 3: 
						new OfficerClient(userChoice, "SPVM1111", "","", "", "", "","","","","");
						new OfficerClient(userChoice, "SPL1111", "","", "", "", "","","","","");
						new OfficerClient(userChoice, "SPB1111", "","", "", "", "","","","","");
						break;
					case 4:	
						new OfficerClient(userChoice, "SPVM1111", "CR11111","", "DILINGER", "", "ONTHERUN","","","","");
						new OfficerClient(userChoice, "SPL1111", "MR11111","", "BAGWELL", "", "MISSING","","","","");
						new OfficerClient(userChoice, "SPB1111", "MR11111","", "DARIK", "", "FOUND","","","","");
						break;
					case 5:
						new OfficerClient(userChoice, "SPVM1111", "CR11111", "", "", "", "", "", "", "","spl");
						break;
					case 6:
						new OfficerClient(userChoice, "SPVM2222", "","JOHN", "DILINGER", "MURDER", "CAPTURED","","","","");
						new OfficerClient(userChoice, "SPL2222", "","JAMES", "WHISTLER", "MURDER", "ONTHERUN","","","","");
						new OfficerClient(userChoice, "SPB2222", "","JOY", "DON", "MURDER", "CAPTURED","","","","");
						new OfficerClient(userChoice, "SPVM3333", "","ROBERT", "POLE", "", "MISSING","12/12/2011","MONTREAL","MAISONNEUVE","");
						new OfficerClient(userChoice, "SPL3333", "","FERNANDO", "SUCRE", "", "MISSING","01/01/2009","QUEBEC","QUEBEC","");
						new OfficerClient(userChoice, "SPB3333", "","MICHAEL", "SCOFIELD", "", "FOUND","12/02/2011","TORONTO","TORONTO","");
						new OfficerClient(userChoice, "SPVM4444", "","", "", "", "","","","","");
						new OfficerClient(userChoice, "SPL4444", "","", "", "", "","","","","");
						new OfficerClient(userChoice, "SPB4444", "","", "", "", "","","","","");
						new OfficerClient(userChoice, "SPVM5555", "CR11112","", "DILINGER", "", "ONTHERUN","","","","");
						new OfficerClient(userChoice, "SPL5555", "MR11112","", "SUCRE", "", "FOUND","","","","");
						new OfficerClient(userChoice, "SPB5555", "MR11112","", "SCOFIELD", "", "MISSING","","","","");
						new OfficerClient(userChoice, "SPL6666", "CR11111","", "", "", "","","","","SPVM");
						break;
					default:
						System.out.println("Invalid Input, please try again.");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@Override
	public void run() {
		try{
			String response ="";
			String stationName = badgeID.replaceAll("[0-9]", "").trim();
			FrontEndInterface serverObj;
			
			// get the remote object reference based on station name by using IOR contact details
			if ((serverObj = getRemoteObjectStub(stationName)) != null){ 
				
				// invoke remote methods using remote object reference based on user choice
				switch (testMode) {
					case 1:
						response = serverObj.executeOperation("CR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
						updateClientLog(badgeID,"Create Criminal Record", response);
						break;
					case 2:
						response = serverObj.executeOperation("MR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
						updateClientLog(badgeID,"Create Missing Record", response);
						break;
					case 3:
						response = serverObj.executeOperation("GR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
						updateClientLog(badgeID,"Get Record Count", response);
						break;
					case 4:
						response = serverObj.executeOperation("ER", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
						updateClientLog(badgeID,"Edit Record Status", response);
						break;
					case 5:
						response = serverObj.executeOperation("TR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
						updateClientLog(badgeID,"Edit Record Status", response);
						break;
					case 6:
						String bid = badgeID.replaceAll("[A-Z]", "");
						if(bid.equalsIgnoreCase("2222")){
							response = serverObj.executeOperation("CR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
							updateClientLog(badgeID,"Create Criminal Record", response);
						}else if(bid.equalsIgnoreCase("3333")){
							response = serverObj.executeOperation("MR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
							updateClientLog(badgeID,"Create Missing Record", response);
					    }else if(bid.equalsIgnoreCase("4444")){
					    	response = serverObj.executeOperation("GR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
							updateClientLog(badgeID,"Get Record Count", response);
					    }else if(bid.equalsIgnoreCase("5555")){
					    	response = serverObj.executeOperation("ER", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
							updateClientLog(badgeID,"Edit Record Status", response);
					    }else if(bid.equalsIgnoreCase("6666")){
					    	response = serverObj.executeOperation("TR", badgeID, firstName, lastName, description, status, address, lastDate, lastLocation, recordID, remoteServerName); 
							updateClientLog(badgeID,"Edit Record Status", response);
					    }	
						break;
					default:
						System.out.println("Invalid Input, please try again.");
					}
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * get the remote object reference
	 * @param stationName
	 * @return
	 * @throws IOException
	 */
	private static FrontEndInterface getRemoteObjectStub(String stationName) throws IOException {
		String[] args = null;
		
		// initialize ORB
		ORB orb = ORB.init(args, null);
		
		String property = System.getProperty("user.dir");
		property = property.substring(0, property.lastIndexOf(File.separator));
		
		// get the IOR string from contact file based on station 
		BufferedReader br = new BufferedReader(new FileReader(property+File.separator+"DPISFrontEnd"+File.separator+"FE_contact.txt"));
		String ior = br.readLine();
		br.close();
		
		// Get the CORBA object from IOR
		org.omg.CORBA.Object obj = orb.string_to_object(ior);
		
		// Convert CORBA object to JAVA object
		FrontEndInterface serverObj = FrontEndInterfaceHelper.narrow(obj);
		
		return serverObj;
	}
	
	
	/**
	 * method for displaying menu
	 */
	public static void showMenu() {
		System.out.println("\n****Welcome to DPIS****\n");
		System.out.println("Test cases(1-6)");
		System.out.println("1. Create Criminal Record");
		System.out.println("2. Create Missing Record");
		System.out.println("3. Get Records Count");
		System.out.println("4. Edit Record");
		System.out.println("5. Transfer Record");
		System.out.println("6. Test Concurrency");
	}
	
	/**
	 * method for logging the client operations and status of the performed operations
	 * @param badgeID
	 * @param operation
	 * @param status
	 */
	private synchronized void updateClientLog(String badgeID, String operation, String status) {
		File file;
		BufferedWriter bw =null;
		String header="";
		LineNumberReader lnr = null;
		int lineNumber = 0;
		try{
			file = new File(badgeID+"_log.txt");
			
			// check for the files exists, if not create new with header
			if(!file.exists()){
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getName());
				bw = new BufferedWriter(fw);
				header = "No.\t" + "OPERATION\t\t" + "TIME\t\t" + "STATUS";
				bw.write(header);
				bw.close();
			}
			
			// if exists, add rows to exising file
			FileWriter fw = new FileWriter(file.getName(), true);
			bw = new BufferedWriter(fw);
			lnr = new LineNumberReader(new FileReader(file));
			while (null != lnr.readLine())
				lineNumber = lnr.getLineNumber();
			lnr.close();

			bw.newLine();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
			Date date = new Date();
			
			// writing the performed operation to respective client file
			String data = lineNumber+"\t"+ operation+"\t"+dateFormat.format(date)+"\t"+status;
			bw.write(data);
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			if(bw != null)
				bw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
