package dnt.itsnow.service;

import dnt.itsnow.exception.DictionaryException;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>流程字典业务层</h1>
 */
public interface DictionaryService {

    /**
     * 查询所有流程字典，可分页，可按关键字查询
     * @param keyword 关键字
     * @param pageable 分页实体类
     * @return 字典列表
     */
    public Page<Dictionary> findAll(String keyword, Pageable pageable);

    /**
     * 创建流程字典
     * @param dictionary 字典实体类
     * @return 已更新的字典
     * @throws dnt.itsnow.exception.DictionaryException
     */
    public Dictionary create(Dictionary dictionary) throws DictionaryException;

    /**
     * 更新流程字典
     * @param dictionary 待更新的字典实体类
     * @return 已更新的字典实体类
     * @throws dnt.itsnow.exception.DictionaryException
     */
    public Dictionary update(Dictionary dictionary) throws DictionaryException;

    /**
     * 销毁流程字典记录
     * @param dictionary 字典实体类
     * @throws dnt.itsnow.exception.DictionaryException
     */
    public void destroy(Dictionary dictionary) throws DictionaryException;


    /**
     * 根据CODE查询字典
     * @param code 字典代码
     * @return 流程字典实体类
     */
    public Dictionary findByCode(String code);

}
