package com.weblearn.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.weblearn.stream.GroupSample.Dish.CaloricLevel;

import lombok.Data;

public class GroupSample {

	@Data
	public static class Dish {
		
		public Dish(DishType type, Integer calories, Boolean vegetarian) {
			this.type = type;
			this.calories = calories;
			this.vegetarian = vegetarian;
		}
		
		private DishType type;
		
		private Integer calories;
		
		private Boolean vegetarian;
		
		public enum DishType {
			
			FISH, MEAT, OTHER;
		}
		
		public enum CaloricLevel {
			DIET, NORMAL, FAT;
		}
	}
	
	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(new Dish(Dish.DishType.FISH, 100, true), new Dish(Dish.DishType.FISH, 200, false), 
				new Dish(Dish.DishType.MEAT, 200, true), new Dish(Dish.DishType.MEAT, 300, false), new Dish(Dish.DishType.OTHER, 500, true));
		
		//String as = paramMapList.stream().map(String::toLowerCase).sorted((a, b) -> a.compareTo(b)).collect(Collectors.joining(","));
		
		//多级分组
		Map<Dish.DishType, Map<Dish.CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = 
				menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy((Dish dish) -> { 
					if(dish.getCalories() <=200) {
						return CaloricLevel.DIET;
					}
					if(dish.getCalories() <=300) {
						return CaloricLevel.NORMAL;
					}
					return CaloricLevel.FAT;})));
		System.out.println(dishesByTypeCaloricLevel);
		
		//求值
		int totalCalories = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
		totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
		System.out.println(totalCalories);
		
		//分组统计
		Map<Dish.DishType, Integer> statistic = menu.stream().collect(
				Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
		System.out.println(statistic);
		
		Map<Dish.DishType, Long> typesCount = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
		System.out.println(typesCount);
		
		Map<Dish.DishType, Optional<Dish>> mostCaloricByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
		System.out.println(mostCaloricByType);
		
		//这个方法是很有用的，在实际应用中可以对pojo类进行流处理，最后返回dto类流7
		Map<Dish.DishType, Dish> mostCaloricByTypeDish = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, 
						Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
		System.out.println(mostCaloricByTypeDish);
		
		//分区
		Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(Collectors.partitioningBy(Dish::getVegetarian));
		System.out.println(partitionedMenu);
		
		Map<Boolean, Map<Dish.DishType, List<Dish>>> vegetarianDishesByType = 
				menu.stream().collect(Collectors.partitioningBy(Dish::getVegetarian, Collectors.groupingBy(Dish::getType)));
		System.out.println(vegetarianDishesByType);
	}
}
