package com.example.demo.serviceImpl;

import com.example.demo.bean.Emp1;
import com.example.demo.dao.Emp1Dao;
import com.example.demo.service.Emp1Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Emp1ServiceImpl implements Emp1Service {

    @Resource
    private Emp1Dao dao;

    @Override
    public List<Emp1> selemps() {
        return dao.allemp();
    }

    @Override
    public void saveemp(Emp1 emp) {
        dao.addemp(emp);
    }
}
