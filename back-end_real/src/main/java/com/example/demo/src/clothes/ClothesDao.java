package com.example.demo.src.clothes;

import com.example.demo.src.clothes.model.GetClothesListReq;
import com.example.demo.src.clothes.model.GetClothesListRes;
import com.example.demo.src.clothes.model.PostClothesReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ClothesDao {
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int createClothes(PostClothesReq postClothesReq, String imgUri) {
        String createClothesQuery = "insert into clothes(userIdx, imgUrl, season,bigCategory,smallCategory, color ) VALUES (?,?,?,?,?,?)";
        Object[] createClothesParams = new Object[]{postClothesReq.getUserIdx(),imgUri,postClothesReq.getSeason(),postClothesReq.getBigCategory(),postClothesReq.getSmallCategory(),postClothesReq.getColor()};
        this.jdbcTemplate.update(createClothesQuery, createClothesParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public List<GetClothesListRes> selectPosts(GetClothesListReq getClothesListReq) {
        String selectClothesQuery ="SELECT clothes.clothesIdx, clothes.imgUrl, clothes.bigCategory, clothes.smallCategory,clothes.color, clothes.season\n"+
        "FROM clothes, user\n"+
        "WHERE clothes.userIdx = user.userIdx and user.userIdx=? and clothes.status ='ACTIVE' and clothes.season=? and clothes.bigCategory =?\n";

        System.out.println("userIdx:"+getClothesListReq.getUserIdx()+"season"+ getClothesListReq.getSeason()+
                "bigCategory"+getClothesListReq.getBigCategory()+"smallCategory"+ getClothesListReq.getSmallCategory());
        GetClothesListReq selectClothesParam = getClothesListReq;
        return this.jdbcTemplate.query(selectClothesQuery,
                (rs,rowNum) -> new GetClothesListRes(
                        rs.getInt("clothesIdx"),
                        rs.getString("imgUrl"),
                        rs.getInt("season"),
                        rs.getInt("bigCategory"),
                        rs.getInt("smallCategory"),
                        rs.getString("color")),
                        getClothesListReq.getUserIdx(),getClothesListReq.getSeason()
                        ,getClothesListReq.getBigCategory()
                );
    }
    public List<GetClothesListRes> selectPostsAll(int userIdx) {
        String selectClothesQuery = "Select c.clothesIdx, c.imgUrl, c.season, c.bigCategory, c.smallCategory, c.color\n"+
                "FROM clothes c, user u\n"+
                "WHERE c.userIdx =u.userIdx\n"+
                "and u.userIdx=? \n"+
                "and c.status='ACTIVE';";

        return this.jdbcTemplate.query(selectClothesQuery,
                (rs,rowNum) -> new GetClothesListRes(
                        rs.getInt("clothesIdx"),
                        rs.getString("imgUrl"),
                        rs.getInt("season"),
                        rs.getInt("bigCategory"),
                        rs.getInt("smallCategory"),
                        rs.getString("color")), userIdx);
    }

    public int deleteClothes(int clothesIdx){
        String deleteClothesQuery = "UPDATE clothes SET status='INACTIVE' WHERE clothesIdx=?";
        Object[] deleteClothesParams = new Object[]{clothesIdx};
        return this.jdbcTemplate.update(deleteClothesQuery,
                deleteClothesParams);
    }
}
