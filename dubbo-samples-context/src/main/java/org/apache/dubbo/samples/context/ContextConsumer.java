/*
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
 */

package org.apache.dubbo.samples.context;

import org.apache.dubbo.samples.context.api.ContextService;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextConsumer {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-context-consumer.xml"});
        context.start();

        // get remote service proxy
        ContextService contextService = (ContextService) context.getBean("demoService");

        // call remote method
        String hello = contextService.sayHello("world");

        // get result
        System.out.println(hello);

        boolean isConsumerSide = RpcContext.getContext().isConsumerSide();
        String application = RpcContext.getContext().getUrl().getParameter("application");
        String serverIP = RpcContext.getContext().getRemoteHost();


        // 当前线程是：Spring-context的某个线程
        // Thread-0  INFO support.ClassPathXmlApplicationContext: Closing org.springframework.context.support.ClassPathXmlApplicationContext@73a28541: startup date [Thu Apr 25 15:43:19 CST 2019]; root of context hierarchy
        System.out.printf("consumer:isConsumerSide:%s,application=%s,serverIP=%s", isConsumerSide, application, serverIP);


    }
}
