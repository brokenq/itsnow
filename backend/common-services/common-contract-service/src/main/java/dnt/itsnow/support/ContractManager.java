/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.service.ContractService;
import dnt.spring.Bean;
import org.springframework.stereotype.Service;

/**
 *  <h1> The contract manager</h1>
 *
 *  通用的合同管理
 */
@Service
public class ContractManager extends Bean implements ContractService {
    @Override
    public Page<Contract> findAllByAccount(Account account, Pageable pageable) {
        return null;
    }

    @Override
    public Contract findByAccountAndSn(Account account, String sn, boolean withDetails) {
        return null;
    }

    @Override
    public Contract approve(Account account, String sn) {
        return null;
    }

    @Override
    public Contract reject(Account account, String sn) {
        return null;
    }

    @Override
    public void updateDetail(ContractDetail detail) {

    }
}
