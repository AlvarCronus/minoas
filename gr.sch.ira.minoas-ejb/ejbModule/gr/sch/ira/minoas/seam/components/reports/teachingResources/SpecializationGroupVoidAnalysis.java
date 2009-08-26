package gr.sch.ira.minoas.seam.components.reports.teachingResources;

import java.util.ArrayList;
import java.util.List;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SpecializationGroupVoidAnalysis implements Comparable<SpecializationGroupVoidAnalysis> {

	private List<SpecializationGroupVoidAnalysisItem> resources;

	private SpecializationGroup specializationGroup;
	
	private Integer totalAvailableHours = 0;
	
	private Integer totalRequiredHours = 0;
	
	

	/**
	 * 
	 */
	public SpecializationGroupVoidAnalysis() {
		super();
		resources = new ArrayList<SpecializationGroupVoidAnalysisItem>();
	}

	public SpecializationGroupVoidAnalysisItem addTeachingResource(SpecializationGroupVoidAnalysisItem resource) {
		getResources().add(resource);
		this.totalRequiredHours+=resource.getResource().getRequired();
		this.totalAvailableHours+=resource.getResource().getAvailable();
		return resource;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(SpecializationGroupVoidAnalysis o) {
		return getSpecializationGroup().compareTo(o.getSpecializationGroup());
	}

	/**
	 * @return the resources
	 */
	public List<SpecializationGroupVoidAnalysisItem> getResources() {
		return resources;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @return the totalAvailableHours
	 */
	public Integer getTotalAvailableHours() {
		return totalAvailableHours;
	}

	public Integer getTotalFullFilledRegularEmployees() {
		return getTotalAvailableHours() / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}

	public Integer getTotalMissingHours() {
		return getTotalAvailableHours() - getTotalRequiredHours();
	}

	/**
	 * @return the totalRequiredHours
	 */
	public Integer getTotalRequiredHours() {
		return totalRequiredHours;
	}

	public Integer getTotalRequiredRegularEmployees() {
		return getTotalRequiredHours() / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}

	public boolean hasMissingHours() {
		return getTotalMissingHours() > 0;
	}

	public Integer getTotalVoids() {
		return getTotalRequiredRegularEmployees()-getTotalFullFilledRegularEmployees();
	}
	
	public boolean isVoid() {
		return getTotalVoids() > 0;
	}
	
	/**
	 * @param resources the resources to set
	 */
	public void setResources(List<SpecializationGroupVoidAnalysisItem> resources) {
		this.resources = resources;
	}
	
	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}
	
	/**
	 * @param totalAvailableHours the totalAvailableHours to set
	 */
	public void setTotalAvailableHours(Integer totalAvailableHours) {
		this.totalAvailableHours = totalAvailableHours;
	}
	
	/**
	 * @param totalRequiredHours the totalRequiredHours to set
	 */
	public void setTotalRequiredHours(Integer totalRequiredHours) {
		this.totalRequiredHours = totalRequiredHours;
	}

}