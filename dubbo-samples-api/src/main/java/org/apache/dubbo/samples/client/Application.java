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

package org.apache.dubbo.samples.client;


import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.samples.api.Person;

import java.time.Period;
import java.util.*;

public class Application {
    public static void main(String[] args) {

        System.setProperty("java.net.preferIPv4Stack", "true");


        // 泛化调用:GenericService
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

        reference.setApplication(new ApplicationConfig("first-dubbo-client"));
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        // reference.setInterface(GreetingsService.class);


        // 使用泛化调用
        reference.setGeneric(true);
        reference.setInterface("org.apache.dubbo.samples.api.GreetingsService");


        // 泛化调用基本的信息
        GenericService genericService = reference.get();
        Object message = genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"aaa"});
        System.out.println(message);


        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", 1);
        data.put("name", "xixi");
        data.put("createdAt", new Date());


        Object obj = genericService.$invoke("getPerson", new String[]{"org.apache.dubbo.samples.api.Person"}, new Object[]{data});
        // class
        System.out.println(JSONObject.toJSONString(obj));

        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("id", 2);
        data2.put("name", "chen");
        data2.put("createdAt", new Date());

        /*
        data.put("class","org.apache.dubbo.samples.api.Person");
        data2.put("class","org.apache.dubbo.samples.api.Person");
        */


        // 使用List丢失泛型的问题应该在接口层是可以获取的....
        // 获取的功能有限
        List<Map> list = new ArrayList<Map>();
        list.add(data);
        list.add(data2);

        Object res = genericService.$invoke("listPersson", new String[]{"java.util.List"}, new Object[]{list});
        System.out.println(JSONObject.toJSONString(res));

    }
}
