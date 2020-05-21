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

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

public class AsyncProvider {

    public static void main(String[] args) throws Exception {
        testFuture();
    }

    public static void startProvider() throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/async-provider.xml"});
        context.start();
        System.in.read();
    }

    public static void testFuture() {
       /* ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        try {
            Integer res = 0;
            //res = future.get();
            while (true) {
                if (future.isDone()) {
                    // todo
                    res = future.get();
                    break;
                }
            }
            System.out.println("future res: " + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        CompletableFuture<String> future = CompletableFuture
                .completedFuture("1")
                .thenApply((r) -> {
                    System.out.println("res = " + r);
                    return "2";
                })
                .whenComplete((r, e) -> {
                    System.out.println(r);
                });
    }
}
