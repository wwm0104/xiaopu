package com.chinaxiaopu.xiaopuMobi.service.authorization;

import com.chinaxiaopu.xiaopuMobi.dto.authorization.ResourceDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.RoleResourceDto;
import com.chinaxiaopu.xiaopuMobi.mapper.ResourceMapper;
import com.chinaxiaopu.xiaopuMobi.model.Resource;
import com.chinaxiaopu.xiaopuMobi.model.RoleResource;
import com.chinaxiaopu.xiaopuMobi.vo.authorization.ResourceTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Ellie on 2016/11/17.
 */
@Service
public class ResouceService {


    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceService roleResourceService;


    public Resource findResourceById(Integer id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    public List<Resource> findByPermission(String permission) {
        return resourceMapper.selectByPermission(permission);
    }

    public List<Resource> findByRoleId(Integer roleId) {
        return resourceMapper.selectResourceByRoleId(roleId);
    }

    public List<Resource> findAll() {
        return resourceMapper.findAll();
    }

    /**
     * 将资源列表重新排序为递归树
     */
    public List<Resource> selectResource() {
        List<Resource> resourceList = new ArrayList<>();
        List<Resource> _resourceList = resourceMapper.selectAll();
        for(Resource resource : _resourceList){
            if(resource.getParentId() == 0){
                resourceList.add(resource);
                if(!checkIsExistChildNode(_resourceList,resource.getId())){
                    List<Resource> childrens = new ArrayList<>();
                    childrens = getChildList(_resourceList,resource.getId(),childrens);
                    resourceList.addAll(childrens);
                }
            }
        }
        return resourceList;
    }

    /**
     * 递归加载子节点
     * @param resourceList
     * @param pid
     * @param childList
     * @return
     */
    public List<Resource> getChildList(List<Resource> resourceList,Integer pid,List<Resource> childList){
            for(Resource resource : resourceList){
                if(resource.getParentId() == pid){
                    childList.add(resource);
                    if(!checkIsExistChildNode(resourceList,resource.getId())){
                        getChildList(resourceList,resource.getId(),childList);
                    }
                }
            }
        return childList;
    }


    public int updateResource(Resource resource) {
        return resourceMapper.updateByPrimaryKeySelective(resource);
    }

    public int deleteResource(Integer id) {
        List<Resource> resourceList = resourceMapper.selectAll();
        Set<Integer> resourceIds = new HashSet<>();
        resourceIds.add(id);
        resourceIds = findChildIdsById(resourceList, resourceIds, id);
        List<Integer> ids = new ArrayList<>();
        ids.addAll(resourceIds);
        roleResourceService.deleteRoleResourceByResourceIds(ids);
        return resourceMapper.deleteByIds(ids);
    }

    /**
     * 判断当前节点是否存在子节点如果存在获取所有的子节点id
     *
     * @param resourceList
     * @param cids
     * @param id
     * @return
     */
    public Set<Integer> findChildIdsById(List<Resource> resourceList, Set<Integer> cids, Integer id) {
        if (!checkIsExistChildNode(resourceList, id)) {
            List<Resource> oneNode = resourceMapper.selectByParentId(id);
            for (Resource resource : oneNode) {
                cids.add(resource.getId());
                findChildIdsById(resourceList, cids, resource.getId());
            }
        }
        return cids;
    }

    public Set<Integer> findParentIdsByRoleId(Integer roleId) {
        List<Resource> dataList = resourceMapper.selectResourceByRoleId(roleId);
        Set<Integer> pids = new HashSet<>();
        for (Resource resource : dataList) {
            Integer parentId = resource.getParentId();
            if (parentId != null) {
                pids.add(parentId);
            }
        }
        return pids;
    }

    /**
     * 生成管理员菜单树
     *
     * @param resources
     * @return
     */
    public List<ResourceTreeVo> buildAminResourceTreeData(List<Resource> resources) {
        List<ResourceTreeVo> treeList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceTreeVo treeVo = new ResourceTreeVo();
            treeVo.setId(resource.getId());
            if (resource.getParentId() == 0) {
                treeVo.setParent("#");
                treeVo.setSelected(false);
            } else {
                treeVo.setParent(resource.getParentId() + "");
                treeVo.setSelected(true);
            }
            treeVo.setOpened(true);
            treeVo.setText(resource.getName());
            treeVo.setSort(Integer.parseInt(resource.getSort()));
            treeList.add(treeVo);

        }
        return treeList;
    }

    /**
     * 生成用户资源菜单
     *
     * @param userResources
     * @param WholeResource
     * @return
     */
    public List<ResourceTreeVo> builUserResourceTreeData(List<Resource> userResources, List<Resource> WholeResource) {

        List<ResourceTreeVo> treeList = new ArrayList<>();
        Set<Integer> userResourceIdSet = new HashSet<>();
        for (Resource resource : userResources) {
            userResourceIdSet.add(resource.getId());
        }

        for (Resource resource : WholeResource) {
            ResourceTreeVo treeVo = new ResourceTreeVo();
            treeVo.setId(resource.getId());

            if (resource.getParentId() == 0) {
                treeVo.setParent("#");
                treeVo.setSelected(false);
            } else {
                treeVo.setParent(resource.getParentId() + "");
                if (userResourceIdSet.contains(resource.getId()) && checkIsExistChildNode(userResources, resource.getId())) {
                    treeVo.setSelected(true);
                } else {
                    treeVo.setSelected(false);
                }
            }
            treeVo.setText(resource.getName());
            treeVo.setSort(Integer.parseInt(resource.getSort()));
            treeList.add(treeVo);
        }
        return treeList;
    }


