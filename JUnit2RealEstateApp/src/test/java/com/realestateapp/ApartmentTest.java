package com.realestateapp;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.*;

class ApartmentTest {

	@Nested
	class RateApartmentTests {
		// TODO: test this with edge case too, parameterized test
		@Test
		void shouldReturnMinusOneWhenAreaIsZero() {
			// Given
			Apartment apt = new Apartment(0.0, new BigDecimal(4000.0));
			
			// When
			int result = ApartmentRater.rateApartment(apt);
			
			// Then
			assertEquals(result, -1);
		}
		
		// TODO: try with 599999 too - as a parameterized test
		@Test
		void shouldReturnZeroWhenRatioIsLessThanCheapThreshold() {
			Apartment apt = new Apartment(100, new BigDecimal(550000.0));
		
			int result = ApartmentRater.rateApartment(apt);
			
			assertEquals(result, 0);
		}
		
		// TODO: Edge cases are not as I expected, 599999 doesnt fail.
		// TODO: Try this with a lower and upper edge case as well as something between, parameterized test
		@Test
		void shouldReturnOneWhenRatioIsBetweenCheapAndModerate() {
			Apartment apt = new Apartment(100, new BigDecimal(600000.0));
			
			int result = ApartmentRater.rateApartment(apt);
			
			assertEquals(result, 1);
		}
		
		// TODO: try this with an edge case too, parameterized test
		@Test
		void shouldReturnTwoWhenRatioIsGreaterThanModerate() {
			Apartment apt = new Apartment(100, new BigDecimal(800000.0));
			
			int result = ApartmentRater.rateApartment(apt);
			
			assertEquals(result, 2);
		}
	}
	
	

}
