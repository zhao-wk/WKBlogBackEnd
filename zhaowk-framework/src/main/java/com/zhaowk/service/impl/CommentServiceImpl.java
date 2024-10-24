package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.Comment;
import com.zhaowk.domain.entity.User;
import com.zhaowk.domain.vo.CommentVO;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.enums.AppHttpCodeEnum;
import com.zhaowk.exception.SystemException;
import com.zhaowk.mapper.CommentMapper;
import com.zhaowk.service.CommentService;
import com.zhaowk.service.UserService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //文章则需要articleId，友链不需要

        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT);
        //评论类型
        queryWrapper.eq(Comment::getType, commentType);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //添加其他字段
        List<CommentVO> commentVOList = toCommentVOList(page.getRecords());

        //查询所有根评论对应的子评论集合，赋值
        for (CommentVO commentVO : commentVOList) {
            //查询对应子评论
            List<CommentVO> children = getChildren(commentVO.getId());
            commentVO.setChildren(children);
        }


        return ResponseResult.okResult(new PageVO(commentVOList , page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id查询对应的子评论集合
     * @param parentId
     * @return
     */
    private List<CommentVO> getChildren(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, parentId);
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments = list(queryWrapper);
        List<CommentVO> commentVOs = toCommentVOList(comments);
        return commentVOs;
    }


    /**
     * 将Comment结果转化为CommentVO并添加username、toCommentUserName字段
     * @param list
     * @return
     */
    private List<CommentVO> toCommentVOList(List<Comment> list) {
        List<CommentVO> commentVOS = BeanCopyUtils.copyBeanList(list, CommentVO.class);
        //遍历vo集合
        for (CommentVO commentVO : commentVOS) {
            //通过createBy查询用户昵称并赋值
            String nickName = userService.getById(commentVO.getCreateBy()).getNickName();
            commentVO.setUsername(nickName);
            //通过toCommentUserId查询用户昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVO.getToCommentUserId() != -1){
                String toCommentUsername = userService.getById(commentVO.getToCommentUserId()).getNickName();
                commentVO.setToCommentUserName(toCommentUsername);
            }
        }
        return commentVOS;
    }
}
