package dnt.itsnow.support;

import dnt.itsnow.exception.DictionaryException;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.DictionaryRepository;
import dnt.itsnow.service.DictionaryService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>数据字典业务实现类</h1>
 */
@Service
@Transactional
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
            throw new DictionaryException("Code of Dictionary can not be empty");
        }

        repository.delete(dictionary.getCode());

        logger.warn("Deleted  {}", dictionary);
    }

    @Override
    public Dictionary findByCode(String code) {
        logger.debug("Finding dictionary by code: {}", code);

       Dictionary dictionary = repository.findByCode(code);

        logger.debug("Found   {}", dictionary);

        return dictionary;

    }

    @Override
    public Dictionary findByName(String name) {
        logger.debug("Finding dictionary by name: {}", name);

        Dictionary dictionary = repository.findByName(name);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

    @Override
    public Dictionary findByLabel(String label) {
        logger.debug("Finding dictionary by label: {}", label);

        Dictionary dictionary = repository.findByLabel(label);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

}
