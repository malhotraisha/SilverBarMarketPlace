package com.silverbars;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;
import com.silverbars.domain.UserId;
import com.silverbars.exception.EmptyOrderListException;

import java.util.Scanner;

import static com.silverbars.support.DSL.*;


public class OrderTester {

    ILiveOrderBoard board = new LiveOrderBoard();


    private void collectOrders(){
        Scanner myObj = new Scanner(System.in);

        System.out.println("enter userId:");
        String userId = myObj.nextLine();

        System.out.println("enter quantity in Kgs:");
        String quantity = myObj.nextLine();

        System.out.println("enter PricePerKg in Pounds:");
        String price = myObj.nextLine();

        System.out.println("enter Order Type Buy/Sell");
        String option = myObj.nextLine();

        if (option.equalsIgnoreCase("Buy")) {
            board.register(buy(kg(Double.parseDouble(quantity)), £(Double.parseDouble(price)), new UserId(userId)));
        } else if (option.equalsIgnoreCase("Sell")) {
            board.register(sell(kg(Double.parseDouble(quantity)), £(Double.parseDouble(price)), new UserId(userId)));
        } else {
            System.out.println("Invalid Option selected");
        }
        System.out.println("If you wish to add more orders enter Y ");
        String moreOrders = myObj.nextLine();
        if(moreOrders.equalsIgnoreCase("Y")){
            collectOrders();
        }else{
            System.out.println("Do you wish to confirm order? If no press C to cancel an order");
            String cnfOrder = myObj.nextLine();
            if(cnfOrder.equalsIgnoreCase("C"))
            {
                System.out.println("Enter details to cancel order");
                System.out.println("enter userId:");
                userId = myObj.nextLine();

                System.out.println("enter quantity in Kgs:");
                quantity = myObj.nextLine();

                System.out.println("enter PricePerKg in Pounds:");
                price = myObj.nextLine();

                System.out.println("enter Order Type Buy/Sell");
                option = myObj.nextLine();

                if (option.equalsIgnoreCase("Buy")) {
                    board.cancel(buy(kg(Double.parseDouble(quantity)), £(Double.parseDouble(price)), new UserId(userId)));
                } else if (option.equalsIgnoreCase("Sell")) {
                    board.cancel(sell(kg(Double.parseDouble(quantity)), £(Double.parseDouble(price)), new UserId(userId)));
                } else {
                    System.out.println("Invalid Option selected");
                }
            }
            else {
                System.out.println("Order Confirmed");
             }
            }
        }


    public static void main(String[] args) throws EmptyOrderListException {

        OrderTester orderTester=new OrderTester();
        System.out.println("Enter details to register an Order");
        orderTester.collectOrders();
            for (OrderSummary summary : orderTester.board.summary()) {
                System.out.println(summary.toString());
            }
        }
}
