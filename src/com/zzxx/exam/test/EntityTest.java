package com.zzxx.exam.test;

import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.User;
import com.zzxx.exam.ui.ExamFrame;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityTest {

    @Test
    public void ss() throws IOException {

        /*EntityContext en = new EntityContext();
        String s = EntityTest.class.getClassLoader().getResource("com/zzxx/exam/util/user.txt").getPath();
        System.out.println(s);
        en.loadUsers(s);
        Set<Map.Entry<Integer, User>> entries = en.getUsers().entrySet();
        for (Map.Entry<Integer, User> ss : entries) {
            System.out.println(ss);
        }
        en.getUsers().keySet();*/



        /*EntityContext en = new EntityContext();
        String s = EntityTest.class.getClassLoader().getResource("com/zzxx/exam/util/corejava.txt").getPath();
        en.loadQuestion(s);

        Set<Map.Entry<Integer, List<Question>>> entries = en.getQuestion().entrySet();

        for (Map.Entry<Integer, List<Question>> ss : entries) {

            List<Question> value = ss.getValue();
            for (Question q: value){
                System.out.println(q);
            }

        }*/


    }

    public static void main(String[] args) {
        new ExamFrame().setVisible(true);
    }
}
