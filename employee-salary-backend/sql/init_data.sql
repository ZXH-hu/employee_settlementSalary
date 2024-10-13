use employee_salary;

INSERT INTO employee_time_manage (employeeNo, employeeName, timeType, salaryMath, workTime)
VALUES ('E12345', '张三', '一线员工', 1, 12);

INSERT INTO employee_time_manage (employeeNo, employeeName, timeType, salaryMath, workTime)
VALUES ('E12345', '李四', '二线员工', 2, 8);

INSERT INTO time_manage (timeType, salaryMath, runStatus)
VALUES ('一线员工', 1, 0), ('二线员工', 2, 0);

