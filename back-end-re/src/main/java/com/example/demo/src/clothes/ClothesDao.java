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
        String selectClothesQuery = "Select c.clothesIdx, c.imgUrl\n"+
        "FROM clothes as c, user u\n"+
        "WHERE c.userIdx =u.userIdx\n"+
                "and u.userIdx=? \n"+
                "and c.status='ACTIVE' and c.bigCategory=? and c.smallCategory=? and c.season=?";

        System.out.println("userIdx:"+getClothesListReq.getUserIdx()+"season"+ getClothesListReq.getSeason()+
                "bigCategory"+getClothesListReq.getBigCategory()+"smallCategory"+ getClothesListReq.getSmallCategory());
        GetClothesListReq selectClothesParam = getClothesListReq;
        return this.jdbcTemplate.query(selectClothesQuery,
                (rs,rowNum) -> new GetClothesListRes(
                        rs.getString("imgUrl"),
                        rs.getInt("clothesIdx")),
                        getClothesListReq.getUserIdx(),getClothesListReq.getBigCategory()
                        ,getClothesListReq.getSmallCategory(),getClothesListReq.getSmallCategory()
                );
    }
    public List<GetClothesListRes> selectPostsAll(int userIdx) {
        String selectClothesQuery = "Select c.clothesIdx, c.imgUrl\n"+
                "FROM clothes as c, user u\n"+
                "WHERE c.userIdx =u.userIdx\n"+
                "and u.userIdx=? \n"+
                "and c.status='ACTIVE'";

        return this.jdbcTemplate.query(selectClothesQuery,
                (rs,rowNum) -> new GetClothesListRes(
                        rs.getString("imgUrl"),
                        rs.getInt("clothesIdx")), userIdx);
    }
}
