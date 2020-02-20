package com.kshrd.repository;
import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserRepository {
    @Select(" select userinfoinsert(#{firstName},#{lastName},#{gender},#{dob},#{email},#{password},#{facebookId},#{status},#{school},#{imageUrl})")
    public boolean add(User user);
    @Select("Select  * from lh_user WHERE  facebook_id= #{facebookId}")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dob", column = "dob"),
            @Result(property = "email", column = "email"),
            @Result(property = "signupDate", column = "sign_up_date"),
            @Result(property = "modifyDate", column = "modified_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "password", column = "password"),
            @Result(property = "school", column = "school"),
            @Result(property = "id", column = "id"),
            @Result(property = "facebookId", column = "facebook_id"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "id", column = "id"),
            @Result(property = "roles", column = "id" ,
                    many= @Many(select = "com.kshrd.repository.RoleRepository.findRoleByUserId")
            )
    })
    public User findOne(String id);
    @Select("Select id,first_name,last_name,gender,dob,email, facebook_id,sign_up_date,modified_date,status,school,image_url from lh_user")
       @Results({
               @Result(property = "firstName", column = "first_name"),
               @Result(property = "lastName", column = "last_name"),
               @Result(property = "gender", column = "gender"),
               @Result(property = "dob", column = "dob"),
               @Result(property = "email", column = "email"),
               @Result(property = "signupDate", column = "sign_up_date"),
               @Result(property = "modifyDate", column = "modified_date"),
               @Result(property = "status", column = "status"),
               @Result(property = "school", column = "school"),
               @Result(property = "facebookId", column = "facebook_id"),
               @Result(property = "imageUrl", column = "image_url")
       })
        public List<User> findAll();
        @Delete("Delete from lh_user WHERE  facebook_id= #{facebookId}")
         public boolean delete(String id);
         @Update("update lh_user set school=#{school},first_name=#{firstName},last_name=#{lastName},gender=#{gender}, dob=#{dob},email=#{email}  where facebook_id= #{facebookId} ")
         public boolean update(User user);

        @Select("SELECT u.facebook_id FROM lh_user u WHERE u.facebook_id=#{id}")
        @Results({
            @Result(property = "facebookId", column = "facebook_id"),
            @Result(property = "roles", column = "id" ,
            many= @Many(select = "com.kshrd.repository.RoleRepository.findRoleByUserId")
            )
    })
    public String findById(String id);

    @Select("SELECT * from lh_user where email like #{email}")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dob", column = "dob"),
            @Result(property = "email", column = "email"),
            @Result(property = "signupDate", column = "sign_up_date"),
            @Result(property = "modifyDate", column = "modified_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "password", column = "password"),
            @Result(property = "school", column = "school"),
            @Result(property = "id", column = "id"),
            @Result(property = "facebookId", column = "facebook_id"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "roles", column = "id" ,
                    many= @Many(select = "com.kshrd.repository.RoleRepository.findRoleByUserId")
            )
    })
    User loadUserByEmail(String email);
    @Select("SELECT COUNT(id) FROM lh_user")
    String findQtyUser();
    @Select("SELECT COUNT(id) FROM lh_quiz where topic_id isnull")
    String findAllQuiz();
    @Select("select count(qu.id) as count_question from lh_major m join lh_sub_major sm \n" +
            "on m.id=sm.major_id\n" +
            "join lh_sub_major ssm on sm.id=ssm.parent_id\n" +
            "join lh_quiz q on q.sub_major_id =ssm.id\n" +
            "join lh_instruction ins on ins.quiz_id=q.id\n" +
            "join lh_question qu on qu.instruction_id=ins.id\n" +
            "where m.id=1")
    String findItQuestion();
    @Select("select count(qu.id) as count_question from lh_major m join lh_sub_major sm \n" +
            "on m.id=sm.major_id\n" +
            "join lh_sub_major ssm on sm.id=ssm.parent_id\n" +
            "join lh_quiz q on q.sub_major_id =ssm.id\n" +
            "join lh_instruction ins on ins.quiz_id=q.id\n" +
            "join lh_question qu on qu.instruction_id=ins.id\n" +
            "where m.id=2")
    String findEnglishQuestion();
    @Select("select count(qu.id) as count_question from lh_major m join lh_sub_major sm \n" +
            "on m.id=sm.major_id\n" +
            "join lh_sub_major ssm on sm.id=ssm.parent_id\n" +
            "join lh_quiz q on q.sub_major_id =ssm.id\n" +
            "join lh_instruction ins on ins.quiz_id=q.id\n" +
            "join lh_question qu on qu.instruction_id=ins.id\n" +
            "where m.id=3")
    String findKoreanQuestion();

    @Select("SELECT id,first_name,last_name FROM lh_user u ORDER BY u.modified_date DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
             @Result(property = "roles", column = "id" ,
                    many= @Many(select = "com.kshrd.repository.RoleRepository.findRoleByUserId")
            )
    })
    List<User> findAllUser(@Param("paging")Paging paging);

    @Select("SELECT COUNT(id) from lh_user")
    public int countUser();

    @Select("SELECT COUNT(id) from lh_class where status = 0;")
    int findAllClass();

    @Select("SELECT COUNT(*) from lh_class_detail where status = true;")
    int findAllStudent();


}
