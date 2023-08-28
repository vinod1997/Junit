package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DietPlannerTest {
	
	private DietPlanner dietplanner ;
	
	@BeforeEach
	void setUp() {
		this.dietplanner = new DietPlanner(20,30,50);
	}
	@AfterEach
	void afterEach () {
		System.out.println("A unit test was finished");
	}
	@Test
	void should_Return_Correct_Dietplan_WhenCorrectCoder() {
		
		//given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		//when
		DietPlan actual = dietplanner.calculateDiet(coder);
		
		//then
		assertAll(
		     () ->assertEquals(expected.getCalories(), actual.getCalories()),
		     () ->assertEquals(expected.getProtein(), actual.getProtein()),
		     () ->assertEquals(expected.getFat(), actual.getFat()),
		     () ->assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
		);
	}

}
