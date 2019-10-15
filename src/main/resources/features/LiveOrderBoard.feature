Feature: LiveOrderBoardSummary

  Scenario: Adding an item to the order
    Given I have not yet ordered anything
    When I add a bid by type and price
    Then I have a 1 order registered

  Scenario: Cancelling an item from the order
    Given The order type by a user exists on the board
    When I cancel an order from the same user of the same type
    Then I have the order cancelled  for the user

  Scenario: Summary of the Buy orders on board
    Given The buy orders from all users
    When I click on the summary
    Then I have the orders grouped by price and type irrespective of users in descending order

  Scenario: Summary of the Sell orders on board
    Given The Sell orders from all users
    When I click on the summary
    Then I have the orders grouped by price and type irrespective of users in ascending order

  Scenario: Cancel an order from a different user
      Given An order placed from user1
      When User2 cancels the order placed by user1
      Then The order should not be cancelled

