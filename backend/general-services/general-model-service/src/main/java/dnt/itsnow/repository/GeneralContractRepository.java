/**
 * Developer: Kadvin Date: 14-8-26 下午2:53
 */
package dnt.itsnow.repository;

/**
 * <h1>The general contract repository used by MSU/MSP</h1>
 *
 * 这里定义这个接口
 * 可以避免从 common-model-service 包中引入 CommonContractRepository
 * 违背 common-model-service的封装性
 */
public interface GeneralContractRepository extends CommonContractRepository {

    //Contract saveContract(Contract contract);

    //Contract updateContract(Contract contract);

    //void deleteContract(String sn);
}
