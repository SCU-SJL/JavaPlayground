package stream;

import org.junit.Test;
import pojo.User;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author ShaoJiale
 * Date: 2020/2/15
 */
public class streamTest {

    @Test
    public void createStream() throws IOException {
        // create stream from collections
        List<Integer> list = new LinkedList<>();
        Stream<Integer> sequenceStream = list.stream();
        Stream<Integer> parallelStream = list.parallelStream();

        // create stream from array
        Integer[] nums = new Integer[10];
        Stream<Integer> numStream = Arrays.stream(nums);

        // create stream from Stream
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<Integer> stream2 = Stream.iterate(0, n -> n + 2).limit(5);
        stream2.forEach(System.out::print);
        Stream<Double> stream3 = Stream.generate(Math::random).limit(5);
        System.out.println();
        stream3.forEach(System.out::println);

        // create stream from reader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(streamTest.class.getClassLoader().getResourceAsStream("database.properties"))
        );
        Stream<String> lineStream = reader.lines();
        lineStream.forEach(System.out::println);

        // create stream from pattern
        Pattern pattern = Pattern.compile(",");
        Stream<String> stringStream = pattern.splitAsStream("Hello,world!");
        stringStream.forEach(System.out::println);
    }

    @Test
    public void filterOperations() {
        Stream<Integer> stream = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        Stream<Integer> newStream = stream.filter(num -> num > 5)
                .distinct() // [6, 7, 9, 8, 10, 12, 14]
                .skip(2)    // [9, 8, 10, 12, 14]
                .limit(2);  // [9, 8]
        newStream.forEach(System.out::println);
    }

    /**
     * {@link Stream#map} accept a function and apply it on each element to make it new
     * {@link Stream#flatMap(Function)} accept a function and apply it on each element,
     * then convert the element into a stream, and assemble these streams eventually.
     */
    @Test
    public void mapOperations() {
        List<String> list = Arrays.asList("hello,world", "one,two,three");
        Stream<String> stream = list.stream().map(s -> s.replaceAll(",", " "));
        stream.forEach(System.out::println);    // hello world && one two three
        stream = list.stream().flatMap(s -> {
            String[] afterSplit = s.split(",");
            Stream<String> res = Arrays.stream(afterSplit);
            return res;
        });
        stream.forEach(System.out::println);
    }

    @Test
    public void sortOperation() {
        List<String> list = Arrays.asList("cc", "zz", "aa");
        list.stream().sorted().forEach(System.out::println);

        User u1 = new User.Builder()
                .name("Mellisa")
                .age(22)
                .build();
        User u2 = new User.Builder()
                .name("Jal")
                .age(20)
                .build();

        List<User> userList = Arrays.asList(u1, u2);
        userList.stream().sorted((user1, user2) -> {
            if (user1.getName().equals(user2.getName())) {
                return user1.getAge() - user2.getAge();
            }
            return user1.getName().compareTo(user2.getName());
        }).forEach(System.out::println);
    }
}
