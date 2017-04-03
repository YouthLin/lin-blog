# 需求文档

## 一、系统介绍
项目名称：基于 SSM 框架的博客系统的设计和实现  
所用技术：Spring, Spring MVC, MyBatis, MySQL, jQuery, Bootstrap 等  
项目简介：使用 SSM 框架搭建的博客网站。  
包含功能：
  - 管理员 - 文章、评论、主题、设置。  
  - 游客 - 阅读、评论。  

## 二、模块划分
### 2.1 角色
权限管理基于角色，不同角色有不同权限。

#### 2.1.1 管理员
全部权限。
- 概览  已发布文章篇数、评论条数、赞个数、注册用户个数等
- 文章  写文章、发表文章、编辑文章、删除文章
- 评论  查看、审核、设为不可见、删除
- 主题  更换、上传、删除
- 插件  启用、停用、上传、删除
- 设置  网站设置：站名、默认注册用角色、游客评论默认通过等
- 个人资料  邮件、密码等

#### 2.1.2 注册用户
注册用户默认是读者角色。
管理员可以添加用户，或为已有角色重新指定角色为：读者、投稿者、编辑、管理员
- 读者  
  与游客没有多大区别，但可编辑自己的评论。后台管理可见页面：概览、文章、评论、个人资料  
  权限：`read`
- 投稿者  
  可以写文章，但不能发表。后台可见：概览、文章(可以写文章但不能发表)、评论、个人资料  
  权限：
  * `read`, 
  * `delete_posts`, 
  * `edit_posts`
- 作者  
  权限：
  * `read`, 
  * `delete_posts`,`delete_published_posts`,  
  * `edit_posts`, `edit_published_posts`, 
  * `poblish_posts`, 
  * `upload_files`

- 编辑  
  可以发表自己的或投稿者的文章，但不能管理系统设置。后台可见：概览、文章、评论、个人资料
  * `read`, `read_private_posts`, `read_private_pages`,  
  * `delete_posts`, `delete_pages`,   
    `delete_published_posts`, `delete_published_pages`,   
    <del>`delete_private_posts`, `delete_private_pages`, </del>  
    `delete_others_posts` ,`delete_others_pages`,  
  * `edit_posts`, `edit_pages`,  
    `edit_published_posts`, `edit_published_pages`,  
    `edit_private_posts`, `edit_private_pages`,  
    `edit_others_posts`, `edit_others_pages`
  * `poblish_posts`, `publish_pages`,
  * `upload_files`, 
  * `manage_categories`, `manage_links`, `manage_comments`,

> __注： 读者与管理员是 P1 优先级，其他角色可以先不实现。__  

#### 2.1.3 游客
不能进入后台页面。可以查看文章、发表评论。

### 2.2 前台
- 首页 最新文章列表；页面；侧栏
- 文章页 文章内容；评论；写评论；侧栏

> 注：侧栏最好可定制（即：可指定显示近期评论 n 条，或近期文章 n 篇或显示其他模块）  
> （管理员在后台定制，时间不够可先不实现而只是单一写死的内容，如只有近期评论、近期文章）

### 2.3 后台
- 概览页
- 文章： 写文章（存草稿，发布）、所有文章（修改）
- 评论管理： 标记为垃圾评论（前台不可见）、彻底删除（真的删除了）
- 主题管理： 换肤（低优先级）
- 插件管理： （低优先级）
- 系统设置
- 个人资料

## 三、详细说明
### 3.1 文章 Post
一篇日志、一个页面、上传的一个文件都作为一个 Post, 
拥有唯一的 ID(前台可以通过固定链接访问`?p=id`), 
但通常只有日志显示在主页文章列表，页面单独作为菜单显示，
附件一般不显示在首页文章列表。  
Post 属于一个或多个分类，可以有零个或多个标签。
针对一个 Post, 可以对其发表评论。当然，可以设置关闭评论。
可以 `赞` 一个 Post.

### 3.2 分类 & 标签
分类可以有子分类。前台可以查询一个分类下所有文章。  
标签没有层级。可以查看一个标签下所有文章。

### 3.3 评论
通常所有人都可以对 Post 发表评论，除非　Post 已关闭评论。  
评论可以回复(嵌套多少层后台管理员设置)、赞、踩。

### 3.4 发表文章
- 写草稿
- 设置发表时间、分类、标签、是否允许评论、是否设置密码
- 编写：纯文本、Markdown、HTML
- more, 摘要：文章列表显示摘要或 more 标签之前内容，文章页显示全部内容


## 四、进度安排
