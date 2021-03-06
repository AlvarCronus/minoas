/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */

@Entity
@Table(name = "TEACHING_REQUIREMENT")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeachingRequirement extends BaseIDModel implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "COMMENT", nullable = true, length = 255)
	private String comment;

	@Basic
	@Column(name = "HOURS", nullable = false)
	private Integer hours;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "SCHOOL_ID", nullable = false)
	private School school;

	@ManyToOne
	@JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
	private SchoolYear schoolYear;

	@ManyToOne
	@JoinColumn(name = "SPECIALIZATION_GROUP_ID", nullable = false)
	private SpecializationGroup specialization;

	/**
	 * 
	 */
	public TeachingRequirement() {
		super();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		TeachingRequirement clone = (TeachingRequirement) super.clone();
		clone.setId(null);
		return clone;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the specialization
	 */
	public SpecializationGroup getSpecialization() {
		return specialization;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(SpecializationGroup specialization) {
		this.specialization = specialization;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeachingRequirement [");
		if (hours != null) {
			builder.append("hours=");
			builder.append(hours);
			builder.append(", ");
		}
		if (school != null) {
			builder.append("school=");
			builder.append(school);
			builder.append(", ");
		}
		if (schoolYear != null) {
			builder.append("schoolYear=");
			builder.append(schoolYear);
			builder.append(", ");
		}
		if (specialization != null) {
			builder.append("specialization=");
			builder.append(specialization);
		}
		builder.append("]");
		return builder.toString();
	}

}
