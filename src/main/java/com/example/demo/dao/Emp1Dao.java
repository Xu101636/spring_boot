package com.example.demo.dao;

import com.example.demo.bean.Emp1;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface Emp1Dao {
    List<Emp1> allemp();
    void addemp(Emp1 emp);
}
