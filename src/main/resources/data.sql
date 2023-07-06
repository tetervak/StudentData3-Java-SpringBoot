INSERT INTO program
    (program_name)
VALUES
    ('Computer Programmer'),
    ('Systems Technology'),
    ('Engineering Technician');

INSERT INTO student
    (first_name, last_name, program_id, program_year, program_coop, international)
VALUES
    ('Bart','Simpson',1,1,true,false),
    ('Lisa','Simpson',2,2,false,true),
    ('Homer','Simpson',2,1,false,false),
    ('Marge','Simpson',3,2,true,true);
