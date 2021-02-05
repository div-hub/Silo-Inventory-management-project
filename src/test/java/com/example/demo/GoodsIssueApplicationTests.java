package com.example.demo;

import com.example.demo.Service.SiloService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class GoodsIssueApplicationTests {
    @Autowired
    SiloService siloTrackerSiloService;


    @Test
    void contextLoads() {
    }

    @Test
    void test() {

       //siloTrackerSiloService.findBatchIdBySiloId(4l);


        }






    }

