package com.example.demo.src.post;


import com.example.demo.src.clothes.model.PostClothesReq;
import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시글 리스트 조회
    public List<GetPostsRes> selectPosts(GetPostsReq getPostsReq){
        String selectPostsQuery = "SELECT u.name as name, p.title as title, IF(postLikeCount is null, 0, postLikeCount) as postLikeCount,\n"+
        "IF(pl.status = 'ACTIVE', 'Y', 'N') as likeOrNot, p.imgUrl, p.postIdx\n"+
        "FROM Post as p\n"+
        "join User as u on u.userIdx = p.userIdx\n"+
        "left join (select postIdx, userIdx, count(postLikeidx) as postLikeCount from PostLike WHERE status = 'ACTIVE' group by postIdx) plc on plc.postIdx = p.postIdx\n"+
        "left join postlike as pl on pl.userIdx = u.userIdx and pl.postIdx = p.postIdx\n"+
        "WHERE not u.userIdx=? and p.status = 'ACTIVE'\n"+
        "group by p.postIdx;\n";
        int selectPostsParam = getPostsReq.getUserIdx();
        return this.jdbcTemplate.query(selectPostsQuery,
                (rs,rowNum) -> new GetPostsRes(
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getInt("postLikeCount"),
                        rs.getString("likeOrNot"),
                        rs.getString("imgUrl"),
                        rs.getInt("postIdx")),selectPostsParam);
    }

    public List<GetPostsRes> selectMyPosts(GetPostsReq getPostsReq) {
        String selectPostsQuery = "SELECT u.name as name, p.title as title, IF(postLikeCount is null, 0, postLikeCount) as postLikeCount,\n"+
                "IF(pl.status = 'ACTIVE', 'Y', 'N') as likeOrNot, p.imgUrl, p.postIdx\n"+
                "FROM Post as p\n"+
                "join User as u on u.userIdx = p.userIdx\n"+
                "left join (select postIdx, userIdx, count(postLikeidx) as postLikeCount from PostLike WHERE status = 'ACTIVE' group by postIdx) plc on plc.postIdx = p.postIdx\n"+
                "left join postlike as pl on pl.userIdx = u.userIdx and pl.postIdx = p.postIdx\n"+
                "WHERE u.userIdx=? and p.status = 'ACTIVE'\n"+
                "group by p.postIdx;\n";
        int selectPostsParam = getPostsReq.getUserIdx();
        return this.jdbcTemplate.query(selectPostsQuery,
                (rs,rowNum) -> new GetPostsRes(
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getInt("postLikeCount"),
                        rs.getString("likeOrNot"),
                        rs.getString("imgUrl"),
                        rs.getInt("postIdx")),selectPostsParam);
    }

    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public int checkPostExist(int postIdx){
        String checkPostExistQuery = "select exists(select postIdx from Post where postIdx = ?)";
        int checkPostExistParams = postIdx;
        return this.jdbcTemplate.queryForObject(checkPostExistQuery,
                int.class,
                checkPostExistParams);
    }

    public int checkPostLikeExist(PostPostsLikeReq postPostsLikeReq){
        String checkPostExistQuery = "select exists(select postIdx from PostLike where postIdx = ? and userIdx = ? and status='ACTIVE')";
        PostPostsLikeReq checkPostLikeExistParams = postPostsLikeReq;
        return this.jdbcTemplate.queryForObject(checkPostExistQuery,
                int.class,
                checkPostLikeExistParams.getPostIdx(),checkPostLikeExistParams.getUserIdx());
    }

    public int createPosts(PostPostsReq postPostsReq, String imgUri) {
        String createPostsQuery = "insert into post(userIdx, title, imgUrl ) VALUES (?,?,?)";
        Object[] createPostsParams = new Object[]{postPostsReq.getUserIdx(),postPostsReq.getTitle(), imgUri};
        this.jdbcTemplate.update(createPostsQuery, createPostsParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }



    public int deletePost(int postIdx){
        String updatePostQuery = "UPDATE Post SET status='INACTIVE' WHERE postIdx=?";
        Object[] deletePostParams = new Object[]{postIdx};
        return this.jdbcTemplate.update(updatePostQuery,
                deletePostParams);
    }

    public int likePosts(PostPostsLikeReq postPostsLikeReq) {
        String createPostsQuery = "insert into postlike(userIdx, postIdx) VALUES (?,?)";
        Object[] createPostsParams = new Object[]{postPostsLikeReq.getUserIdx(),postPostsLikeReq.getPostIdx()};
        this.jdbcTemplate.update(createPostsQuery, createPostsParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}
