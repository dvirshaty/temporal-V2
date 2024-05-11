package com.temporal.demos.temporalspringbootdemo.service;

import com.temporal.demos.temporalspringbootdemo.activities.ssdf.SsdfAbrActivity;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SomeService {

    @Transactional
    public void runInParallel(SsdfAbrActivity ssdfActivity) {

        List<Promise<Integer>> promiseList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            promiseList.add(Async.function(ssdfActivity::getAbr222,i));
        }
        Promise.allOf(promiseList).get();
    }

}

