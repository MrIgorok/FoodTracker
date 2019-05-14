CREATE TABLE Ingredient (
  id  SERIAL PRIMARY KEY,
  name VARCHAR(64) UNIQUE NOT NULL,
  kilo_calories_per_100_grams FLOAT NOT NULL,
  proteins_per_100_grams FLOAT NOT NULL,
  carbohydrates_per_100_grams FLOAT NOT NULL,
  fats_per_100_grams FLOAT NOT NULL
);

CREATE TABLE Dish (
  id SERIAL PRIMARY KEY,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE Dish_Ingredient_Relations (
  dish_fk INT NOT NULL,
  ingredient_fk INT NOT NULL,
  ingredient_weight DOUBLE PRECISION NOT NULL,
  FOREIGN KEY(dish_fk) REFERENCES Dish(id) ON DELETE CASCADE,
  FOREIGN KEY(ingredient_fk) REFERENCES Ingredient(id) ON DELETE RESTRICT,
  UNIQUE (dish_fk, ingredient_fk)
);

CREATE TABLE User_t (
  id  SERIAL PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  login  VARCHAR(32) UNIQUE NOT NULL,
  password VARCHAR(32) NOT NULL
);

CREATE TABLE User_Food_Info (
  id  SERIAL PRIMARY KEY,
  user_fk INT NOT NULL,
  calories_per_day FLOAT NOT NULL,
  FOREIGN KEY(user_fk) REFERENCES User_t(id) ON DELETE CASCADE
);

CREATE TABLE User_Food_Info_Dish_Relations (
  user_food_info_fk INT NOT NULL,
  dish_fk INT NOT NULL,
  date_time TIMESTAMP NOT NULL,
  FOREIGN KEY(user_food_info_fk) REFERENCES User_Food_Info(id) ON DELETE CASCADE,
  FOREIGN KEY(dish_fk) REFERENCES Dish(id) ON DELETE CASCADE,
  UNIQUE (user_food_info_fk, dish_fk)
);