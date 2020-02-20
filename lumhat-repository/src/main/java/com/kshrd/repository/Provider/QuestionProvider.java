package com.kshrd.repository.Provider;

import com.kshrd.model.QuestionFilter;
import org.apache.ibatis.jdbc.SQL;

public class QuestionProvider {

    public String findInstructionByQuizId(QuestionFilter questionFilter){
        return new SQL(){{
            SELECT("i.id as inst_id, i.title as inst_title, " +
                    "q.title as quiz_title, q.duration as quiz_duration, q.id as quiz_id");
            FROM("lh_instruction i");
            RIGHT_OUTER_JOIN("lh_quiz q ON i.quiz_id = q.id");
            WHERE("q.id=" + questionFilter.getQuiz_id());
            ORDER_BY(questionFilter.getIsRandom()?"RANDOM()":"q.id");
        }}.toString();
    }

    public String findQuestionByInstructionId(Integer inst_id){
        return new SQL(){{
            SELECT("q.id as q_id, q.title as q_title, i.id as in_id");
            FROM("lh_question q");
            INNER_JOIN("lh_instruction i ON q.instruction_id = i.id");
            WHERE("i.id = " + inst_id);
        }}.toString();
    }

}

