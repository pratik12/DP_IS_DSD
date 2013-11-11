package failureDetectionSystem;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer 
{

	public static void main(String[] args) 
	{
		DatagramSocket bSocket = null;
		DatagramSocket cSocket = null;
		try
		{
			bSocket = new DatagramSocket(1111);
			if(bSocket != null)
				System.out.println("Host 'A' UDP server is up and running");
			byte[] buffer = new byte[13];
			byte[] replybuffer = "yes".getBytes();
			while(true)
			{
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				bSocket.receive(request);
				System.out.println(new String(request.getData()));
				
				DatagramPacket response = new DatagramPacket(replybuffer, replybuffer.length,request.getAddress(), request.getPort());
				bSocket.send(response);
			}
		}
		catch(Exception e)
		{
			
		}

	}

}
