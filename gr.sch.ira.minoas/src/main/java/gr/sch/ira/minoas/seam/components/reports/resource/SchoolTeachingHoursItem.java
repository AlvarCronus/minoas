package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.seam.components.reports.TeachingVoidAnalysisReport;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SchoolTeachingHoursItem {

	private Integer availableHours = 0;

	private Integer requiredHours = 0;

	private School school;

	/**
	 * 
	 */
	public SchoolTeachingHoursItem() {
	}

	/**
	 * @param school
	 * @param availableHours
	 * @param requiredHours
	 */
	public SchoolTeachingHoursItem(School school, Integer availableHours, Integer requiredHours) {
		super();
		this.school = school;
		this.availableHours = availableHours;
		this.requiredHours = requiredHours;
	}

	/**
	 * @return the availableHours
	 */
	public Integer getAvailableHours() {
		return availableHours;
	}

	public Integer getMissingHours() {
		return (availableHours - requiredHours);
	}

	public Integer getMissingReqularEmployees() {
		return (getMissingHours()) / TeachingVoidAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}

	/**
	 * @return the requiredHours
	 */
	public Integer getRequiredHours() {
		return requiredHours;
	}

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param availableHours the availableHours to set
	 */
	public void setAvailableHours(Integer availableHours) {
		this.availableHours = availableHours;
	}

	/**
	 * @param requiredHours the requiredHours to set
	 */
	public void setRequiredHours(Integer requiredHours) {
		this.requiredHours = requiredHours;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}

}
