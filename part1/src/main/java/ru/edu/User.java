package ru.edu;

import java.util.Objects;

public class User {
    private String login;
    private String password;

    private Basket basket;

    public User(String login, String password, Basket basket) {
        this.login = login;
        this.password = password;
        this.basket = basket;
    }

    public Basket getBasket() {
        return basket;
    }

    public String getLogin() {
        return login;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public boolean isAuth(String login, String password){
        return Objects.equals(this.login, login) && Objects.equals(this.password, password);
    }

    @Override
    public String toString() {
        return "User: \n" +
                "   Login: " + login + '\n' +
                "   " + basket;
    }
}
