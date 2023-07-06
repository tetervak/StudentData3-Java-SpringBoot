INSERT INTO program
    (program_name)
VALUES
    ('Computer Programmer'),
    ('Systems Technology'),
    ('Engineering Technician');

INSERT INTO student
    (first_name, last_name, program_id, international, program_year, program_coop)
VALUES
    ('Bart','Simpson',1,false,1,true),
    ('Lisa','Simpson',2,true,2,false),
    ('Homer','Simpson',2,false,1,false),
    ('Marge','Simpson',3,true,2,true);
