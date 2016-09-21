package com.weibangong.dept;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weibangong.security.model.Base;
import lombok.Data;
import lombok.ToString;
import com.weibangong.security.annotation.FieldVaildation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangping on 16/3/8.
 */
@Data
@ToString(exclude = {"parent", "children"})
public class Dept extends Base {

    /**
     * 部门名称
     */
    @FieldVaildation(desc = "部门名称", empty = false, length = 20)
    private String fullname;

    /**
     * 部门头像
     */
    private String avatar;

    /**
     * 部门权重 用于排序
     */
    private Integer weight;

    /**
     * 部门负责人id
     */
    private Long managerId;

    /**
     * 上级部门id;
     */
    private Long parentId;

    /**
     * 上级部门名称
     */
    private String parentFullname;

    /**
     * 所有子部门
     */
    private List<Dept> children;

    /**
     * 所有子部门ID
     */
    private List<Long> childIds;

    @JsonIgnore
    private Dept parent;

    public static void main(String[] args) {
        List<Long> list1 = new ArrayList<>();

        list1.add(1L);
        list1.add(2L);
        list1.add(3L);
        list1.add(4L);

        List<Long> list3 = new ArrayList<>(list1);

        List<Long> list2 = new ArrayList<>();

        list2.add(3L);
        list2.add(4L);
        list2.add(5L);
        list2.add(6L);


        list1.retainAll(list2);
        list3.removeAll(list1);
        list2.removeAll(list1);

        for (Long ll : list1 ) {
            System.out.println(ll);
        }

        System.out.println("=====");

        for (Long ll : list2) {
            System.out.println(ll);
        }


        System.out.println("=====");

        for (Long ll : list3) {
            System.out.println(ll);
        }




    }
}
