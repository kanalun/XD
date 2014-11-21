package com.xindian.awaits.id;

import java.io.Serializable;

import com.xindian.awaits.Session;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * <b>uuid</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32, This string will
 * consist of only hex digits. Optionally, the string may be generated with
 * separators between each component of the UUID.
 * 
 * Mapping parameters supported: separator.
 * 
 * @author Gavin King
 */
public class UUIDHexGenerator extends AbstractUUIDGenerator
{
	// private static final Logger log =
	// LoggerFactory.getLogger(UUIDHexGenerator.class);
	private static boolean warned = false;

	private String sep = "";

	public UUIDHexGenerator()
	{
		if (!warned)
		{
			warned = true;
			// log.warn("Using {} which does not generate IETF RFC 4122 compliant UUID values; consider using {} instead",
			// this
			// .getClass().getName(), UUIDGenerator.class.getName());
		}
	}

	public Serializable generate(Session session, Object object)
	{
		return new StringBuffer(36).append(format(getIP())).append(sep).append(format(getJVM())).append(sep)
				.append(format(getHiTime())).append(sep).append(format(getLoTime())).append(sep).append(format(getCount()))
				.toString();
	}

	protected String format(int intValue)
	{
		String formatted = Integer.toHexString(intValue);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortValue)
	{
		String formatted = Integer.toHexString(shortValue);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}
}
