package dnt.itsnow.support;

import dnt.itsnow.annotation.Dictionary;
import dnt.itsnow.exception.DictionaryException;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.util.DefaultPage;
import dnt.itsnow.repository.DictionaryRepository;
import dnt.itsnow.service.DictionaryService;
import net.happyonroad.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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
    public Page<dnt.itsnow.model.Dictionary> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding dictionaries by keyword: {}", keyword);

       int total = repository.count(keyword);
       List<dnt.itsnow.model.Dictionary> dictionaries = new ArrayList<dnt.itsnow.model.Dictionary>();
        if (total > 0) {
            dictionaries = repository.findAll(keyword, pageable);
       }
        DefaultPage page = new DefaultPage<dnt.itsnow.model.Dictionary>(dictionaries, pageable, total);

        logger.debug("Found   {}", page.getContent());

       return page;

    }

    @Override
    public dnt.itsnow.model.Dictionary create(dnt.itsnow.model.Dictionary dictionary) throws DictionaryException {
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
    public dnt.itsnow.model.Dictionary update(dnt.itsnow.model.Dictionary dictionary) throws DictionaryException {
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
    public void destroy(dnt.itsnow.model.Dictionary dictionary) throws DictionaryException {
        logger.warn("Deleting {}", dictionary);

        if (dictionary == null) {
            throw new DictionaryException("Code of Dictionary can not be empty");
        }

        repository.delete(dictionary.getCode());

        logger.warn("Deleted  {}", dictionary);
    }

    @Override
    public dnt.itsnow.model.Dictionary findByCode(String code) {
        logger.debug("Finding dictionary by code: {}", code);

       dnt.itsnow.model.Dictionary dictionary = repository.findByCode(code);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

    @Override
    public dnt.itsnow.model.Dictionary find(Class clazz) {

        String code = "";

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Dictionary dict = field.getAnnotation(Dictionary.class);
            if (dict != null) {
                code = dict.code();
            }
        }

        logger.debug("Finding dictionary by code: {}", code);

        dnt.itsnow.model.Dictionary dictionary = repository.findByCode(code);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

    @Override
    public dnt.itsnow.model.Dictionary findByName(String name) {
        logger.debug("Finding dictionary by name: {}", name);

        dnt.itsnow.model.Dictionary dictionary = repository.findByName(name);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

    @Override
    public dnt.itsnow.model.Dictionary findByLabel(String label) {
        logger.debug("Finding dictionary by label: {}", label);

        dnt.itsnow.model.Dictionary dictionary = repository.findByLabel(label);

        logger.debug("Found   {}", dictionary);

        return dictionary;
    }

}
