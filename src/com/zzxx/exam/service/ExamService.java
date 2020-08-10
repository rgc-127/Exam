package com.zzxx.exam.service;

import com.zzxx.exam.entity.*;
import com.zzxx.exam.util.Config;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExamService {


    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    private EntityContext entityContext;


    public User login(String id, String psw) throws IdOrPasswordException {
        if (id.equals("")) {
            throw new IdOrPasswordException("请输入用户名");
        }
        User user = entityContext.findUserById(Integer.parseInt(id));

        if (user != null) {
            if (psw.equals(user.getPassword())) {

                return user;

            } else {
                throw new IdOrPasswordException("密码错误");
            }

        } else {

            throw new IdOrPasswordException("用户不存在");

        }


    }

    public String getRule() {

        return this.entityContext.getRule().toString();

    }

    List<QuestionInfo> questionPaper;

    private void creatExamPaper() {

        questionPaper = new ArrayList<>();
        Map<Integer, List<Question>> question = this.entityContext.getQuestion();
        Set<Integer> integers = question.keySet();
        int questionIndex = 0;
        for (Integer i : integers) {

            List<Question> list = question.get(i);

            if (list.size() > 2) {

                for (int j = 0; j < 2; j++) {
                    int n = (int) (Math.random() * list.size());
                    QuestionInfo questionInfo = new QuestionInfo(questionIndex, list.get(n));

                    if (!questionPaper.contains(questionInfo)) {

                        questionPaper.add(questionInfo);
                        questionIndex++;
                    } else {
                        j--;
                    }

                }


            } else {

                for (Question q : list) {
                    questionPaper.add(new QuestionInfo(questionIndex, q));
                    questionIndex++;
                }
            }


        }

    }

    public Question getQuestionFromPaper(int questionIndex) {

        return questionPaper.get(questionIndex).getQuestion();

    }


    public ExamInfo startExam(User user) {

        String name = user.getName();
        Config config = this.entityContext.getConfig();
        String paperTitle = config.getString("PaperTitle");

        int timeLimit = config.getInt("TimeLimit");
        int questionNumber = config.getInt("QuestionNumber");

        creatExamPaper();


        return new ExamInfo(paperTitle, user, timeLimit, questionNumber);
    }


    public void addUserAnswer(List<String> answerOptions, int questionIndex) {

        questionPaper.get(questionIndex).setUserAnswers(answerOptions);

    }

    public String getResult(int id) {

        try {
            entityContext.loadResult(id);
        } catch (IOException e) {
            return "你还没有记录";
        }
        return entityContext.getResult();
    }


    public ArrayList<String> getUserAnswer(int questionIndex) {

        return (ArrayList) (questionPaper.get(questionIndex).getUserAnswers());
    }

    public void addResult(int id) {

        String path = ExamService.class.getResource("..").getPath();
        PrintWriter resultWriter = null;
        try {
            resultWriter = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path + "result/" + id + ".txt", true)
                    ));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Date date = new Date();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy年 MM月 dd日 HH:mm:ss E");

        String str = sf.format(date);

        resultWriter.println("考试时间：" + str);

        String name = entityContext.getUsers().get(id).getName();

        int result = 0;
        for (int i = 0; i < questionPaper.size(); i++) {

            if (questionPaper.get(i).getQuestion().getAnswers().containsAll(questionPaper.get(i).getUserAnswers())
                    && questionPaper.get(i).getUserAnswers().containsAll(questionPaper.get(i).getQuestion().getAnswers())) {


                result += questionPaper.get(i).getQuestion().getScore();


            }


        }

        resultWriter.println("姓名： " + name + "       考试分数： " + result + "分");
        resultWriter.close();
    }
}
