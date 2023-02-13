-- Table: users --
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    age TINYINT NOT NULL
) ENGINE = InnoDB;

-- Table: roles --
CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL
) ENGINE = InnoDB;

-- Table for mapping: user_roles --
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),

    UNIQUE (user_id, role_id)
) ENGINE = InnoDB;

-- Insert data --
INSERT INTO users VALUE (1, 'Ivan', 'Ivanov', 'iva', '1111', 25);

INSERT INTO roles VALUE (1, 'ROLE_USER');
INSERT INTO roles VALUE (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUE (1, 1);