package com.kshrd.repository.classroomRepository;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.Answer;
import com.kshrd.model.classroom.*;
import com.kshrd.repository.classroomRepository.provider.ClassroomClassProvider;
import com.kshrd.repository.classroomRepository.provider.ClassroomQuestionProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClassroomQuizRepository {

    @Insert("INSERT INTO lh_quiz (title,duration,status,draft,topic_id,created_date,date_expire) " +
            "VALUES (trim(#{title}),#{duration}*60,'t',#{isDraft},#{topic.id},now(),#{dateExpire});")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertQuiz(ClassroomQuiz quiz);

    @Insert("INSERT INTO lh_instruction (title,quiz_id) VALUES (trim(#{title}),#{quiz.id})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertInstruction(Instruction instruction);

    @Insert("INSERT INTO lh_question (title,instruction_id) VALUES (trim(#{title}),#{instruction.id})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertQuestion(ClassroomQuestion question);

    @Insert("INSERT INTO lh_answer (option,iscorrect,question_id) VALUES (trim(#{option}),#{isCorrect},#{question.id})")
    void insertAnswer(ClassroomAnswer answer);

    @Select("select * from add_topic(#{classId},#{topic})")
    int insertTopic(@Param("classId") int classId,@Param("topic") String topic);

    @Select("select * from lh_topic where class_id = #{classId}")
    List<Topic> findAllTopicByClassId(int classId);

    @Select("select id,title,created_date,draft,topic_id,date_expire from lh_quiz where topic_id = #{topicId} and status = true;")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "dateExpire",column = "date_expire"),
            @Result(property = "topic.id",column = "topic_id"),
    })
    List<ClassroomQuiz> findQuizByTopicId(int topicId);

    @Select("select id,title,created_date,draft,topic_id,date_expire from lh_quiz where topic_id = #{topicId} and status = true and date_expire > now();")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "dateExpire",column = "date_expire"),
            @Result(property = "topic.id",column = "topic_id"),
    })
    List<ClassroomQuiz> findAllQuizActiveByTopicId(int topicId);

    @Select("select lq.id, title, created_date, draft, topic_id,topic, date_expire from lh_quiz lq\n" +
            "inner join lh_topic lt on lq.topic_id = lt.id\n" +
            "where lt.class_id = #{classId} and lq.status = true order by\n" +
            "        case when lt.id = (select lt2.id from lh_topic lt2\n" +
            "            inner join lh_quiz l on lt2.id = l.topic_id\n" +
            "            order by l.created_date desc limit 1) then  1 end,lt.id,lq.created_date desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "topic.id",column = "topic_id"),
            @Result(property = "topic.topic",column = "topic"),
            @Result(property = "dateExpire",column = "date_expire"),
    })
    List<ClassroomQuiz> findAllQuizByClassId(int classId);

    @Select("select lq.id, title, created_date, draft, topic_id,topic, date_expire from lh_quiz lq\n" +
            "inner join lh_topic lt on lq.topic_id = lt.id\n" +
            "where lt.class_id = #{classId} and lq.status = true order by\n" +
            "        case when lt.id = (select lt2.id from lh_topic lt2\n" +
            "            inner join lh_quiz l on lt2.id = l.topic_id\n" +
            "            order by l.created_date desc limit 1) then  1 end,lt.id,lq.created_date desc\n" +
            "limit #{paging.limit} offset #{paging.offset};")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "topic.id",column = "topic_id"),
            @Result(property = "topic.topic",column = "topic"),
            @Result(property = "dateExpire",column = "date_expire"),
    })
    List<ClassroomQuiz> findAllQuizByClassIdByPage(@Param("classId") int classId,@Param("paging") Paging paging);

    @Select("select lq.id,lq.title,lq.created_date,lq.draft,lq.topic_id,lq.date_expire, lqr.status quizRecordStatus\n" +
            "from lh_quiz lq\n" +
            "inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "where topic_id = #{topicId} and lq.status = true and lq.draft = false and lqr.user_id = #{userId};")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "dateExpire",column = "date_expire"),
            @Result(property = "topic.id",column = "topic_id"),
            @Result(property = "quizRecordStatus", column = "quizrecordstatus")
    })
    List<ClassroomQuiz> findQuizStudentByTopicId(@Param("topicId") int topicId,@Param("userId")int userId);

    @Select("select lq.id, title, created_date, draft, topic_id,topic, date_expire,lqr.status quizRecordStatus from lh_quiz lq\n" +
            "inner join lh_topic lt on lq.topic_id = lt.id\n" +
            "inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "where lt.class_id = #{classId} and lq.status = true and lq.draft = false and lqr.user_id = #{userId} order by\n" +
            "        case when lt.id = (select lt2.id from lh_topic lt2\n" +
            "            inner join lh_quiz l on lt2.id = l.topic_id\n" +
            "            order by l.created_date desc limit 1) then  1 end,lt.id,lq.created_date desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "dateExpire",column = "date_expire"),
            @Result(property = "topic.id",column = "topic_id"),
            @Result(property = "quizRecordStatus", column = "quizrecordstatus")
    })
    List<ClassroomQuiz> findQuizStudentByClassId(@Param("classId") int classId, @Param("userId")int userId);

    @Select("select lq.id, title, created_date, draft, topic_id,topic, date_expire,lqr.status quizRecordStatus from lh_quiz lq\n" +
            "inner join lh_topic lt on lq.topic_id = lt.id\n" +
            "inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "where lt.class_id = #{classId} and lq.status = true and lq.draft = false and lqr.user_id = #{userId} order by\n" +
            "        case when lt.id = (select lt2.id from lh_topic lt2\n" +
            "            inner join lh_quiz l on lt2.id = l.topic_id\n" +
            "            order by l.created_date desc limit 1) then  1 end,lt.id,lq.created_date desc\n" +
            "limit #{paging.limit} offset #{paging.offset};")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "draft",column = "draft"),
            @Result(property = "dateExpire",column = "date_expire"),
            @Result(property = "topic.id",column = "topic_id"),
            @Result(property = "topic.topic",column = "topic"),
            @Result(property = "quizRecordStatus", column = "quizrecordstatus")
    })
    List<ClassroomQuiz> findQuizStudentByClassIdByPage(@Param("classId") int classId, @Param("userId")int userId,@Param("paging")Paging paging);

    @Delete("update lh_quiz set status = 'f' where id=#{id}")
    Boolean deleteQuizByID(Integer id);

    @Delete("delete from lh_quiz where id=#{id}")
    void removeQuizFromDbById(Integer id);

    @Select("select lt.id,lt.topic  from lh_topic lt\n" +
            "inner join lh_quiz lq on lt.id = lq.topic_id\n" +
            "where lq.id = #{id}")
    Topic findTopicByQuizId(Integer id);

    //Add all student to quizrecord after created quiz
    @InsertProvider(method = "insertQuizRecord",type = ClassroomQuestionProvider.class)
    void insertQuizRecord(int userId, int quizId,int classId);

    @SelectProvider(method = "findQuizById",type = ClassroomQuestionProvider.class)
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "dateExpire",column = "date_expire"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "topic.id", column = "topic_id"),
            @Result(property = "topic.topic", column = "topic"),
            @Result(property = "instructions", column = "id", javaType = List.class,many = @Many(select = "findInstructionByQuizId"))
    })
    ClassroomQuiz findQuizById(@Param("isDraft") Boolean isDraft,@Param("quizId") int quizId);

    @Select("select * from lh_instruction li where li.quiz_id = #{id};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "questions", column = "id", javaType = List.class, many = @Many(select = "findQuestionByInstructionId"))
    })
    List<Instruction> findInstructionByQuizId(int id);

    @Select("select * from lh_question lq where lq.instruction_id = #{id};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "answers", column = "id", javaType = List.class, many = @Many(select = "findAnswerByQuestionId"))
    })
    List<ClassroomQuestion> findQuestionByInstructionId(int id);

    @Select("select * from lh_answer la where la.question_id = #{id}")
    @Results({
           @Result(property = "isCorrect",column = "iscorrect")
    })
    List<ClassroomAnswer> findAnswerByQuestionId(int id);

    @Update("update lh_quiz set draft = false,duration = #{duration},date_expire = #{dateExpire} where id = #{quizId};")
    void publishQuiz(@Param("quizId") Integer quizId,@Param("duration") Integer duration,@Param("dateExpire") Date dateExpire);
}
