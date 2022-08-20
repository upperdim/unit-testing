package com.healthycoderapp;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {
	// Some unit tests may be production only, some may be development only, some may be both
	// We want to run `should_ReturnCoderWithHighestBMIIn1Ms_when_codersListHas1000Elements` test only in production for example
	//
	// We simulate the environment for this project with below variable:
	private String environment = "dev";
	
	// This is run before all unit tests (Unline BeforeEach).
	// Usually used for expensive operations that are costly 
	// to run before each test such as establishing database connection.
	//
	// Can have any names, but MUST be static
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before all unit tests...");
	}
	
	// Exact opposite of BeforeAll.
	// Typically used to close databases or servers.
	@AfterAll
	static void afterAll() {
		System.out.println("After all unit tests...");
	}
	
	// Below allows us to create nested classes so that we can group related tests under a class
	@Nested
	@DisplayName("My Custom Inner Class Display Name in the Test Results")
	class IsDietRecommendedTests {
		@Test
		@DisplayName("My Custom Test Display Name in the Test Results")
		// @Disable // this annotation will skip the test, it won't get executed
		// @DisabledOnOs(OS.WINDOWS) // will only be disabled on a certain operating system
		// Should ... when ... (convention, best practice)
		void should_ReturnTrue_When_Recommended() {
			// Given / Arrange	(convention): initial conditions, input values
			double weight = 89.0;
			double height = 1.72;
			
			// When  / Act		(convention) when we invoke method under test and store the result in a variable
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			// Then	 / Assert	(convention)
			assertTrue(recommended);
		}
		
		// We could create tests like above and test many more values but it's not good practice.
		// We should use a "parameterized test".
		//
		// In here, we will show the simplest way where a single value will change after each test. (weight)
		@ParameterizedTest
		@ValueSource(doubles = {89.0, 95.0, 110.0})
		void should_ReturnTrue_When_Recommended2(Double anyName) {
			// Given
			double weight = anyName; // each value will be injected into this variable and get called, performing the test cases
			double height = 1.72;
			
			// When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			// Then
			assertTrue(recommended);
		}
		
		// Importing test values from a comma separated list (CSV)
		@ParameterizedTest(name = "weight={0}, height={1}") // set names for 0th and 1st parameter respectively (for humans to read in the test results)
		@CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"}) // parameter name MUST be "values" here
		void should_ReturnTrue_When_Recommended3(Double anyNameWeight, Double anyNameHeight) {
			// Given
			double weight = anyNameWeight; // each value will be injected into this variable and get called, performing the test cases
			double height = anyNameHeight;
			
			// When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			// Then
			assertTrue(recommended);
		}
		
		// Importing test values from an actual comma separated list (CSV) file now
		@ParameterizedTest(name = "weight={0}, height={1}")
		@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1) // Giving file name: JUnit automatically start searching at the path test/resources. numLinesToSkip = 1 will skip the first line cause it contain CSV header (text), not data
		void should_ReturnTrue_When_Recommended4(Double anyNameWeight, Double anyNameHeight) {
			// Given
			double weight = anyNameWeight; // each value will be injected into this variable and get called, performing the test cases
			double height = anyNameHeight;
			
			// When
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			// Then
			assertTrue(recommended);
		}
		
		@Test
		void should_ReturnFalse_When_DietNotRecommended() {
			// Given / Arrange
			double weight = 60.0;
			double height = 1.75;
			
			// When  / Act
			boolean recommended = BMICalculator.isDietRecommended(weight, height);
			
			// Then	 / Assert
			assertFalse(recommended);
		}
		
		@Test
		void should_ThrowArithmeticException_When_HeightZero() {
			// Given / Arrange
			double weight = 60.0;
			double height = 0.0;
			
			// When  / Act
			Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
			
			// Then	 / Assert
			assertThrows(ArithmeticException.class, executable);
		}
	}
	
	@Nested
	class FindCoderWithWorstBMITests {
		@Test
		void should_ReturnCoderWithHighestBMI_when_codersListNotEmpty() {
			// Given / Arrange
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));
			coders.add(new Coder(1.82, 64.7));
			
			// When / Act
			Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			// Then / Assert
			assertAll(
				() -> assertEquals(1.82, coderWorstBMI.getHeight()),
				() -> assertEquals(98.0, coderWorstBMI.getWeight())
			);
		}
		
		@Test
		void should_ReturnCoderWithHighestBMIIn1Ms_when_codersListHas1000Elements() {
			// Given
			
			// Below is for running this performance test only in production.
			// Our environment is "dev" and this assertion will fail.
			// This test will be skipped upon this failure.
			// Because below is `assume`, not `assert`. Failure won't fail the test.
			assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
			
			List<Coder> coders = new ArrayList<>();
			for (int i = 0; i < 1000; ++i) {
				coders.add(new Coder(1.0 + i, 10.0 + i));
			}
			
			// When
			Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
			
			// Then
			assertTimeout(Duration.ofMillis(500), executable);
		}
		
		@Test
		void should_ReturnNullWorstBMICoder_when_codersListEmpty() {
			// Given / Arrange
			List<Coder> coders = new ArrayList<>();
			
			// When / Act
			Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			// Then / Assert
			assertNull(coderWorstBMI);
		}
	}
	
	@Nested
	class GetBMIScoresTests {
		@Test
		void should_ReturnCorrectBMIScoreArray_when_CoderListNotEmpty() {
			// Given / Arrange
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));
			coders.add(new Coder(1.82, 64.7));
			
			double[] expected = {18.52, 29.59, 19.53};
			
			// When / Act
			double[] bmiScores = BMICalculator.getBMIScores(coders);
			
			// Then / Assert
			// assertEquals(expected, bmiScores); // will fail, because they're 2 different objects in memory. This checks if they're the same object.
			assertArrayEquals(expected, bmiScores);
		}
	}

}
