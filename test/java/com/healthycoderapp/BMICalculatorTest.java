package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BMICalculatorTest {
	
	@BeforeAll 
	static void beforeAll() {
		System.out.println("Unit test started");
	}
	@AfterAll 
	static void afterAll() {
		System.out.println("Unit test finished");
	}
	@Test
	void should_ReturnTrue_When_DietRecommended() {		
		
		//Given
		double weight = 89.0;
		double height = 1.72;
		
		//When
		boolean recommeded =  BMICalculator.isDietRecommended(weight, height);
		
		//Then
		assertTrue(recommeded);
	}
	
	@Test
	void should_ReturnFalse_When_DietNotRecommended() {
		
		//Given
		double weight = 50.0;
		double height = 1.92;
		
		//When
		boolean recommeded =  BMICalculator.isDietRecommended(weight, height);
		
		//Then
		assertFalse(recommeded);
	}
	
	@Test
	void should_ThrowArithmeticException_When_HeightIsZero() {
		
		//Given
		double weight = 50.0;
		double height = 0.0;
		
		//When
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
		
		//Then
		assertThrows(ArithmeticException.class, executable);
	}
	
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
		assertAll(
		   () -> assertEquals(1.82 , coderWithWorstBMI.getHeight()),
		   () -> assertEquals(98 , coderWithWorstBMI.getWeight())
		);
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
