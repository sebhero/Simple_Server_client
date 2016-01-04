package com.seb.upg1.server;

import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Upg1_Server_Main
{

	private DatagramSocket socket;
	private final byte[] data;

	/***
	 * Server that communicates with the client. Waits for messages to be sent to
	 * it. Waits on port 10001
	 */
	Upg1_Server_Main()
	{
		data = new byte[2048];
		try
		{
			socket = new DatagramSocket(10001);
		}
		catch (final SocketException e)
		{
			e.printStackTrace();
			socket = null;
		}
	}

	/**
	 * Sets up the server and starts it, so it then waits for messages from
	 * client.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		final Upg1_Server_Main server = new Upg1_Server_Main();
		server.start();

	}

	/***
	 * Server start method.
	 */
	private void start()
	{
		System.out.println("server started");
		System.out.println(socket.getLocalSocketAddress());

		while (true)
		{
			final DatagramPacket inpackage = new DatagramPacket(data, data.length);
			try
			{
				socket.receive(inpackage);
				final String answear = packageHandler(inpackage);
				final byte[] outData = answear.getBytes();

				final DatagramPacket outPackage = new DatagramPacket(outData, outData.length, inpackage.getAddress(),
						inpackage.getPort());
				socket.send(outPackage);

			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	/**
	 * Handles incomming packages from clients.
	 * 
	 * @param inpackage
	 *          incomming packages from client.
	 * @return a robber language message to the client
	 */
	private String packageHandler(DatagramPacket inpackage)
	{

		final String message = new String(inpackage.getData(), 0, inpackage.getLength());

		final StringWriter fw = new StringWriter();
		try
		{
			final RobberWriter rw = new RobberWriter(fw);
			rw.write(message);
			rw.close();

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("the answear" + fw.toString());
		return fw.toString();

	}
}
