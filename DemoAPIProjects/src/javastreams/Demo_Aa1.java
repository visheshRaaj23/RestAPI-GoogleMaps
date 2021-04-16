package javastreams;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.Streams;

public class Demo_Aa1 {

	@Test
	public void test_a1() {
		int count = 0;
		List<String> nameObj = new ArrayList<String>();
		nameObj.add("Swathi");
		nameObj.add("Meghana");
		nameObj.add("Aishwarya");
		nameObj.add("Arun");
		nameObj.add("Supriya");
		nameObj.add("Anchal");
		for (String name : nameObj) {
			if (name.startsWith("A")) {
				count++;
			}
		}
		System.out.println(count);
	}

	@Test
	public void test_a2() {
		long count;
		List<String> nameObj = new ArrayList<String>();
		nameObj.add("Swathi");
		nameObj.add("Meghana");
		nameObj.add("Aishwarya");
		nameObj.add("Arun");
		nameObj.add("Supriya");
		nameObj.add("Anchal");
		count = nameObj.stream().filter(s -> s.startsWith("A")).count();
		System.out.println(count);
		count = Stream.of("Swathi", "Meghana", "Aishwarya", "Arun", "Supriya", "Anchal").filter(s ->
		// {
		s.startsWith("A")
		// return false;
		// return true;
		// }
		).count();
		System.out.println(count);

		nameObj.stream().filter(s -> s.length() > 4).forEach(x -> System.out.println(x));

		// To Limit
		nameObj.stream().filter(s -> s.length() > 4).limit(3).forEach(x -> System.out.println(x));

	}

	@Test
	public void streamMap() {
		List<String> nameObj = new ArrayList<String>();
		List<String> nameObj1 = new ArrayList<String>();
		nameObj.add("Swathi");
		nameObj.add("Meghana");
		nameObj.add("Aishwarya");
		nameObj.add("Arun");
		nameObj.add("Supriya");
		nameObj.add("Anchal");
		nameObj1.add("Ganapathy Subramaniyam");
		nameObj1.add("Vishesh");
		nameObj1.add("Avinash");
		nameObj.stream().filter(s -> s.endsWith("a")).sorted().map(x -> x.toUpperCase()).collect(Collectors.toList()).stream()
				.forEach(v -> System.out.println(v));
		Stream<String> newStream = Streams.concat(nameObj.stream(),nameObj1.stream());
		boolean flag=newStream.anyMatch(s -> s.equalsIgnoreCase("Swathi"));
		System.out.println(flag);
		Assert.assertTrue(flag);
	}
	
	@Test
	public void streamNumbers() {
		List<Integer> numbers = Arrays.asList(3,2,2,7,5,1,9,7);
		List<Integer> sortedNumbers = numbers.stream().distinct().sorted().collect(Collectors.toList());
		System.out.println(sortedNumbers);
	}

}
