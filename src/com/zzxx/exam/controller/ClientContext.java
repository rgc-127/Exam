package com.zzxx.exam.controller;

import com.zzxx.exam.entity.ExamInfo;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.User;
import com.zzxx.exam.service.ExamService;
import com.zzxx.exam.service.IdOrPasswordException;
import com.zzxx.exam.ui.*;

import java.util.List;
import java.util.TimerTask;

//控制器，控制所有视图，接收视图传过来的数据，再传给module模型，module处理后返回结果，再判断界面显示什么
public class ClientContext {


    private ExamFrame exam;
    private LoginFrame login;
    private MenuFrame menu;
    private MsgFrame msg;
    private WelcomeWindow welcomeWindow;
    private ExamService Service;


    public void startShow() {
        this.login.setVisible(true);

    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void login() {
        String id = login.getIdField().getText();
        String psw = login.getPwdField().getText();

        try {
            this.user = Service.login(id, psw);

            //没有异常，进入菜单界面
            login.setVisible(false);
            menu.getInfo().setText(this.user.getName() + "同学您好");
            menu.setVisible(true);

        } catch (IdOrPasswordException e) {

            login.getMessage().setText(e.getMessage());

        }

    }



    private ExamInfo examInfo;

    public void startExam() {


        examInfo = Service.startExam(user);
        questionCount = 0;
        questionIndex = 0;
        timeCount();

        menu.setVisible(false);
        exam.setVisible(true);
    }

    private void showExam(ExamInfo examInfo) {

        exam.getExamInfo().setText("姓名: " + examInfo.getUser().getName() + "  考试: " + examInfo.getTitle() + "  考试时间: " + examInfo.getTimeLimit() + "分钟");

        this.question = Service.getQuestionFromPaper(questionIndex);
        StringBuilder sBQuestion = new StringBuilder();
        sBQuestion.append(this.question.getTitle() + "\n");
        for (int i = 0; i < this.question.getOptions().size(); i++) {
            sBQuestion.append((char) (i + 'A') + "." + this.question.getOptions().get(i) + "\n");
        }
        exam.getQuestionCount().setText("题目:" + examInfo.getQuestionCount() + " 题的第" + (questionIndex + 1) + "题");
        exam.getQuestionArea().setText(sBQuestion.toString());
        questionCount = examInfo.getQuestionCount();

    }

    private Question question;
    private int questionIndex = 0;
    private int questionCount;

    public void prev(List<String> answerOption) {
        Service.addUserAnswer(answerOption, questionIndex);
        if (questionIndex > 0) {


            questionIndex--;
            showExam(examInfo);
        }
        exam.setOption(Service.getUserAnswer(questionIndex));

    }

    public void next(List<String> answerOption) {
        Service.addUserAnswer(answerOption, questionIndex);
        if (questionIndex < questionCount - 1) {


            questionIndex++;

            showExam(examInfo);

        }

        exam.setOption(Service.getUserAnswer(questionIndex));




    }



    public void send(List<String> answerOption) {

        Service.addUserAnswer(answerOption, questionIndex);
        Service.addResult(user.getId());
        timer.cancel();
        timer = null;
        timeCount = 120;
        exam.setVisible(false);
        menu.setVisible(true);

    }
    public void showRule() {

        String rule = Service.getRule();
        msg.showMsg(rule);
        msg.setVisible(true);

    }

    public void showResult() {


        String result = Service.getResult(user.getId());
        msg.showMsg(result);
        msg.setVisible(true);


    }


    private int timeCount = 120;
    java.util.Timer timer;

    public void timeCount() {

        if (timer != null) {
            return;
        }
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                exam.updateTime(timeCount--);
                showExam(examInfo);
                if (timeCount <= 0) {
                   exam.getSend().doClick();
                }
            }
        }, 0, 1000);

    }


    public ExamFrame getExam() {
        return exam;
    }

    public void setExam(ExamFrame exam) {
        this.exam = exam;
    }

    public LoginFrame getLogin() {
        return login;
    }

    public void setLogin(LoginFrame login) {
        this.login = login;
    }

    public MenuFrame getMenu() {
        return menu;
    }

    public void setMenu(MenuFrame menu) {
        this.menu = menu;
    }

    public MsgFrame getMsg() {
        return msg;
    }

    public void setMsg(MsgFrame msg) {
        this.msg = msg;
    }

    public WelcomeWindow getWelcomeWindow() {
        return welcomeWindow;
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }

    public ExamService getService() {
        return Service;
    }

    public void setService(ExamService service) {
        Service = service;
    }


}
