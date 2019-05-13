INSERT INTO Ingredient (name, kilo_calories_per_100_grams, proteins_per_100_grams, carbohydrates_per_100_grams, fats_per_100_grams)
  VALUES ('Egg', 157.0, 12.7, 0.7, 11.5), ('Butter', 746.71, 0.54, 0.78, 82.48);

INSERT INTO Dish(name) VALUES ('Napoleon cake');

INSERT INTO Dish_Ingredient_Relations(dish_fk, ingredient_fk, ingredient_weight) VALUES (1, 1, 300.0), (1, 2, 400.0);

INSERT INTO User_t(login, password, name) VALUES ('login', 'password', 'name');

INSERT INTO User_Food_Info(user_fk, calories_per_day) VALUES (1, 2150.0);

INSERT INTO User_Food_Info_Dish_Relations(user_food_info_fk, dish_fk, date_time) VALUES (1, 1, '2019-05-12 17:13:23');
