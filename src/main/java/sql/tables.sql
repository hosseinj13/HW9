CREATE TABLE IF NOT EXISTS admins
(
    admin_id       SERIAL PRIMARY KEY,
    admin_name     VARCHAR(50)        NOT NULL,
    admin_username VARCHAR(50) UNIQUE NOT NULL,
    admin_email    VARCHAR(50) UNIQUE NOT NULL,
    admin_password VARCHAR(50)        NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id       SERIAL PRIMARY KEY,
    name          VARCHAR(50)        NOT NULL,
    username      VARCHAR(50) UNIQUE NOT NULL,
    user_email    VARCHAR(50) UNIQUE NOT NULL,
    user_password VARCHAR(50)        NOT NULL
);

CREATE TABLE IF NOT EXISTS category
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS subCategory
(
    subCategory_id   SERIAL PRIMARY KEY,
    subCategory_name VARCHAR(50) NOT NULL,
    category_id_fk     INT,
    FOREIGN KEY (category_id_fk) REFERENCES category (category_id)
);

CREATE TABLE IF NOT EXISTS product
(
    product_id     SERIAL PRIMARY KEY,
    product_name   VARCHAR(255) NOT NULL,
    product_price  DOUBLE PRECISION,
    product_number INT,
    subCategory_id INT,
    FOREIGN KEY (subCategory_id) REFERENCES subCategory (subCategory_id)
);

CREATE TABLE IF NOT EXISTS factor
(
    factor_id SERIAL PRIMARY KEY,
    user_id   INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS factor_product
(
    factor_id  SERIAL PRIMARY KEY,
    product_id INT,
    FOREIGN KEY (factor_id) REFERENCES factor (factor_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id)
);

