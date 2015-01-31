package com.tptools.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTimeField;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;

/**
 * 
 * @author Marcin Berendt
 *
 */
public class TrainingsDatesGenerator
{ 		
	/**
	 * 
	 * @param trainingsFrequency (String which contains 7characters like : 0101111 which simply means : 0 - non training day, 1 - training day)
	 * @param diaryBeginDate - Date of trainingDiary start inf format YYYY-MM-DD
	 * @param diaryEndDate - Date of trainingDiary end YYYY-MM-DD
	 */
	 public static List<Date>generateTrainingsDates(String trainingsFrequency, String diaryBeginDate, String diaryEndDate)
	 {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = new Date();
			Date endDate = new Date();
			List<Date> datesOfTrainings = new ArrayList<Date>();
			try 
			{
				startDate =  format.parse(diaryBeginDate);
				endDate = format.parse(diaryEndDate);
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			
			List<Date> allDatesBetweenGivenDates = generateAllDatesListBetweenDates(startDate, endDate);
			List<Integer> listOfWeeklyTrainingDaysNumbers = getListOfWeeklyTrainingDaysNumbers(trainingsFrequency);
			for(Date currentDate : allDatesBetweenGivenDates)
			{
				Calendar c = Calendar.getInstance();
				c.setTime(currentDate);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				if(listOfWeeklyTrainingDaysNumbers.contains(Integer.valueOf(dayOfWeek)))
				{
					datesOfTrainings.add(currentDate);
				}
			}
					 
			return datesOfTrainings;	
	   }
		
	   private static List<Integer> getListOfWeeklyTrainingDaysNumbers(String frequency)
	   {
		 List<Integer> trainingDaysNumbers = new ArrayList<Integer>();
		 char[] frequencyArray = frequency.toCharArray();
	
		 for(int i=0; i<7; i++)
		 {
			 char currentFrequencyArrayValue = frequencyArray[i];
			 String currentCharacter = String.valueOf(currentFrequencyArrayValue);
			 if(currentCharacter.equals("1"))
			 {
				 trainingDaysNumbers.add(i+1);
			 }
		 }	
		 return trainingDaysNumbers;
	   }
	
	   private static List<Date> generateAllDatesListBetweenDates(Date startDate, Date endDate)
	   {
		    List<Date> dates = new ArrayList<Date>();
		    Calendar calendar = new GregorianCalendar();

		    calendar.setTime(startDate);
		    while (calendar.getTime().before(endDate))
		    {
		        Date result = calendar.getTime();
		        dates.add(result);
		        calendar.add(Calendar.DATE, 1);
		    }
		    
		   return dates;   
	   }
		/**
	    * Method returns maximum days for given month number (1 for January, 2 for February etc. )
	    * @param monthNumber
	    * @return
	    */
	   private static int getMaximumAmountOfDaysInGivenMonth(int monthNumber)
	   {
		   	GregorianChronology calendar = GregorianChronology.getInstance();
	        DateTimeField field = calendar.dayOfMonth();
	        
	        	LocalDate date = new LocalDate(getCurrentYearNumber(), monthNumber , 1, calendar);
	        	int maxDays = field.getMaximumValue(date);
        	
	       return maxDays;
	   }
	   
	   /**
	    * Mathod return maximum days of currentMonth
	    * @return
	    */
	   private static int getMaximumAmountOfDaysInCurrentMonth()
	   {
		   Calendar c = Calendar.getInstance();
		   int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		   return monthMaxDays;
	   }
	   /**
	    * Method returns number of current month (1 for January, 2 for February etc. )
	    * @return currentMonth
	    */
	   private static int getCurrentMonthNumber()
	   {
		  int currentMonth = Calendar.getInstance().get(Calendar.MONTH); 
		  return currentMonth+1;
	   }
	   /**
	    * Method returns current year Number
	    * @return currentYear
	    */
	   private static int getCurrentYearNumber()
	   {
		  int currentYear = Calendar.getInstance().get(Calendar.YEAR); 
		  return currentYear;
	   }
	   /**
	    * Method returns 
	    * @return
	    */
	   private static int getCurrentDayOfMonthNumber()
	   {
		  int currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); 
		  return currentDayOfMonth;
	   }

}
