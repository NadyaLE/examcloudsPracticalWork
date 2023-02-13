package ru.edu;

import org.jetbrains.annotations.Nullable;
import ru.edu.exception.WrongLoginException;
import ru.edu.exception.WrongPasswordException;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Auth {
    private static User[] users = new User[]{new User("lol", "1", new Basket()),
            new User("kek", "2", new Basket()),
            new User("ok", "3", new Basket())};
    private static Locale current = new Locale("ru", "RU");
    private static ResourceBundle rb = ResourceBundle.getBundle("text", current);
    private static NumberFormat nf = NumberFormat.getInstance(current);
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> userMap = new HashMap<>();

    static {
        for (User user : users) {
            userMap.put(user.getLogin(), user);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new java.io.PrintStream(System.out, true, rb.getString("encoding")));
        nf.setMaximumFractionDigits(2);
        User user;
        String login, password, confirmPassword;

        while (true) {
            System.out.print(rb.getString("login"));
            login = scanner.nextLine();
            System.out.print(rb.getString("password"));
            password = scanner.nextLine();
            System.out.print(rb.getString("confirmPassword"));
            confirmPassword = scanner.nextLine();
            try {
                user = authorization(login, password, userMap, rb);
                if (user != null) {
                    System.out.println("-------------------------------------------------------\n" +
                            rb.getString("greetings") + user.getLogin() + "!\n" +
                            "-------------------------------------------------------");
                    getMenu(user);
                } else {
                    if (checkLoginAndPass(login, password, confirmPassword, rb)) {
                        userMap.put(login, new User(login, password, new Basket()));
                    }
                }
            } catch (WrongPasswordException ex) {
                ex.printStackTrace();
            }
            System.out.println();
        }
    }

    private static void getMenu(User user) {
        Category category;
        System.out.println(rb.getString("menu"));
        printEnumInLocale(Menu.values(), rb);
        while (true) {
            System.out.print("\n" + rb.getString("selectMenuItem"));
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("\n" + rb.getString("directoryList"));
                    printEnumInLocale(Category.values(), rb);
                    continue;
                }
                case 2: {
                    System.out.print(rb.getString("selectCatalog"));
                    choice = scanner.nextInt();
                    int i = 0;
                    if ((category = Category.valueOf(choice - 1)) != null) {
                        System.out.printf("%-10s%-15s%10s%10s%n---------------------------------------------%n",
                                rb.getString("number"), rb.getString("name"),
                                rb.getString("price"), rb.getString("rating"));
                        for (Product product : category.getProducts()) {
                            System.out.println(String.format("%-10d", i++ + 1) + toStringInLocaleWithFormat(product, rb, nf));
                        }
                    }
                    continue;
                }
                case 3: {
                    System.out.print(rb.getString("catalogNumber"));
                    choice = scanner.nextInt();
                    if ((category = Category.valueOf(choice - 1)) != null) {
                        System.out.print(rb.getString("productNumber"));
                        choice = scanner.nextInt();
                        if (choice > 0 && choice <= category.getProducts().size()) {
                            Product product = category.getProducts().get(choice - 1);
                            System.out.println(rb.getString("addProduct").substring(0, rb.getString("addProduct").indexOf("\"") + 1) +
                                    rb.getString(product.getName()) +
                                    rb.getString("addProduct").substring(rb.getString("addProduct").indexOf("\"") + 1));
                            if (scanner.next().compareToIgnoreCase("y") == 0) {
                                user.getBasket().addProduct(product);
                            }
                        }
                    }
                    continue;
                }
                case 4: {
                    printPurchases(user, rb, nf);
                    continue;
                }
                case 5: {
                    double sum = user.getBasket().getProducts().stream().mapToDouble(Product::getPrice).sum();
                    System.out.print(rb.getString("countSum").substring(0, rb.getString("countSum").indexOf("-") + 2) +
                            nf.format(sum) +
                            rb.getString("countSum").substring(rb.getString("countSum").indexOf("-") + 2) + "  : ");
                    if (scanner.next().startsWith("y")) {
                        printPurchases(user, rb, nf);
                        user.getBasket().getProducts().clear();
                    }
                    continue;
                }
                default:
                    break;
            }
            break;
        }
    }

    public static User authorization(final String login, final String password, Map<String, User> source, @Nullable ResourceBundle rb)
            throws WrongPasswordException {
        if (source.containsKey(login)) {
            User user = source.values().stream().filter(u -> u.isAuth(login, password)).findAny().orElse(null);
            if (user == null)
                throw new WrongPasswordException((rb == null) ? "Entered wrong password!" : rb.getString("enteredWrongPassword"));
            return user;
        }
        return null;
    }

    public static User authorization(final String login, final String password, Map<String, User> source)
            throws WrongPasswordException {
        return authorization(login, password, source, null);
    }

    public static <E extends Enum<E>> void printEnumInLocale(E[] en, ResourceBundle rb) {
        Arrays.stream(en).forEach(e -> System.out.println(e.ordinal() + 1 + ". " + ((rb != null) ? rb.getString(e.name()) : e)));
    }

    public static String toStringInLocaleWithFormat(Product product, @Nullable ResourceBundle rb, @Nullable NumberFormat nf) {
        return String.format("%-15s", ((rb == null) ? product.getName() : rb.getString(product.getName().toLowerCase()))) +
                ((nf == null) ? String.format("%10.2f%10.2f", product.getPrice(), product.getRating()) :
                        String.format("%10s%10s", nf.format(product.getPrice()), nf.format(product.getRating())));
    }

    public static String toStringWithFormat(Product product) {
        return toStringInLocaleWithFormat(product, null, null);
    }

    public static void printPurchases(User user, ResourceBundle rb, NumberFormat nf) {
        System.out.printf("%-7s%34s%n%n", rb.getString("date"), LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy", rb.getLocale())));
        System.out.printf("%-15s%-15s%10s%n-----------------------------------------%n",
                rb.getString("products"), rb.getString("category"), rb.getString("price"));
        user.getBasket().getProducts().forEach(e -> System.out.printf("%-15s%-15s%10s%n", rb.getString(e.getName()),
                rb.getString(Category.getCategoryByProduct(e).toString()), nf.format(e.getPrice())));
        System.out.printf("-----------------------------------------%n%-7s%34s%n", rb.getString("total"),
                nf.format(user.getBasket().getProducts().stream().mapToDouble(Product::getPrice).sum()));
    }

    public static boolean checkLoginAndPass(String login, String password, String confirmPassword, @Nullable ResourceBundle rb) {
        try {
            if (!login.matches("[a-zA-Z\\d_]+") || login.length() > 20) {
                throw new WrongLoginException((rb == null) ? "Wrong login!" : rb.getString("wrongLogin"));
            }
            if (!password.matches("[a-zA-Z\\d_]+") || !Objects.equals(password, confirmPassword) || password.length() > 20) {
                throw new WrongPasswordException((rb == null) ? "Wrong password!" : rb.getString("wrongPassword"));
            }
        } catch (WrongPasswordException | WrongLoginException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean checkLoginAndPass(String login, String password, String confirmPassword) {
        return checkLoginAndPass(login, password, confirmPassword, null);
    }

}

enum Menu {
    PRODUCT_CATEGORIES("View a list of product catalogs"),
    CATEGORYS_PRODUCTS("View a list of products in a specific catalog"),
    ITEM_TO_BASKET("Select item to basket"),
    DISPLAY_BASKET("Display items in basket"),
    BUY("Buying items in the shopping cart"),
    EXIT("Change user");

    private final String defaultValue;

    Menu(String value) {
        this.defaultValue = value;
    }

    @Override
    public String toString() {
        return defaultValue;
    }

}
