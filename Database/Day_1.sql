-- ============================================================
-- Company schema - NTI Database Systems, Day 1 (PostgreSQL 15+)
-- Re-runnable. Runs as one transaction: all or nothing.
-- Plain INTEGER keys (not identity) so seed IDs stay fixed for Day 2.
-- FK columns are deliberately NOT indexed (Day 3 shows why that matters).
-- ============================================================
BEGIN;

DROP TABLE IF EXISTS dependents, employee_projects, employee_phones,
                     department_locations, projects, employees, departments
CASCADE;

CREATE TABLE departments (
    department_id    INTEGER,
    department_name  VARCHAR(30) NOT NULL,
    manager_id       INTEGER,
    mgr_start_date   DATE,
    CONSTRAINT pk_departments PRIMARY KEY (department_id),
    CONSTRAINT uq_dept_name   UNIQUE (department_name)
);

CREATE TABLE employees (
    employee_id    INTEGER,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    street         VARCHAR(100),
    city           VARCHAR(50),
    postal_code    VARCHAR(10),
    salary         NUMERIC(8,2),
    birth_date     DATE,
    hire_date      DATE DEFAULT CURRENT_DATE,
    department_id  INTEGER NOT NULL,
    manager_id     INTEGER,
    CONSTRAINT pk_employees    PRIMARY KEY (employee_id),
    CONSTRAINT chk_emp_salary  CHECK (salary > 0),
    CONSTRAINT fk_emp_dept     FOREIGN KEY (department_id)
                               REFERENCES departments (department_id),
    CONSTRAINT fk_emp_manager  FOREIGN KEY (manager_id)
                               REFERENCES employees (employee_id)
);

-- Circular dependency: added after both tables exist
ALTER TABLE departments
    ADD CONSTRAINT uq_dept_manager UNIQUE (manager_id),
    ADD CONSTRAINT fk_dept_manager FOREIGN KEY (manager_id)
                                   REFERENCES employees (employee_id);

CREATE TABLE department_locations (
    department_id  INTEGER,
    location       VARCHAR(50),
    CONSTRAINT pk_dept_locations PRIMARY KEY (department_id, location),
    CONSTRAINT fk_deptloc_dept   FOREIGN KEY (department_id)
                                 REFERENCES departments (department_id)
                                 ON DELETE CASCADE
);

CREATE TABLE employee_phones (
    employee_id  INTEGER,
    phone        VARCHAR(20),
    CONSTRAINT pk_emp_phones   PRIMARY KEY (employee_id, phone),
    CONSTRAINT fk_empphone_emp FOREIGN KEY (employee_id)
                               REFERENCES employees (employee_id)
                               ON DELETE CASCADE
);

CREATE TABLE projects (
    project_id     INTEGER,
    project_name   VARCHAR(50) NOT NULL,
    location       VARCHAR(50),
    department_id  INTEGER NOT NULL,
    CONSTRAINT pk_projects     PRIMARY KEY (project_id),
    CONSTRAINT uq_project_name UNIQUE (project_name),
    CONSTRAINT fk_proj_dept    FOREIGN KEY (department_id)
                               REFERENCES departments (department_id)
);

CREATE TABLE employee_projects (
    employee_id  INTEGER,
    project_id   INTEGER,
    hours        NUMERIC(5,1),
    CONSTRAINT pk_emp_projects PRIMARY KEY (employee_id, project_id),
    CONSTRAINT fk_ep_employee  FOREIGN KEY (employee_id)
                               REFERENCES employees (employee_id),
    CONSTRAINT fk_ep_project   FOREIGN KEY (project_id)
                               REFERENCES projects (project_id),
    CONSTRAINT chk_ep_hours    CHECK (hours >= 0)
);

