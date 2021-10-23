package br.com.pucminas.apiconsumer.constants;

public class ApiConstants {

    public static final String RABBIT_QUEUE_NAME = "${spring.rabbitmq.queue}";
    public static final String TASK_EXECUTOR = "task_executor_tcc";
    public static final String THREAD_POOL_TASK = "threadPoolTaskExecutor";
    private ApiConstants(){}
}
