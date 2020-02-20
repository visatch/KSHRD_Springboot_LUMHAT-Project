package com.kshrd.service.admin.quiz;

import com.kshrd.model.Answer;
import com.kshrd.model.Instruction;
import com.kshrd.model.Question;
import com.kshrd.model.Quiz;
import com.kshrd.repository.adminReposity.AdminQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class    AdminQuizServiceImp implements AdminQuizService {

    AdminQuizRepository adminQuizRepository;

    @Autowired
    public void setAdminQuizRepository(AdminQuizRepository adminQuizRepository) {
        this.adminQuizRepository = adminQuizRepository;
    }

    @Override
    public void insertQuiz(Quiz quiz) {
        adminQuizRepository.insertQuiz(quiz);
    }

    @Override
    public void insertInstruction(Instruction instruction) {
        adminQuizRepository.insertInstuction(instruction);
    }

    @Override
    public void insertQuestion(Question question) {
        adminQuizRepository.insertQuestion(question);
    }

    @Override
    public void insertAnswer(Answer answer) {
        adminQuizRepository.insertAnswer(answer);
    }

    @Override
    public Quiz findQuizById(Integer id) {
        return adminQuizRepository.findQuizById(id);
    }

    @Override
    public Boolean deleteQuestionByQuestionID(Integer id) {
        return adminQuizRepository.deleteQuestionByID(id);
    }

    @Override
    public Boolean deleteQuizByID(Integer id) {
        return adminQuizRepository.deleteQuizByID(id);
    }

    @Override
    public Boolean insertAllDataIntoDatabase(Quiz quiz) {
        try {

            //add quiz
            insertQuiz(quiz);

            int countQuestionAdded = 1;
            for (int i = 0; i < quiz.getInstructions().size(); i++) {

                quiz.getInstructions().get(i).setQuiz(quiz);
                //add instruction
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
                    countQuestionAdded++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
        return true;
    }

    @Override
    public Boolean deleteAnswerByQuestionID(Integer id) {
        return adminQuizRepository.deleteAnswerByQuestionID(id);
    }

    @Override
    public Boolean updateQuestionByID(Question question) {
        deleteAnswerByQuestionID(question.getId());

        for (int k = 0; k < question.getAnswers().size(); k++) {
            if (question.getAnswers().get(k).getOption().equals("")) {

            } else {
                question.getAnswers().get(k).setQuestion(question);
                //add answer
                insertAnswer(question.getAnswers().get(k));
            }
        }


        return adminQuizRepository.updateQuestionByID(question);
    }
}