    /**
     * 更新资源角色关系
     * @param roleResourceDto
     */
    public int updateRoleResource(RoleResourceDto roleResourceDto) {
        List<Resource> dataList = resourceMapper.selectResourceByRoleId(roleResourceDto.getRoleId());
        roleResourceService.deleteRoleResources(roleResourceDto.getRoleId());//清空现有资源
        Set<Integer> resourceIdSet = new HashSet<>();
        List<Integer> resourceList = roleResourceDto.getResourceList();
        for (int i : resourceList) {
            Set<Integer> pids = getParentIdsById(new HashSet<Integer>(), i);
            resourceIdSet.addAll(pids);
        }
        int count = roleResourceService.updateRoleResources(roleResourceDto.getRoleId(), resourceIdSet);
        return count;
    }

    /**
     * 获取选中资源的父节点Id
     *
     * @param pids
     * @param id
     * @return
     */
    public Set<Integer> getParentIdsById(Set<Integer> pids, Integer id) {
        pids.add(id);
        Resource resource = resourceMapper.selectByPrimaryKey(id);
        if (resource != null && resource.getParentId() != 0) {
            pids.add(resource.getParentId());
            return getParentIdsById(pids, resource.getParentId());
        }
        return pids;
    }

    /**
     * 创建资源
     * 每次创建新资源的时候都需要给管理员加上该资源
     *
     * @param resource
     * @return
     */
    public int createResource(Resource resource, final int createBy) {
        resource.setCreateBy(createBy);
        resource.setCreateTime(new Date());
        resource.setUpdateBy(createBy);

        Set<Integer> pids = new HashSet<>();
        pids = this.getParentIdsById(pids, resource.getParentId());
        String parentIds = "";
        for (int parentId : pids) {
            if(parentIds.equals("")){
                parentIds = parentId + "";
            }else {
                parentIds = parentIds + "," + parentId ;
            }
        }
        resource.setParentIds(parentIds);
//        String pids = resourceMapper.selectParentIdsById(resource.getParentId());
//        resource.setParentIds(StringUtils.isEmpty(pids) ? resource.getParentId() + "" : (pids + "," + resource.getParentId()));
        int flag = resourceMapper.insertSelective(resource);
        if (flag > 0) {
            Resource resource1 = resourceMapper.selectByName(resource.getName());
            RoleResource roleResource = new RoleResource();
            roleResource.setResourceId(resource1.getId());
            roleResource.setRoleId(1);
            roleResourceService.insertBySelective(roleResource);
        }
        return flag;
    }


    /**
     * 判断当前节点是(false)否(true)存在子节点
     *
     * @param resources
     * @param id
     * @return
     */
    public boolean checkIsExistChildNode(List<Resource> resources, int id) {
        boolean flag = true;
        for (Resource resource : resources) {
            if (resource.getParentId() == id) flag = false;
        }
        return flag;
    }


    public List<ResourceDto> getTreeTableData() {

        List<Resource> dataList = resourceMapper.selectAll();

        List<ResourceDto> rList = new ArrayList<>();
        Map<Integer, String> typeMap = new HashMap<>();
        typeMap.put(1, "菜单");
        typeMap.put(2, "按钮");
        typeMap.put(3, "超链接");
        typeMap.put(4, "列表");
        for (Resource resource : dataList) {
            ResourceDto dto = new ResourceDto();
            dto.setId(resource.getId());
            dto.setParentId(resource.getParentId());
            dto.setName(resource.getName());
            dto.setUrl(resource.getUrl());
            dto.setPermission(resource.getPermission());
            dto.setSort(resource.getSort());
            dto.setIcon(resource.getIcon());
//            dto.setType(typeMap.get(resource.getType()));
            dto.setAvailable(resource.getAvailable() == 1 ? "启用" : "未启用");
            rList.add(dto);
        }
        return rList;
    }

//    /**
//     * 生成菜单树
//     *
//     * */
//    public List<ResourceTreeTableDto> buildTreeTableData(List<ResourceTreeTableDto> dataList) {
//        List<ResourceTreeTableDto> nodeList = new ArrayList<>();
//        for (ResourceTreeTableDto r1 : dataList) {
//            boolean mark = false;
//            for (ResourceTreeTableDto r2 : dataList) {
//                if (r1.getParentId() == r2.getId()) {
//                    mark = true;
//                    if (r2.getChildren() == null) {
//                        r2.setChildren(new ArrayList<ResourceTreeTableDto>());
//                    }
//                    r2.getChildren().add(r1);
//                    break;
//                }
//            }
//            if (!mark) {
//                nodeList.add(r1);
//            }
//        }
//        return nodeList;
//    }

}
