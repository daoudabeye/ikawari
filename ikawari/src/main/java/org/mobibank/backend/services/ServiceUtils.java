package org.mobibank.backend.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ServiceUtils {

	/**
	 * Methode for date time setting 00:00:00 intervale 23:59:59 
	 * @param date
	 * @param temporalType
	 * @param start
	 * @return
	 */
	public static Date setTime(Date date, Boolean start){
		Calendar cal  = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, start ? 0 : 23);
		cal.set(Calendar.MINUTE, start ? 0 : 59);
		cal.set(Calendar.SECOND, start ? 0 : 59);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * Methode aléatoir de chaine de caractère
	 * @param length longueur de la chaine
	 * @return
	 */
	public static String randomString(int length){
		Random random = new Random();
		return random.ints(48,122)
				.filter(i-> (i<57 || i>65) && (i <90 || i>97))
				.mapToObj(i -> (char) i)
				.limit(length)
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
	}
}
