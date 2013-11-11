package failureDetectionSystem;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class UDPClient 
{
	
	public static void main(String[] args)
	{
		
			int hostA_address = 1111;
			int hostB_address = 2222;
			DatagramSocket bSocket = null;
			DatagramPacket sendPacket = null;
			DatagramPacket receivePacket = null;
			byte[] req_buffer = null;
			byte[] res_buffer = null;
			try
			{
				String str = "Are u there?";
				InetAddress aHost = InetAddress.getByName("localhost");
				bSocket = new DatagramSocket();
				req_buffer = str.getBytes();
				
				sendPacket = new DatagramPacket(req_buffer, req_buffer.length, aHost, hostA_address);
				bSocket.send(sendPacket);
				
				sendPacket = new DatagramPacket(req_buffer, req_buffer.length, aHost, hostB_address);
				bSocket.send(sendPacket);
				
				res_buffer = new byte[5];
				receivePacket = new DatagramPacket(res_buffer, res_buffer.length);
				bSocket.receive(receivePacket);
				System.out.println(new String(receivePacket.getData()));
				
				res_buffer = null;
				receivePacket = new DatagramPacket(res_buffer, res_buffer.length);
				bSocket.receive(receivePacket);
				System.out.println(new String(receivePacket.getData()));

		}
		catch(Exception e)
		{
			
		}

	}

}
