
--------------------------------------------------  Inner Joins -----------------------------------

--- Employee with department name
SELECT e.first_name, d.department_name
FROM employees e, departments d
Where e.department_id=d.department_id

--- employee with its manager
SELECT e.first_name, m.first_name
FROM employees e, employees m
Where e.manager_id=m.employee_id;

--- department with its manager
SELECT d.department_name, e.first_name
FROM employees e, departments d
Where e.employee_id=d.manager_id;

--- project with its department
SELECT p.project_name, d.department_name
FROM projects p, departments d
WHERE p.department_id = d.department_id

--- employee, projects & hours
SELECT e.first_name,p.project_name,e_p.hours
FROM employees e JOIN employee_projects e_p 
ON e.employee_id= e_p.employee_id
JOIN projects p
ON p.project_id=e_p.project_id


--- All department with employees
SELECT d.department_name,e.first_name
FROM departments d LEFT JOIN employees e
ON d.department_id=e.department_id

--- Departments with no employees
SELECT d.department_name
FROM departments d LEFT JOIN employees e
ON d.department_id=e.department_id
WHERE e.employee_id is null

--- employees with no project
SELECT e.first_name
FROM employees e LEFT JOIN employee_projects e_p
on e.employee_id=e_p.employee_id
WHERE e_p.employee_id is null

--- All department with employees earning over 20000
SELECT d.department_name,e.first_name,e.salary
FROM departments d LEFT JOIN employees e
ON d.department_id=e.department_id
WHERE e.salary > 20000

-------------------------------------------  Aggregation Functions --------------------------
SELECT COUNT(*) AS employees_count, COUNT(e.manager_id) AS employees_With_managers_count,
COUNT(DISTINCT e.manager_id) AS managers_count
FROM employees e

--- Total, Average, highest, lowst salary
SELECT AVG(e.salary) AS avg_salary, max(e.salary) As max_salary, min(e.salary) AS min_salary
FROM employees e

--- Headcount & avg salary per department
SELECT d.department_name,COUNT(e.employee_id) as empl_count, ROUND(AVG(e.salary),2)
FROM employees e join departments d
ON e.department_id=d.department_id
GROUP BY d.department_name

--- Headcount per department and city
SELECT d.department_name,e.city,COUNT(e.employee_id) AS emp_count
FROM employees e join departments d
ON e.department_id=d.department_id
GROUP BY d.department_name,e.city

--- Departments with more than one employee
SELECT d.department_name,COUNT(e.employee_id) AS emp_count
FROM employees e join departments d
ON e.department_id=d.department_id
GROUP BY d.department_name
HAVING COUNT(e.employee_id)>1

--- Departments with avg salary above 20000
SELECT d.department_name,ROUND(AVG(e.salary),2) AS avg_salary
FROM employees e join departments d
ON e.department_id=d.department_id
GROUP BY d.department_name
HAVING AVG(e.salary)>20000

--- Departments with their headcount
SELECT d.department_name,COUNT(e.employee_id) AS emp_count
FROM departments d  left join employees e
ON e.department_id=d.department_id
GROUP BY d.department_name

--- each project with number of employees and total hours
SELECT p.project_name, COUNT (e_p.employee_id) AS employees_count,SUM(e_p.hours) AS total_hours
FROM projects p JOIN employee_projects e_p
ON p.project_id = e_p.project_id
GROUP BY p.project_name

--- Employee with their project count including none
SELECT e.first_name, COUNT (e_p.project_id) AS projects_count
FROM employees e LEFT JOIN employee_projects e_p
ON e.employee_id = e_p.employee_id
GROUP BY e.employee_id

--- Per department headcount & numer of employees earning more than 20000
SELECT d.department_name,COUNT(*) AS headcount,
       COUNT(*) FILTER( WHERE e.salary>20000) AS high_earners
FROM departments d join employees e
ON e.department_id=d.department_id
GROUP BY d.department_name