CREATE TABLE dependents (
    employee_id     INTEGER,
    dependent_name  VARCHAR(50),
    birth_date      DATE,
    relationship    VARCHAR(20),
    CONSTRAINT pk_dependents PRIMARY KEY (employee_id, dependent_name),
    CONSTRAINT fk_dep_emp    FOREIGN KEY (employee_id)
                             REFERENCES employees (employee_id)
                             ON DELETE CASCADE
);

-- ---------------- Seed data ----------------
INSERT INTO departments (department_id, department_name) VALUES
    (10, 'Engineering'),
    (20, 'Finance'),
    (30, 'Marketing'),
    (40, 'Legal');

INSERT INTO employees (employee_id, first_name, last_name, street, city,
                       postal_code, salary, birth_date, hire_date,
                       department_id, manager_id) VALUES
    (4470,'Hoda', 'Kamal',  '1 Nile St',  'Cairo',     '11511',32000,DATE '1980-04-12',DATE '2010-01-15',10,NULL),
    (4471,'Ahmed','Hassan', '5 Tahrir St','Cairo',     '11512',18000,DATE '1995-03-20',DATE '2019-03-01',10,4470),
    (4472,'Sara', 'Mahmoud','9 Corniche', 'Alexandria','21500',21000,DATE '1993-11-02',DATE '2020-07-15',20,4470),
    (4473,'Omar', 'Khaled', '12 Mokattam','Cairo',     '11571',17500,DATE '1997-06-30',DATE '2021-01-10',10,4471),
    (4474,'Nour', 'Ali',    '3 Zamalek',  'Cairo',     '11211',19000,DATE '1994-09-18',DATE '2022-05-01',30,4470),
    (4475,'Mona', 'Fathy',  '7 Maadi',    'Cairo',     '11728',24000,DATE '1990-02-25',DATE '2018-08-20',20,4472);

UPDATE departments SET manager_id = 4470, mgr_start_date = DATE '2015-01-01' WHERE department_id = 10;
UPDATE departments SET manager_id = 4472, mgr_start_date = DATE '2021-03-01' WHERE department_id = 20;
UPDATE departments SET manager_id = 4474, mgr_start_date = DATE '2023-01-01' WHERE department_id = 30;

INSERT INTO department_locations (department_id, location) VALUES
    (10,'Cairo'), (10,'Alexandria'), (20,'Cairo'), (30,'Giza');

INSERT INTO employee_phones (employee_id, phone) VALUES
    (4471,'0100-111-1111'), (4471,'0122-222-2222'),
    (4472,'0155-333-3333'), (4473,'0111-444-4444');

INSERT INTO projects (project_id, project_name, location, department_id) VALUES
    (1,'Gearbox',  'Cairo',     10),
    (2,'Sensors',  'Alexandria',10),
    (3,'Dashboard','Cairo',     10),
    (4,'Audit2024','Cairo',     20);

INSERT INTO employee_projects (employee_id, project_id, hours) VALUES
    (4471,1,12.5), (4471,2,20.0), (4472,4,15.0),
    (4473,3,30.0), (4473,1, 8.0), (4475,4,22.5);

INSERT INTO dependents (employee_id, dependent_name, birth_date, relationship) VALUES
    (4471,'Ali',  DATE '2020-01-05','Son'),
    (4471,'Layla',DATE '2022-08-11','Daughter'),
    (4472,'Ali',  DATE '2019-04-30','Son');

COMMIT;

-- ---------------- Sanity check (expected counts) ----------------
SELECT 'departments' AS t, count(*) FROM departments                        -- 4
UNION ALL SELECT 'employees',            count(*) FROM employees            -- 6
UNION ALL SELECT 'department_locations', count(*) FROM department_locations -- 4
UNION ALL SELECT 'employee_phones',      count(*) FROM employee_phones      -- 4
UNION ALL SELECT 'projects',             count(*) FROM projects             -- 4
UNION ALL SELECT 'employee_projects',    count(*) FROM employee_projects    -- 6
UNION ALL SELECT 'dependents',           count(*) FROM dependents;          -- 3
