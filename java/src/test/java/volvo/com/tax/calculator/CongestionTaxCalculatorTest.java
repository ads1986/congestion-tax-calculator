package volvo.com.tax.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import volvo.com.tax.calculator.model.Car;
import volvo.com.tax.calculator.usecase.CongestionTaxCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@SpringBootTest
class CongestionTaxCalculatorTest {

	private CongestionTaxCalculator congestion;

	@BeforeEach
	void init (){
		this.congestion = new CongestionTaxCalculator();
	}

	@Test
	void testWithAllInvalidTaxChargeDays() {
		for (LocalDate invalidDays : getAllInvalidTaxedDays()) {
			Date date = Date.from(invalidDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
			int taxValue = congestion.GetTollFee(date, new Car());
			Assertions.assertEquals(taxValue, 0);
		}
	}

	@Test
	void testAllTimeRanges() {
		Map<String, Integer> taxMap = getRateMap();
		for (String rate : getRateMap().keySet()){
			String validHour = rate.split("–")[0] + ":00";
			Date date = createDate("2013-01-07 " +  validHour);
			int taxValue = congestion.GetTollFee(date, new Car());
			Assertions.assertEquals(taxValue, taxMap.get(rate));
		}
	}

	@Test
	void testVehiclePassesSeveralStationsIn60MinutesDay() {
		Map<String, Integer> taxMap = getRateMap();
		taxMap.remove("18:30–05:59");
		List<Date> listOfTimes = new ArrayList<>();
		for (String rate : getRateMap().keySet()) {
			String validHour = rate.split("–")[0] + ":00";
			listOfTimes.add(createDate("2013-01-07 " +  validHour));
		}

		Date[] dates = new Date[listOfTimes.size()];
		dates = listOfTimes.toArray(dates);

		int taxValue = congestion.getTax(new Car(), dates);
		Assertions.assertEquals(taxValue, 60);
	}

	private List<LocalDate> getAllInvalidTaxedDays(){
		List<LocalDate> allInvalidTaxedDays = new ArrayList<>();
		allInvalidTaxedDays.addAll(getAllWeekendsIn2013());
		allInvalidTaxedDays.addAll(getAllPublicHolidaysIn2013());
		allInvalidTaxedDays.addAll(getAllDaysOfJuly());
		allInvalidTaxedDays.addAll(getAllDaysBeforePublicHolidaysIn2013());
		return allInvalidTaxedDays;
	}

	private List<LocalDate> getAllWeekendsIn2013(){
		LocalDate ld = LocalDate.of(2013, Month.JANUARY, 1);
		LocalDate endDate = ld.plusYears(1);
		List<LocalDate> weekends = new ArrayList<>(365);
		while (ld.isBefore(endDate)) {
			if (ld.getDayOfWeek() == DayOfWeek.SATURDAY || ld.getDayOfWeek() == DayOfWeek.SUNDAY) {
				weekends.add(ld);
			}
			ld = ld.plusDays(1);
		}
		return weekends;
	}

	private List<LocalDate> getAllDaysBeforePublicHolidaysIn2013(){
		List<LocalDate> allPublicHolidaysList = new ArrayList<>();
		for (LocalDate holiday: getAllPublicHolidaysIn2013())
			allPublicHolidaysList.add(holiday.minusDays(1));
		return allPublicHolidaysList;
	}

	private List<LocalDate> getAllPublicHolidaysIn2013(){
		String[] holidays = "2013-01-01,2013-01-06,2013-03-29,2013-03-31,2013-04-01,2013-05-01,2013-05-09,2013-05-19,2013-05-20,2013-06-06,2013-06-22,2013-11-01,2013-12-25,2013-12-26,2013-12-31".split(",");
		List<LocalDate> allPublicHolidaysList = new ArrayList<>();
		for (String holiday: holidays)
			allPublicHolidaysList.add(LocalDate.parse(holiday));
		return allPublicHolidaysList;
	}

	private List<LocalDate> getAllDaysOfJuly(){
		LocalDate ld = LocalDate.of(2013, Month.JULY, 1);
		LocalDate endDate = ld.plusDays(30);
		List<LocalDate> julyDays = new ArrayList<>(31);
		while (ld.isBefore(endDate) || ld.isEqual(endDate)) {
			julyDays.add(ld);
			ld = ld.plusDays(1);
		}
		return julyDays;
	}

	private Date createDate(String date){
		Date dateTime = null;
		try {
			dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return dateTime;
	}

	private Integer getAmountByHour(String rate){
		Map<String, Integer> taxMap = getRateMap();
		return taxMap.get(rate);
	}

	private Map<String, Integer> getRateMap(){
		Map<String, Integer> taxMap = new HashMap<>();

		taxMap.put("06:00–06:29", 8);
		taxMap.put("06:30–06:59", 13);
		taxMap.put("07:00–07:59", 18);
		taxMap.put("08:00–08:29", 13);
		taxMap.put("08:30–14:59", 8);
		taxMap.put("15:00–15:29", 13);
		taxMap.put("15:30–16:59", 18);
		taxMap.put("17:00–17:59", 13);
		taxMap.put("18:00–18:29", 8);
		taxMap.put("18:30–05:59", 0);

		return taxMap;
	}

	private List<String> listOfDates() {
		String[] dates = {"2013-01-14 21:00:00",

				"2013-01-15 21:00:00",

				"2013-02-07 06:23:27",

				"2013-02-07 15:27:00",

				"2013-02-08 06:27:00",

				"2013-02-08 06:20:27",

				"2013-02-08 14:35:00",

				"2013-02-08 15:29:00",

				"2013-02-08 15:47:00",

				"2013-02-08 16:01:00",

				"2013-02-08 16:48:00",

				"2013-02-08 17:49:00",

				"2013-02-08 18:29:00",

				"2013-02-08 18:35:00",

				"2013-03-26 14:25:00",

				"2013-03-28 14:07:27"};

		return Arrays.asList(dates);
	}
}