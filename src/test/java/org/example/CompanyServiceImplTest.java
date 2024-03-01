package org.example;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/*
  @author   kosta
  @project   company-service-lab3
  @class  CompanyServiceImplTest
  @version  1.0.0 
  @since 01.03.2024 - 18.34
*/class CompanyServiceImplTest {

    private final Company head = new Company(null, 3);
    private final Company management = new Company(head, 2);
    private final Company development = new Company(management, 7);
    private final Company design = new Company(management, 1);
    private final Company accounting = new Company(head, 1);
    private final Company marketing = new Company(null, 1);

    private final List<Company> companyDepartments= List.of(head, management, development,design,accounting);


    private final ICompanyService companyService = new CompanyServiceImpl();

    @Test
    void whenCompanyHasNoParentThenItIsTopLevel(){
        Assertions.assertEquals(head,companyService.getTopLevelParent(head));
    }

    @Test
    void whenCompanyHasNoParentNoChild(){
        Assertions.assertEquals(marketing,companyService.getTopLevelParent(marketing));
    }

    @Test
    void whenCompanyIsNullThenException(){
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            companyService.getTopLevelParent(null);
        });
        Assertions.assertSame(exception.getClass(), IllegalArgumentException.class);
        Assertions.assertEquals("Company cannot be null", exception.getMessage());

    }

    @Test
    void whenCompanyHasOneStepToTheTopThenFindTop(){
        Assertions.assertEquals(head, companyService.getTopLevelParent(management));
    }

    @Test
    void whenCompanyHasTwoStepToTheTopThenFindTop(){
        Assertions.assertEquals(head, companyService.getTopLevelParent(design));
    }


    //getEmployeeCountForCompanyAndChildren()
    @Test
    void whenCompanyHasChildrenThenCountAll() {
        Assertions.assertEquals(10, companyService.getEmployeeCountForCompanyAndChildren(
                management, companyDepartments
        ));
    }

    @Test
    void whenCompanyIsNotInTheListThenException(){
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            companyService.getEmployeeCountForCompanyAndChildren(marketing, companyDepartments);
        });
        Assertions.assertSame(exception.getClass(), IllegalArgumentException.class);
        Assertions.assertEquals("Company not found in the list", exception.getMessage());

    }

    @Test
    void whenCompanyListIsNullThenException(){
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            companyService.getEmployeeCountForCompanyAndChildren(development, null);
        });
        Assertions.assertSame(exception.getClass(), IllegalArgumentException.class);
        Assertions.assertEquals("Company and company list cannot be null", exception.getMessage());

    }

    @Test
    void whenCompanyListIsEmptyThenException() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            companyService.getEmployeeCountForCompanyAndChildren(development, Collections.emptyList());
        });
        Assertions.assertSame(exception.getClass(), IllegalArgumentException.class);
        Assertions.assertEquals("Company not found in the list", exception.getMessage());
    }

    @Test
    void whenNullCompanyInListThenException() {
        List<Company> companiesWithNull = Arrays.asList(head, null, management, design, development);
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            companyService.getEmployeeCountForCompanyAndChildren(head, companiesWithNull);
        });
        Assertions.assertSame(exception.getClass(), NullPointerException.class);
    }

    @Test
    void whenCompanyHasMultipleLevelsToTheTopThenFindTop() {
        Company grandDad = new Company(null,1);
        Company grandChild = new Company(new Company(grandDad, 10), 15);
        Assertions.assertEquals(grandDad, companyService.getTopLevelParent(grandChild));
    }
}