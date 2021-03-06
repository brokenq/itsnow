/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.service.ServiceException;

/**
 *  <h1>合同服务</h1>
 */
public interface CommonContractService {
    /**
     * 找到特定账户下的所有合同
     *
     * @param account  企业/提供商的账户
     * @param pageable 分页请求
     * @return 合同分页数据
     */
    Page<Contract> findAllByAccount(Account account, Pageable pageable) throws ServiceException;

    /**
     * 找到特定账户下的特定合同(sn)，根据指定参数决定是否加载合同明细
     *
     * @param account     企业/提供商的账户
     * @param sn          合同编号
     * @param withDetails 是否加载合同明细
     * @return 合同对象
     */
    Contract findByAccountAndSn(Account account, String sn, boolean withDetails) throws ServiceException;
}
