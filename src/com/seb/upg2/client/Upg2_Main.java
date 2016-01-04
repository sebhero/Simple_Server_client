package com.seb.upg2.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Upg2_Main
{

	private JFrame frame;
	private JTextField txtFieldCaptcha;
	protected URL captachaPage;
	protected Date imgdate;

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
					final Upg2_Main window = new Upg2_Main();
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
	public Upg2_Main()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 294, 212);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtFieldCaptcha = new JTextField();
		txtFieldCaptcha.setBounds(10, 119, 152, 20);
		frame.getContentPane().add(txtFieldCaptcha);
		txtFieldCaptcha.setColumns(10);

		final JButton btnAnswear = new JButton("answear");
		btnAnswear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final String answear = validateInput();
				JOptionPane
						.showMessageDialog(null, "Result: " + answear, "Result from server", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnAnswear.setBounds(179, 118, 89, 23);
		frame.getContentPane().add(btnAnswear);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 258, 97);
		frame.getContentPane().add(scrollPane);

		final JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);

		final JLabel lblImage = new JLabel("");
		panel.add(lblImage);
		try
		{
			getImageFromUrl(lblImage);
		}
		catch (final MalformedURLException e2)
		{
			e2.printStackTrace();
		}
		catch (final IOException e2)
		{
			e2.printStackTrace();
		}

		final JButton btnReload = new JButton("reload");
		/**
		 * Load a new image from the website.
		 */
		btnReload.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					getImageFromUrl(lblImage);

				}
				catch (final Exception e1)
				{
					e1.printStackTrace();
				}

			}

		});
		btnReload.setBounds(10, 150, 89, 23);
		frame.getContentPane().add(btnReload);
	}

	/***
	 * Get the image from the website
	 * 
	 * @param lblImage
	 *          where to display the image.
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private void getImageFromUrl(final JLabel lblImage) throws MalformedURLException, IOException
	{
		captachaPage = new URL("http://dt066g.programvaruteknik.nu/captcha.php");
		final URLConnection connection = captachaPage.openConnection();
		final BufferedImage imgReader = ImageIO.read(connection.getInputStream());
		imgdate = new Date(connection.getLastModified());

		final ImageIcon icon = new ImageIcon(imgReader);

		lblImage.setIcon(icon);
	}

	/***
	 * Validate users input with remote website
	 * 
	 * @return the answear from server
	 */
	private String validateInput()
	{
		String validateStr = "http://dt066g.programvaruteknik.nu/validate.php?code=" + txtFieldCaptcha.getText();

		final String RFC1123_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss";
		final SimpleDateFormat dateFormat = new SimpleDateFormat(RFC1123_DATE_PATTERN, Locale.US);
		final TimeZone zone = TimeZone.getTimeZone("GMT");
		dateFormat.setTimeZone(zone);
		final String dateStr = dateFormat.format(imgdate);
		String encodeDate = "";
		String answear = "";
		try
		{
			encodeDate = URLEncoder.encode(dateStr, "UTF-8");
		}
		catch (final UnsupportedEncodingException e2)
		{
			e2.printStackTrace();
		}

		validateStr += "&date=" + encodeDate;
		System.out.println(validateStr);

		try
		{
			final URL validatePage = new URL(validateStr);

			final InputStream is = validatePage.openStream();
			answear = convertStreamToString(is);
			return answear;

		}
		catch (final IOException e1)
		{
			e1.printStackTrace();
		}
		return answear;
	}

	/***
	 * Converts a input stream to a string
	 * 
	 * @param myInputStream
	 * @return a String from the inputstream
	 */
	@SuppressWarnings("resource")
	static String convertStreamToString(java.io.InputStream myInputStream)
	{
		final java.util.Scanner s = new java.util.Scanner(myInputStream).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
