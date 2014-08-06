/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.platform.support;

import dnt.itsnow.platform.service.AutoNumberService;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>基础的自动序号服务</h1>
 * 主要是完成了格式和数字的转换，实际实现类只需要做计数即可
 */
public abstract class AbstractAutoNumberService implements AutoNumberService {

    private Map<String, NumberRule> configurations = new HashMap<String, NumberRule>();

    @Override
    public void configure(String catalog, String rule) {
        NumberRule numberRule = NumberRule.parse(rule);
        configurations.put(process(catalog), numberRule);
    }

    @Override
    public String next(String catalog) {
        NumberRule rule = configurations.get(process(catalog));
        if( rule == null ){
            rule = NumberRule.parse(process(catalog) + "_%06d/1000");
            configurations.put(process(catalog), rule);
        }
        long next = nextValue(catalog, rule.start);
        return String.format(rule.format, next);
    }

    protected abstract long nextValue(String catalog, long start);


    String process(String catalog) {
        return catalog.toUpperCase();
    }

    static class NumberRule{

        private final String format;
        private final long start;

        public NumberRule(String format, long start) {
            this.format = format;
            this.start = start;
        }

        public NumberRule(String rule) {
            this(rule, 0L);
        }

        public static NumberRule parse(String rule) {
            if( rule.contains("/") ){
                String[] formatAndStart = rule.split("/");
                return new NumberRule(formatAndStart[0], Long.valueOf(formatAndStart[1]));
            }else{
                return new NumberRule(rule);
            }
        }
    }
}
