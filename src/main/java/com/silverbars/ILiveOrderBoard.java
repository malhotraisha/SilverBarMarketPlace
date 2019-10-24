package com.silverbars;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;
import com.silverbars.exception.EmptyOrderListException;

import java.util.List;

public interface ILiveOrderBoard {

    void register(Order order);

    void cancel(Order order);

    List<OrderSummary> summary() throws EmptyOrderListException;
}
