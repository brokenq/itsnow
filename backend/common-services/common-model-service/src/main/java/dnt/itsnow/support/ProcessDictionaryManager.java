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
import java.util.UUID;

/**
 * <h1>流程字典业务实现类</h1>
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
            DefaultPage page = new DefaultPage<ProcessDictionary>(dictionaries, pageable, total);

            logger.debug("Finded Process Dictionary Info: {}", page);

            return page;
        }else{
            int total = repository.countByKeyword("%"+keyword+"%");
            List<ProcessDictionary> dictionaries = repository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            DefaultPage page =  new DefaultPage<ProcessDictionary>(dictionaries, pageable, total);

            logger.debug("Finded Process Dictionary Info: {}", page);

            return page;
        }

    }

    @Override
    public ProcessDictionary create(ProcessDictionary dictionary) throws ProcessDictionaryException {

        logger.info("Creating dictionary :{}", dictionary);

        if(dictionary == null){
            throw new ProcessDictionaryException("Dictionary entry can not be empty");
        }
        dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dictionary.setUpdatedAt(dictionary.getCreatedAt());
        dictionary.setSn(UUID.randomUUID().toString());
        repository.create(dictionary);

        logger.info("Created dictionary :{}", dictionary);

        return dictionary;
    }

    @Override
    public ProcessDictionary update(ProcessDictionary dictionary) throws ProcessDictionaryException {

        logger.info("Updating Process Dictionary {}", dictionary);

        if(dictionary==null){
            throw new ProcessDictionaryException("Dictionary entry can not be empty");
        }
        dictionary.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        repository.update(dictionary);

        logger.info("Updated Process Dictionary {}", dictionary);

        return dictionary;
    }

    @Override
    public void destroy(ProcessDictionary dictionary) throws ProcessDictionaryException {

        logger.warn("Deleting Process Dictionary: {}", dictionary);

        if(dictionary==null){
            throw new ProcessDictionaryException("SN of Process Dictionary can not be empty");
        }

        repository.delete(dictionary.getSn());

        logger.warn("Deleting Process Dictionary: {}", dictionary);
    }

    @Override
    public ProcessDictionary findBySn(String sn) {

        logger.debug("Finding Process Dictionary by sn: {}", sn);

        ProcessDictionary dictionary = repository.findBySn(sn);

        logger.debug("Finded Process Dictionary : {}", dictionary);

        return dictionary;
    }

    @Override
    public List<ProcessDictionary> findByCode(String code) {

        logger.debug("Finding Process Dictionary by code: {}", code);

        List<ProcessDictionary> dictionaries = repository.findByCode(code);

        logger.debug("Finding Process Dictionary : {}", dictionaries);

        return dictionaries;
    }

}
