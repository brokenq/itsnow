/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.service.ServiceException;

/**
 *  <h1>合同服务</h1>
 */
public interface CommonContractService {
    /**
     * 查询当前账户下的所有合同
     *
     * @param account  企业/提供商的账户
     * @param own  是否包含未被应约的合同，true为不包含，false为包含
     * @param pageable 分页请求
     * @return 合同分页数据
     */
    Page<Contract> findAllByAccount(Account account, Boolean own, Pageable pageable) throws ServiceException;

    /**
     * 找到特定账户下的特定合同(sn)，根据指定参数决定是否加载合同明细
     *
     * @param account     企业/提供商的账户
     * @param sn          合同编号
     * @param withDetails 是否加载合同明细
     * @return 合同对象
     */
    Contract findByAccountAndSn(Account account, String sn, boolean withDetails) throws ServiceException;

    /**
     * <h2>根据SN查找合同</h2>
     * @param sn 合同SN
     * @return 合同实体类
     * @throws ServiceException
     */
    Contract findBySn(String sn) throws ServiceException;
}
