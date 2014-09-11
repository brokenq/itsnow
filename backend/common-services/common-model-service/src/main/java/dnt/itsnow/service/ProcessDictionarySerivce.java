package dnt.itsnow.service;

import dnt.itsnow.exception.ProcessDictionaryException;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>流程字典Service</h1>
 */
public interface ProcessDictionarySerivce {
    public Page<ProcessDictionary> findAll(String keyword, Pageable pageable);

    public ProcessDictionary findBySn(String sn);

    public ProcessDictionary create(ProcessDictionary site) throws ProcessDictionaryException;

    public ProcessDictionary update(ProcessDictionary site) throws ProcessDictionaryException;

    public void destroy(String sn) throws ProcessDictionaryException;
}
