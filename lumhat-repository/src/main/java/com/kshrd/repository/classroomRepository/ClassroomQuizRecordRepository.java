package com.kshrd.repository.classroomRepository;

import com.kshrd.model.QuizRecord;
import com.kshrd.model.classroom.ClassroomQuizRecord;
import com.kshrd.repository.classroomRepository.provider.ClassroomQuizRecordProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomQuizRecordRepository {
    @UpdateProvider(method = "updateQuizRecord",type = ClassroomQuizRecordProvider.class)
    void updateQuizRecord(ClassroomQuizRecord classroomQuizRecord);
}
