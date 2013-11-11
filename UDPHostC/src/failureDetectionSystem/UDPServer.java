package failureDetectionSystem;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer 
{

	public static void main(String[] args) 
	{
		DatagramSocket cSocket = null;
		try
		{
			cSocket = new DatagramSocket(3333);
			if(cSocket != null)
				System.out.println("Host 'C' is up and running");
			byte[] buffer = new byte[13];
			byte[] replybuffer = "yes".getBytes();
			while(true)
			{
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				cSocket.receive(request);
				System.out.println(new String(request.getData()));
				
				DatagramPacket response = new DatagramPacket(replybuffer, replybuffer.length,request.getAddress(), request.getPort());
				cSocket.send(response);
			}
		}
		catch(Exception e)
		{
			
		}

	}

}
