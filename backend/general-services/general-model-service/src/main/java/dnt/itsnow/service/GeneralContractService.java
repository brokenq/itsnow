/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.service.ServiceException;

/**
 * <h1>一般性合同服务</h1>
 *
 *  本服务虽然是统一服务，其部署的当前实体没有对主数据库的修改权限（仅有对其slave的读权限）
 *  但其可以通过向MSC发起SPI请求实现修改
 */
public interface GeneralContractService extends CommonContractService {

    /**
     * <h2>MSU创建合同</h2>
     *
     * 本方法是调用远程msc的SPI实现的
     *
     * @param account 企业/提供商的账户
     * @param contract      合同对象
     * @return 合同对象
     */
    Contract create(Account account, Contract contract) throws ServiceException;

    /**
     * <h2>MSP投标</h2>
     *
     * 本方法是调用远程msc的SPI实现的
     *
     * @param account 企业/提供商的账户
     * @param sn      合同编号
     * @return 合同对象
     */
    Contract bid(Account account, String sn) throws ServiceException;


    /**
     * <h2>MSU批准特定账户下的特定合同</h2>
     *
     * 本方法是调用远程msc的SPI实现的
     *
     * @param account 企业/提供商的账户
     * @param sn      合同编号
     * @return 合同对象
     */
    Contract approve(Account account, String sn) throws ServiceException;
    /**
     * <h2>拒绝特定账户下的特定合同</h2>
     *
     * 本方法是调用远程msc的SPI实现的
     *
     * @param account 企业/提供商的账户
     * @param sn      合同编号
     * @return 合同对象
     */
    Contract reject(Account account, String sn) throws ServiceException;

    /**
     * <h2>更新特定合同明细</h2>
     *
     * @param detail 明细
     */
    ContractDetail updateDetail(ContractDetail detail, String sn) throws ServiceException;
}
