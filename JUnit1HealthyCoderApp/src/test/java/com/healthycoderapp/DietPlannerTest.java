package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
// import org.junit.jupiter.api.Test;

class DietPlannerTest {

	// Since methods of DietPlanner class is are not static, 
	// we need an object of DietPlanner to use and test them.
	//
	// It's not good idea to initialize it inside the unit test. 
	// Because we would have to initialize sperately in all of them.
	// 
	// We don't want to initialize it directly here as well. 
	// Because tests should be independent. 
	// We want to have a brand new instance of DietPlanner for each test.
	private DietPlanner dietPlanner;
	
	// This code snippet will be run before each unit test
	@BeforeEach
	void setup() {
		// New DietPlanner will be created before each test
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}
	
//	@AfterEach
//	void afterEach() {
//		System.out.println("A unit test was finished.");
//	}
	
	// If you want to use repeated tests instead, do the following.
	// But use them only when they actually make sense.
	// If the method uses random numbers, changes state in any way, method runs on multiple threads etc.
	//
	// @Test
	@RepeatedTest(10) // will run the method 10 times
	void should_ReturnCorrectDietPlan_when_CorrectCoder() {
		// Given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		// When
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		// Then
		assertAll(
			() -> assertEquals(expected.getCalories(), actual.getCalories()),
			() -> assertEquals(expected.getProtein(), actual.getProtein()),
			() -> assertEquals(expected.getFat(), actual.getFat()),
			() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
		);
	}

}
