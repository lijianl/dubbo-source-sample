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

package org.apache.dubbo.samples.echo;

import org.apache.dubbo.samples.echo.api.DemoService;

import com.alibaba.dubbo.rpc.service.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class EchoConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/echo-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy


        // echo 服务
        // 居然是强制类型转换.....
        // 这个可能是通过Duddo-SPI的Wrapper实现的
        EchoService echoService = (EchoService) demoService;
        String status = (String) echoService.$echo("OK");
        // OK
        System.out.println("echo result: " + status);
    }
}
