package dnt.itsnow.service;

import dnt.itsnow.exception.SiteException;
import dnt.itsnow.model.Site;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>地点业务类</h1>
 */
public interface SiteService {

    /**
     * 查询所有地点，有分布及过滤功能
     * @param keyword 关键字
     * @param pageable 分页实体类
     * @return 地点分页结果实体类
     */
    public Page<Site> findAll(String keyword, Pageable pageable);

    /**
     * 根据SN查询地点
     * @param sn 序列号
     * @return 地点实体类
     */
    public Site findBySn(String sn);

    /**
     * 新建地点
     * @param site 待新建地点
     * @return 已新建地点
     * @throws SiteException
     */
    public Site create(Site site) throws SiteException;

    /**
     * 更新地点
     * @param site 待更新的地点
     * @return 已更新的地点
     * @throws SiteException
     */
    public Site update(Site site) throws SiteException;

    /**
     * 删除地点
     * @param site 待删除地点的实体类
     * @throws SiteException
     */
    public void destroy(Site site) throws SiteException;

}
