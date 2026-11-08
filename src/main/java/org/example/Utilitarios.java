package org.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Utilitarios {

    public static void fecharPool
            (
            ScheduledExecutorService scheduleWorkers,
            ExecutorService workers,
            AtomicInteger valorAtomico,
            List<Integer> quantidadeTotalCodigo
            ){
        workers.shutdown();
        //Não foi colocado um scheduleWorkers.shutdown() pois ele é desliga instantaneamente a repetição da tarefa
        try {
            if (!workers.awaitTermination(30,TimeUnit.SECONDS)){
                workers.shutdownNow();
            }
            scheduleWorkers.shutdownNow();
            //A exebição do valor foi colocado fora da condição pois assim ele é exibido e qualquer forma
            System.out.println("Valor total das notas fiscais emitidas R$"+valorAtomico);
            System.out.println("Número total de notas fiscais:"+quantidadeTotalCodigo.size());

        }catch (InterruptedException e){
            scheduleWorkers.shutdownNow();
            workers.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
