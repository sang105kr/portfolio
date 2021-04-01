package com.kh.portfolio.test.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Workday {

	public static void main(String[] args) {
		//공휴일
		Set<LocalDate> holidays = new HashSet<>();
		holidays.add( LocalDate.of(2021, 2, 11));
		holidays.add( LocalDate.of(2021, 2, 12));
		
		List<LocalDate> list = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2021, 1, 1);
		LocalDate toDate = LocalDate.of(2021, 2, 28);
		
		while(!fromDate.equals(toDate)) {
			fromDate=fromDate.plusDays(1);
			list.add(fromDate);
		}
		
		list.stream()
				.filter(day -> day.getDayOfWeek()!=DayOfWeek.SATURDAY && day.getDayOfWeek()!=DayOfWeek.SUNDAY) //토일제거
				.filter(day -> !holidays.contains(day)) //공휴일제거
				.forEach(day -> System.out.println(day));
		
		System.out.println(list.size());
	}
}
