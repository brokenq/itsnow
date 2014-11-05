/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.platform.support;

import dnt.itsnow.platform.util.NumberRule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>在内存中为各个分类计数</h1>
 *
 * 这个服务应该基于类似于Oracle的sequence服务，或者基于某张持久化表
 */
public class AutoNumberInMemory extends AbstractAutoNumberService {
    private Map<String, Long> sequences = new ConcurrentHashMap<String, Long>();


    @Override
    public String next(String catalog) {
        NumberRule rule = getConfiguration(catalog);
        long next = nextValue(catalog, rule.getStart());
        return String.format(rule.getFormat(), next);
    }


    protected long nextValue(String catalog, long start) {
        Long seq = sequences.get(catalog);
        if( seq == null ){
            seq = start;
        }
        seq++;
        sequences.put(catalog, seq);
        return seq;
    }
}
