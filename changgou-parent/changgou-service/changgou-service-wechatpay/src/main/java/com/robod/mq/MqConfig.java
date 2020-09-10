package com.robod.mq;

/**
 * @author Robod
 * @date 2020/9/8 14:44
 * 暂时在web端手动配置，不用程序生成
 */
//@Configuration
public class MqConfig {

//    @Autowired
//    private Environment environment;
//
//    /**
//     * 创建队列
//     * @return
//     */
//    @Bean
//    public Queue orderQueue() {
//        return new Queue(environment.getProperty("mq.pay.queue.order"));
//    }
//
//    /**
//     * 创建交换机
//     * @return
//     */
//    @Bean
//    public Exchange orderExchange() {
//        return new DirectExchange(environment.getProperty("mq.pay.exchange.order"));
//    }
//
//    /**
//     * 队列绑定交换机
//     * @param queue
//     * @param exchange
//     * @return
//     */
//    @Bean
//    public Binding orderQueueExchange(Queue queue , Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(environment.getProperty("mq.pay.routing.key")).noargs();
//    }

}
