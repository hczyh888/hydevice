package com.hy.common.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNode {
    private String id;
    /**
     * 父节点id
     */
    private String parentId;
    /**
     * 是否上级节点
     */
    private boolean isParent;

    /**
     * 是否有上级节点
     */
    private boolean hasParent;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 类型：1目录2菜单
     */
    private int type;
    /**
     * 菜单RUL
     */
    private String url;
    /**
     * 图标
     */
    private String icon;
    /**
     * 节点数据
     */
    private List<TreeNode> parent;
    /**
     * 子节点数据
     */
    private List<TreeNode> children;
    /**
     * 父节点数据
     */
    private List<TreeNode> linkedList = new ArrayList<TreeNode>();
    private List<TreeNode> linkedListP = new ArrayList<TreeNode>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeNode> getParent() {
        return parent;
    }

    public void setParent(List<TreeNode> parent) {
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }



    public void setParent(boolean parent) {
        isParent = parent;
    }



    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public List<TreeNode> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(List<TreeNode> linkedList) {
        this.linkedList = linkedList;
    }

    public List<TreeNode> getLinkedListP() {
        return linkedListP;
    }

    public void setLinkedListP(List<TreeNode> linkedListP) {
        this.linkedListP = linkedListP;
    }



    public void buildNodes(List<TreeNode> nodeList){
        for (TreeNode treeNode : nodeList) {
            List<TreeNode> linkedList = treeNode.findChildNodes(nodeList, treeNode.getId());
           // List<TreeNode> linkedListp = treeNode.findParentNodes(nodeList, treeNode.getParentId());
            if (linkedList.size() > 0) {
                treeNode.setIsParent(true);
                treeNode.setChildren(linkedList);
                treeNode.setLinkedList(null);
            }
           /* if(linkedListp.size() > 0) {
                treeNode.setHasParent(true);
                treeNode.setParent(linkedListp);
            }*/
        }
    }

    public List<TreeNode> findChildNodes(List<TreeNode> list, Object parentId) {
        if (list == null && parentId == null)
            return null;
        for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
            TreeNode node = (TreeNode) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (node.getParentId() != "0"
                    && parentId.toString().equals(node.getParentId())) {
                recursionFn(list, node);
            }
        }
        return linkedList;
    }

    public List<TreeNode> findParentNodes(List<TreeNode> list, Object childId) {
        if (list == null && childId == null)
            return null;
        for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
            TreeNode node = (TreeNode) iterator.next();
            if (childId.toString().equals(node.getId())) {
                recursionFnP(list, node);
            }
        }
        return linkedListP;
    }



    private void recursionFn(List<TreeNode> list, TreeNode node) {
        List<TreeNode> childList = getChildList(list, node);// 得到子节点列表
        if (childList.size() > 0) {// 判断是否有子节点
            linkedList.add(node);
            Iterator<TreeNode> it = childList.iterator();
            while (it.hasNext()) {
                TreeNode n = (TreeNode) it.next();
                recursionFn(list, n);
            }
        } else {
            linkedList.add(node);
        }
    }

    private void recursionFnP(List<TreeNode> list, TreeNode node) {
        List<TreeNode> parentList = getParentList(list, node);// 得到父节点列表
        if (parentList.size() > 0) {// 判断是否有父节点
            linkedListP.add(node);
            Iterator<TreeNode> it = parentList.iterator();
            while (it.hasNext()) {
                TreeNode n = (TreeNode) it.next();
                recursionFnP(list, n);
            }
        } else {
            linkedListP.add(node);
        }
    }

    // 得到子节点列表
    private List<TreeNode> getChildList(List<TreeNode> list, TreeNode node) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        Iterator<TreeNode> it = list.iterator();
        while (it.hasNext()) {
            TreeNode n = (TreeNode) it.next();
            if (n.getParentId().equals(node.getId())) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }

    // 得到子节点列表
    private List<TreeNode> getParentList(List<TreeNode> list, TreeNode node) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        Iterator<TreeNode> it = list.iterator();
        while (it.hasNext()) {
            TreeNode n = (TreeNode) it.next();
            if (n.getId().equals(node.getParentId())) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
}
