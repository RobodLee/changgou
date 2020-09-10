package com.robod.order.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Robod
 * @date 2020/9/10 16:58
 */
@Configuration
public class MqConfig {

    /**
     * 队列1，延时队列，消息过期后发送给队列2
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder
                .durable("orderDelayQueue")
                //orderDelayQueue队列信息会过期，过期之后，进入到死信队列，死信队列数据绑定到其他交换机
                .withArgument("x-dead-letter-exchange","orderListenerExchange")
                .withArgument("x-dead-letter-routing-key","orderListenerRoutingKey") //绑定指定的routing-key
                .build();
    }

    /**
     * 队列2
     * @return
     */
    @Bean(name = "orderListenerQueue")  //名称不写默认就是方法名
    public Queue orderListenerQueue() {
        return new Queue("orderListenerQueue",true);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public Exchange orderListenerExchange() {
        return new DirectExchange("orderListenerExchange");
    }

    /**
     * 队列queue2绑定exchange
     * @param orderListenerQueue
     * @param orderListenerExchange
     * @return
     */
    @Bean
    public Binding orderListenerBinding(Queue orderListenerQueue,Exchange orderListenerExchange) {
        return BindingBuilder
                .bind(orderListenerQueue)
                .to(orderListenerExchange)
                .with("orderListenerRoutingKey")
                .noargs();
    }

}
