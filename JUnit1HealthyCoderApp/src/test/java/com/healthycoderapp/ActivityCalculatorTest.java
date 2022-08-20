package com.healthycoderapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ActivityCalculatorTest {
	@Test
	void should_ReturnBad_when_AvgBelow20() {
		// Given
		int weeklyCardioMins = 40;
		int weeklyWorkouts = 1;
		
		// When
		String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
		
		// Then
		assertEquals("bad", actual);
	}
	
	@Test
	void should_ReturnAverage_when_AvgBetween20and40() {
		// Given
		int weeklyCardioMins = 40;
		int weeklyWorkouts = 3;
		
		// When
		String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
		
		// Then
		assertEquals("average", actual);
	}
	
	@Test
	void should_ReturnGood_when_AvgAbove40() {
		// Given
		int weeklyCardioMins = 40;
		int weeklyWorkouts = 7;
		
		// When
		String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
		
		// Then
		assertEquals("good", actual);
	}
	
	@Test
	void should_ThrowException_when_InputBelowZero() {
		// Given
		int weeklyCardioMins = -40;
		int weeklyWorkouts = 7;
		
		// When
		Executable executable = () -> ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
		
		// Then
		assertThrows(RuntimeException.class, executable);
	}
}
