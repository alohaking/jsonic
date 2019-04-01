package org.kevin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommandTest {

    SonicClient sonicClient = null;
    String collection = "c:1";
    String bucket = "b:1";
    List<String> datas = Arrays.asList("胡歌", "奥巴马", "张三", "特朗普", "河蟹", "长城", "贝加尔湖", "其他");
    @Before
    public void setUp(){
        SonicSettings settings = new SonicSettings();
        settings.setHost("192.168.198.131");
        sonicClient = new SonicClient(settings);
    }

    @Test
    public void testMultiPush(){
        CountDownLatch latch  = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> all = IntStream.range(0, 1000).mapToObj(i -> {
            CompletableFuture<String> resp = sonicClient.push(collection, bucket, String.valueOf(i), randomText());
            return resp;
        }).collect(Collectors.toList());
        CompletableFuture<String>[] arr = new CompletableFuture[all.size()];
        CompletableFuture<Void> promise = CompletableFuture.allOf(all.toArray(arr));
        promise.whenComplete((r,e) -> {
            latch.countDown();
        });
        try {
            latch.await();
            System.out.println(System.currentTimeMillis() - start);
            sonicClient.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void suggest(){
        CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture<List<String>> promise = sonicClient.suggest(collection, bucket, "奥巴马", 3);
        promise.whenComplete((r,e) -> {
            if(e != null){
                e.printStackTrace();
            }else{
                System.out.println(r);
            }
            latch.countDown();
        });

        try {
            latch.await();
            sonicClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String randomText(){
        Random random = new Random(System.currentTimeMillis());
        int len = random.nextInt(datas.size());
        if(len < 0){
            len = 2;
        }
        return IntStream.range(0, len).mapToObj(i -> {
            int index = random.nextInt(datas.size());
            return datas.get(index);
        }).reduce((s1, s2) -> s1+","+s2).get();
    }

}
