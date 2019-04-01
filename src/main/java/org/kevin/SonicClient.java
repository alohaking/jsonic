package org.kevin;

import org.kevin.codec.*;
import org.kevin.enums.Action;
import org.kevin.enums.Command;
import org.kevin.exchange.Replier;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SonicClient implements Closeable {
    private SonicExecutor sonicExecutor;
    public SonicClient(SonicSettings sonicSettings){
        this.sonicExecutor = new SonicExecutor(sonicSettings);

    }

    //--------------------search channel start-----------------------------------------
    /**
     * 查询，返回List<uid></uid>。
     * @param collection
     * @param bucket
     * @param queryTerm
     * @param limit
     * @param offset
     * @return
     */
    public CompletableFuture<List<String>> query(String collection,
                                                 String bucket,
                                                 String queryTerm,
                                                 int limit,
                                                 int offset){
        QueryEncoder encoder = new QueryEncoder(collection, bucket, queryTerm, limit, offset);
        return sonicExecutor.execute(SonicChannel.SEARCH, encoder, new EventQueryDecoder());
    }

    public CompletableFuture<List<String>> query(String collection,
                                                 String bucket,
                                                 String queryTerm){
        return this.query(collection, bucket, queryTerm, -1, -1);
    }

    public CompletableFuture<List<String>> suggest(String collection,
                                             String bucket,
                                             String word,
                                             int limit){
        SuggestEncoder encoder = new SuggestEncoder(collection, bucket, word, limit);
        return sonicExecutor.execute(SonicChannel.SEARCH, encoder, new EventQueryDecoder());
    }
    //--------------------search channel end-----------------------------------------


    //--------------------ingest channel start-----------------------------------------
    public CompletableFuture<String> push(String collection,
                                          String bucket,
                                          String uid,
                                          String text){
        PushEncoder encoder = new PushEncoder(collection, bucket, uid, text);
        return sonicExecutor.execute(SonicChannel.INGEST, encoder, Replier.NOPDecoder.INSTANCE);
    }

    public CompletableFuture<String> pop(String collection,
                                         String bucket,
                                         String uid,
                                         String text){
        PopEncoder encoder = new PopEncoder(collection, bucket, uid, text);
        return sonicExecutor.execute(SonicChannel.INGEST, encoder, Replier.NOPDecoder.INSTANCE);
    }

    public CompletableFuture<Long> flushc(String collection){
        FlushEncoder encoder = new FlushEncoder(collection, null, null, Command.FLUSHC);
        return sonicExecutor.execute(SonicChannel.INGEST, encoder, new ResultDecoder());
    }

    public CompletableFuture<Long> flushb(String collection, String bucket){
        FlushEncoder encoder = new FlushEncoder(collection, bucket, null, Command.FLUSHB);
        return sonicExecutor.execute(SonicChannel.INGEST, encoder, new ResultDecoder());
    }

    public CompletableFuture<Long> flusho(String collection, String bucket, String uid){
        FlushEncoder encoder = new FlushEncoder(collection, bucket, uid, Command.FLUSHO);
        return sonicExecutor.execute(SonicChannel.INGEST, encoder, new ResultDecoder());
    }

    public CompletableFuture<Long> count(String collection, String bucket, String uid){
        CountEncoder encoder = new CountEncoder(collection, bucket, uid);
        return sonicExecutor.execute(SonicChannel.INGEST, encoder, new ResultDecoder());
    }
    //--------------------ingest channel end-----------------------------------------

    //--------------------control channel start-----------------------------------------
    public CompletableFuture<String> trigger(Action action){
        TriggerEncoder encoder = new TriggerEncoder(action.name());
        return sonicExecutor.execute(SonicChannel.CONTROL, encoder, Replier.NOPDecoder.INSTANCE);
    }
    //--------------------control channel start-----------------------------------------

    //-------------------common start------------------------------------------------------
    public CompletableFuture<String> ping(SonicChannel channel){
        PingEncoder encoder = new PingEncoder();
        return sonicExecutor.execute(channel, encoder, Replier.NOPDecoder.INSTANCE);
    }

    public CompletableFuture<String> help(SonicChannel channel, String manual){
        HelpEncoder encoder = new HelpEncoder(manual);
        return sonicExecutor.execute(channel, encoder, Replier.NOPDecoder.INSTANCE);
    }

    public CompletableFuture<Void> quit(SonicChannel channel){
        QuitEncoder encoder = new QuitEncoder();
        return sonicExecutor.execute(channel, encoder, Replier.NOPDecoder.INSTANCE)
                .thenAccept(r -> {
                    try {
                        sonicExecutor.close();
                    } catch (IOException e) {
                        //ignore
                    }
                });
    }
    //------------------common end-------------------------------------------------------

    @Override
    public void close() throws IOException {
        this.sonicExecutor.close();
    }
}
