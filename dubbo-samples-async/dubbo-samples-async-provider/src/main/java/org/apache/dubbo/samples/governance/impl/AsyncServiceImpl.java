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

package org.apache.dubbo.samples.governance.impl;

import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.governance.api.AsyncService;

/**
 * AsyncServiceImpl
 */
public class AsyncServiceImpl implements AsyncService {

    @Override
    public String sayHello(String name) {
        System.out.println("Main sayHello() method start.");

        //  异步context
        final AsyncContext asyncContext = RpcContext.startAsync();

        new Thread(() -> {


            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();

            System.out.println("Attachment from consumer: " + RpcContext.getContext().getAttachment("consumer-key1"));
            System.out.println("    -- Async start.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 真正的返回
            asyncContext.write("Hello " + name + ", response from provider.");

            System.out.println("    -- Async end.");
        }).start();

        System.out.println("Main sayHello() method end.");

        // 这个没有返回
        return "hello, " + name;
    }

}
