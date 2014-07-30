/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 *  <h1>合同服务</h1>
 *
 *  本服务虽然是统一服务，其部署的当前实体没有对主数据库的修改权限（仅有对其slave的读权限）
 *  但其可以通过向MSC发起SPI请求实现修改
 */
public interface ContractService {
    /**
     * 找到特定账户下的所有合同
     *
     * @param account  企业/提供商的账户
     * @param pageable 分页请求
     * @return 合同分页数据
     */
    Page<Contract> findAllByAccount(Account account, Pageable pageable);

    /**
     * 找到特定账户下的特定合同(sn)，根据指定参数决定是否加载合同明细
     *
     * @param account     企业/提供商的账户
     * @param sn          合同编号
     * @param withDetails 是否加载合同明细
     * @return 合同对象
     */
    Contract findByAccountAndSn(Account account, String sn, boolean withDetails);

    /**
     * <h2>批准特定账户下的特定合同</h2>
     *
     * 本方法是调用远程msc的SPI实现的
     *
     * @param account 企业/提供商的账户
     * @param sn      合同编号
     * @return 合同对象
     */
    Contract approve(Account account, String sn);
    /**
     * <h2>拒绝特定账户下的特定合同</h2>
     *
     * 本方法是调用远程msc的SPI实现的
     *
     * @param account 企业/提供商的账户
     * @param sn      合同编号
     * @return 合同对象
     */
    Contract reject(Account account, String sn);

    /**
     * <h2>更新特定合同明细</h2>
     *
     * @param detail 明细
     */
    void updateDetail(ContractDetail detail);
}
