package ru.edu;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.edu.exception.WrongPasswordException;

import java.util.*;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static ru.edu.Category.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    Basket basket1;
    Basket basket2;

    User user1;
    User user2;

    Map<String, User> userMap = new HashMap<>();

    @Before
    public void init() {
        basket1 = new Basket().addProduct(SPRING.getProducts().get(0)).addProduct(SUMMER.getProducts().get(1));
        //new Basket(new Product[]{SPRING.getProducts()[0], });
        user1 = new User("lol", "1234", basket1);

        basket2 = new Basket().addProduct(SPRING.getProducts().get(2)).addProduct(SUMMER.getProducts().get(1));
        //new Product[]{SPRING.getProducts()[2], SUMMER.getProducts()[1]});
        user2 = new User("kek", "1111", basket2);

        userMap.put("lol", user1);
        userMap.put("kek", user2);
    }


    @Test
    public void shouldAnswerWithTrue() {
        assertSame(user1.getBasket().getProducts().get(0), SPRING.getProducts().get(0));
        SPRING.addProduct(new Product("happiness", 12, 5));
        ArrayList<Product> productList = SUMMER.getProducts();
        productList.sort(Comparator.comparing(Product::getRating).thenComparing(Product::getPrice));
        SUMMER.sortProducts(productList);
        SPRING.getProducts().forEach(e -> System.out.println(e + "  =  " + e.hashCode()));
        AUTUMN.getProducts().forEach(e -> System.out.println(e + "  =  " + e.hashCode()));
        SUMMER.getProducts().forEach(e -> System.out.println(e + "  =  " + e.hashCode()));
        WINTER.getProducts().forEach(e -> System.out.println(e + "  =  " + e.hashCode()));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test()
    public void testAuthorization() {
        try {
            assertSame(Auth.authorization("lil", "1", userMap), null);
            assertSame(Auth.authorization("lol", "1234", userMap), user1);
            thrown.expect(RuntimeException.class);
            Auth.authorization("lol", "1", userMap);
        } catch (WrongPasswordException e) {
            throw new RuntimeException(e);
        }
    }
}


