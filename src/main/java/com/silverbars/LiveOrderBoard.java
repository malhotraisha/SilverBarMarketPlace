package com.silverbars;

import com.silverbars.domain.Bid;
import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;
import com.silverbars.exception.EmptyOrderListException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 *
 * This class allows the user to register an order , cancel an order
 * and display the order summary
 */

public class LiveOrderBoard implements ILiveOrderBoard {
    private final List<Order> registeredOrders = new ArrayList<>();
    private final Comparator<OrderSummary> byTypeAndPrice = new OrderComparator();

    final static Logger logger = LoggerFactory.getLogger(LiveOrderBoard.class);

    /**
     * Registers an order , where the order must contain user id, order quantity,
     * price per kg as well as the type of order ( BUY or SELL)
     *
     * @param order
     */
    @Override
    public void register(Order order) {
        logger.info("Registering Order: " + order.toString());
        registeredOrders.add(order);
        logger.info("Order successfully registered");
    }

    /**
     * Cancels a registered order
     *
     * @param order
     */
    @Override
    public void cancel(Order order) {
        logger.info("Cancelling Order: " + order.toString());
        boolean exists = registeredOrders.contains(order);
        if(exists){
            registeredOrders.remove(order);
            logger.info("Order successfully cancelled");
        }
        else{
            logger.info("Invalid Order input");
        }
    }

    /**
     * Displays the summary of the order using OrderComparator
     * which is an aggregation of three comparators
     *
     * @return
     */
    @Override
    public List<OrderSummary> summary() throws EmptyOrderListException {
        logger.info("Displaying Order Summary");
        if(registeredOrders.size() > 0) {
                return registeredOrders.stream().
                        collect(groupingBy(Bid::forOrder, mapping(Order::quantity, toList()))).
                        entrySet().stream().
                        map(toOrderSummary()).
                        sorted(byTypeAndPrice).
                        collect(toList());
        }
        else {
            throw new EmptyOrderListException("Empty Order List. Size: " + registeredOrders.size());
        }
    }

    private Function<Map.Entry<Bid, List<Quantity<Mass>>>, OrderSummary> toOrderSummary() {
        return entry -> new OrderSummary(entry.getKey(), entry.getValue());
    }
}
