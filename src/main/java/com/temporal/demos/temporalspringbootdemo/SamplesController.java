/*
 *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
 *
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.temporal.demos.temporalspringbootdemo;

import com.temporal.demos.temporalspringbootdemo.dto.HsiaDto;
import com.temporal.demos.temporalspringbootdemo.workflows.HsiaWorkflow;
import com.temporal.demos.temporalspringbootdemo.workflows.HsiaWorkflowSaga;
import io.temporal.api.workflowservice.v1.WorkflowServiceGrpc;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowExecutionMetadata;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.common.SearchAttributeKey;
import io.temporal.common.SearchAttributes;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.Ansi8BitColor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
@RequestMapping("/hsia")
@RequiredArgsConstructor
public class SamplesController {


    private final WorkflowClient client;
    private WorkflowServiceStubs workflowServiceStubs;
    WorkflowServiceGrpc.WorkflowServiceBlockingStub workflowServiceBlockingStub;

    public void init() {
        workflowServiceStubs = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.getDefaultInstance());
        workflowServiceBlockingStub = workflowServiceStubs.blockingStub();
    }


    @PostMapping("/submit")
    ResponseEntity<Boolean> hsia(@RequestBody HsiaDto hsiaDto) {
        log.info("validate Hsia payload");
        validate();
        log.info("done validate Hsia payload");

        String uuid = UUID.randomUUID().toString();
        log.info("workflow uuid - {}", uuid);
        HsiaWorkflow workflow = client.newWorkflowStub(HsiaWorkflow.class, WorkflowOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build()).setWorkflowId(uuid).build());

        WorkflowClient.start(workflow::validateAndExecute, hsiaDto);
        // workflow.validateAndExecute(input);
        log.info("done web controller");
        return ResponseEntity.ok(true);
    }

    @GetMapping("/att/{name}")
    public ResponseEntity<List<String>> getWorkflowsBySAART(@PathVariable String name) {
        try {


            List<WorkflowExecutionMetadata> list = client.listExecutions(String.format("WorkflowType = 'HsiaWorkflowSaga' and dogName='%s'",name)).distinct().toList();

            return ResponseEntity.ok(list.stream().map(workflowExecutionMetadata1 -> workflowExecutionMetadata1.getExecution().getWorkflowId()).toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/submit/saga")
    ResponseEntity<Boolean> hsiaSaga(@RequestBody HsiaDto hsiaDto) {
        log.info("validate Hsia With Saga pattern payload");
        validate();

        String uuid = UUID.randomUUID().toString();
        hsiaDto.setUuid(uuid);

        log.info("done validate Hsia payload");
        SearchAttributeKey<String> dogName = SearchAttributeKey.forKeyword("dogName");
        SearchAttributes searchAttributes = SearchAttributes.newBuilder().set(dogName,hsiaDto.getName()).build();

        log.info("workflow uuid - {}", uuid);
        HsiaWorkflowSaga workflow = client.newWorkflowStub(HsiaWorkflowSaga.class, WorkflowOptions.newBuilder().setTypedSearchAttributes(searchAttributes).setTaskQueue("HsiaTaskQueue").setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build()).setWorkflowId(uuid).build());
        WorkflowClient.start(workflow::validateAndExecute, hsiaDto);
        // workflow.validateAndExecute(input);
        log.info("done web controller {}", uuid);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/setAtp/{uuid}")
    ResponseEntity<Boolean> setATPSignal(@PathVariable String uuid) {
        log.info("workflow setATPSignal - {}", uuid);
        HsiaWorkflowSaga workflow = client.newWorkflowStub(HsiaWorkflowSaga.class, uuid);
        workflow.cancelFlow();

     /*   WorkflowStub stub = client.newUntypedWorkflowStub(uuid, Optional.empty(), Optional.empty());
        stub.cancel();
*/

        return ResponseEntity.ok(true);

    }

    private void validate() {
        log.info("validate web controller");
    }
}


