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

package org.apache.dubbo.samples.governance;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.governance.api.AsyncService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;

/**
 * CallbackConsumer
 */
public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/async-consumer.xml"});
        context.start();


        AsyncService asyncService = (AsyncService) context.getBean("asyncService");
        // RpcContext 必须配合 xml 配置async = true
        asyncService.sayHello("world");
        // ...
        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
        helloFuture.whenComplete((retValue, exception) -> {
            if (exception == null) {
                System.out.println("async consumer >>> ");
                System.out.println(retValue);
            } else {
                exception.printStackTrace();
            }
        });

        // 异步调用有返回值
        CompletableFuture<String> f = RpcContext.getContext().asyncCall(
                () -> {
                    System.out.println("async consumer ...");
                    return asyncService.sayHello("async call request");
                }
        );

        System.out.println("async call ret :" + f.get());


        // 异步调用; 不需要返回值
        RpcContext.getContext().asyncCall(() -> {
            System.out.println("one way consumer ...");
            asyncService.sayHello("oneway call request1");
        });

        System.in.read();
    }

}
