package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.EmployeeCriteria;
import gr.sch.ira.minoas.seam.components.criteria.EmploymentCriteria;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationSearchType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "employmentReport")
@Scope(ScopeType.CONVERSATION)
public class EmploymentReport extends BaseReport {

	@In(create = true, required = true)
	private EmploymentCriteria employmentCriteria;

	@DataModel(value = "reportData")
	private List<EmploymentReportItem> reportData;

	/**
	 * 
	 */
	public EmploymentReport() {
	}

	public void generatePDFReport() throws Exception {

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("EMPLOYEE_TYPE_FILTER",
					employmentCriteria.getType() != null ? getLocalizedMessage(employmentCriteria.getType().getKey())
							: "Όλοι οι Τύποι");
			switch (getEmploymentCriteria().getSpecializationSearchType()) {
			case SPECIALIZATION_GROUP:
				parameters.put("EMPLOYEE_SPECIALIZATION_FILTER",
						employmentCriteria.getSpecializationGroup() != null ? employmentCriteria.getSpecializationGroup()
								.getTitle() : "Όλες οι Ομάδες Ειδικοτήτων");
				break;
			case SPECIALIZATION:
				parameters.put("EMPLOYEE_SPECIALIZATION_FILTER",
						employmentCriteria.getSpecialization() != null ? employmentCriteria.getSpecialization().getTitle()
								: "Όλες οι Ομάδες Ειδικοτήτων");
				break;
			}
			parameters.put("EMPLOYEE_DATE_SEARCH_FILTER", getLocalizedMessage(employmentCriteria.getDateSearchType()
					.getKey()));

			parameters.put("EMPLOYEE_EFFECTIVE_DATE_FILTER", employmentCriteria.getEffectiveDate());
			parameters.put("EMPLOYEE_EFFECTIVE_DATE_FROM_FILTER", employmentCriteria.getEffectiveDateFrom());
			parameters.put("EMPLOYEE_EFFECTIVE_DATE_UNTIL_FILTER", employmentCriteria.getEffectiveDateUntil());

			/* create the leave type helper */
			for (EmployeeType employeeType : getCoreSearching().getEmployeeTypes()) {
				parameters.put(employeeType.name(), getLocalizedMessage(employeeType.getKey()));
			}

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
			byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream(
					"/reports/employeeByType.jasper"), parameters, (JRDataSource) ds);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=EmployeeReportByType.pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}

	}

	/**
	 * 
	 */
	public void generateReport() {

		//Date effectiveDate = getEmploymentCriteria().getEffectiveDate();
		//Date effectiveDateFrom = getEmploymentCriteria().getEffectiveDateFrom();
		//Date effectiveDateUntil = getEmploymentCriteria().getEffectiveDateUntil();
		EmploymentType employeeTye = getEmploymentCriteria().getType();
		SpecializationSearchType specializationSearchType = getEmploymentCriteria().getSpecializationSearchType();
		SpecializationGroup specializationGroup = getEmploymentCriteria().getSpecializationGroup();
		Specialization specialization = getEmploymentCriteria().getSpecialization();
		SchoolYear schoolYear = getEmploymentCriteria().getSchoolYear();

		//DateSearchType dateSearchType = getEmploymentCriteria().getDateSearchType();
		//
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT em FROM Employment em JOIN FETCH em.employee WHERE em.active IS TRUE AND em.employee.active IS TRUE AND em.schoolYear=:schoolYear ");
		
		/*
		switch (dateSearchType) {
		case AFTER_DATE:
			sb.append(" AND em.established >= :effectiveDate) ");
			break;
		case BEFORE_DATE:
			sb.append(" AND em.established <= :effectiveDate) ");
			break;
		case DURING_DATE:
			sb.append(" AND (:effectiveDate BETWEEN em.established AND em.terminated)) ");
			break;
		case DURING_DATE_PERIOD:
			sb.append(" AND (:effectiveDateFrom <= em.established AND  :effectiveDateUntil >= em.terminated)) ");
			break;
		}
		*/
		if (employeeTye != null) {
			sb.append(" AND em.type=:employmentType ");
		}
		

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND em.specialization MEMBER OF g.specializations) ");
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			sb.append(" AND em.specialization=:specialization) ");
		}

		sb.append(" ORDER BY em.type,em.specialization.id, em.employee.lastName, em.employee.firstName");

		Query q = getEntityManager().createQuery(sb.toString());
		q.setParameter("schoolYear", schoolYear);
		
		/*
		if (dateSearchType != DateSearchType.DURING_DATE_PERIOD) {
			q.setParameter("effectiveDate", effectiveDate);

		} else {
			q.setParameter("effectiveDateFrom", effectiveDateFrom);
			q.setParameter("effectiveDateUntil", effectiveDateUntil);
		}
		*/
		if (employeeTye != null) {
			q.setParameter("employmentType", employeeTye);
		}
		
		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			q.setParameter("specializationGroup", specializationGroup);
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			q.setParameter("specialization", specialization);
		}

		Collection<Employment> employments = q.getResultList();
		info("found totally #0 employments matching criteria", employments.size());
		reportData = new ArrayList<EmploymentReportItem>(employments.size());
		for (Employment employment : employments) {
			reportData.add(new EmploymentReportItem(employment));
		}
	}

	/**
	 * @return the employeeCriteria
	 */
	public EmploymentCriteria getEmploymentCriteria() {
		return employmentCriteria;
	}

	/**
	 * @param employeeCriteria the employeeCriteria to set
	 */
	public void setEmploymentCriteria(EmploymentCriteria employeeCriteria) {
		this.employmentCriteria = employeeCriteria;
	}

	/**
	 * @return the reportData
	 */
	public List<EmploymentReportItem> getReportData() {
		return reportData;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(List<EmploymentReportItem> reportData) {
		this.reportData = reportData;
	}

	

	
	

}