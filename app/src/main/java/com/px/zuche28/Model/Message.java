package com.px.zuche28.Model;

import com.px.zuche28.global.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import lombok.Data;

/**
 * @ClassName Message
 * @Author song
 * @Date 2019/4/19 22:41
 * 功能:用于封装【向前端传输的数据】
 * 变量【data】中存储这需要向前端传输的对象
 **/
@Data
@HttpResponse(parser = JsonResponseParser.class)
public class Message<T> {
    private boolean success;
    private String msg;
    private T data;
}
