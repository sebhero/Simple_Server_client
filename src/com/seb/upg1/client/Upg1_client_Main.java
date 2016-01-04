package com.seb.upg1.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Upg1_client_Main
{

	private JFrame frame;
	private JTextField txtFieldSendText;
	private final Upg1_Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					final Upg1_client_Main window = new Upg1_client_Main();
					window.frame.setVisible(true);
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Upg1_client_Main()
	{
		client = new Upg1_Client();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 344, 204);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtFieldSendText = new JTextField();
		txtFieldSendText.setBounds(10, 11, 184, 20);
		frame.getContentPane().add(txtFieldSendText);
		txtFieldSendText.setColumns(10);

		final JButton btnSendText = new JButton("Send text");
		btnSendText.setBounds(215, 11, 89, 23);
		frame.getContentPane().add(btnSendText);

		final JTextArea txtAreaAnswear = new JTextArea();
		txtAreaAnswear.setBounds(10, 56, 184, 98);
		frame.getContentPane().add(txtAreaAnswear);

		btnSendText.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				// get the text message to be sent.
				final String message = txtFieldSendText.getText();
				// check that there is a message to send
				if (!message.isEmpty())
				{
					// send message and wait for a answear
					final String answear = client.sendMsg(message);
					// display answear
					txtAreaAnswear.append(answear + "\n");
				}

			}
		});

	}
}
