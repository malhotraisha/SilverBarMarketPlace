# The Live Order Board

In this exercise we're asked to design a "Live Order Board" for a company called "Silver Bars Marketplace".
The responsibility of the Live Order Board is to tell the users how much demand for silver bars there is on the market.

## Features

### Register an order

The [`LiveOrderBoard`](https://github.com/malhotraisha/SilverBarMarketPlace/blob/master/src/main/java/com/silverbars/LiveOrderBoard.java) should allow the user 
to register an [`Order`](https://github.com/jmalhotraisha/SilverBarMarketPlace/blob/src/main/java/com/silverbars/domain/Order.java), which, according to the requirements, 
_"must contain the following fields"_: 
user id, order quantity (e.g.: 3.5 kg), price per kg (e.g.: £303) as well as the type of order: either buy or sell.

`Order` is modelled as follows:
- **user id** 
- **order quantity (e.g.: 3.5 kg)** - to make the basic arithmetic operations easier and retain the meaning of the domain concept 
the quantity of the order is modelled using the  [`unitsofmeasurement`](https://github.com/unitsofmeasurement/uom-se) library
- **price per kg (e.g.: £303)** - to allow for precise comparison of orders, the price is modelled using the [Joda Money](http://www.joda.org/joda-money/) library, 
which `Money` type is wrapped in the `PricePerKg` to retain the domain concept
- **order type: BUY or SELL** - the type of the order is modelled using a nested enumeration, defined as part of the Order class

_the registration of an `Order` [looks as follows](https://github.com/malhotraisha/SilverBarMarketPlace/blob/master/src/test/java/com/silverbars/domain/OrderTest.java):

```java
LiveOrderBoard board = new LiveOrderBoard();

board.register(new Order(
        new UserId("user 1"),
        Quantities.getQuantity(3.5, KILOGRAM),
        new PricePerKg(Money.of(GBP, 306)),
        Order.Type.Buy
));
```

### Cancel a registered order

It seemed sensible that an `Order` can be cancelled only by the user who placed it. 

```java
LiveOrderBoard board = new LiveOrderBoard();

Order order = buy(kg(3.5), £(306), Alice);

board.register(order);
board.cancel(order);
```

### Summary information of live orders

The [`LiveOrderBoard`](https://github.com/malhotraisha/SilverBarMarketPlace/blob/master/src/main/java/com/silverbars/LiveOrderBoard.java) is backed by a simple `List<Order>` implementation, 
which makes the cost of inserting (registering) and removing (cancelling) `Orders` linear . 
 
To generate a "live summary", the orders in the list need to be [aggregated and mapped](https://github.com/malhotraisha/sSilverBarMarketPlace/blob/master/src/main/java/com/silverbars/LiveOrderBoard.java#L28) 
to instances of `OrderSummary`.


The list of `OrderSummary` objects is sorted using the [`OrderComparator`](https://github.com/malhotraisha/SilverBarMarketPlace/blob/master/src/main/java/com/silverbars/OrderComparator.java#L9), 
which is a composition of three other comparators. 

### Running the application

This is a simple java application and can be run using [`OrderTester`](https://github.com/malhotraisha/SilverBarMarketPlace/blob/master/src/test/java/com/silverbars/OrderTester.java)
This class asks the user to input the order details (userid , quantity, price , type). If you wish to add more orders press Y and enter details.
Before submitting the orders if you wish to cancel one of the many orders press C and enter the order details to be cancelled
The class then displays the order summary at the end. 

Program Execution :

Enter details to register an Order
enter userId:
esha
enter quantity in Kgs:
1
enter PricePerKg in Pounds:
11
enter Order Type Buy/Sell
buy

If you wish to add more orders enter Y 
n
Do you wish to confirm order? If no press C to cancel an order
c
Enter details to cancel order
enter userId:
e
enter quantity in Kgs:
2
enter PricePerKg in Pounds:
11
enter Order Type Buy/Sell
sell
15:28:22.575 [main] INFO  com.silverbars.LiveOrderBoard - Cancelling Order: Order{userId=UserId{userId='e'}, quantity=2.0 kg, pricePerKg=PricePerKg{amount=GBP 11.00}, orderType=Sell}
15:28:22.575 [main] INFO  com.silverbars.LiveOrderBoard - Invalid Order input
15:28:22.575 [main] INFO  com.silverbars.LiveOrderBoard - Displaying Order Summary
15:28:22.580 [main] INFO  com.silverbars.LiveOrderBoard - Displaying Order Summary
OrderSummary{pricePerKg=PricePerKg{amount=GBP 11.00}, orderType=Buy, quantity=1.0 kg}

Process finished with exit code 0

