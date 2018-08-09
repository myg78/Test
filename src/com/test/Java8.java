package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.test.beans.Car;
import com.test.beans.Person;
import com.test.service.LogService;
import com.test.service.LogServiceImpl;
import com.test.service.PrintService;
import com.test.service.TestService;
import com.test.service.TestServiceImpl;
import com.test.util.StaticClass;

public class Java8 {

	public static void main(String[] args) {
		
		//testLambda();
		//testForEach();
		//testDefaultInterface();
		//testStreamFilter();
		//testStreamMatch();
		testStreamMapReduce();
		//testMethodReference();
	}
	
	private static void testLambda() {
		
		//implement inline
		TestService testService = new TestService() {
			@Override
			public String doService(String param) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		//implement separate class
		TestService testService2 = new TestServiceImpl();
		System.out.println(testService2.doService("mykz"));
		
		//lambda single line
		TestService testService3 = (param) -> "hello " + param;
		System.out.println(testService3.doService("mykz"));
		
		//lambda multiple lines
		TestService testService4 = (param) -> {
			return "hello there " + param;
		};
		System.out.println(testService4.doService("mykz"));
		
		//void lambda single line
		LogService logService1 = () -> System.out.println("log");
		logService1.log();
		
		//void lambda multiple lines
		LogService logService2 = () -> {
			String text = "test";
			System.out.println("log " + text);
		};
		logService2.log();
		
		//void param lambda single line
		PrintService printService1 = (param) -> System.out.println("print " + param);
		printService1.print("mykz");
		
		PrintService printService2 = (param) -> {
			String text = "temp";
			System.out.println("print " + text + " " + param);
		};
		printService2.print("mykz");
	}
	
	private static void testForEach() {
		
		List<String> list = new ArrayList<>();
		list.add("tony");
		list.add("peter");
		list.add("steve");
		
		//forEach implement Consumer
		list.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println("hello " + t);
			}
		});
		
		//forEach lambda single line
		list.forEach((current)->System.out.println("hello there " + current));
		
		//forEach lambda multiple lines
		list.forEach((current)->{
			String text = "hi there " + current;
			System.out.println(text);
		 }
		);
		
		//forEach method reference
		list.forEach(System.out::println);
	}
	
	private static void testDefaultInterface() {
		
		LogService logService = new LogServiceImpl();
		
		//abstract method
		logService.log();
		
		//default method
		logService.log("mykz");
		
		//static
		LogService.logger("mykz");
	}
	
	private static void testStreamFilter() {
		
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<100; i++) list.add(i);
		
		//sequential stream
		Stream<Integer> sequential = list.stream();
		Stream<Integer> filtered = sequential.filter(p -> p>80);
		filtered.forEach(current -> System.out.println("sequential= " + current));
		
		//parallel stream
		Stream<Integer> parallel = list.parallelStream();
		Stream<Integer> parallel2 = list.parallelStream();
		Stream<Integer> filteredParallel1 = parallel.filter(p -> p>80);
		//Stream<Integer> filteredParallel2 = parallel.filter(p -> p<40);//not possible to reuse after stream is used
		Stream<Integer> filteredParallel2 = parallel2.filter(p -> p<40);
		
		filteredParallel1.forEach(current -> System.out.println("parallel-1= " + current));
		filteredParallel2.forEach(current -> System.out.println("parallel-2= " + current));
	}
	
	private static void testStreamMatch() {
		Stream<Integer> numbers = Stream.of(1,2,3,4,5);
		
		//List<Integer> intList = numbers.collect(Collectors.toList());
		//intList.forEach(i->System.out.println("number=" + i));
		
		//boolean match = numbers.anyMatch(i -> i==6);
		//System.out.println("match: " + match);
		
		boolean allMatch = numbers.allMatch(i -> i<5);
		System.out.println("allMatch: " + allMatch);
	}
	
	private static void testStreamMapReduce() {
		
		//
		List<String> list = new ArrayList<>();
		list.add("Alice");
		list.add("tony");
		list.add("ab");
		list.add("cD");
		list.add("FEED");
		
		//map uppercase
		Stream<String> stream = list.stream();
		//Stream<String> mapped = stream.map((i)-> i.toUpperCase());
		Stream<String> mapped = stream.map(String::toUpperCase);
		//mapped.forEach((i)->System.out.println("i="+ i));
		mapped.forEach(System.out::println);
		
		//reduce integer sum
		Stream<Integer> numbers = Stream.of(1,2,3,4,5);
		//int sum = numbers.reduce(0, (x,y)->x+y);
		int sum = numbers.reduce(0, Integer::sum);
		System.out.println("sum=" + sum);
		
		//map reduce
		List<Person> persons = new ArrayList<>(); 
		persons.add(new Person("tony", "male", 27));
		persons.add(new Person("peter", "male", 18));
		persons.add(new Person("bruce", "male", 29));
		persons.add(new Person("natasha", "female", 21));
		persons.add(new Person("wanda", "female", 20));
		
		//map males
		List<String> females = persons.stream().filter((p)->p.getGender().equalsIgnoreCase("female")).map(Person::getName).collect(Collectors.toList());
		females.forEach(System.out::println);
		
		//map reduce sum
		int sumMaleAges = persons.stream().filter((p)->p.getGender().equalsIgnoreCase("male"))
										  .mapToInt((p)->p.getAge())
										  .reduce(0, (x,y)->x+y);
		
		int sumMaleAges2 = persons.stream().filter((p)->p.getGender().equalsIgnoreCase("male"))
										   .mapToInt(Person::getAge)
										   .reduce(0, Integer::sum);
		
		int sumMaleAges3 = persons.stream().filter((p)->p.getGender().equalsIgnoreCase("male"))
										   .mapToInt(Person::getAge).sum();
		
		System.out.println("sumMaleAges: " + sumMaleAges);
		System.out.println("sumMaleAges2: " + sumMaleAges2);
		System.out.println("sumMaleAges3: " + sumMaleAges3);
		
		//map reduce average
		double avgMaleAges = persons.stream().filter((p)->p.getGender().equalsIgnoreCase("male"))
											 .mapToInt(Person::getAge)
											 .average().getAsDouble();
		System.out.println("avgMaleAges: " + avgMaleAges);
	}
	
	private static void testMethodReference() {
		
		PrintService printService1 = (param) -> System.out.println("print " + param);
		printService1.print("mykz");
		
		PrintService printService2 = System.out::println;
		printService2.print("peter");
		
		PrintService printService3 = StaticClass::doSomething;
		printService3.print("kimi");
		
		PrintService printService4 = (param)->StaticClass.returnSomething(param);
		printService4.print("tony");
		
		PrintService printService5 = StaticClass::returnSomething;
		printService5.print("bruce");
		
		List<Car> cars = new ArrayList<>();
		cars.add(new Car("2016", "Honda", "Civic"));
		cars.add(new Car("2017", "Chevrolet", "Camaro"));
		cars.add(new Car("2018", "Ford", "Mustang"));
		
		cars.forEach(Car::printCar);
		cars.forEach((param)->{
			String str = param.getMake();
			StaticClass.doSomething(str);
		});
		
	}
	
}

