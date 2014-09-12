package dnt.itsnow.support;

import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.MsuWorkflowRepository;
import dnt.itsnow.service.MsuWorkflowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public class MsuWorkflowManager extends WorkflowManager implements MsuWorkflowService {

    @Autowired
    private MsuWorkflowRepository msuWorkflowRepository;

    @Override
    public Page<Workflow> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding workflow by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = msuWorkflowRepository.count();
            List<Workflow> workflows = msuWorkflowRepository.find("0","updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Workflow>(workflows, pageable, total);
        }else{
            int total = msuWorkflowRepository.countByKeyword("%"+keyword+"%");
            List<Workflow> workflows = msuWorkflowRepository.findByKeyword("0","%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Workflow>(workflows, pageable, total);
        }
    }

    @Override
    public Workflow findBySn(String sn) {
        logger.debug("Finding Workflow by sn: {}", sn);

        return msuWorkflowRepository.findBySn(sn,"0");
    }

}
