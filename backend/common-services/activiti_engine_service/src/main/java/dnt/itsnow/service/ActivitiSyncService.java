package dnt.itsnow.service;

/**
 * Created by jacky on 2014/8/13.
 */
public interface ActivitiSyncService {

    /*
     * 同步users，groups，groups_member数据至activiti
     */
    void syncActiviti();
}
