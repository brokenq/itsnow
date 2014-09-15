/**
 * Developer: Kadvin Date: 14-9-15 下午4:15
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.SystemJob;
import dnt.itsnow.service.SystemInvokeService;
import dnt.spring.Bean;
import org.springframework.stereotype.Service;

/**
 * <h1>系统调用服务</h1>
 */
@Service
public class SystemInvokeManager extends Bean implements SystemInvokeService {
    @Override
    public String addJob(SystemJob job) {
        return null;
    }

    @Override
    public void cancelJob(String job) {

    }

    @Override
    public boolean isFinished(String job) {
        return false;
    }

    @Override
    public void waitJobFinished(String job) throws SystemInvokeException {

    }

    @Override
    public String[] read(String job, int offset) {
        return new String[0];
    }
}
