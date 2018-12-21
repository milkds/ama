import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AmaDao {

    private static Session session = HibernateUtil.getSession();
    private static final Logger logger = LogManager.getLogger(AmaDao.class.getName());

    public static void saveItems(List<AmItem> items) {
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            for (AmItem item : items) {
                session.persist(item);
            }
            transaction.commit();
            logger.info("Items successfully saved " + items);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            logger.error("error while saving");
        }
    }
}
