CREATE TABLE program (
    id INT AUTO_INCREMENT PRIMARY KEY,
    program_name VARCHAR(30)
);
CREATE TABLE student (
     id INT AUTO_INCREMENT PRIMARY KEY,
     first_name VARCHAR(30),
     last_name VARCHAR(30),
     program_id INTEGER NOT NULL,
     program_year INTEGER,
     program_coop BOOLEAN,
     international BOOLEAN,
     FOREIGN KEY(program_id) REFERENCES program(id)
);

