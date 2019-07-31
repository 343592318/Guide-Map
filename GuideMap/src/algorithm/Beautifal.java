package algorithm;

import Message.LinkedNode;
import Message.RoadMessage;
import Message.SpotMessage;

import java.util.ArrayList;

public class Beautifal implements Algorithm {
    private final int MAX = 99999999;
    private int[][] tu = new int[100][100];
    private int[] dis = new int[100];
    private int min = MAX;
    private int max = 0;
    private ArrayList<SpotMessage> spotMessageArrayList = null;
    private ArrayList<RoadMessage> roadMessageArrayList = null;
    private ArrayList<String> minList = new ArrayList<>();
    private boolean flag[] = new boolean[100];
    LinkedNode[] queue = new LinkedNode[1000];
    private int qidian;
    private int zhongdian;

    public Beautifal(ArrayList<SpotMessage> spotMessageArrayList, ArrayList<RoadMessage> roadMessageArrayList,
                     int qidian, int zhongdian) {
        this.qidian = qidian;
        this.zhongdian = zhongdian;
        this.roadMessageArrayList = roadMessageArrayList;
        this.spotMessageArrayList = spotMessageArrayList;
    }

    public ArrayList<String> doAlgorithm() {
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
        for (int i = 0;i < tu.length;i++) {
            tu[i][i] = 0;
        }

        flag[qidian] = true;
        dfs(0, qidian, 0);
        minList.add(spotMessageArrayList.get(zhongdian).getName());
        return minList;
    }

    private void dfs(int step, int pos, int sum) {
        //System.out.println("!!!");
        if (pos == zhongdian) {
            sum += Search(spotMessageArrayList.get(pos), spotMessageArrayList.get(zhongdian));
            if (sum > max) {
                minList.clear();
                minList.add(spotMessageArrayList.get(qidian).getName());
                for (int i = 0; i < step - 1; i++) {
                    minList.add(spotMessageArrayList.get(dis[i]).getName());
                }
            }
        } else {
            for (int i = 0; i < spotMessageArrayList.size(); i++) {
                if (tu[pos][i] != MAX && tu[pos][i] != 0 && !flag[i]) {
                    int meihua = Search(spotMessageArrayList.get(pos), spotMessageArrayList.get(i));
                    sum += meihua;
                    int pre = pos;
                    pos = i;
                    dis[step] = pos;
                    flag[pos] = true;
                    dfs(step + 1, pos, sum);
                    flag[pos] = false;
                    pos = pre;
                    sum -= meihua;
                }
            }
        }
    }

    private int Search(SpotMessage start, SpotMessage end) {
        for (RoadMessage roadMessage : roadMessageArrayList) {
            String temp1 = roadMessage.getStart_spot().getName();
            String temp2 = roadMessage.getEnd_spot().getName();
            if ((start.getName().equals(temp1) && end.getName().equals(temp2)) || (start.getName().equals(temp2) && end.getName().equals(temp1))) {
                return roadMessage.getMeihua();
            }
        }
        return 0;
    }
}
