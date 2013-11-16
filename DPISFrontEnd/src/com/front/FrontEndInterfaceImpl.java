package com.front;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import FrontEnd.FrontEndInterfacePOA;

public class FrontEndInterfaceImpl extends FrontEndInterfacePOA {

	private static Long sequence = 1L;
	private static Map<Long, String> currentExecutingRequests = new HashMap<Long, String>();
	private DatagramSocket socket = null;
	private Object lock = new Object();

	@Override
	public String executeOperation(String method, String badgeId,
			String firstName, String lastName, String description,
			String status, String address, String lastDate,
			String lastLocation, String recordId, String remoteServerName) {
		System.out.println("method;" + method + ",badgeId:" + badgeId
				+ ",firstName;" + firstName + ",lastName;" + lastName
				+ ",description;" + description + ",status;" + status
				+ ",address;" + address + ",lastDate;" + lastDate
				+ ",lastLocation;" + lastLocation + ",recordId;" + recordId
				+ ",remoteServerName;" + remoteServerName);
		String[] response = null;
		String leaderInfo = getLeaderInfo();
				synchronized (lock) {
					try {
						String currentRequest = constructRequest(method, badgeId, firstName, lastName, description,
								status, address, lastDate, lastLocation, recordId,
								remoteServerName);
						String[] split = leaderInfo.split(":");
						socket = new DatagramSocket();
						InetAddress host = InetAddress.getByName(split[0]);
						byte[] msg = currentRequest.getBytes();
						DatagramPacket request = new DatagramPacket(msg,
								currentRequest.length(), host,
								Integer.parseInt(split[1]));
						socket.send(request);
						System.out.println(sequence + "------" + currentRequest);
						currentExecutingRequests.put(sequence, currentRequest);
		
						byte[] buffer = new byte[100];
						DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
						socket.receive(reply);
		
						String result = new String(reply.getData());
						response = result.split(":");
						currentExecutingRequests.remove(response[0]);
		
					} catch (SocketException s) {
						System.out.println("Socket: " + s.getMessage());
					} catch (Exception e) {
						System.out.println("IO: " + e.getMessage());
					} finally {
						if (socket != null) {
							socket.close();
						}
					}
		}
		return response[1];
	}

	private String getLeaderInfo() {
		String info = "";
		String property = System.getProperty("user.dir");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(property + File.separator
					+ "leader.txt"));
			info = br.readLine();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return info;
	}

	private synchronized String constructRequest(String method, String badgeId,
			String firstName, String lastName, String description,
			String status, String address, String lastDate,
			String lastLocation, String recordId, String remoteServerName) {
		StringBuffer request = new StringBuffer();
		request.append(sequence).append(":").append(method).append(":")
				.append(badgeId).append(":");
		if ("CR".equalsIgnoreCase(method)) {
			request.append(firstName).append(":").append(lastName).append(":")
					.append(description).append(":").append(status);
		} else if ("MR".equalsIgnoreCase(method)) {
			request.append(firstName).append(":").append(lastName).append(":")
					.append(address).append(":").append(lastDate).append(":")
					.append(lastLocation).append(":").append(status);
		} else if ("ER".equalsIgnoreCase(method)) {
			request.append(lastName).append(":").append(recordId).append(":")
					.append(status);
		} else if ("TR".equalsIgnoreCase(method)) {
			request.append(recordId).append(":").append(remoteServerName);
		}
		sequence++;
		return request.toString();
	}

}
