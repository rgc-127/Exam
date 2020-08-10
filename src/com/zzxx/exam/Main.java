package com.zzxx.exam;

import com.zzxx.exam.controller.ClientContext;
import com.zzxx.exam.entity.EntityContext;

import com.zzxx.exam.service.ExamService;

import com.zzxx.exam.ui.ExamFrame;
import com.zzxx.exam.ui.LoginFrame;
import com.zzxx.exam.ui.MenuFrame;
import com.zzxx.exam.ui.MsgFrame;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        ClientContext clientContext = new ClientContext();
        ExamFrame examFrame = new ExamFrame();
        LoginFrame loginFrame = new LoginFrame();
        MenuFrame menuFrame = new MenuFrame();
        MsgFrame msgFrame = new MsgFrame();
        ExamService examService = new ExamService();
        EntityContext entityContext = new EntityContext();

        //加载数据库

        entityContext.loadConfig("config.properties");
        String path = "util/";
        String path1 = Main.class.getResource(path + "user.txt").getPath();
        entityContext.loadUsers(path1);
        path1 = Main.class.getResource(path + "corejava.txt").getPath();
        entityContext.loadQuestion(path1);
        path1 = Main.class.getResource(path + "rule.txt").getPath();
        entityContext.loadRule(path1);
        //注入依赖
        clientContext.setLogin(loginFrame);
        clientContext.setService(examService);
        clientContext.setMenu(menuFrame);
        clientContext.setMsg(msgFrame);
        clientContext.setExam(examFrame);

        loginFrame.setClientContext(clientContext);
        examService.setEntityContext(entityContext);
        menuFrame.setClientContext(clientContext);
        examFrame.setClientContext(clientContext);
        clientContext.startShow();

    }
}
