package com.kshrd.repository.Provider;

import com.kshrd.model.MajorFilter;
import org.apache.ibatis.jdbc.SQL;

public class CategoryProvider {

    public String findMajorById(Integer id){
        return new SQL(){{

            SELECT("id, major");
            FROM("lh_major");
            WHERE("id="+id);

        }}.toString();
    }

    public String findSubMajorById(Integer id){
        return new SQL(){{
            SELECT("id, sub_major_name, major_id");
            FROM("lh_sub_major");
            WHERE("major_id="+id);
            WHERE("parent_id ISNULL");
            ORDER_BY("id");
        }}.toString();
    }

    public String findSubjectById(Integer id){
        return new SQL(){{
            SELECT("id, sub_major_name");
            FROM("lh_sub_major");
            WHERE("parent_id=" + id);
            ORDER_BY("id ASC");
        }}.toString();
    }



//  Provider History
    public String findSubMajorByUserId(Integer userId){
        return new SQL(){{
            SELECT("id,sub_major_name");
            FROM("lh_sub_major sm");
            INNER_JOIN("lh_quiz q ON sm.id= q.sub_major_id ");
            INNER_JOIN("lh_quiz_record qr ON q.id=qr.quiz_id");
            WHERE("qr.user_id=#{userId}");
        }}.toString();
    }
}
