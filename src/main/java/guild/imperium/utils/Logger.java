package guild.imperium.utils;

import org.jetbrains.annotations.NotNull;

public class Logger {

	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";


	public static void log(Level level, @NotNull String message) {


		switch (level) {
			case ERROR:
				System.out.println(RED + "[ERROR] " + YELLOW + message);
				break;
			case WARNING:
				System.out.println(PURPLE + "[WARNING] " + YELLOW + message);
				break;
			case INFO:
				System.out.println(BLUE + "[INFO] " + WHITE + message);
			case SUCCESS:
				System.out.println(GREEN + "[SUCCESS] " + WHITE + message);
				break;
			case OUTLINE:
				System.out.println(WHITE +  message);
				break;
		}
	}

	public static void log(Level level, @NotNull String message, Exception ex) {

		switch (level) {
			case ERROR:
				System.out.println(RED + "[ERROR] " + YELLOW + message);
				ex.printStackTrace();
				break;
			case WARNING:
				System.out.println(PURPLE + "[WARNING] " + YELLOW + message);
				ex.printStackTrace();
				break;
			case INFO:
				System.out.println(BLUE + "[INFO] " + WHITE + message);
				ex.printStackTrace();
			case SUCCESS:
				System.out.println(GREEN + "[SUCCESS] " + WHITE + message);
				ex.printStackTrace();
				break;
		}

	}

	public enum Level { ERROR, WARNING, INFO, SUCCESS, OUTLINE }

}
