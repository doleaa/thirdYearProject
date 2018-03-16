CREATE TABLE recipes (
  recipe_id INT NOT NULL,
  recipe_name VARCHAR(30) NOT NULL,
  PRIMARY KEY (recipe_id)
);

INSERT INTO recipes 
    (recipe_id, recipe_name) 
VALUES 
    (1,'Tacos'),
    (2,'Tomato Soup'),
    (3,'Grilled Cheese');
    
    
CREATE TABLE ingredients (
  ingredient_id INT NOT NULL, 
  ingredient_name VARCHAR(30) NOT NULL,
  ingredient_price INT NOT NULL,
  PRIMARY KEY (ingredient_id)
);

INSERT INTO ingredients
    (ingredient_id, ingredient_name, ingredient_price)
VALUES 
    (1, 'Beef', 5),
    (2, 'Lettuce', 1),
    (3, 'Tomatoes', 2),
    (4, 'Taco Shell', 2),
    (5, 'Cheese', 3),
    (6, 'Milk', 1),
    (7, 'Bread', 2);

    CREATE TABLE recipe_ingredients (
      recipe_id int NOT NULL,
      ingredient_id INT NOT NULL,
      amount INT NOT NULL,
      PRIMARY KEY (recipe_id,ingredient_id)
    );


    INSERT INTO recipe_ingredients
        (recipe_id, ingredient_id, amount)
    VALUES
        (1,1,1),
        (1,2,2),
        (1,3,2),
        (1,4,3),
        (1,5,1),
        (2,3,2),
        (2,6,1),
        (3,5,1),
        (3,7,2);



    select r.recipe_name, i.ingredient_name, re.amount
    from ingredients i, recipes r, recipe_ingredients re
    where r.recipe_name = 'Tacos'
    and re.recipe_id = r.recipe_id
    and i.ingredient_id = re.ingredient_id;

    INSERT INTO recipes
        (recipe_id, recipe_name)
    VALUES
        ((select max(recipe_id) + 1 from recipes) ,'Tomato Tacos');

    INSERT INTO recipe_ingredients
            (recipe_id, ingredient_id, amount)
        VALUES
            ((select recipe_id from recipes where recipe_name='Tomato Tacos'),(select ingredient_id from ingredients where ingredient_name='Tomatoes'),2);

    INSERT INTO recipe_ingredients
            (recipe_id, ingredient_id, amount)
        VALUES
            ((select recipe_id from recipes where recipe_name='Tomato Tacos'),(select ingredient_id from ingredients where ingredient_name='Taco Shell'),1);

    select r.recipe_name, i.ingredient_name, re.amount
    from ingredients i, recipes r, recipe_ingredients re
    where r.recipe_name = 'Tacos'
    and re.recipe_id = r.recipe_id
    and i.ingredient_id = re.ingredient_id;

    delete from recipe_ingredients where recipe_id = (select recipe_id from recipes where recipe_name='Tomato Tacos');
    delete from recipes where recipe_name = 'Tomato Tacos';
