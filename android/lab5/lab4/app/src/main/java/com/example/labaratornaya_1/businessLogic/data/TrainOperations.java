package com.example.labaratornaya_1.businessLogic.data;

import com.example.labaratornaya_1.entity.Train;

import java.util.Date;

public class TrainOperations {

    public Train getDataSplit(String data) {
        if(data != null) {
            String[] args = data.split(";");

            int id = Integer.parseInt(args[0]);

            return new Train(id + 1, args[1], args[2],
                args[3], args[4], args[5]);
        }
        return null;
    }
}
