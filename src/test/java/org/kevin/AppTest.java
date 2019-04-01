package org.kevin;

import org.kevin.enums.Action;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * Unit test for simple App.
 */
public class AppTest {
    static String collection = "c:1";
    static String bucket = "b:1";

    public static void query(SonicClient client,CountDownLatch latch){
        CompletableFuture<List<String>> c1 = client.query(collection, bucket, "胡歌");
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }
            r.stream().forEach(System.out::println);
            latch.countDown();

        });

    }
    static void push(SonicClient client, CountDownLatch latch){

            CompletableFuture<String> resp = client.push(collection, bucket, "dadsadsad", "打算看电话卡仕达胡歌打瞌睡的好看");
            resp.whenComplete((r,e)->{
                if(e != null)
                    e.printStackTrace();
                System.out.println(r);
                latch.countDown();

            });
    }

    public static void suggest(SonicClient client,CountDownLatch latch){
        CompletableFuture<List<String>> c1 = client.suggest(collection, bucket, "胡", 2);
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }
    public static void pop(SonicClient client,CountDownLatch latch){
        CompletableFuture<String> c1 = client.pop(collection, bucket, "dadsadsad", "电话卡");
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }
    public static void flushc(SonicClient client,CountDownLatch latch){
        CompletableFuture<Long> c1 = client.flushc(collection);
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }
    public static void flushb(SonicClient client,CountDownLatch latch){
        CompletableFuture<Long> c1 = client.flushb(collection, bucket);
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }
    public static void ping(SonicClient client,CountDownLatch latch){
        CompletableFuture<String> c1 = client.ping(SonicChannel.SEARCH);
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }
    public static void help(SonicClient client,CountDownLatch latch){
        CompletableFuture<String> c1 = client.help(SonicChannel.SEARCH,"commands");
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }

    public static void count(SonicClient client,CountDownLatch latch){
        CompletableFuture<Long> c1 = client.count(collection, bucket, "dadsadsad");
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }
    public static void trigger(SonicClient client,CountDownLatch latch){
        CompletableFuture<String> c1 = client.trigger(Action.CONSOLIDATE);
        c1.whenComplete((r,e)->{
            if(e != null){
                e.printStackTrace();
            }

            System.out.println(r);
            latch.countDown();

        });

    }

    public static void main(String[] args) {
        SonicSettings sonicSettings = new SonicSettings();
        sonicSettings.setHost("192.168.198.131");
        SonicClient client = new SonicClient(sonicSettings);
        CountDownLatch latch = new CountDownLatch(1);
//        push(client, latch);
        //query(client, latch);
//        suggest(client, latch);
//        pop(client, latch);
       // flushc(client, latch);
        //flushb(client, latch);
//        ping(client, latch);
        //help(client, latch);
       count(client, latch);
        //trigger(client, latch);
        try {
            latch.await();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
