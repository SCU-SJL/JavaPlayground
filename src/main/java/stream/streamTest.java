package stream;

import org.junit.Assert;
import org.junit.Test;
import pojo.User;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

    /**
     * {@link Stream#sorted()} elements are supposed to implement {@link Comparable}
     * {@link Stream#sorted(Comparator)} use the custom {@link Comparator} to sort the elements
     */
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


    /**
     * Notice the difference between {@link Stream#map(Function)} and {@link Stream#peek(Consumer)},
     * the first method requires a {@link Function}, which means it may has return value. But
     * the last method requires a {@link Consumer}, which means it does not has return value.
     */
    @Test
    public void peekOperation() {
        User u1 = new User.Builder()
                .name("Joe")
                .age(41)
                .build();
        User u2 = new User.Builder()
                .name("Bill")
                .age(46)
                .build();

        List<User> userList = Arrays.asList(u1, u2);

        userList.stream().peek(user -> user.setAge(18)).forEach(System.out::println);
    }

    @Test
    public void aggregateOperation() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        Assert.assertFalse(list.stream().allMatch(e -> e > 10));
        Assert.assertTrue(list.stream().noneMatch(e -> e < 1));
        Assert.assertTrue(list.stream().anyMatch(e -> e == 5));

        Assert.assertEquals(5, list.stream().count());
        Assert.assertEquals(5, list.stream().max(Integer::compareTo).get().intValue());
        Assert.assertEquals(1, list.stream().min(Integer::compareTo).get().intValue());
    }

    @Test
    public void reduceOperation() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);

        Assert.assertEquals(10, list.stream().reduce((n1, n2) -> n1 + n2).get().intValue());
        Assert.assertEquals(110, list.stream().reduce(100, (n1, n2) -> n1 + n2).intValue());
    }

    @Test
    public void collectOperation() {
        User u1 = new User.Builder().name("Martin").age(19).build();
        User u2 = new User.Builder().name("Marven").age(20).build();
        User u3 = new User.Builder().name("Justin").age(21).build();
        List<User> users = Arrays.asList(u1, u2, u3);

        List<Integer> ageList = users.stream().map(User::getAge).collect(Collectors.toList());
        Assert.assertEquals(3, ageList.size());
        Assert.assertEquals(19, ageList.get(0).intValue());

        Set<Integer> ageSet  = users.stream().map(User::getAge).collect(Collectors.toSet());
        Assert.assertTrue(ageSet.contains(21));

        Map<String, Integer> userMap = users.stream().collect(Collectors.toMap(User::getName, User::getAge));
        Assert.assertEquals(20, userMap.get("Marven").intValue());

        Assert.assertEquals(3, users.stream().collect(Collectors.counting()).intValue());
        Assert.assertEquals(21, users.stream().map(User::getAge).collect(Collectors.maxBy(Integer::compareTo)).get().intValue());
        Assert.assertEquals(60, users.stream().collect(Collectors.summarizingInt(User::getAge)).getSum());
        Assert.assertEquals(20, users.stream().collect(Collectors.averagingInt(User::getAge)).intValue());

    }
}
