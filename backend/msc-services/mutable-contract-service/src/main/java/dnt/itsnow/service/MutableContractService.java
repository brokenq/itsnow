package dnt.itsnow.service;

import dnt.itsnow.model.Contract;

/**
 * Created by jacky on 2014/9/4.
 */
public interface MutableContractService extends CommonContractService{

    Contract create(Contract contract);

    Contract update(Contract contract);

    void delete(Contract contract);
}
