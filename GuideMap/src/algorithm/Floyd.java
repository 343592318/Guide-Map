package algorithm;

import Message.RoadMessage;
import Message.SpotMessage;

import javax.swing.*;
import java.util.ArrayList;

public class Floyd implements Algorithm{
    private final int MAX = 99999999;
    private int[][] tu = new int[100][100];
    private int[] dis = new int[100];
    private ArrayList<SpotMessage> spotMessageArrayList = null;
    private ArrayList<RoadMessage> roadMessageArrayList = null;
    private int qidian;
    private int zhongdian;

    public Floyd(ArrayList<SpotMessage> spotMessageArrayList, ArrayList<RoadMessage> roadMessageArrayList,
                 int qidian, int zhongdian) {
        this.qidian = qidian;
        this.zhongdian = zhongdian;
        this.roadMessageArrayList = roadMessageArrayList;
        this.spotMessageArrayList = spotMessageArrayList;
    }

    public ArrayList<String> doalgorithm() {
//        for (SpotMessage spotMessage : spotMessageArrayList) {
//            System.out.println(spotMessage.getName());
//        }
//        System.out.println(spotMessageArrayList.size());
        for (int i = 0; i < spotMessageArrayList.size(); i++) {
            for (int j = 0; j < spotMessageArrayList.size(); j++) {
                tu[i][j] = MAX;
            }
        }
        for (int i = 0; i < roadMessageArrayList.size(); i++) {
            int x = 0;
            int y = 0;
            for (int j = 0; j < spotMessageArrayList.size(); j++) {
                if (roadMessageArrayList.get(i).getStart_spot().getName().equals(spotMessageArrayList.get(j).getName())) {
                    x = j;
                }
                if (roadMessageArrayList.get(i).getEnd_spot().getName().equals(spotMessageArrayList.get(j).getName())) {
                    y = j;
                }
            }
            tu[x][y] = roadMessageArrayList.get(i).getLength();
            tu[y][x] = roadMessageArrayList.get(i).getLength();
        }
        for (int i = 0; i < spotMessageArrayList.size(); i++) {
            tu[i][i] = 0;
        }
        int[][] path = new int[100][100];
        for (int i = 0; i < spotMessageArrayList.size(); i++) {
            for (int j = 0; j < spotMessageArrayList.size(); j++) {
                if (tu[i][j] == MAX) {
                    path[i][j] = -1;
                } else {
                    path[i][j] = j;
                }
            }
        }
        for (int i = 0; i < spotMessageArrayList.size(); i++) {
            for (int j = 0; j < spotMessageArrayList.size(); j++) {
                for (int z = 0; z < spotMessageArrayList.size(); z++) {
                    if (tu[j][i] + tu[i][z] < tu[j][z]) {
                        tu[j][z] = tu[j][i] + tu[i][z];
                        path[j][z] = path[j][i];
                    }
                }
            }
        }

        int temp = qidian;
        ArrayList<String> list = new ArrayList<>();
        list.add(spotMessageArrayList.get(qidian).getName());
        while (temp != zhongdian && path[temp][zhongdian] != -1) {
            list.add(spotMessageArrayList.get(path[temp][zhongdian]).getName());
            temp = path[temp][zhongdian];
        }
        //System.out.println(qidian + "\n" + zhongdian);
        JFrame jFrame = new JFrame();
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
        JOptionPane.showMessageDialog(jFrame, "最短距离为" + tu[qidian][zhongdian]);
        //jFrame.setVisible(true);
        return list;
    }
}
