package com.example.demo.src.auth;

import com.example.demo.src.auth.model.PostLoginReq;
import com.example.demo.src.auth.model.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AuthDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public LoginUserInfo getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, name, id, pwd from User where id = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new LoginUserInfo(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("pwd")
                ),
                getPwdParams
        );
    }
}
