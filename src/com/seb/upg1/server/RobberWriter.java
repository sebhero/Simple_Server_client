package com.seb.upg1.server;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by seb on 2014-02-17.
 */
public class RobberWriter extends FilterWriter
{
	/**
	 * Create a new filtered writer.
	 * 
	 * @param out
	 *          a Writer object to provide the underlying stream.
	 * @throws NullPointerException
	 *           if <code>out</code> is <code>null</code>
	 */
	public RobberWriter(Writer out)
	{
		super(out);
	}

	/***
	 * omvandlar input strangen till rovarspraket.
	 * 
	 * @param text
	 */
	@Override
	public void write(String text)
	{

		final StringBuilder textbuild = new StringBuilder();

		for (int i = 0; i < text.length(); i++)
		{
			final char currentUnchanged = text.charAt(i);
			final char currentChar = text.toUpperCase().charAt(i);
			switch (currentChar)
			{
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'H':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
				final String temp = currentUnchanged + "o" + currentUnchanged;

				textbuild.append(temp);

				break;
			case '\n':
			case ',':
			case '.':
				textbuild.append(currentUnchanged);
				break;
			default:
				textbuild.append(currentUnchanged);
				break;
			}
		}

		System.out.println(textbuild);
		try
		{
			write(textbuild.toString(), 0, textbuild.length());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

	}
}
