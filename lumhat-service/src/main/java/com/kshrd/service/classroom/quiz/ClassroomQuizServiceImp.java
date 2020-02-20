package com.kshrd.service.classroom.quiz;

import com.kshrd.configuration.utility.DateExpire;
import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.*;
import com.kshrd.repository.classroomRepository.ClassroomQuizRepository;
import com.kshrd.repository.classroomRepository.ClassroomTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ClassroomQuizServiceImp implements ClassroomQuizService{

    private ClassroomQuizRepository classroomQuizRepository;
    private ClassroomTeacherRepository classroomTeacherRepository;

    @Autowired
    public void setClassroomQuizRepository(ClassroomQuizRepository classroomQuizRepository) {
        this.classroomQuizRepository = classroomQuizRepository;
    }

    @Autowired
    public void setClassroomTeacherRepository(ClassroomTeacherRepository classroomTeacherRepository) {
        this.classroomTeacherRepository = classroomTeacherRepository;
    }

    @Override
    public Boolean insertAllDataIntoDatabase(ClassroomQuiz quiz) {
        int quizId = 0;
        try {
            if(quiz.getTopic().getId() == 0){
                int topicId = classroomQuizRepository.insertTopic(quiz.getClassId(),quiz.getTopic().getTopic());
                quiz.setTopic(new Topic(topicId,quiz.getTopic().getTopic()));
            }
            //add quiz
            quiz.setDateExpire(DateExpire.getDateExpire(quiz.getHourExpire()));
            insertQuiz(quiz);
            for (int i = 0; i < quiz.getInstructions().size(); i++) {

                quiz.getInstructions().get(i).setQuiz(quiz);
                //add instruction
                if(quiz.getInstructions().get(i).getQuestions().size()>=1){
                    insertInstruction(quiz.getInstructions().get(i));
                    for (int j = 0; j < quiz.getInstructions().get(i).getQuestions().size(); j++) {

                        quiz.getInstructions().get(i).getQuestions().get(j).setInstruction(quiz.getInstructions().get(i));

                        //add question
                        insertQuestion(quiz.getInstructions().get(i).getQuestions().get(j));

                        for (int k = 0; k < quiz.getInstructions().get(i).getQuestions().get(j).getAnswers().size(); k++) {
                            if (quiz.getInstructions().get(i).getQuestions().get(j).getAnswers().get(k).getOption().equals("")) {
                            } else {
                                quiz.getInstructions().get(i).getQuestions().get(j).getAnswers().get(k).setQuestion(quiz.getInstructions().get(i).getQuestions().get(j));
                                //add answer
                                insertAnswer(quiz.getInstructions().get(i).getQuestions().get(j).getAnswers().get(k));
                            }
                        }

                    }
                }
            }
            if(!quiz.isDraft){
                insertQuizRecordAfterAssignQuiz(quiz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    @Override
    public void insertQuiz(ClassroomQuiz classroomQuiz) {
         classroomQuizRepository.insertQuiz(classroomQuiz);
    }

    @Override
    public void insertInstruction(Instruction instruction) {
            classroomQuizRepository.insertInstruction(instruction);
    }

    @Override
    public void insertQuestion(ClassroomQuestion question) {
            classroomQuizRepository.insertQuestion(question);
    }

    @Override
    public void insertAnswer(ClassroomAnswer answer) {
            classroomQuizRepository.insertAnswer(answer);
    }

    @Override
    public List<Topic> findAllTopicByClassId(int classId) {
        return classroomQuizRepository.findAllTopicByClassId(classId);
    }

    @Override
    public List<ClassroomQuizTopic> findAllQuizByTopicId(int classId) {
        List<ClassroomQuizTopic> classroomQuizTopics = new ArrayList<>();
        List<Topic> topics = classroomQuizRepository.findAllTopicByClassId(classId);
        for (Topic topic : topics) {
            classroomQuizTopics.add(new ClassroomQuizTopic(topic.getTopic(), classroomQuizRepository.findQuizByTopicId(topic.getId())));
        }
        return classroomQuizTopics;
    }

    @Override
    public List<ClassroomQuizTopic> findAllQuizActiveByTopicId(int classId) {
        List<ClassroomQuizTopic> classroomQuizTopics = new ArrayList<>();
        List<Topic> topics = classroomQuizRepository.findAllTopicByClassId(classId);
        for (Topic topic : topics) {
            classroomQuizTopics.add(new ClassroomQuizTopic(topic.getTopic(), classroomQuizRepository.findAllQuizActiveByTopicId(topic.getId())));
        }
        return classroomQuizTopics;
    }

    @Override
    public Boolean deleteQuizByID(Integer id) {
        return classroomQuizRepository.deleteQuizByID(id);
    }

    @Override
    public ClassroomQuiz findQuizById(Boolean isDraft, int quizId) {
        return classroomQuizRepository.findQuizById(isDraft,quizId);
    }

    @Override
    public void insertQuizRecordAfterAssignQuiz(ClassroomQuiz quiz) {
        if(!quiz.isDraft){
            List<ClassroomStudent> students = classroomTeacherRepository.findAllStudentByClassId(quiz.getClassId());
            if(students.size()>0){
                for (ClassroomStudent stu: students) {
                    int userId = stu.getId();
                    classroomQuizRepository.insertQuizRecord(userId,quiz.getId(),quiz.getClassId());
                }
            }
        }
    }

    @Override
    public List<ClassroomQuiz> findAllQuizByClassId(int classId) {
        return classroomQuizRepository.findAllQuizByClassId(classId);
    }

    @Override
    public List<ClassroomQuiz> findAllQuizByClassIdByPage(int classId, Paging paging) {
        return classroomQuizRepository.findAllQuizByClassIdByPage(classId,paging);
    }

    @Override
    public List<ClassroomQuiz> findQuizStudentByClassId(int classId, int userId) {
        return classroomQuizRepository.findQuizStudentByClassId(classId,userId);
    }

    @Override
    public List<ClassroomQuiz> findQuizStudentByClassIdByPage(int classId, int userId, Paging paging) {
        return classroomQuizRepository.findQuizStudentByClassIdByPage(classId,userId,paging);
    }

    @Override
    public Topic findTopicByQuizId(Integer id) {
        return classroomQuizRepository.findTopicByQuizId(id);
    }

    @Override
    public void removeQuizFromDbById(Integer id) {
        classroomQuizRepository.removeQuizFromDbById(id);
    }

    @Override
    public void publishQuiz(Integer quizId, Integer duration, Date dateExpire) {
        classroomQuizRepository.publishQuiz(quizId,duration,dateExpire);
    }


}
