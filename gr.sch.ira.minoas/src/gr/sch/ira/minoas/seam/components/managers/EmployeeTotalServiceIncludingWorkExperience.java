package gr.sch.ira.minoas.seam.components.managers;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.seam.components.BaseSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

@Name("employeeTotalServiceIncludingWorkExperience")
public class EmployeeTotalServiceIncludingWorkExperience extends BaseSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;

    public EmployeeTotalServiceIncludingWorkExperience() {
    }

    @Unwrap
    public Integer calculate() {
        try {
            EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
            if (employeeInfo != null)
                return employeeInfo.getTotalWorkService() + employeeInfo.getSumOfExperience();
            else
                return 0;
        } catch (NullPointerException npe) {
            return 0;
        }
    }

}
