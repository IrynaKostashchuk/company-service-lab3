package org.example;/*
  @author   kosta
  @project   company-service-lab3
  @class  Company
  @version  1.0.0 
  @since 01.03.2024 - 18.27
*/

public class Company {
    private final Company parent;
    private final long employeeCount;

    public Company(Company parent, long employeeCount) {
        this.parent = parent;
        this.employeeCount = employeeCount;
    }

    public Company getParent() {
        return parent;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }
}
