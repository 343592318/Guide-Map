package algorithm;

import Message.LinkedNode;
import Message.RoadMessage;
import Message.SpotMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Greening implements Algorithm {
    private final int MAX = 99999999;
    private int[][] tu = new int[100][100];
    private int[] dis = new int[100];
    private int min = MAX;
    private int max = 0;
    private ArrayList<SpotMessage> spotMessageArrayList = null;
    private ArrayList<RoadMessage> roadMessageArrayList = null;
    private ArrayList<String> minlist = new ArrayList<>();
    private boolean flag[] = new boolean[100];
    LinkedNode[] queue = new LinkedNode[1000];
    private int qidian;
    private int zhongdian;

    public Greening(ArrayList<SpotMessage> spotMessageArrayList, ArrayList<RoadMessage> roadMessageArrayList,
                    int qidian, int zhongdian) {
        this.qidian = qidian;
        this.zhongdian = zhongdian;
        this.roadMessageArrayList = roadMessageArrayList;
        this.spotMessageArrayList = spotMessageArrayList;
    }

    public ArrayList<String> doalgorithm() {
        for (SpotMessage spotMessage : spotMessageArrayList) {
            System.out.println(spotMessage.getName());
        }
        System.out.println(spotMessageArrayList.size());
        for (int i = 0; i < tu.length; i++) {
            for (int j = 0; j < tu.length; j++) {
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
        for (int i = 0; i < tu.length; i++) {
            tu[i][i] = 0;
        }
        //Arrays.fill(flag,false);
        for (int i = 0; i < spotMessageArrayList.size(); i++) {
            for (int j = 0; j < spotMessageArrayList.size(); j++) {
                //path[i][j] = -1;
                if (tu[i][j] != MAX) {
                    System.out.printf("%4d", tu[i][j]);
                } else {
                    System.out.print(" !! ");
                }
            }
            System.out.println("");
        }
        System.out.println("!!!" + qidian + "!!" + zhongdian);
        System.out.println("!!!" + tu[qidian][zhongdian]);
        flag[qidian] = true;
        dfs(0, qidian, 0);
        minlist.add(spotMessageArrayList.get(zhongdian).getName());
        return minlist;
    }
    private void dfs(int step, int pos, int sum) {
        //System.out.println("!!!");
        if (pos == zhongdian) {
            sum += Search(spotMessageArrayList.get(pos), spotMessageArrayList.get(zhongdian));
            if (sum > max) {
                minlist.clear();
                minlist.add(spotMessageArrayList.get(qidian).getName());
                for (int i = 0; i < step - 1; i++) {
                    minlist.add(spotMessageArrayList.get(dis[i]).getName());
                }
            }
        } else {
            for (int i = 0; i < spotMessageArrayList.size(); i++) {
                if (tu[pos][i] != MAX && tu[pos][i] != 0 && !flag[i]) {
                    int lvhua = Search(spotMessageArrayList.get(pos), spotMessageArrayList.get(i));
                    sum += lvhua;
                    int pre = pos;
                    pos = i;
                    dis[step] = pos;
                    flag[pos] = true;
                    dfs(step + 1, pos, sum);
                    flag[pos] = false;
                    pos = pre;
                    sum -= lvhua;
                }
            }
        }
    }

    public int Search(SpotMessage start, SpotMessage end) {
        for (int i = 0; i < roadMessageArrayList.size(); i++) {
            String temp1 = roadMessageArrayList.get(i).getStart_spot().getName();
            String temp2 = roadMessageArrayList.get(i).getEnd_spot().getName();
            if ((start.getName().equals(temp1) && end.getName().equals(temp2)) || (start.getName().equals(temp2) && end.getName().equals(temp1))) {
                return roadMessageArrayList.get(i).getLvhua();
            }
        }
        return 0;
    }
}
