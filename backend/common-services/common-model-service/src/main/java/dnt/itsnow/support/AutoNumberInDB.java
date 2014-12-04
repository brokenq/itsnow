/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Sequence;
import net.happyonroad.platform.support.AbstractAutoNumberService;
import net.happyonroad.platform.util.NumberRule;
import dnt.itsnow.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>在DB中为各个分类计数</h1>
 *
 * 这个服务类似于Oracle的sequence服务，基于某张持久化表
 */
@Service("autoNumberInDB")
@Transactional
public class AutoNumberInDB extends AbstractAutoNumberService implements Lifecycle{
    @Autowired
    SequenceRepository repository;
    private boolean running;


    @Override
    public void configure(String catalog, String rule) {
        Sequence sequence = repository.findByCatalog(catalog);
        NumberRule numberRule = NumberRule.parse(rule);
        if( sequence != null ){
            sequence.setRule(rule);
            sequence.setIncrement(numberRule.getIncrement());
            repository.update(sequence);
        }else{
            sequence = new Sequence(catalog, rule);
            sequence.setValue(numberRule.getStart());
            sequence.setIncrement(numberRule.getIncrement());
            repository.create(sequence);
        }
        setConfiguration(catalog, numberRule);
    }

    @Override
    public String next(String catalog) {
        NumberRule rule = getConfiguration(catalog);
        long next = repository.nextValue(catalog);
        return String.format(rule.getFormat(), next);
    }

    @Override
    public void start() {
        List<Sequence> sequences = repository.findAll();
        for (Sequence sequence : sequences) {
            setConfiguration(sequence.getCatalog(), NumberRule.parse(sequence.getRule()));
        }
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
