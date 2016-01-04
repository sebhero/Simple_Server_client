package com.seb.upg1.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Upg1_Client
{

	private DatagramSocket socketClient;
	private InetAddress reciver;
	private final int port;

	/***
	 * Handles on the client side the communication with the server connects to
	 * port 10001 and server address is localhost sets a timeout of 10001 ms
	 */
	public Upg1_Client()
	{
		final String reciverName = "localhost";
		port = 10001;

		try
		{
			reciver = InetAddress.getByName(reciverName);

			socketClient = new DatagramSocket(1000);
			socketClient.setSoTimeout(10001);

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/***
	 * Sends a message to the server and waits for a answear.
	 * 
	 * @param message
	 * @return the answear from the server
	 */
	public String sendMsg(String message)
	{

		final byte[] data = new byte[2048];
		final byte[] dataMsg = message.getBytes();
		DatagramPacket inPackage = null;

		final DatagramPacket outPackage = new DatagramPacket(dataMsg, dataMsg.length, reciver, port);
		System.out.println("client sent msg to server");
		try
		{
			socketClient.send(outPackage);
			inPackage = new DatagramPacket(data, data.length);
			socketClient.receive(inPackage);

		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		if (inPackage == null)
		{
			System.out.println("didnt recieve a answear");
			return "didnt recieve a answear!";
		}
		else
		{
			final String answear = new String(inPackage.getData(), 0, inPackage.getLength());
			System.out.println("the answear: " + answear);
			return answear;
		}

	}

}
