package com.leyou.client;

import java.util.*;

/**
 * Author: 98050
 * Time: 2018-10-17 15:15
 * Feature:
 */
public class Poker {
    public static void main(String[] args) {
        List<String> poker = new ArrayList<>();
        //底牌
        List<String> lastpoker = new ArrayList<>();
        //a,b,c三个人
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        creat(poker);
        rufflePoker(poker);
        lastPoker(poker,lastpoker);
        deal(poker,a,b,c);
        //看底牌
        showPoker(lastpoker);
    }

    //1.创建54张牌
    public static void creat( List<String> poker){
        String[] colors = {"黑桃","梅花","方块","红桃"};
        String[] nums ={"2", "3", "4","5","6","7","8","9","10","J","Q","K","A"};
        for (String color :colors){
            for (String num :nums){
                poker.add(color+num);
            }
        }
        poker.add("大王");
        poker.add("小王");
    }

    //2.洗牌
    public static void rufflePoker(List<String> poker){
        Collections.shuffle(poker);
    }

    //3.留三张底牌
    public static void lastPoker(List<String> poker,List<String> dipai){
        Random random = new Random();
        for (int i = 0;i < 3;i ++ ){
            int index = random.nextInt(poker.size());
            dipai.add(poker.remove(index));
        }
    }

    //4.看牌
    public static void showPoker(List<String> poker){
        System.out.println("看牌：");
        for (String s :poker){
            System.out.println(s);
        }
    }

    //5.发牌
    public static void deal(List<String> poker,List<String> a,List<String> b,List<String> c){
        // 遍历发牌
        for (int i = 0; i < poker.size(); i++) {
            int mod = i % 3;    // 0 1 2 0 1 2
            String pai = poker.get(i);
            if (mod == 0) {
                a.add(pai);
            } else if (mod == 1) {
                b.add(pai);
            } else {
                c.add(pai);
            }
        }
    }
}
