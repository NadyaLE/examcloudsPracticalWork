package ru.edu;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertSame;
import static ru.edu.Category.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    Basket basket1;
    Basket basket2;

    User user1;
    User user2;
    /**
     * Rigorous Test :-)
     */
    @Before
    public void init(){
        basket1 = new Basket().addProduct(SPRING.getProducts().get(0)).addProduct(SUMMER.getProducts().get(1));
                //new Basket(new Product[]{SPRING.getProducts()[0], });
        user1 = new User("lol", "1234", basket1);

        basket2 = new Basket().addProduct(SPRING.getProducts().get(2)).addProduct(SUMMER.getProducts().get(1));
    //new Product[]{SPRING.getProducts()[2], SUMMER.getProducts()[1]});
        user2 = new User("kek", "1111", basket2);
    }


    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println(SPRING);
        System.out.println(SUMMER);
        System.out.println(WINTER);
        System.out.println(user1);
        System.out.println(user2);
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


}


