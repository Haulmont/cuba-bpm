// built-ins
import com.haulmont.cuba.core.Persistence
Persistence persistence
UUID entityId

// our script
import com.company.workshop.entity.Order

def em = persistence.getEntityManager()
def order = em.find(Order.class, entityId)
order.setProcessState('Done')