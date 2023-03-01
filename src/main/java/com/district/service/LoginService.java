package com.district.service;

import com.district.bean.TblUserRecord;
import com.district.mapper.TblUserRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private TblUserRecordMapper tblUserRecordMapper;

    public TblUserRecord login(String username, String password) {
        return tblUserRecordMapper.login(username,password);
    }
}
