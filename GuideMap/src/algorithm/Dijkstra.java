package algorithm;

import Message.RoadMessage;
import Message.SpotMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Dijkstra implements Algorithm{
    private int[][] tu = new int[1000][1000];
    private int[] predecessor = new int[1000];
    private int[] D = new int[1000];
    private int[] S = new int[1000];
    private int MAX = 0x3f3f3f3f;
    private boolean[] visited = new boolean[1000];
    private ArrayList<SpotMessage> spotMessageArrayList = null;
    private ArrayList<RoadMessage> roadMessageArrayList = null;
    private int qidian;
    private int zhongdian;

    public Dijkstra(ArrayList<SpotMessage> spotMessageArrayList, ArrayList<RoadMessage> roadMessageArrayList,
                    int qidian, int zhongdian) {
        this.qidian = qidian;
        this.zhongdian = zhongdian;
        this.roadMessageArrayList = roadMessageArrayList;
        this.spotMessageArrayList = spotMessageArrayList;
    }

    public ArrayList<String> doalgorithm() {
        int temp = qidian;
        qidian = zhongdian;
        zhongdian = temp;
        for (int i = 0;i < spotMessageArrayList.size();i++) {
            for (int j = 0;j < spotMessageArrayList.size();j++) {
                tu[i][j] = MAX;
            }
        }
        for (int i = 0;i < D.length;i++) {
            D[i] = 0;
        }
        for (int i = 0;i < S.length;i++) {
            S[i] = 0;
        }
        for (int i = 0;i < predecessor.length;i++) {
            predecessor[i] = qidian;
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
        for (int i = 0;i < spotMessageArrayList.size();i++) {
            tu[i][i] = 0;
        }

        for (int i = 0;i < D.length;i++) {
            D[i] = tu[qidian][i];
        }
        int min = 0;
        for (int i = 0; i < spotMessageArrayList.size(); i++) {
            for (int j = 0; (j < spotMessageArrayList.size()); j++) {
                if (j == qidian)	//防止寻找到的路径的起始点和终点相同
                    continue;
                if (D[min] + tu[min][j] < D[j]) {
                    D[j] = D[min] + tu[min][j];
                    predecessor[j] = min;	//	前驱的建立
                }
            }
            min = search_min(D, S, i);	//	寻找当前最短路径，返回最短路径的终点

            S[i] = min;		//编号为min的顶点作为最短路径的终点，所以把此顶点放入S中
            if (min == zhongdian)	//如果现在选择的最短路径的终点恰好是adr2，则停止寻找最短路径，减少时间复杂度
                break;

        }
        ArrayList<String> list = new ArrayList<>();
        int pre = zhongdian;		//注意，前面对adr1和adr2进行了交换
        //System.out.println();("从%s到%s的路径长度为%d,现在输出路径信息\n", position[adr2].name,position[adr1].name,D[adr2]);
        for (int i = 0; pre != qidian; i++) {

            //System.out.print(spotMessageArrayList.get(pre).getName()+"-->");
            list.add(spotMessageArrayList.get(pre).getName());
            pre = predecessor[pre];
        }
        list.add(spotMessageArrayList.get(qidian).getName());
        return list;

    }

    private int search_min(int[] D,int[] S,int k) {
        int min = -1;	//若为非连通图，则返回-1
        int m = MAX;
        for (int i = 0; i < spotMessageArrayList.size(); i++)
            if (!has_selected(S, i, k) && (D[i] < m)) {
                min = i;
                m = D[i];
            }

        return min;
    }
    public boolean has_selected(int[] S,int i,int k) {
        for (int j = 0; j < k; j++)
            if (i == S[j])
                return true;
        return false;
    }
}
