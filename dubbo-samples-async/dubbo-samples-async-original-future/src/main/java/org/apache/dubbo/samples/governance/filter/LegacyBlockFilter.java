/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.governance.filter;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * 启动是自动加载
 */
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class LegacyBlockFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 业务执行
        Result result = invoker.invoke(invocation);
        // 结果判断，此处异步的执行不会立即返回结果
        // 如果是异步的调用.本质还是同步的..此处会返回结果
        System.out.println("LegacyBlockFilter: This is the default return value: " + result.getValue());
        if (result.hasException()) {
            System.out.println("LegacyBlockFilter: This will only happen when the real exception returns: " + result.getException());
        }
        //
        System.out.println("LegacyBlockFilter: This msg should not be blocked.");
        return result;
    }
}
