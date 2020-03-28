package com.project.petrichor.model;

import java.util.List;

public class AjaxResponseBody {
    String msg;
    List<Question> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }

}
