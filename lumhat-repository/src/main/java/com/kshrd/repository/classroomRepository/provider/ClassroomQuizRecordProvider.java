package com.kshrd.repository.classroomRepository.provider;

import com.kshrd.model.classroom.ClassroomQuizRecord;

public class ClassroomQuizRecordProvider {
    public String updateQuizRecord(ClassroomQuizRecord classroomQuizRecord){
        return "update lh_quiz_record set record_date = now(), score = "+classroomQuizRecord.getScore()+", duration = "+classroomQuizRecord.getDuration()+", status = 0 where user_id = "+classroomQuizRecord.getUserId()+" and class_id = "+classroomQuizRecord.getClassId()+ "and quiz_id = "+ classroomQuizRecord.getQuiz().getId() +";";
    }
}
