/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.stub.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// java.lang.ClassNotFoundException: org.apache.dubbo.samples.stub.api.DemoServiceStub
// Stub的类必须是指定name格式的，根据ApiStub的名字的方式加载
// 记载是通过反射加载, 感觉应用的场景不大
public class DemoServiceStub implements DemoService {

    private static Logger logger = LoggerFactory.getLogger(DemoServiceStub.class);

    private final DemoService demoService;

    // 代理
    public DemoServiceStub(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public String sayHello(String name) {
        logger.info("before execute remote service, parameter: " + name);
        try {
            String result = demoService.sayHello(name);
            logger.info("after execute remote service, result: " + result);
            return result;
        } catch (Exception e) {
            logger.warn("fail to execute service", e);
            return null;
        }
    }
}
