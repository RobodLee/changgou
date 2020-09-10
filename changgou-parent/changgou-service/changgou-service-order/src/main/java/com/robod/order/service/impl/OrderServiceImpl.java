package com.robod.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.robod.entity.IdWorker;
import com.robod.goods.feign.SkuFeign;
import com.robod.goods.pojo.Sku;
import com.robod.order.api.pojo.Order;
import com.robod.order.api.pojo.OrderItem;
import com.robod.order.mapper.OrderItemMapper;
import com.robod.order.mapper.OrderMapper;
import com.robod.order.service.intf.OrderService;
import com.robod.user.feign.UserFeign;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/****
 * @Author:admin
 * @Description:Order业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void updateStatus(String outTradeNo,String timeEnd,String transactionId) {
        Order order = orderMapper.findById(outTradeNo);
        LocalDateTime payTime = LocalDateTime.parse(timeEnd, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        order.setPayStatus("1");                //支付状态修改为1表示已支付
        order.setTransactionId(transactionId);  //交易流水号
        order.setPayTime(payTime);              //交易时间

        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public void deleteOrder(String outTradeNo) {
        Order order = orderMapper.findById(outTradeNo);
        LocalDateTime time = LocalDateTime.now();
        //修改状态
        order.setPayStatus("2");    //交易失败
        order.setUpdateTime(time);
        //提交到数据库
        orderMapper.updateByPrimaryKey(order);
        //回滚库存
        List<OrderItem> orderItems = orderItemMapper.findByOrderId(order.getId());
        List<Long> skuIds = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            skuIds.add(orderItem.getSkuId());
        }
        List<Sku> skuList = skuFeign.findBySkuIds(order.getSkuIds()).getData(); //数据库中对应的sku集合
        Map<Long, Sku> skuMap = skuList.stream().collect(Collectors.toMap(Sku::getId, a -> a));
        for (OrderItem orderItem : orderItems) {
            Sku sku = skuMap.get(orderItem.getSkuId());
            sku.setNum(sku.getNum()+orderItem.getNum());    //加库存
        }
        skuFeign.updateMap(skuMap);
    }

    /**
     * 增加Order
     * @param order
     */
    @Override
    public synchronized void add(Order order) {
        order.setId(String.valueOf(idWorker.nextId()));
//        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps("Cart_" + order.getUsername());
//        int totalNum=0,totalMoney=0;    //总数量，总金额
//        LocalDateTime localDateTime = LocalDateTime.now();
//        List<OrderItem> orderItems = boundHashOperations.values();   //从购物车中获取订单明细
//        if (orderItems == null || orderItems.size()==0) {
//            throw new RuntimeException("购物车数据异常,下单失败");
//        }
//        List<Sku> skuList = skuFeign.findBySkuIds(order.getSkuIds()).getData(); //数据库中对应的sku集合
//        //如果数据库中查询出来的sku集合数量与前端传过来的sku数量不一致，说明数据有误，下单失败
//        if (skuList.size() != order.getSkuIds().size()){
//            throw new RuntimeException("sku数据库数据异常,下单失败");
//        }
//        Map<Long,Sku> skuMap = skuList.stream().collect(Collectors.toMap(Sku::getId,a -> a));
//        //遍历购物车中的数据，判断是否是选中的，将选中的订单明细数据补充完整
//        for (OrderItem orderItem : orderItems) {
//            if (order.getSkuIds().contains(orderItem.getSkuId())) {     //判断当前遍历到的orderItem是否是选中的
//                orderItem.setId(String.valueOf(idWorker.nextId()));
//                orderItem.setOrderId(order.getId());
//                orderItem.setIsReturn("0");
//                Sku sku = skuMap.get(orderItem.getSkuId()); //数据库中的sku
//                if (orderItem.getNum() <= sku.getNum()) {   //判断库存是否充足，不足则报异常订单提交失败
//                    totalNum += orderItem.getNum();
//                } else {
//                    throw new RuntimeException("库存不足,下单失败");
//                }
//                totalMoney += sku.getPrice();
//            }
//        }
//        //减库存，删购物车
//        for (OrderItem orderItem : orderItems) {
//            if (order.getSkuIds().contains(orderItem.getSkuId())) {
//                Sku sku = skuMap.get(orderItem.getSkuId()); //数据库中的sku
//                sku.setNum(sku.getNum() - orderItem.getNum());	//减库存
//                boundHashOperations.delete(orderItem.getSkuId());	//删购物车
//                orderItemMapper.insertSelective(orderItem); //添加到订单明细表
//            }
//        }
//        skuFeign.updateMap(skuMap); //将sku信息提交到数据库中的sku表
//
//        order.setCreateTime(localDateTime);
//        order.setUpdateTime(localDateTime);
//        order.setTotalNum(totalNum);
//        order.setTotalMoney((double)totalMoney);
//        order.setSourceType("1");   //1.web
//        order.setOrderStatus("0");
//        order.setPayStatus("0");
//        order.setIsDelete("0");
//        orderMapper.insertSelective(order); //添加到订单表
//
//        userFeign.addPoints(totalMoney);    //添加积分，1块钱1分

        rabbitTemplate.convertAndSend("orderDelayQueue", (Object)order.getId(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("7000");   //定时7秒过时
                return message;
            }
        });
    }

    /**
     * Order条件+分页查询
     * @param order 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Order> findPage(Order order, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(order);
        //执行搜索
        return new PageInfo<Order>(orderMapper.selectByExample(example));
    }

    /**
     * Order分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Order> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Order>(orderMapper.selectAll());
    }

    /**
     * Order条件查询
     * @param order
     * @return
     */
    @Override
    public List<Order> findList(Order order){
        //构建查询条件
        Example example = createExample(order);
        //根据构建的条件查询数据
        return orderMapper.selectByExample(example);
    }


    /**
     * Order构建查询对象
     * @param order
     * @return
     */
    public Example createExample(Order order){
        Example example=new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if(order!=null){
            // 订单id
            if(!StringUtils.isEmpty(order.getId())){
                    criteria.andEqualTo("id",order.getId());
            }
            // 数量合计
            if(!StringUtils.isEmpty(order.getTotalNum())){
                    criteria.andEqualTo("totalNum",order.getTotalNum());
            }
            // 金额合计
            if(!StringUtils.isEmpty(order.getTotalMoney())){
                    criteria.andEqualTo("totalMoney",order.getTotalMoney());
            }
            // 优惠金额
            if(!StringUtils.isEmpty(order.getPreMoney())){
                    criteria.andEqualTo("preMoney",order.getPreMoney());
            }
            // 邮费
            if(!StringUtils.isEmpty(order.getPostFee())){
                    criteria.andEqualTo("postFee",order.getPostFee());
            }
            // 实付金额
            if(!StringUtils.isEmpty(order.getPayMoney())){
                    criteria.andEqualTo("payMoney",order.getPayMoney());
            }
            // 支付类型，1、在线支付、0 货到付款
            if(!StringUtils.isEmpty(order.getPayType())){
                    criteria.andEqualTo("payType",order.getPayType());
            }
            // 订单创建时间
            if(!StringUtils.isEmpty(order.getCreateTime())){
                    criteria.andEqualTo("createTime",order.getCreateTime());
            }
            // 订单更新时间
            if(!StringUtils.isEmpty(order.getUpdateTime())){
                    criteria.andEqualTo("updateTime",order.getUpdateTime());
            }
            // 付款时间
            if(!StringUtils.isEmpty(order.getPayTime())){
                    criteria.andEqualTo("payTime",order.getPayTime());
            }
            // 发货时间
            if(!StringUtils.isEmpty(order.getConsignTime())){
                    criteria.andEqualTo("consignTime",order.getConsignTime());
            }
            // 交易完成时间
            if(!StringUtils.isEmpty(order.getEndTime())){
                    criteria.andEqualTo("endTime",order.getEndTime());
            }
            // 交易关闭时间
            if(!StringUtils.isEmpty(order.getCloseTime())){
                    criteria.andEqualTo("closeTime",order.getCloseTime());
            }
            // 物流名称
            if(!StringUtils.isEmpty(order.getShippingName())){
                    criteria.andEqualTo("shippingName",order.getShippingName());
            }
            // 物流单号
            if(!StringUtils.isEmpty(order.getShippingCode())){
                    criteria.andEqualTo("shippingCode",order.getShippingCode());
            }
            // 用户名称
            if(!StringUtils.isEmpty(order.getUsername())){
                    criteria.andLike("username","%"+order.getUsername()+"%");
            }
            // 买家留言
            if(!StringUtils.isEmpty(order.getBuyerMessage())){
                    criteria.andEqualTo("buyerMessage",order.getBuyerMessage());
            }
            // 是否评价
            if(!StringUtils.isEmpty(order.getBuyerRate())){
                    criteria.andEqualTo("buyerRate",order.getBuyerRate());
            }
            // 收货人
            if(!StringUtils.isEmpty(order.getReceiverContact())){
                    criteria.andEqualTo("receiverContact",order.getReceiverContact());
            }
            // 收货人手机
            if(!StringUtils.isEmpty(order.getReceiverMobile())){
                    criteria.andEqualTo("receiverMobile",order.getReceiverMobile());
            }
            // 收货人地址
            if(!StringUtils.isEmpty(order.getReceiverAddress())){
                    criteria.andEqualTo("receiverAddress",order.getReceiverAddress());
            }
            // 订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面
            if(!StringUtils.isEmpty(order.getSourceType())){
                    criteria.andEqualTo("sourceType",order.getSourceType());
            }
            // 交易流水号
            if(!StringUtils.isEmpty(order.getTransactionId())){
                    criteria.andEqualTo("transactionId",order.getTransactionId());
            }
            // 订单状态,0:未完成,1:已完成，2：已退货
            if(!StringUtils.isEmpty(order.getOrderStatus())){
                    criteria.andEqualTo("orderStatus",order.getOrderStatus());
            }
            // 支付状态,0:未支付，1：已支付，2：支付失败
            if(!StringUtils.isEmpty(order.getPayStatus())){
                    criteria.andEqualTo("payStatus",order.getPayStatus());
            }
            // 发货状态,0:未发货，1：已发货，2：已收货
            if(!StringUtils.isEmpty(order.getConsignStatus())){
                    criteria.andEqualTo("consignStatus",order.getConsignStatus());
            }
            // 是否删除
            if(!StringUtils.isEmpty(order.getIsDelete())){
                    criteria.andEqualTo("isDelete",order.getIsDelete());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        orderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Order
     * @param order
     */
    @Override
    public void update(Order order){
        orderMapper.updateByPrimaryKey(order);
    }

    /**
     * 根据ID查询Order
     * @param id
     * @return
     */
    @Override
    public Order findById(String id){
        return  orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Order全部数据
     * @return
     */
    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }
}
