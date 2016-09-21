package com.weibangong.fastview;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghailong on 16/1/15.
 */
@Data
public class FastViewPage {

    private List<Object> items = new ArrayList<>();

    private Integer offset;

    private Integer limit;

    private Long total;
}
