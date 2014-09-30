package dnt.itsnow.service;

import dnt.itsnow.exception.ProcessDictionaryException;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>流程字典业务层</h1>
 */
public interface ProcessDictionaryService {

    /**
     * 查询所有流程字典，可分页，可按关键字查询
     * @param keyword 关键字
     * @param pageable 分页实体类
     * @return 字典列表
     */
    public Page<ProcessDictionary> findAll(String keyword, Pageable pageable);

    /**
     * 创建流程字典
     * @param dictionary 字典实体类
     * @return 已更新的字典
     * @throws ProcessDictionaryException
     */
    public ProcessDictionary create(ProcessDictionary dictionary) throws ProcessDictionaryException;

    /**
     * 更新流程字典
     * @param dictionary 待更新的字典实体类
     * @return 已更新的字典实体类
     * @throws ProcessDictionaryException
     */
    public ProcessDictionary update(ProcessDictionary dictionary) throws ProcessDictionaryException;

    /**
     * 销毁流程字典记录
     * @param dictionary 字典实体类
     * @throws ProcessDictionaryException
     */
    public void destroy(ProcessDictionary dictionary) throws ProcessDictionaryException;

    /**
     * 根据CODE查询一类字典
     * @param code 字典代码
     * @return 流程字典列表
     */
    public List<ProcessDictionary> findByCode(String code);

    /**
     * 根据SN查询字典
     * @param sn 序列号
     * @return 流程字典实体类
     */
    public ProcessDictionary findBySn(String sn);

}
