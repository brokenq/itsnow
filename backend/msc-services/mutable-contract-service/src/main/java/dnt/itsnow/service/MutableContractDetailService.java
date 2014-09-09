package dnt.itsnow.service;

import dnt.itsnow.model.ContractDetail;

/**
 * Created by jacky on 2014/9/4.
 */
public interface MutableContractDetailService{

    ContractDetail create(ContractDetail contractDetail);

    ContractDetail update(ContractDetail contractDetail);

    void delete(ContractDetail contractDetail);
}
