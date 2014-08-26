/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Group;

import java.util.List;

/**
 * <h1>Group services</h1>
 */
public interface GroupService {
    List<Group> search(String keyword);

    Group find(String name);
}
