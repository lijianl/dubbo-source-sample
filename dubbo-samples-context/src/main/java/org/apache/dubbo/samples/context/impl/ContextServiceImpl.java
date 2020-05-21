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

package org.apache.dubbo.samples.context.impl;

import org.apache.dubbo.samples.context.api.ContextService;

import com.alibaba.dubbo.rpc.RpcContext;

public class ContextServiceImpl implements ContextService {

    @Override
    public String sayHello(String name) {

        boolean isProviderSide = RpcContext.getContext().isProviderSide();

        String clientIP = RpcContext.getContext().getRemoteHost();

        String application = RpcContext.getContext().getUrl().getParameter("application");

        System.out.printf("provider:isProviderSide:%s,application=%s,clientIP=%s", isProviderSide, application, clientIP);

        // sessionId 应该是zk的invoke调用的链接
        // 没有调适找到IO线程/业务处理的线程
        // ProcessThread(sid:0 cport:2181):  INFO server.PrepRequestProcessor: Processed session termination for sessionid: 0x10000b95d0b0003
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
