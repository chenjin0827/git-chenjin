//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.common.framework.dao;

import com.chenjin.common.entity.BaseEntity;
import com.chenjin.common.entity.BasicEntity;
import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.Entity;
import com.chenjin.common.entity.PageParam;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.framework.exception.MyException;
import com.chenjin.common.framework.util.HqlUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class BaseDao<T, ID extends Serializable> implements IBaseDao<T, ID> {
    protected Class<T> entityClass = (Class)((Class)((ParameterizedType)((ParameterizedType)this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    private SessionFactory sessionFactory;
    private static Logger logger = LoggerFactory.getLogger(BaseDao.class);
    @Resource(
            name = "jdbc"
    )
    private JdbcTemplate jdbc;

    public BaseDao() {
    }

    @Resource(
            name = "sessionFactory"
    )
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    protected Session getNewSession() {
        return this.sessionFactory.openSession();
    }

    public T getById(ID id) {
        return this.get(id, (LockOptions)null);
    }

    public T get(ID id, LockOptions lockOptions) {
        if (id == null) {
            return null;
        } else {
            return lockOptions != null ? (T)this.getSession().get(this.entityClass, id, lockOptions) : (T)this.getSession().get(this.entityClass, id);
        }
    }

    public void lock(T entity, LockOptions lockOptions) {
        if (entity != null && lockOptions != null) {
            this.getSession().buildLockRequest(lockOptions).lock(entity);
        }

    }

    public T save(T entity) {
        Assert.notNull(entity, "欲添加对象不能为空");
        this.getSession().save(entity);
        return entity;
    }

    public void delete(T entity) {
        Assert.notNull(entity, "欲删除对象不能为空");
        this.getSession().delete(entity);
    }

    public T update(T entity) {
        Assert.notNull(entity, "欲更新对象不能为空");
        this.getSession().update(entity);
        return entity;
    }

    public void saveOrUpdate(T entity) {
        Assert.notNull(entity, "对象不能为空");
        this.getSession().saveOrUpdate(entity);
    }

    public T persist(T entity) {
        Assert.notNull(entity, "欲新增对象不能为空");
        this.getSession().persist(entity);
        return entity;
    }

    public T merge(T entity) {
        Assert.notNull(entity, "欲更新对象不能为空");
        this.getSession().merge(entity);
        return entity;
    }

    protected void setParameter(Query query, Object... args) {
        if (args != null && args.length > 0) {
            int index = 0;
            Object[] var4 = args;
            int var5 = args.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Object arg = var4[var6];
                query.setParameter(index++, arg);
            }
        }

    }

    protected void setAliasParameter(Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            Iterator var3 = params.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                query.setParameter(key, params.get(key));
            }
        }

    }

    public T getByHql(String hql, Object... args) {
        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        return (T)query.uniqueResult();
    }

    public T getByHqlInLockMode(String hql, String alias, Object... args) {
        Query query = this.getSession().createQuery(hql);
        query.setFlushMode(FlushMode.COMMIT);
        query.setLockMode(alias, LockMode.PESSIMISTIC_WRITE);
        this.setParameter(query, args);
        return (T)query.uniqueResult();
    }

    public List<T> getAll(PageParam pageable) {
        return this.list((String)null, pageable);
    }

    public List<T> limit(int size, Sort sort) {
        return this.limitList((String)null, size, sort);
    }

    public T getByKey(PageParam pageable) {
        List<T> list = this.list((String)null, pageable);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    protected String initSort(String hql, Sort sort) {
        if (sort != null) {
            if (hql.toUpperCase().indexOf(" ORDER ") != -1) {
                hql = hql + "," + sort.toString().replaceAll(":", " ");
            } else {
                hql = hql + " order by " + sort.toString().replaceAll(":", " ");
            }
        }

        return hql;
    }

    public List<T> list(String hql, PageParam pageable, Object... args) {
        if (pageable == null) {
            pageable = new PageRequest();
        }

        if (hql == null || "".equals(hql)) {
            hql = "from " + this.entityClass.getName() + " t";
        }

        HqlUtil hqlUtil = new HqlUtil();
        hqlUtil.addFilter(((PageParam)pageable).getQuery());
        if (hql.indexOf(" where ") < 0) {
            hql = hql + " where 1=1";
        }

        hql = hql + hqlUtil.getWhereHql();
        hql = this.initSort(hql, ((PageParam)pageable).getMySort());
        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        this.setAliasParameter(query, hqlUtil.getParams());
        return query.list();
    }

    public <N> List<N> listN(String hql, PageParam pageable, Object... args) {
        if (pageable == null) {
            pageable = new PageRequest();
        }

        if (hql == null || "".equals(hql)) {
            hql = "from " + this.entityClass.getName() + " t";
        }

        HqlUtil hqlUtil = new HqlUtil();
        hqlUtil.addFilter(((PageParam)pageable).getQuery());
        if (hql.indexOf(" where ") < 0) {
            hql = hql + " where 1=1";
        }

        hql = hql + hqlUtil.getWhereHql();
        hql = this.initSort(hql, ((PageParam)pageable).getMySort());
        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        this.setAliasParameter(query, hqlUtil.getParams());
        return query.list();
    }

    public List<T> limitList(String hql, int size, Sort sort, Object... args) {
        if (hql == null || "".equals(hql)) {
            hql = "from " + this.entityClass.getName();
        }

        hql = this.initSort(hql, sort);
        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        query.setMaxResults(size);
        return query.list();
    }

    public <N> List<N> limitBySql(String sql, int size, Sort sort, Class<?> clz, Object... args) {
        if (sql != null && !"".equals(sql)) {
            sql = this.initSort(sql, sort);
            SQLQuery sq = this.getSession().createSQLQuery(sql);
            if (clz != null) {
                if (Map.class.isAssignableFrom(clz)) {
                    sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                } else if (Entity.class.isAssignableFrom(clz)) {
                    sq.addEntity(clz);
                } else {
                    sq.setResultTransformer(Transformers.aliasToBean(clz));
                }
            }

            this.setParameter(sq, args);
            sq.setMaxResults(size);
            return sq.list();
        } else {
            throw new IllegalArgumentException("sql不能为null！");
        }
    }

    protected String getCountHql(String hql, boolean isHql) {
        String e = hql.substring(hql.indexOf("from"));
        String c = "select count(*) " + e;
        if (isHql) {
            c = c.replaceAll("fetch", "");
        }

        return c;
    }

    protected String getCountSql(String sql) {
        String countSQL = "";
        if (sql.contains(" group by ")) {
            countSQL = "select count(*) from (" + sql + ") tmp_count_t";
        } else {
            String e = sql.substring(sql.indexOf(" from "));
            countSQL = "select count(*) " + e;
        }

        return countSQL;
    }

    public DataGrid<T> query(PageParam pageable) {
        return this.query((String)null, pageable);
    }

    public DataGrid<T> query(String hql, PageParam pageable, Object... args) {
        if (pageable == null) {
            pageable = new PageRequest();
        }

        System.out.println("page =" + ((PageParam)pageable).getPageNumber());
        System.out.println("rows =" + ((PageParam)pageable).getPageSize());
        System.out.println("query =" + ((PageParam)pageable).getQuery());
        System.out.println("filter =" + ((PageParam)pageable).getFilterRules());
        System.out.println("getSort =" + ((PageParam)pageable).getSort());
        System.out.println("getOrder =" + ((PageParam)pageable).getOrder());
        if (hql == null || "".equals(hql)) {
            hql = "from " + this.entityClass.getName() + " t";
        }

        HqlUtil hqlUtil = new HqlUtil();
        hqlUtil.addFilter(((PageParam)pageable).getQuery());
        if (hql.indexOf(" where ") < 0) {
            hql = hql + " where 1=1";
        }

        hql = hql + hqlUtil.getWhereHql();
        String cq = this.getCountHql(hql, true);
        hqlUtil.setSort((PageParam)pageable);
        hql = this.initSort(hql, ((PageParam)pageable).getMySort());
        hql = this.easyuiSort(hql, ((PageParam)pageable).getSort(), ((PageParam)pageable).getOrder());
        System.out.println("hql==========999111999====" + hql);
        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        this.setAliasParameter(query, hqlUtil.getParams());
        query.setFirstResult(((PageParam)pageable).getOffset()).setMaxResults(((PageParam)pageable).getPageSize());
        DataGrid<T> page = new DataGrid(query.list(), (PageParam)pageable, this.count(cq, hqlUtil.getParams(), args).longValue());
        return page;
    }

    public DataGrid<T> queryWithCompNo(String hql, PageParam pageable, Object... args) {
        if (pageable == null) {
            pageable = new PageRequest();
        }

        if (hql == null || "".equals(hql)) {
            hql = "from " + this.entityClass.getName() + " t";
        }

        HqlUtil hqlUtil = new HqlUtil();
        hqlUtil.addFilter(((PageParam)pageable).getQuery());
        if (hql.indexOf(" where ") < 0) {
            hql = hql + " where t.compNo=?";
        } else {
            hql = hql + " and t.compNo=?";
        }

        hql = hql + hqlUtil.getWhereHql();
        String cq = this.getCountHql(hql, true);
        hqlUtil.setSort((PageParam)pageable);
        hql = this.initSort(hql, ((PageParam)pageable).getMySort());
        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        this.setAliasParameter(query, hqlUtil.getParams());
        query.setFirstResult(((PageParam)pageable).getOffset()).setMaxResults(((PageParam)pageable).getPageSize());
        DataGrid<T> page = new DataGrid(query.list(), (PageParam)pageable, this.count(cq, hqlUtil.getParams(), args).longValue());
        return page;
    }

    public <N> List<N> listBySql(String sql, Sort sort, Class<?> clz, Object... args) {
        if (sql != null && !"".equals(sql)) {
            sql = this.initSort(sql, sort);
            SQLQuery sq = this.getSession().createSQLQuery(sql);
            if (clz != null) {
                if (Map.class.isAssignableFrom(clz)) {
                    sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                } else if (Entity.class.isAssignableFrom(clz)) {
                    sq.addEntity(clz);
                } else {
                    sq.setResultTransformer(Transformers.aliasToBean(clz));
                }
            }

            this.setParameter(sq, args);
            return sq.list();
        } else {
            throw new IllegalArgumentException("sql不能为null！");
        }
    }

    public <N> List<N> listByHql(String hql, Sort sort, Object... args) {
        if (hql != null && !"".equals(hql)) {
            hql = this.initSort(hql, sort);
            Query q = this.getSession().createQuery(hql);
            this.setParameter(q, args);
            return q.list();
        } else {
            throw new IllegalArgumentException("hql不能为null！");
        }
    }

    public <N> DataGrid<N> findBySql(String sql, PageParam pageable, Class<?> clz, Object... args) {
        System.out.println("findBySql=====");
        if (pageable == null) {
            pageable = new PageRequest();
        }

        int gb = sql.indexOf("group by");
        if (gb == -1) {
            gb = sql.indexOf("order by");
        }

        String preSql = sql;
        String sufSql = "";
        if (gb != -1) {
            preSql = sql.substring(0, gb);
            sufSql = sql.substring(gb);
        }

        HqlUtil hqlUtil = new HqlUtil();
        hqlUtil.addFilter(((PageParam)pageable).getQuery());
        if (preSql.indexOf(" where ") < 0) {
            preSql = preSql + " where 1=1 ";
        }

        sql = preSql + hqlUtil.getWhereHql() + sufSql;
        String cq = this.getCountSql(sql);
        hqlUtil.setSort((PageParam)pageable);
        sql = this.initSort(sql, ((PageParam)pageable).getMySort());
        sql = this.easyuiSort(sql, ((PageParam)pageable).getSort(), ((PageParam)pageable).getOrder());
        System.out.println("sql==========88888888====" + sql);
        SQLQuery sq = this.getSession().createSQLQuery(sql);
        this.setParameter(sq, args);
        this.setAliasParameter(sq, hqlUtil.getParams());
        sq.setFirstResult(((PageParam)pageable).getOffset()).setMaxResults(((PageParam)pageable).getPageSize());
        if (clz != null) {
            if (Map.class.isAssignableFrom(clz)) {
                sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else if (Entity.class.isAssignableFrom(clz)) {
                sq.addEntity(clz);
            } else {
                sq.setResultTransformer(Transformers.aliasToBean(clz));
            }
        }

        DataGrid<N> page = new DataGrid(sq.list(), (PageParam)pageable, this.countBySql(cq, hqlUtil.getParams(), args).longValue());
        return page;
    }

    protected String easyuiSort(String sql, String sort, String order) {
        System.out.println("easyuiSort sql =" + sql);
        System.out.println("easyuiSort sort =" + sort);
        System.out.println("easyuiSort order =" + order);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            int index = sql.indexOf("order by");
            if (index > 0) {
                sql = sql.substring(0, index);
            }

            sql = sql + " order by ";
            String[] ks = sort.split(",");
            String[] ds = order.split(",");

            for(int i = 0; i < ks.length; ++i) {
                sql = sql + ks[i] + " " + ds[i] + ",";
            }

            sql = sql.substring(0, sql.length() - 1);
        }

        return sql;
    }

    public <N> N getObject(String hql, Class<?> clz, Object... args) {
        if (hql != null && !"".equals(hql)) {
            Query query = this.getSession().createQuery(hql);
            this.setParameter(query, args);
            if (clz != null) {
                if (Map.class.isAssignableFrom(clz)) {
                    query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                } else {
                    query.setResultTransformer(Transformers.aliasToBean(clz));
                }
            }

            return (N)query.uniqueResult();
        } else {
            throw new IllegalArgumentException("hql不能为null！");
        }
    }

    public <N> N getBySql(String sql, Class<?> clz, Object... args) {
        if (sql != null && !"".equals(sql)) {
            SQLQuery query = this.getSession().createSQLQuery(sql);
            this.setParameter(query, args);
            if (clz != null) {
                if (Map.class.isAssignableFrom(clz)) {
                    query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                } else if (Entity.class.isAssignableFrom(clz)) {
                    query.addEntity(clz);
                } else {
                    query.setResultTransformer(Transformers.aliasToBean(clz));
                }
            }

            return (N)query.uniqueResult();
        } else {
            throw new IllegalArgumentException("sql不能为null！");
        }
    }

    public Long count(String hql, Object... args) {
        if (hql == null || "".equals(hql)) {
            hql = "select count(*) from " + this.entityClass.getName();
        }

        Query query = this.getSession().createQuery(hql);
        this.setParameter(query, args);
        return (Long)query.uniqueResult();
    }

    public Long count(String hql, Map<String, Object> params, Object... args) {
        if (hql != null && !"".equals(hql)) {
            Query query = this.getSession().createQuery(hql);
            this.setParameter(query, args);
            this.setAliasParameter(query, params);
            return (Long)query.uniqueResult();
        } else {
            throw new IllegalArgumentException("sql不能为null！");
        }
    }

    public Long countBySql(String sql, Object... args) {
        if (sql != null && !"".equals(sql)) {
            SQLQuery query = this.getSession().createSQLQuery(sql);
            this.setParameter(query, args);
            return (new BigDecimal(query.uniqueResult().toString())).longValue();
        } else {
            throw new IllegalArgumentException("sql不能为null！");
        }
    }

    public Long countBySql(String sql, Map<String, Object> params, Object... args) {
        if (sql != null && !"".equals(sql)) {
            SQLQuery query = this.getSession().createSQLQuery(sql);
            this.setParameter(query, args);
            this.setAliasParameter(query, params);
            return (new BigDecimal(query.uniqueResult().toString())).longValue();
        } else {
            throw new IllegalArgumentException("sql不能为null！");
        }
    }

    public int executeHql(String hql, Object... args) {
        if (hql != null && !"".equals(hql)) {
            Query query = this.getSession().createQuery(hql);
            this.setParameter(query, args);
            return query.executeUpdate();
        } else {
            throw new IllegalArgumentException("hql不能为null！");
        }
    }

    public int executeSql(String sql, Object... args) {
        if (sql != null && !"".equals(sql)) {
            SQLQuery query = this.getSession().createSQLQuery(sql);
            this.setParameter(query, args);
            return query.executeUpdate();
        } else {
            throw new IllegalArgumentException("hql不能为null！");
        }
    }

    public boolean isManaged(T entity) {
        Assert.notNull(entity, "对象不能为空");
        return this.getSession().contains(entity);
    }

    public void clear() {
        this.getSession().clear();
    }

    public void flush() {
        this.getSession().flush();
    }

    public void evict(T entity) {
        this.getSession().evict(entity);
    }

    public void refresh(T entity) {
        this.refresh(entity, (LockOptions)null);
    }

    public void refresh(T entity, LockOptions lockOptions) {
        Assert.notNull(entity, "刷新对象不能为空");
        if (lockOptions != null) {
            this.getSession().refresh(entity, lockOptions);
        } else {
            this.getSession().refresh(entity);
        }

    }

    public ID getIdentifier(T entity) {
        ClassMetadata meta = this.sessionFactory.getClassMetadata(this.entityClass);
        return (ID)meta.getIdentifier(entity);
    }

    public int saveBatch(List<T> list) {
        boolean var2 = true;

        try {
            Long startTime = System.currentTimeMillis();
            String sql = this.getSaveSql();
            int count = this.doBatchSave(sql, list);
            Long endTime = System.currentTimeMillis();
            System.out.println("新增用时：" + (endTime.longValue() - startTime.longValue()));
            return count;
        } catch (Exception var6) {
            var6.printStackTrace();
            logger.error(var6.getMessage(), var6);
            throw new MyException();
        }
    }

    public int updateBatch(List<T> list) {
        boolean var2 = true;

        try {
            Long startTime = System.currentTimeMillis();
            String sql = this.getUpdateSql();
            int count = this.doBatchUpdate(sql, list);
            Long endTime = System.currentTimeMillis();
            System.out.println("修改用时：" + (endTime.longValue() - startTime.longValue()));
            return count;
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new MyException();
        }
    }

    public int updateBatchWithInclude(List<T> list, String... args) {
        int count = -1;
        if (args != null && args.length != 0) {
            try {
                Long startTime = System.currentTimeMillis();
                String sql = this.getUpdateSql(args);
                int count1 = this.doBatchUpdate(sql, list, args);
                Long endTime = System.currentTimeMillis();
                System.out.println("修改用时：" + (endTime.longValue() - startTime.longValue()));
                return count1;
            } catch (Exception var7) {
                var7.printStackTrace();
                throw new MyException();
            }
        } else {
            return count;
        }
    }

    private int doBatchUpdate(String sql, final List<T> list, final String... args) {
        int[] count = this.jdbc.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                try {
                    T t = list.get(i);
                    int indexflag = BaseDao.this.setPPS(ps, t, args);
                    Method getidm = BaseDao.this.entityClass.getMethod("getId");
                    Object value = getidm.invoke(t);
                    ps.setObject(indexflag, value);
                } catch (Exception var7) {
                    var7.printStackTrace();
                    throw new SQLException("value异常");
                }
            }

            public int getBatchSize() {
                return list.size();
            }
        });
        int updatenum = 0;
        int[] var6 = count;
        int var7 = count.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            int i = var6[var8];
            if (i > 0) {
                updatenum += i;
            }
        }

        return updatenum;
    }

    private String upFirstLetter(String key) {
        return key != null && key.length() != 0 ? key.substring(0, 1).toUpperCase() + key.substring(1) : "";
    }

    protected int setPPS(PreparedStatement pps, T t, String... args) throws Exception {
        Integer indexflag = Integer.valueOf(1);
        String[] var5 = args;
        int var6 = args.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String arg = var5[var7];
            Method method = this.entityClass.getMethod("get" + this.upFirstLetter(arg));
            String name = method.getName();
            Class<?> type = method.getReturnType();
            Object value = method.invoke(t);
            if (this.isEmbedded(this.entityClass, name)) {
                indexflag = this.addEmbeddedVal(pps, type, value, indexflag);
            } else {
                indexflag = this.setPPSVal(indexflag, pps, type, name, value);
            }
        }

        return indexflag.intValue();
    }

    public int deleteBatch(List<T> list) {
        boolean var2 = true;

        try {
            Long startTime = System.currentTimeMillis();
            String sql = this.getDeleteSql();
            int count = this.doBatchDelete(sql, list);
            Long endTime = System.currentTimeMillis();
            System.out.println("删除用时：" + (endTime.longValue() - startTime.longValue()));
            return count;
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new MyException();
        }
    }

    private String getUpdateSql() throws Exception {
        StringBuffer sqlkey = new StringBuffer();
        Method[] ms = this.entityClass.getMethods();
        Method[] var3 = ms;
        int var4 = ms.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            String name = method.getName();
            Class<?> type = method.getReturnType();
            if (!this.isTransient(name)) {
                if (this.isEmbedded(this.entityClass, name)) {
                    this.appendUpdateEmbedded(this.entityClass, name, sqlkey);
                } else if (this.isBasicEntity(type)) {
                    sqlkey.append("," + name.substring(3) + "Id=?");
                } else {
                    sqlkey.append("," + name.substring(3) + "=?");
                }
            }
        }

        Table table = (Table)this.entityClass.getAnnotation(Table.class);
        String tablename = table.name();
        StringBuffer sql = new StringBuffer();
        sql.append("update  " + tablename + " set id=id ");
        sql.append(sqlkey + " where id=? ");
        System.out.println("sql ======" + sql);
        return sql.toString();
    }

    private String getUpdateSql(String... args) throws Exception {
        StringBuffer sqlkey = new StringBuffer();
        String[] var3 = args;
        int var4 = args.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String arg = var3[var5];
            sqlkey.append("," + arg + "=?");
        }

        Table table = (Table)this.entityClass.getAnnotation(Table.class);
        String tablename = table.name();
        StringBuffer sql = new StringBuffer();
        sql.append("update  " + tablename + " set  ");
        sql.append(sqlkey + " where id=? ");
        System.out.println("sql ======" + sql);
        return sql.toString();
    }

    private int doBatchUpdate(String sql, final List<T> list) throws Exception {
        int[] count = this.jdbc.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                try {
                    T t = list.get(i);
                    int indexflag = BaseDao.this.setPPS(ps, t);
                    Method getidm = BaseDao.this.entityClass.getMethod("getId");
                    Object value = getidm.invoke(t);
                    ps.setObject(indexflag, value);
                } catch (Exception var7) {
                    var7.printStackTrace();
                    throw new SQLException("value异常");
                }
            }

            public int getBatchSize() {
                return list.size();
            }
        });
        int updatenum = 0;
        int[] var5 = count;
        int var6 = count.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            int i = var5[var7];
            if (i > 0) {
                updatenum += i;
            }
        }

        return updatenum;
    }

    private int doBatchSave(String sql, final List<T> list) throws Exception {
        int[] count = this.jdbc.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                try {
                    T t = list.get(i);
                    BaseDao.this.setPPS(ps, t);
                } catch (Exception var4) {
                    var4.printStackTrace();
                    BaseDao.logger.error(var4.getMessage(), var4);
                    throw new SQLException("value异常");
                }
            }

            public int getBatchSize() {
                return list.size();
            }
        });
        int updatenum = 0;
        int[] var5 = count;
        int var6 = count.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            int i = var5[var7];
            if (i > 0) {
                updatenum += i;
            }
        }

        return updatenum;
    }

    private int doBatchDelete(String sql, final List<T> list) throws Exception {
        int[] count = this.jdbc.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                try {
                    T t = list.get(i);
                    Method getidm = BaseDao.this.entityClass.getMethod("getId");
                    Object value = getidm.invoke(t);
                    ps.setObject(1, value);
                } catch (Exception var6) {
                    var6.printStackTrace();
                    throw new SQLException("value异常");
                }
            }

            public int getBatchSize() {
                return list.size();
            }
        });
        int updatenum = 0;
        int[] var5 = count;
        int var6 = count.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            int i = var5[var7];
            if (i > 0) {
                updatenum += i;
            }
        }

        return updatenum;
    }

    private String getSaveSql() throws Exception {
        StringBuffer sqlkey = new StringBuffer();
        StringBuffer sqlval = new StringBuffer();
        Method[] ms = this.entityClass.getMethods();
        Method[] var4 = ms;
        int var5 = ms.length;

        Method getidm;
        for(int var6 = 0; var6 < var5; ++var6) {
            getidm = var4[var6];
            String name = getidm.getName();
            Class<?> type = getidm.getReturnType();
            if (!this.isTransient(name)) {
                if (this.isEmbedded(this.entityClass, name)) {
                    this.appendEmbedded(this.entityClass, name, sqlkey, sqlval);
                } else if (this.isBasicEntity(type)) {
                    sqlkey.append("," + name.substring(3) + "Id");
                    sqlval.append(",?");
                } else {
                    sqlkey.append("," + name.substring(3));
                    sqlval.append(",?");
                }
            }
        }

        Table table = (Table)this.entityClass.getAnnotation(Table.class);
        String tablename = table.name();
        String seqname = "";
        getidm = this.entityClass.getMethod("getId");
        Annotation[] var16 = getidm.getAnnotations();
        int var18 = var16.length;

        for(int var10 = 0; var10 < var18; ++var10) {
            Annotation an = var16[var10];
            if (an instanceof SequenceGenerator) {
                SequenceGenerator sequenceGenerator = (SequenceGenerator)an;
                seqname = sequenceGenerator.sequenceName();
            }
        }

        StringBuffer sql = new StringBuffer();
        if (seqname.equals("")) {
            sqlkey = sqlkey.deleteCharAt(0);
            sqlval = sqlval.deleteCharAt(0);
            sql.append("insert into " + tablename + " (");
            sql.append(sqlkey + ") values (");
            sql.append(sqlval + ")");
        } else {
            sql.append("insert into " + tablename + " (id");
            sql.append(sqlkey + ") values (" + seqname + ".nextval");
            sql.append(sqlval + ")");
        }

        System.out.println("sql ======" + sql);
        return sql.toString();
    }

    private void appendEmbedded(Class<T> c, String name, StringBuffer sqlkey, StringBuffer sqlval) throws Exception {
        Method getidm = c.getMethod(name);
        Embedded e = (Embedded)getidm.getAnnotation(Embedded.class);
        if (e != null) {
            Class<?> ec = getidm.getReturnType();
            Method[] ms = ec.getMethods();
            Method[] var9 = ms;
            int var10 = ms.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                Method method = var9[var11];
                Class<?> type = method.getReturnType();
                name = method.getName();
                if (!this.isTransient(ec, name)) {
                    if (this.isBasicEntity(type)) {
                        sqlkey.append("," + name.substring(3) + "Id");
                        sqlval.append(",?");
                    } else {
                        sqlkey.append("," + name.substring(3));
                        sqlval.append(",?");
                    }
                }
            }
        }

    }

    private void appendUpdateEmbedded(Class<T> c, String methodName, StringBuffer sqlkey) throws Exception {
        Method getidm = c.getMethod(methodName);
        Embedded e = (Embedded)getidm.getAnnotation(Embedded.class);
        if (e != null) {
            Class<?> ec = getidm.getReturnType();
            Method[] ms = ec.getMethods();
            Method[] var8 = ms;
            int var9 = ms.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Method method = var8[var10];
                Class<?> type = method.getReturnType();
                String name = method.getName();
                if (!this.isTransient(ec, name)) {
                    if (this.isBasicEntity(type)) {
                        sqlkey.append("," + name.substring(3) + "Id=?");
                    } else {
                        sqlkey.append("," + name.substring(3) + "=?");
                    }
                }
            }
        }

    }

    private boolean isTransient(Class<?> c, String name) throws Exception {
        if (name.equalsIgnoreCase("getClass")) {
            return true;
        } else if (name.equalsIgnoreCase("getId")) {
            return true;
        } else if (!name.startsWith("get")) {
            return true;
        } else {
            Method getidm = c.getMethod(name);
            Annotation[] var4 = getidm.getAnnotations();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Annotation an = var4[var6];
                if (an instanceof Transient) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean isTransient(String name) throws Exception {
        if (name.equalsIgnoreCase("getClass")) {
            return true;
        } else if (name.equalsIgnoreCase("getId")) {
            return true;
        } else if (!name.startsWith("get")) {
            return true;
        } else {
            Method getidm = this.entityClass.getMethod(name);
            Annotation[] var3 = getidm.getAnnotations();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Annotation an = var3[var5];
                if (an instanceof Transient) {
                    return true;
                }

                if (an instanceof OneToMany) {
                    return true;
                }

                if (an instanceof ManyToMany) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean isBasicEntity(Class<?> type) {
        if (BasicEntity.class.isAssignableFrom(type)) {
            return true;
        } else {
            return BaseEntity.class.isAssignableFrom(type);
        }
    }

    private boolean isEnum(Class<?> type) {
        return Enum.class.isAssignableFrom(type);
    }

    public T saveJDBC(final T t) {
        Assert.notNull(t, "欲添加对象不能为空");
        Long keyid = Long.parseLong("-1");

        try {
            final String sql = this.getSaveSql();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbc.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement pps = connection.prepareStatement(sql, new String[]{"id"});

                    try {
                        BaseDao.this.setPPS(pps, t);
                        return pps;
                    } catch (Exception var4) {
                        var4.printStackTrace();
                        throw new MyException();
                    }
                }
            }, keyHolder);
            keyid = keyHolder.getKey().longValue();
            Method setidM = this.entityClass.getMethod("setId", Long.class);
            setidM.invoke(t, keyid);
            return t;
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new MyException(var6.getMessage());
        }
    }

    private int setPPS(PreparedStatement pps, T t) throws Exception {
        Method[] ms = this.entityClass.getMethods();
        Integer indexflag = Integer.valueOf(1);
        Method[] var5 = ms;
        int var6 = ms.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            String name = method.getName();
            Class<?> type = method.getReturnType();
            if (!this.isTransient(name)) {
                Object value = method.invoke(t);
                if (this.isEmbedded(this.entityClass, name)) {
                    indexflag = this.addEmbeddedVal(pps, type, value, indexflag);
                } else {
                    indexflag = this.setPPSVal(indexflag, pps, type, name, value);
                }
            }
        }

        return indexflag.intValue();
    }

    private Integer addEmbeddedVal(PreparedStatement pps, Class<?> c, Object t, Integer indexflag) throws Exception {
        Method[] ms = c.getMethods();
        Method[] var6 = ms;
        int var7 = ms.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Method method = var6[var8];
            String name = method.getName();
            Class<?> type = method.getReturnType();
            if (!this.isTransient(c, name)) {
                Object value = method.invoke(t);
                indexflag = this.setPPSVal(indexflag, pps, type, name, value);
            }
        }

        return indexflag;
    }

    private Integer setPPSVal(Integer indexflag, PreparedStatement pps, Class<?> c, String methodName, Object value) throws Exception {
        Integer var6;
        if (value == null) {
            if (methodName.equals("getCreateDate")) {
                var6 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setTimestamp(var6.intValue(), new Timestamp((new Date()).getTime()));
            } else {
                var6 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setObject(var6.intValue(), value);
            }
        } else {
            Integer var8;
            Method subm;
            if (this.isBasicEntity(c)) {
                subm = c.getMethod("getId");
                Long subId = (Long)subm.invoke(value);
                var8 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setObject(var8.intValue(), subId);
            } else if (this.isEnum(c)) {
                subm = c.getMethod("ordinal");
                Integer subId = (Integer)subm.invoke(value);
                var8 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setObject(var8.intValue(), subId);
            } else if (Date.class.isAssignableFrom(c)) {
                var6 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setTimestamp(var6.intValue(), new Timestamp(((Date)value).getTime()));
            } else if (java.sql.Date.class.isAssignableFrom(c)) {
                var6 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setDate(var6.intValue(), (java.sql.Date)value);
            } else {
                var6 = indexflag;
                indexflag = indexflag.intValue() + 1;
                pps.setObject(var6.intValue(), value);
            }
        }

        return indexflag;
    }

    private boolean isEmbedded(Class<T> c, String name) throws Exception {
        return c.getMethod(name).getAnnotation(Embedded.class) != null;
    }

    public int updateJDBC(final T t) {
        Assert.notNull(t, "欲更新对象不能为空");
        boolean var2 = true;

        try {
            String sql = this.getUpdateSql();
            int i = this.jdbc.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement pps) throws SQLException {
                    try {
                        int indexflag = BaseDao.this.setPPS(pps, t);
                        Method getidm = BaseDao.this.entityClass.getMethod("getId");
                        Object value = getidm.invoke(t);
                        pps.setObject(indexflag, value);
                    } catch (Exception var5) {
                        var5.printStackTrace();
                        throw new SQLException("value异常");
                    }
                }
            });
            return i;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new MyException();
        }
    }

    public int deleteJDBC(final T t) {
        Assert.notNull(t, "欲删除对象不能为空");
        boolean var2 = true;

        try {
            String sql = this.getDeleteSql();
            int i = this.jdbc.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement pps) throws SQLException {
                    try {
                        Method getidm = BaseDao.this.entityClass.getMethod("getId");
                        Object value = getidm.invoke(t);
                        pps.setObject(1, value);
                    } catch (Exception var4) {
                        var4.printStackTrace();
                        throw new SQLException("value异常");
                    }
                }
            });
            return i;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new MyException();
        }
    }

    private String getDeleteSql() throws Exception {
        Table table = (Table)this.entityClass.getAnnotation(Table.class);
        String tablename = table.name();
        StringBuffer sql = new StringBuffer();
        sql.append("delete from  " + tablename + " where id=? ");
        System.out.println("sql ======" + sql);
        return sql.toString();
    }

    public <N> List<N> listBySql2(String sql, PageParam pageable, Class<?> clz, Object... args) {
        if (pageable == null) {
            pageable = new PageRequest();
        }

        int gb = sql.indexOf("group by");
        if (gb == -1) {
            gb = sql.indexOf("order by");
        }

        String preSql = sql;
        String sufSql = "";
        if (gb != -1) {
            preSql = sql.substring(0, gb);
            sufSql = sql.substring(gb);
        }

        HqlUtil hqlUtil = new HqlUtil();
        hqlUtil.addFilter(((PageParam)pageable).getQuery());
        if (preSql.indexOf(" where ") < 0) {
            preSql = preSql + " where 1=1 ";
        }

        sql = preSql + hqlUtil.getWhereHql() + sufSql;
        sql = this.initSort(sql, ((PageParam)pageable).getMySort());
        SQLQuery sq = this.getSession().createSQLQuery(sql);
        if (clz != null) {
            if (Map.class.isAssignableFrom(clz)) {
                sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else if (Entity.class.isAssignableFrom(clz)) {
                sq.addEntity(clz);
            } else {
                sq.setResultTransformer(Transformers.aliasToBean(clz));
            }
        }

        this.setParameter(sq, args);
        this.setAliasParameter(sq, hqlUtil.getParams());
        return sq.list();
    }
}
