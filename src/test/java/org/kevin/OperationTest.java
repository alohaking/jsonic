package org.kevin;

import io.netty.handler.codec.json.JsonObjectDecoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class OperationTest {
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
    public void testPush(){
        int cnt = 20;
        Consumer<CountDownLatch> func = latch -> {
            IntStream.range(0, cnt)
                    .mapToObj(i -> {
                        String text = OperationTest.this.random();
                        return sonicClient.push(collection, bucket, String.valueOf(i), text);
                    })
                    .forEach(promise -> promise.whenComplete((r,e)->{
                        if(e != null){
                            e.printStackTrace();
                        }
                        System.out.println(r);
                        latch.countDown();
                    }));
        };
        await(func, cnt);
    }

    @Test
    public void testQuery(){
        await(lath -> sonicClient.query(collection, bucket, "胡歌"), 1);
    }

    private void await(Consumer<CountDownLatch> func, int cnt){
        CountDownLatch latch = new CountDownLatch(cnt);
        func.accept(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String random(){
        Random random = new Random(System.currentTimeMillis());
        int len = random.nextInt(datas.size());
        return IntStream.range(0, len).mapToObj(i -> {
            int index = random.nextInt(datas.size());
            return datas.get(index);
        }).reduce((s1, s2) -> s1+","+s2).get();
    }


    @After
    public void close(){
        if(sonicClient != null){
            try {
                sonicClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
