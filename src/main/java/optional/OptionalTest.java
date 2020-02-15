package optional;

import org.junit.Assert;
import org.junit.Test;
import pojo.User;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author ShaoJiale
 * Date: 2020/2/15
 */
public class OptionalTest {
    @Test(expected = NoSuchElementException.class)
    public void createEmptyOptionalAndGet() {
        Optional<User> opt = Optional.empty();
        opt.get();
    }

    @Test(expected = NullPointerException.class)
    public void createOptionalWithNull() {
        Optional<User> opt = Optional.of(null);
    }

    @Test
    public void getObjectFromOptional() {
        String name = "Bruce";
        Optional<String> opt = Optional.ofNullable(name);
        Assert.assertEquals(name, opt.get());
    }

    @Test
    public void checkIfObjectPresents_1() {
        User user = new User.Builder()
                .name("Bruce")
                .age(20)
                .build();
        Optional<User> opt = Optional.ofNullable(user);

        Assert.assertTrue(opt.isPresent());

        Assert.assertEquals(user.getName(), opt.get().getName());
        Assert.assertEquals(user.getAge(), opt.get().getAge());
    }

    @Test
    public void checkIfObjectPresents_2() {
        User user = new User.Builder()
                .name("Eliza")
                .age(21)
                .build();
        Optional<User> opt = Optional.ofNullable(user);

        opt.ifPresent(u -> {
            Assert.assertEquals(user.getName(), opt.get().getName());
            Assert.assertEquals(user.getAge(), opt.get().getAge());
        });
//        opt.ifPresent(new Consumer<User>() {
//            @Override
//            public void accept(User user) {
//                Assert.assertEquals(user.getName(), opt.get().getName());
//                Assert.assertEquals(user.getAge(), opt.get().getAge());
//            }
//        });
    }

    @Test
    public void getObjectOrElse() {
        User user1 = null;
        User user2 = new User.Builder()
                .name("Jack")
                .age(34)
                .build();

        User res = Optional.ofNullable(user1).orElse(user2);
        Assert.assertEquals(res, user2);
    }

    /**
     * Pay attention to the differences between the two methods below.
     * The first one will create the default object even {@link Optional#ofNullable(Object)}
     * is not null while the second method wouldn't.
     *
     * @see Optional#orElse(Object)
     * @see Optional#orElseGet(Supplier)
     */
    @Test
    public void orElseGet() {
        User user1 = null;
        User res = Optional.ofNullable(user1).orElseGet(() -> new User.Builder()
                .name("Sasha")
                .age(27)
                .build());
        Assert.assertEquals("Sasha", res.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void throwExceptionIfNull() {
        User user = null;
        User res = Optional.ofNullable(user).orElseThrow();
    }
}
