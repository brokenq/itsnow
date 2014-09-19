package dnt.itsnow.support;

import dnt.itsnow.exception.ProcessDictionaryException;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.ProcessDictionaryRepository;
import dnt.itsnow.service.ProcessDictionaryService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>流程字典服务实现类</h1>
 */
@Service
public class ProcessDictionaryManager extends Bean implements ProcessDictionaryService {

    @Autowired
    private ProcessDictionaryRepository repository;

    @Override
    public Page<ProcessDictionary> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding Process Dictionary by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<ProcessDictionary> dictionaries = repository.find("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<ProcessDictionary>(dictionaries, pageable, total);
        }else{
            int total = repository.countByKeyword("%"+keyword+"%");
            List<ProcessDictionary> dictionaries = repository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<ProcessDictionary>(dictionaries, pageable, total);
        }

    }

    @Override
    public ProcessDictionary findBySn(String sn) {
        logger.debug("Finding ProcessDictionary by sn: {}", sn);

        return repository.findByCode(sn);
    }

    @Override
    public ProcessDictionary create(ProcessDictionary dictionary) throws ProcessDictionaryException {
        logger.info("Creating site {}", dictionary);
        if(dictionary == null){
            throw new ProcessDictionaryException("site entry can not be empty");
        }
        dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dictionary.setUpdatedAt(dictionary.getCreatedAt());
        repository.create(dictionary);
        return dictionary;
    }

    @Override
    public ProcessDictionary update(ProcessDictionary dictionary) throws ProcessDictionaryException {
        logger.info("Updating Process Dictionary {}", dictionary);
        if(dictionary==null){
            throw new ProcessDictionaryException("Menu item entry can not be empty");
        }
        dictionary.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        repository.update(dictionary);
        return dictionary;
    }

    @Override
    public void destroy(String sn) throws ProcessDictionaryException {
        logger.warn("Deleting Process Dictionary sn: {}", sn);
        if(StringUtils.isBlank(sn)){
            throw new ProcessDictionaryException("sn of Process Dictionary can not be empty");
        }
        repository.delete(sn);
    }

}
