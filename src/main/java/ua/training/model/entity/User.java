package ua.training.model.entity;

import ua.training.model.entity.food.UserFoodInfo;

public class User {
    private long id;
    private String login;
    private String password;
    private String name;

    private UserFoodInfo foodInfo;

    public User() {

    }

    public User(long id, String login, String password, UserFoodInfo foodInfo) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.foodInfo = foodInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserFoodInfo getFoodInfo() {
        return foodInfo;
    }

    public void setFoodInfo(UserFoodInfo foodInfo) {
        this.foodInfo = foodInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
