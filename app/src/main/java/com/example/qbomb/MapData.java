package com.example.qbomb;

import java.util.ArrayList;

public class MapData {
    public static final int MAP_WIDTH = 15;
    public static final int MAP_HEIGHT = 15;
    public static final int ROAD = 0;
    public static final int WALL = 1;
    public static final int BLOCK = 2;
    public static final int BOMB = 3;
    public static final int PROP = 4;
    ArrayList<int [] []> mapDataList = new ArrayList<>();
    public static boolean walk_able(int type){
        if(type == WALL||type == BLOCK||type == BOMB)
            return false;
        return true;
    }
    public MapData(){
//        size = 15*15
        int [] [] mapData1 = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,1,1,1,1,1,1,1,0,1},
                {1,0,1,0,0,0,0,0,0,0,0,0,1,0,1},
                {1,0,1,0,1,1,1,1,1,1,1,0,1,0,1},
                {1,0,1,0,1,0,0,0,0,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                {1,0,0,0,1,0,1,0,1,0,1,0,0,0,1},
                {1,1,1,1,1,0,1,0,1,0,1,1,1,1,1}
        };
        int [ ][ ]  mapData2= {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,2,0,0,0,0,0,2,0,0,2,0,1},
                {1,0,2,1,1,1,1,1,0,1,2,1,1,2,1},
                {1,2,1,2,2,0,2,0,0,0,0,0,1,0,1},
                {1,0,1,0,1,1,1,1,1,1,2,0,1,2,1},
                {1,0,1,2,1,0,0,0,0,0,1,0,1,0,1},
                {1,2,1,0,2,0,1,1,1,0,2,0,2,0,1},
                {1,0,1,2,1,0,2,0,1,0,1,0,1,2,1},
                {1,0,1,0,1,2,1,0,2,0,2,0,2,0,1},
                {1,0,1,2,1,0,2,0,1,0,1,2,1,0,1},
                {1,0,1,0,1,0,1,2,1,0,2,0,1,0,1},
                {1,2,1,0,1,0,1,0,1,0,1,0,1,2,1},
                {1,0,1,0,2,0,2,0,2,2,1,0,1,0,1},
                {1,0,0,2,0,0,0,0,0,0,0,2,2,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        mapDataList.add(mapData1);
        mapDataList.add(mapData2);
    }
}
