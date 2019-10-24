package com.silverbars;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;

import java.util.Comparator;
import static java.util.Comparator.comparing;

/**
 *   This class is an aggregation of three comparators and orders the summary by type and Price
 *   If the type of order type is SELL, summary is displayed in ascending order else descending
 */
class OrderComparator implements Comparator<OrderSummary> {
    static private final Comparator<OrderSummary> byType            = comparing(OrderSummary::orderType);
    static private final Comparator<OrderSummary> byPriceAscending  = comparing(OrderSummary::pricePerKg);
    static private final Comparator<OrderSummary> byPriceDescending = comparing(OrderSummary::pricePerKg).reversed();

    @Override
    public int compare(OrderSummary left, OrderSummary right) {
        if (left.orderType() != right.orderType()) {
            return byType.compare(left, right);
        }

        if (left.orderType() == Order.Type.Sell) {
            return byPriceAscending.compare(left, right);
        }

        if (left.orderType() == Order.Type.Buy) {
            return byPriceDescending.compare(left, right);
        }

        return 0;
    }
}
