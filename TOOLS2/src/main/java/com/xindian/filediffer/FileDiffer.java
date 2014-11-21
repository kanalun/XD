package com.xindian.filediffer;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class FileDiffer
{
	public static void tree()
	{
		String x = "├─";
		String xxx = "└─";
		String xx = "│";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		AnsiConsole.systemInstall();
		System.out.println(Ansi.ansi().eraseScreen().fg(RED).a("Hello").fg(GREEN).a(" World").reset());
		System.out.println(Ansi.ansi().eraseScreen().render("@|red Hello|@ @|green World|@"));
		AnsiConsole.systemUninstall();
	}

}
