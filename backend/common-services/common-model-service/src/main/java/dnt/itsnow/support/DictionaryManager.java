package dnt.itsnow.support;

import dnt.itsnow.exception.DictionaryException;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.DictionaryRepository;
import dnt.itsnow.service.DictionaryService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>数据字典业务实现类</h1>
 */
@Service
public class DictionaryManager extends Bean implements DictionaryService {

    @Autowired
    private DictionaryRepository repository;

    @Override
    public Page<Dictionary> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding dictionaries by keyword: {}", keyword);

        int total = repository.count(keyword);
        List<Dictionary> dictionaries = new ArrayList<Dictionary>();
        if (total > 0) {
            dictionaries = repository.findAll(keyword, pageable);
        }
        DefaultPage page = new DefaultPage<Dictionary>(dictionaries, pageable, total);

        logger.debug("Found   {}", page.getContent());

        return page;
    }

    @Override
    public Dictionary create(Dictionary dictionary) throws DictionaryException {

        logger.info("Creating {}", dictionary);

        if (dictionary == null) {
            throw new DictionaryException("Dictionary entry can not be empty");
        }
        dictionary.creating();
        repository.create(dictionary);

        logger.info("Created  {}", dictionary);

        return dictionary;
    }

    @Override
    public Dictionary update(Dictionary dictionary) throws DictionaryException {

        logger.info("Updating {}", dictionary);

        if (dictionary == null) {
            throw new DictionaryException("Dictionary entry can not be empty");
        }
        dictionary.updating();
        repository.update(dictionary);

        logger.info("Updated  {}", dictionary);

        return dictionary;
    }

    @Override
    public void destroy(Dictionary dictionary) throws DictionaryException {

        logger.warn("Deleting {}", dictionary);

        if (dictionary == null) {
            throw new DictionaryException("SN of Dictionary can not be empty");
        }

        repository.delete(dictionary.getSn());

        logger.warn("Deleted  {}", dictionary);
    }

    @Override
    public Dictionary findBySn(String sn) {

        logger.debug("Finding dictionary by sn: {}", sn);

        Dictionary dictionary = repository.findBySn(sn);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

    @Override
    public List<Dictionary> findByCode(String code) {

        logger.debug("Finding dictionaries by code: {}", code);

        List<Dictionary> dictionaries = repository.findByCode(code);

        logger.debug("Found   {}", dictionaries);

        return dictionaries;
    }

}
