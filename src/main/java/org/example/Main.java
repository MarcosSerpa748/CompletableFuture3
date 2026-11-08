package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class Main {
    static void main() {
        ExecutorService workers = Executors.newFixedThreadPool(4);
        ScheduledExecutorService scheduledWorkers = Executors.newScheduledThreadPool(1);
        AtomicInteger valorTotalNotasFiscais = new AtomicInteger(0);
        List<Integer> codigosNotasFiscais = Collections.synchronizedList(new ArrayList<>());

        for(int i =1;i<=15;i++){
            CompletableFuture.supplyAsync(() ->{

                try{Thread.sleep(1000);}catch (InterruptedException e){Thread.currentThread().interrupt();}
                return new Random().nextInt(100);

            },workers).thenAccept(valorPassado ->{

                valorTotalNotasFiscais.getAndAdd(valorPassado);
                codigosNotasFiscais.add(new Random().nextInt());

            });
        }

        scheduledWorkers.scheduleWithFixedDelay(() ->{
            try {
                System.out.println("Quantidade de notas emitidas "+codigosNotasFiscais.size());
            }catch (Exception e){
                System.out.println(e.getClass().getSimpleName());
            }
        },0,1, TimeUnit.SECONDS);

        Utilitarios.fecharPool(scheduledWorkers,workers,valorTotalNotasFiscais,codigosNotasFiscais);
    }
}
