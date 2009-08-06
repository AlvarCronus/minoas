
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.Audit;
import gr.sch.ira.minoas.model.core.AuditType;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.lang.reflect.ParameterizedType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public abstract class MinoasEntityHome<E> extends EntityHome {

	public static final String PERSITESTENCE_CONTEXT_NAME = "entityManager";

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In()
	private CoreSearching coreSearching;
	
	@In(required=false) FacesMessages facesMessages;
	
	@In(required = false)
	private Identity identity;

	@Logger
	private Log logger;

	private Class<E> persistentClass;
	
	
	protected void foo(AuditType type, String comment) {
		Audit audit = new Audit(type, comment, getPrincipal());
		getEntityManager().persist(audit);
	}
	
	/**
	 * @see org.jboss.seam.framework.EntityHome#create()
	 */
	@Override
	public void create() {
		super.create();
		persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	public E getDefinedInstace() {
		if (isIdDefined()) {
			return getEntityManager().find(persistentClass, getId());
		} else
			return null;
	}

	/**
	 * @return the identity
	 */
	protected Identity getIdentity() {
		return identity;
	}
	
	/**
	 * @return the logger
	 */
	protected Log getLogger() {
		return logger;
	}
	
	/**
	 * @see org.jboss.seam.framework.EntityHome#getPersistenceContextName()
	 */
	@Override
	protected String getPersistenceContextName() {
		return PERSITESTENCE_CONTEXT_NAME;
	}

	protected Principal getPrincipal() {
		return coreSearching.getPrincipal(getEntityManager(), getPrincipalName());
	}

	protected String getPrincipalName() {
		return getIdentity()!=null ? getIdentity().getPrincipal().getName() : "<anonymous>";
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Transactional(TransactionPropagationType.REQUIRED)
	public String persist() {
		foo(AuditType.INSERT, getInstance().toString());
		String result = super.persist();
		getLogger().info("principal '#0' successfully created '#1'", getPrincipal(), getInstance()); 
		return result;
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional(TransactionPropagationType.REQUIRED)
	public String remove() {
		foo(AuditType.REMOVE, getInstance().toString());
		String result = super.remove();
		getLogger().info("principal '#0' successfully removed '#1'", getPrincipal(), getInstance()); 
		return result;
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Transactional(TransactionPropagationType.REQUIRED)
	@Override
	public String update() {
		foo(AuditType.UPDATE, getInstance().toString());
		String result = super.update();
		getLogger().info("principal '#0' successfully updated '#1'", getPrincipal(), getInstance()); 
		return result;
	}

}
