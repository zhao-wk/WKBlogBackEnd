package com.zhaowk.constants;

public class SystemConstants {
    /**
     * 文章草稿状态
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章正常状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 分类正常状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * redis中blog登陆前缀
     */
    public static final String BLOG_LOGIN_PREFIX = "bloglogin:";

    /**
     * redis中admin登陆前缀
     */
    public static final String ADMIN_LOGIN_PREFIX = "adminlogin:";

    /**
     * 根评论
     */
    public static final Long ROOT_COMMENT = -1L;

    /**
     * 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * oss域名前缀
     */
    public static final String OSS_URL_PREFIX = "http://sluoer82h.hd-bkt.clouddn.com/";

    /**
     *文章浏览量redis前缀
     */
    public static final String ARTICLE_VIEW_COUNT_PREFIX = "article:viewCount";

    /**
     * 类型为菜单
     */
    public static final String MENU = "C";
    /**
     * 类型为按钮
     */
    public static final String BUTTON = "F";

}
