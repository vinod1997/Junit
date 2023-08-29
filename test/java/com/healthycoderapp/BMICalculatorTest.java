package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
	
	private String environment = "prod";
	
	@BeforeAll 
	static void beforeAll() {                          //#Executes before any of the test methods executes
		System.out.println("Unit test started");
	}
	@AfterAll                                          //#Executes after every one  of the test methods executes
	static void afterAll() {
		System.out.println("Unit test finished");
	}	
	
	@Nested	
	class Is_DietRecommended_Tests	{
		@ParameterizedTest (name = "weight = {0}, height = {1}" )  // Used to test a method with multiple inputs 
		@CsvFileSource (resources = "/diet-recommended-input-data.csv" , numLinesToSkip = 1)  //CSV file has the inputs 
		void should_ReturnTrue_When_DietRecommended(Double CoderWeight, Double CoderHeight) {		
			
			//Given
			double weight = CoderWeight;
			double height = CoderHeight;
			
			//When
			boolean recommeded =  BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertTrue(recommeded);                                   //To see if the test method returns true 
		}
		
		@DisplayName(">>>False Method<<<")               //Changes name of the method
		                                    //@Disabled Disables the method
		@DisabledOnOs(OS.LINUX)           //Disables the test method on specific OS
		@Test
		void should_ReturnFalse_When_DietNotRecommended() {
			
			//Given
			double weight = 50.0;
			double height = 1.92;
			
			//When
			boolean recommeded =  BMICalculator.isDietRecommended(weight, height);
			
			//Then
			assertFalse(recommeded);                                    //To see if the test method returns False
		}
		
		@Test
		void should_ThrowArithmeticException_When_HeightIsZero() {
			
			//Given
			double weight = 50.0;
			double height = 0.0;
			
			//When
			Executable executable = () -> BMICalculator.isDietRecommended(weight, height);  //Lamda statement represent block of code as an argument 
			
			//Then
			assertThrows(ArithmeticException.class, executable);          
		}
	}
	
	@Nested	
	class CoderWith_WorstBMI	{
		@Test
		void should_ReturnCoderWithWorstBMI_WhenListIsNotEmpty() {
			
			//Given
			List <Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80 , 64.0));
			coders.add(new Coder(1.82 , 98.0));
			coders.add(new Coder(1.82 , 76.0));
			
			//When
			Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			//Then
			assertAll(                                  // The assert statements are present as an whole assert
			   () -> assertEquals(1.82 , coderWithWorstBMI.getHeight()),
			   () -> assertEquals(98 , coderWithWorstBMI.getWeight())
			);
		}
		
		@Test
		void Return_Coder_WithWorstBMI_In1MS_WhenListIs1000() {
			
			//given
			assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
			List <Coder> coders = new ArrayList();
			for(int i = 0; i < 10000 ; i++) {
				coders.add(new Coder (1.0 + 1, 10.0 + 1));
			}
			
			//when
			Executable executable = () -> should_ReturnCoderWithWorstBMI_WhenListIsNotEmpty();
			
			//then
			assertTimeout(Duration.ofMillis(15), executable);    //To assert the time required for the test 
		}
		
		@Test
		void should_ReturnNullWorstBMI_WhenListIsNotEmpty() {
			
			//Given
			List <Coder> coders = new ArrayList<>();
				
			//When
			Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			//Then
			assertNull(coderWithWorstBMI);
		}
	}
	@Nested	
	class GetBMIScoresList	{
		@Test
		void should_ReturnCorrectBMIScoreArray_WhenCoder_ListIsNotEmpty() {
			
			//Given
			List <Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80 , 60.0));
			coders.add(new Coder(1.82 , 98.0));
			coders.add(new Coder(1.82 , 64.7));
			double [] expected = {18.52, 29.59, 19.53};
				
			//When
			double [] bmi_scores = BMICalculator.getBMIScores(coders);
			
			//Then
			assertArrayEquals(expected , bmi_scores);
		}
	}
}


