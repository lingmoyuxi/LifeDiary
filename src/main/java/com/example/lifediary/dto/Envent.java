package com.example.lifediary.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:事件记录
 * 日期: 2023/3/2 14:33:07
 *
 * @author MoYuXi
 */
@Data
public class Envent {
    private static SimpleDateFormat f = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
    public Date timestamp;
    public String time;
    public String incident;

    public Envent(String time, String incident) {
        this.time = time;
        try {
            this.timestamp = f.parse(time);
        } catch (ParseException e) {
            this.timestamp = null;
        }
        this.incident = incident;
    }

    public Envent(Date time, String incident) {
        this(f.format(time), incident);
        this.timestamp = time;
    }
}
