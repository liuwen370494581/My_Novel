package star.liuwen.com.novel_lw.Dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.ruolan.letgo.bean.Dish;
import com.example.ruolan.letgo.bean.BookModel;

import star.liuwen.com.novel_lw.Dao.DishDao;
import star.liuwen.com.novel_lw.Dao.BookModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dishDaoConfig;
    private final DaoConfig bookModelDaoConfig;

    private final DishDao dishDao;
    private final BookModelDao bookModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dishDaoConfig = daoConfigMap.get(DishDao.class).clone();
        dishDaoConfig.initIdentityScope(type);

        bookModelDaoConfig = daoConfigMap.get(BookModelDao.class).clone();
        bookModelDaoConfig.initIdentityScope(type);

        dishDao = new DishDao(dishDaoConfig, this);
        bookModelDao = new BookModelDao(bookModelDaoConfig, this);

        registerDao(Dish.class, dishDao);
        registerDao(BookModel.class, bookModelDao);
    }
    
    public void clear() {
        dishDaoConfig.clearIdentityScope();
        bookModelDaoConfig.clearIdentityScope();
    }

    public DishDao getDishDao() {
        return dishDao;
    }

    public BookModelDao getBookModelDao() {
        return bookModelDao;
    }

}
