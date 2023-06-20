DROP DATABASE Code_Fanatic;
CREATE DATABASE IF NOT EXISTS Code_Fanatic;

USE Code_Fanatic;


-- create table for users
CREATE TABLE users (
  -- id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  -- email VARCHAR(50) UNIQUE NOT NULL,
  role varchar(10) DEFAULT 'user',
  password VARCHAR(255) NOT NULL,
  create_time TIMESTAMP,
  -- wallet DECIMAL(10, 2),
  PRIMARY KEY (username)
);

-- create table for products
CREATE TABLE products (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    type VARCHAR(15) CHECK (type in ("Course", "Merchandise")),
    price DECIMAL(10,2),
    PRIMARY KEY (id)
);

-- create table for merchandise, subclass of products
CREATE TABLE merchandise (
	product_id INT NOT NULL,
    amount INT DEFAULT 0,
    PRIMARY KEY (product_id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

    
-- create table for courses, subclass of products
CREATE TABLE courses (
	product_id INT NOT NULL,
	lesson_count INT DEFAULT 0,
	PRIMARY KEY (product_id),
	FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE cascade ON DELETE cascade
);

-- create table for lessons
CREATE TABLE lessons (
  number INT NOT NULL default 0,
  course_id INT NOT NULL,
  title VARCHAR(50) NOT NULL,
  content TEXT,
  PRIMARY KEY (number, course_id),
  FOREIGN KEY (course_id) REFERENCES courses(product_id) ON UPDATE cascade ON DELETE cascade
);



-- create table for orders
CREATE TABLE orders (
  id INT NOT NULL AUTO_INCREMENT,
  user_username VARCHAR(20) NOT NULL,
  order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_username) REFERENCES users(username) ON UPDATE cascade ON DELETE no action
);

-- create table to keep track of the multivalue attribute "Products" that would relate to orders
CREATE TABLE order_products (
	order_id INT NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(50),
    product_type VARCHAR(15) CHECK (product_type in ("Course", "Merchandise")),
    product_price DECIMAL(10,2),
    quantity int NOT NULL default 1,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE user_products_owned (
	user_username VARCHAR(20) NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (user_username, product_id),
    FOREIGN KEY (user_username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- create table for user lessons
CREATE TABLE user_lessons (
  user_username VARCHAR(20) NOT NULL,
  lesson_number INT NOT NULL,
  course_id INT NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'uncompleted',
  PRIMARY KEY (user_username, lesson_number, course_id),
  FOREIGN KEY (user_username) REFERENCES users(username),
  FOREIGN KEY (lesson_number) REFERENCES lessons(number),
  FOREIGN KEY (course_id) REFERENCES lessons(course_id)
);

CREATE TABLE cart (
user_username VARCHAR(20) NOT NULL,
product_id INT NOT NULL,
quantity INT NOT NULL DEFAULT 1,
PRIMARY KEY (user_username, product_id),
FOREIGN KEY (user_username) REFERENCES users(username),
FOREIGN KEY (product_id) REFERENCES products(id)
);

DELIMITER $$
CREATE TRIGGER update_lesson_count BEFORE INSERT ON lessons
FOR EACH ROW
BEGIN
	UPDATE courses
    SET lesson_count = lesson_count + 1
    WHERE product_id = NEW.course_id;
    SET NEW.number = (
		SELECT lesson_count
		FROM courses
		WHERE NEW.course_id = product_id);
END;
$$
CREATE TRIGGER cart_delete_zero_quantity BEFORE UPDATE ON cart
FOR EACH ROW
BEGIN
	IF NEW.quantity <= 0 THEN
    DELETE FROM cart WHERE user_username = NEW.user_username AND product_id = NEW.product_id;
    END IF;
END;
$$
CREATE TRIGGER user_owns_product_ordered AFTER INSERT ON order_products
FOR EACH ROW
BEGIN
	DECLARE p_type VARCHAR(15);
    DECLARE p_id INT;
    DECLARE u_username VARCHAR(20);
    
    SELECT new.product_type, new.product_id INTO p_type, p_id;
    
    SELECT DISTINCT user_username INTO u_username
    FROM orders 
    WHERE id = new.order_id;
    
    IF p_type <> 'Merchandise' THEN
		INSERT INTO user_products_owned (user_username, product_id)
		VALUES (u_username, p_id);
	END IF;
END;
$$
DELIMITER ;

