/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.platform.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>在内存中为各个分类计数</h1>
 *
 * 这个服务应该基于类似于Oracle的sequence服务，或者基于某张持久化表
 */
public class AutoNumberInDB extends AbstractAutoNumberService {
    private Map<String, Long> sequences = new ConcurrentHashMap<String, Long>();
    @Override
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
