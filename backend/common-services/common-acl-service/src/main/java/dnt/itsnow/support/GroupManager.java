/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Group;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.service.GroupService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>Group Manager</h1>
 */
@Service
public class GroupManager extends Bean implements GroupService {
    @Autowired
    private GroupRepository repository;

    @Override
    public List<Group> search(String keyword) {
        return repository.findAllByKeyword(keyword);
    }

    @Override
    public Group find(String name) {
        return repository.findByName(name);
    }
}
