package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DepartmentTest {

    @Test
    public void isValidDepartment_validDepartment_returnsTrue() {
        assertTrue(Department.isValidDepartment("Sales"));
        assertTrue(Department.isValidDepartment("Marketing Department"));
        assertTrue(Department.isValidDepartment("123 Department"));
    }

    @Test
    public void isValidDepartment_invalidDepartment_returnsFalse() {
        assertFalse(Department.isValidDepartment(""));
        assertFalse(Department.isValidDepartment(" "));
        assertFalse(Department.isValidDepartment("Department!"));
    }

    @Test
    public void equals_sameDepartment_returnsTrue() {
        Department department1 = new Department("Sales");
        Department department2 = new Department("Sales");
        assertTrue(department1.equals(department2));
    }

    @Test
    public void equals_differentDepartment_returnsFalse() {
        Department department1 = new Department("Sales");
        Department department2 = new Department("Marketing");
        assertFalse(department1.equals(department2));
    }

    @Test
    public void testToStringWithNameHyphen() {
        Department department = new Department("-");
        assertTrue(department.toString().equals(""));
    }

    @Test
    public void testToStringWithValidName() {
        Department department = new Department("Tech");
        assertTrue(department.toString().equals("Tech"));
    }
}
