package failureDetectionSystem;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient 
{
	
	public static void main(String[] args)
	{
		int hostB_address = 2222;
		int hostC_address = 3333;
		DatagramSocket aSocket = null;
		DatagramPacket sendPacket = null;
		DatagramPacket receivePacket = null;
		byte[] req_buffer = null;
		byte[] res_buffer = null;
		try
		{
			String str = "Are u there?";
			InetAddress aHost = InetAddress.getByName("localhost");
			aSocket = new DatagramSocket();
			req_buffer = str.getBytes();
			
			sendPacket = new DatagramPacket(req_buffer, req_buffer.length, aHost, hostB_address);
			aSocket.send(sendPacket);
			
			sendPacket = new DatagramPacket(req_buffer, req_buffer.length, aHost, hostC_address);
			aSocket.send(sendPacket);
			
			aSocket.setSoTimeout(300); //timeout in milliseconds
			
			try
			{
				res_buffer = new byte[5];
				receivePacket = new DatagramPacket(res_buffer, res_buffer.length);					aSocket.receive(receivePacket);
				System.out.println(new String(receivePacket.getData()));
				res_buffer = null;
		
				res_buffer = new byte[5];
				receivePacket = new DatagramPacket(res_buffer, res_buffer.length);
				aSocket.receive(receivePacket);
				System.out.println(new String(receivePacket.getData()));
			}
			catch(Exception e)
			{
				System.out.println("timeout reached");
				aSocket.close();
			}		
		}	
		catch(Exception e)
		{
			
		}

	}

}
