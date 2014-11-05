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


    protected Map<String, NumberRule> configurations = new HashMap<String, NumberRule>();

    @Override
    public void configure(String catalog, String rule) {
        NumberRule numberRule = NumberRule.parse(rule);
        configurations.put(process(catalog), numberRule);
    }

    public NumberRule getConfiguration(String catalog){
        String name = process(catalog);
        NumberRule rule = configurations.get(name);
        if( rule == null ){
            rule = NumberRule.parse(name + "_%06d/1000");
            setConfiguration(name, rule);
        }
        return rule;
    }

    public void setConfiguration(String catalog, NumberRule rule){
        configurations.put(process(catalog), rule);
    }

    protected String process(String catalog) {
        return catalog.toUpperCase();
    }

    protected static class NumberRule{

        final String format;
        final long start;

        public NumberRule(String format, long start) {
            this.format = format;
            this.start = start;
        }

        public NumberRule(String rule) {
            this(rule, 0L);
        }

        public long getStart() {
            return start;
        }

        public String getFormat() {
            return format;
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
