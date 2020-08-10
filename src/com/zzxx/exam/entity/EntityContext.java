package com.zzxx.exam.entity;

import com.zzxx.exam.service.ExamService;
import com.zzxx.exam.util.Config;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 实体数据管理, 用来读取数据文件放到内存集合当中
 */
public class EntityContext {

    private Map<Integer, User> users = new HashMap<>();

    public Map<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(Map<Integer, User> users) {
        this.users = users;
    }

    public void loadUsers(String filename) throws IOException {
        //1001:陈贺贺:1234:13810381038:chenhh@zzxx.com.cn
        //id;name;password;phone;email;
        //封装为用户对象

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = reader.readLine()) != null) {

            String[] ss = str.split(":");
            if (ss.length >= 5) {
                User user = new User(ss[1], Integer.parseInt(ss[0]), ss[2]);

                users.put(Integer.parseInt(ss[0]), user);
            }


        }


    }


    private Map<Integer, List<Question>> question = new HashMap<>();

    public Map<Integer, List<Question>> getQuestion() {
        return question;
    }

    public void setQuestion(Map<Integer, List<Question>> question) {
        this.question = question;
    }

    public void loadQuestion(String filename) throws IOException {



       /* private int id;
        private String title;// 题干
        private List<String> options = new ArrayList<String>();// 若干选项
        private List<Integer> answers = new ArrayList<Integer>();// 正确答案
        private int score;// 分数
        private int level;// 难度级别
        private int type; // 类型: 单选 SINGLE_SELECTION /多选 MULTI_SELECTION*/

      /*@answer=2/3,score=5,level=5
        指出下面语句没有编译错误的是:
        long n = 999999999999;
        int n = 999999999999L;
        long n = 999999999999L;
        double n = 999999999999;*/
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str;

        int id = 1;
        while ((str = reader.readLine()) != null) {
            Question que = new Question();
            //操作第一行
            String[] ss = str.split(",");
            for (String s : ss) {
                String[] s1 = s.split("=");
                if (s1[0].contains("answer")) {
                    //对answer操作，写入选项和设置单选多选属性
                    if (s1[1].contains("/")) {
                        String[] ans = s1[1].split("/");
                        List<String> answer = new ArrayList<>();
                        answer.addAll(Arrays.asList(ans));
                        que.setAnswers(answer);
                        que.setType(Question.MULTI_SELECTION);

                    } else {
                        List<String> answer = new ArrayList<>();
                        answer.add(s1[1]);
                        que.setAnswers(answer);
                        que.setType(Question.SINGLE_SELECTION);
                    }

                } else if (s1[0].startsWith("score")) {
                    //设置分数
                    que.setScore(Integer.parseInt(s1[1]));
                } else {
                    //设置等级
                    que.setLevel(Integer.parseInt(s1[1]));

                }

            }

            //操作后5行
            //设置题目
            str = reader.readLine();
            que.setTitle(str);

            //添加选项
            List<String> options = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                str = reader.readLine();
                options.add(str);

            }
            que.setOptions(options);
            que.setId(id++);


            //加入列表
            List<Question> ques = this.question.get(que.getLevel());
            if (ques == null) {
                ques = new ArrayList<>();
            }
            ques.add(que);
            this.question.put(que.getLevel(), ques);
        }


    }


    public User findUserById(Integer id) {

        return this.users.get(id);
    }

    private StringBuilder rule;

    public StringBuilder getRule() {
        return rule;
    }

    public void setRule(StringBuilder rule) {
        this.rule = rule;
    }

    public void loadRule(String filename) throws IOException {
        rule = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str = "";
        while ((str = reader.readLine()) != null) {

            rule.append(str);
            rule.append("\n");
        }
        reader.close();

    }
    private Config config;

    public Config getConfig() {
        return config;
    }

    public void loadConfig(String filename){
        config = new Config(filename);
    }
    private String result;

    public String getResultPath() {
        return resultPath;
    }

    private String resultPath ;
    public void loadResult(int userId) throws IOException {
        String path = EntityContext.class.getResource("..").getPath();
        resultPath = path +"result/"+userId+".txt";




       if (resultPath != null){
           BufferedReader userResult = new BufferedReader(new FileReader(resultPath));
           StringBuilder str = new StringBuilder();

           while ((result = userResult.readLine()) != null){

               str.append(result + "\n");

           }

           result = str.toString();
           userResult.close();
       }



    }

    public String getResult() {

        return result;
    }
}
