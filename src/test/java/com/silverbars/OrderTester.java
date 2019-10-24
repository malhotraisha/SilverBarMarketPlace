package com.silverbars;

import com.silverbars.domain.OrderSummary;
import com.silverbars.domain.UserId;

import java.util.Scanner;

import static com.silverbars.support.DSL.*;


public class OrderTester {

    LiveOrderBoard board = new LiveOrderBoard();


    private void collectOrders(){
        Scanner myObj = new Scanner(System.in);

        System.out.println("Please enter Orders to Buy/Sell");

        System.out.println("Please enter userId");
        String userId = myObj.nextLine();

        System.out.println("Please enter quantity in Kgs");
        String quantity = myObj.nextLine();

        System.out.println("Please enter PricePerKg in Pounds");
        String price = myObj.nextLine();

        System.out.println("Please select Buy/Sell");
        String option = myObj.nextLine();

        if (option.equalsIgnoreCase("Buy")) {
            System.out.println("selected Buy");
            board.register(buy(kg(Double.parseDouble(quantity)), £(Double.parseDouble(price)), new UserId(userId)));
        } else if (option.equalsIgnoreCase("Sell")) {
            System.out.println("selected Sell");
            board.register(sell(kg(Double.parseDouble(quantity)), £(Double.parseDouble(price)), new UserId(userId)));
        } else {
            System.out.println("Invalid Option selected");
        }
        System.out.println("Please select Yes to add More Orders");
        String moreOrders = myObj.nextLine();
        if(moreOrders.equalsIgnoreCase("yes")){
            collectOrders();
        }else{
            System.out.println("End");
        }
    }

    public static void main(String[] args) {
        OrderTester orderTester=new OrderTester();
        orderTester.collectOrders();
        for (OrderSummary summary : orderTester.board.summary()) {
            System.out.println(summary.toString());
        }

    }
}