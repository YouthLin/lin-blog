package com.youthlin.blog.model.bo;

import java.util.List;

/**
 * 分页对象接口
 * <p>
 * 创建： youthlin.chen
 * 时间： 2017-05-10 09:59.
 */
public interface Pageable<T> {
    List<T> getList();

    // region //每页显示 pageSize 条，当前第 currentPage 页，共 totalPage 页。
    // 总页数
    long getTotalPage();

    // 当前第几页，1 开始
    long getCurrentPage();

    // 每页几条数据
    long getPageSize();
    //endregion

    //region // 共 totalRow 条，本页显示 startRow 至 endRow 共 size 条
    // 共几条数据
    long getTotalRow();

    // 本页第一条数据是所有数据第几个
    long getStartRow();

    // 本页最后一条数据
    long getEndRow();

    // 本页几条数据
    long getSize();
    //endregion

}
