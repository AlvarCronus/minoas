package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.WorkExperienceHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "workExperiencesManagement")
@Scope(ScopeType.PAGE)
public class WorkExperiencesManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	
	@In(required=false)
	private WorkExperienceHome workExperienceHome;
	
	/**
	 * Employees current leave history
	 */
	@DataModel(scope=ScopeType.PAGE, value="workExperienceHistory")
	private Collection<WorkExperience> workExperienceHistory = null;
	
	@Factory(value="workExperienceHistory",autoCreate=true)
	public void constructWorkExperienceHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing work experience history for employee #0", employee);
	    setWorkExperienceHistory(coreSearching.getWorkExperienceHistory(employee));
	}
	
	
	
	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	/**
	 * @param employeeHome the employeeHome to set
	 */
	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}
	
    /**
     * @return the workExperienceHistory
     */
    public Collection<WorkExperience> getWorkExperienceHistory() {
        return workExperienceHistory;
    }

    /**
     * @param workExperienceHistory the workExperienceHistory to set
     */
    public void setWorkExperienceHistory(Collection<WorkExperience> workExperienceHistory) {
        this.workExperienceHistory = workExperienceHistory;
    }


//    @SuppressWarnings("unchecked")
//    @Factory(value = "workExperienceTypes")
//    public void initializeWorkExperienceTypes() {
//        workExperienceTypes = getEntityManager().createQuery("SELECT s FROM WorkExperienceType s ORDER BY s.title ASC")
//                .getResultList();
//    }
    
    
//    /**
//     * @return the employeeActiveLeave
//     */
//    public Leave getEmployeeActiveLeave() {
//        return employeeActiveLeave;
//    }
//
//
//    /**
//     * @param employeeActiveLeave the employeeActiveLeave to set
//     */
//    public void setEmployeeActiveLeave(Leave employeeActiveLeave) {
//        this.employeeActiveLeave = employeeActiveLeave;
//    }
    
//    
    public String modifyWorkExperience() {
        if(workExperienceHome != null && workExperienceHome.isManaged()) {
            info("trying to modify work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
            	facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις τροποποιήσατε, είναι μή αποδεκτές.");
                return ACTION_OUTCOME_FAILURE;
            } else {
                info("employee's #0 work experience #1 has been updated!", workExperienceHome.getInstance().getEmployee(), workExperienceHome.getInstance());
                workExperienceHome.update();
                return ACTION_OUTCOME_SUCCESS;
            }
            
            
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is not managed, thus #1 work experience can't be modified.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
//       /**
//     * Checks if a leave should be set active in regards to the reference date.
//     * @param leave
//     * @param referenceDate
//     * @return
//     */
//    protected boolean leaveShouldBeActivated(Leave leave, Date referenceDate) {
//        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
//        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
//        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
//        if((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
//            return true;
//        } else return false;
//    }
//    /**
//     * TODO: We need to re-fresh the method
//     * @param leave
//     * @param addMessages
//     * @return
//     */
//    protected boolean validateLeave(Leave leave, boolean addMessages) {
//        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
//        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
//        /* check if the dates are correct */
//        if (established.after(dueTo)) {
//
//            if (addMessages)
//                facesMessages
//                        .add(Severity.ERROR,
//                                "H ημ/νία έναρξης είναι μεταγενέστερη της ημ/νιας λήξης της άδειας. Μάλλον πρέπει να κάνεις ενα διάλειμα.");
//            return false;
//        }
//
//        Collection<Leave> current_leaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance());
//        for (Leave current_leave : current_leaves) {
//            if (current_leave.getId().equals(leave.getId()))
//                continue;
//            Date current_established = DateUtils.truncate(current_leave.getEstablished(), Calendar.DAY_OF_MONTH);
//            Date current_dueTo = DateUtils.truncate(current_leave.getDueTo(), Calendar.DAY_OF_MONTH);
//            if (DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
//                if (addMessages)
//                    facesMessages.add(Severity.ERROR,
//                            "Υπάρχει ήδη άδεια με τις ημερομηνίες που εισάγατε. Μήπως να πιείτε κανα καφεδάκι ;");
//                return false;
//            }
//
//            if (DateUtils.isSameDay(established, current_dueTo)) {
//
//                if (addMessages)
//                    facesMessages
//                            .add(Severity.ERROR,
//                                    "Η ημ/νία έναρξης της άδειας πρέπει να είναι μεταγενέστερη της ημ/νιας λήξης της προηγούμενης άδειας.");
//                return false;
//            }
//
//            if ((established.before(current_established) && dueTo.after(current_established))
//                    || (established.after(current_established) && dueTo.before(current_dueTo))
//                    || (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
//                if (addMessages)
//                    facesMessages
//                            .add(Severity.ERROR,
//                                    "Υπάρχει επικαλυπτόμενο διάστημα με υπάρχουσες άδειες. Μήπως να κάνεις ένα διάλειμα για καφεδάκο για να ξεσκοτίσεις ;");
//                return false;
//            }
//
//        }
//
//        return true;
//
//    }
//	
	

}