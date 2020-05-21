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

package org.apache.dubbo.samples.governance.api;

import java.util.concurrent.CompletableFuture;

public interface GreetingService {


    // 底层依然同步的调用
    // 同步的调用方法
    String greeting(String name);

    default String replyGreeting(String name) {
        return "Fine, " + name;
    }

    // 使用CompletableFuture封装同步调用，dubbo调用本身还是同步,只是不等待结果返回
    // 使用的是函数接口, 这是很巧妙的实现方法
    default CompletableFuture<String> greeting(String name, byte signal) {
        return CompletableFuture.completedFuture(greeting(name));
    }

    // 还是喜欢这样定义; dubbo很强大
    // AsyncSignal 不是实体的类
    default CompletableFuture<String> asyncGreeting(String name) {
        return CompletableFuture.completedFuture(greeting(name));
    }

}
