package org.example;/*
  @author   kosta
  @project   company-service-lab3
  @class  CompanyServiceImpl
  @version  1.0.0 
  @since 01.03.2024 - 18.33
*/

import java.util.List;

public class CompanyServiceImpl implements ICompanyService {
    @Override
    public Company getTopLevelParent(Company child) {
        if (child == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }

        if (child.getParent() == null) {
            return child;
        }


        while (child.getParent() != null) {
            child = child.getParent();
        }

        return child;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (company == null || companies == null) {
            throw new IllegalArgumentException("Company and company list cannot be null");
        }

        if (!companies.contains(company)) {
            throw new IllegalArgumentException("Company not found in the list");
        }
        return calculateEmployeeCount(company, companies);
    }

    private long calculateEmployeeCount(Company company, List<Company> companies) {
        long count = company.getEmployeeCount();

        // Recursively calculate employee count for children
        for (Company child : companies) {
            if (child.getParent() == company) {
                count += calculateEmployeeCount(child, companies);
            }
        }

        return count;
    }
}
