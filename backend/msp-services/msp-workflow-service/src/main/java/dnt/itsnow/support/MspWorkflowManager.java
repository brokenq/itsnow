package dnt.itsnow.support;

import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.MspWorkflowRepository;
import dnt.itsnow.service.MspWorkflowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public class MspWorkflowManager extends WorkflowManager implements MspWorkflowService {

    @Autowired
    private MspWorkflowRepository mspWorkflowRepository;

    @Override
    public Page<Workflow> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding workflow by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = mspWorkflowRepository.count();
            List<Workflow> workflows = mspWorkflowRepository.find("1","updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Workflow>(workflows, pageable, total);
        }else{
            int total = mspWorkflowRepository.countByKeyword("%"+keyword+"%");
            List<Workflow> workflows = mspWorkflowRepository.findByKeyword("1","%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Workflow>(workflows, pageable, total);
        }
    }

    @Override
    public Workflow findBySn(String sn) {
        logger.debug("Finding Workflow by sn: {}", sn);

        return mspWorkflowRepository.findBySn(sn,"1");
    }

}
