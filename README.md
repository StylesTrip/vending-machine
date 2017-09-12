My solution to Pillar Technology's Vending Machine Kata
Found here: https://github.com/PillarTechnology/kata-vending-machine

Vending Machine
====================


To Run
------
The Vending Machine kata was built with Maven.
The tests can be run by typing: "mvn test" at the command line.
The code was developed with IntelliJ Community Edition 2017.1

Thought Process
----------------
I decided to attempt the Vending Machine kata because there seemed like a series of moving parts to it that
would be a good challenge. I've never attempted a Vending Machine kata before so it was awesome to try something new.

This was also my first attempt at TDD so there were a decent amount of "d'oh, I shouldn't have done that".
At the end of this, I'll go over a few of those moments.

###First Steps
The way I tackled this kata was to go over each of the steps before writing any code. Most parts seemed simple
except for the making change portion so I knew I was going to save that for last. After reading it all, I decided
to start at the top and go with "Accept Coins".

###Accept Coins
I made a Vending Machine class that would accept coins. These coins were made to be Enums that had their width
and height. No value as the kata had asked for. The Vending Machine would be responsible for determining the value.

###Select Product
For this piece, I made an Interface for VMProducts and made a class for each item. One thing I did think of is that
an item also does NOT know its price (just like Coins don't know their value). I had the VendingMachine also determine
what the price of an item was. That way, there is only one place to change the price of an item, in the
VendorMachinePricing class.

###Return Coins
The Vending Machine kept track of how many and what coins were inserted so if the method for coinReturn was called,
the money would be dispensed to the coin return.

###Sold Out
There is an item inventory for the Vending Machine that is Java Optional. The product may or may not be in the inventory.
If the optional is empty, update the display to "SOLD OUT".

###Make Change
This one I had to do research on and how US Vending Machines work for making change. They do a greedy way in that
they look for the largest denomination first to use and go down the line, e.g. 25, 10, 5. The ChangeInventory is where
all of that fun happens. At most, it'll make a dollar's worth of change. I made that assumption because I don't think
people are crazy enough to throw 2+ dollars in for a $.50 but I could be wrong.

###Exact Change Only
Basically the same greedy way as above in Make Change, if the Vending Machine can't make enough change
for a dollar, it will display "Exact Change Only".

##Conclusion
All in all, this was a pretty awesome kata to try out for my first go at TDD. A big change that I should have been
more careful of was throwing everything into the VendingMachineTest class. I believe I should have used Mocks for the
other classes VendingMachine depended on for testing and when I needed to test those separate classes, I should have
put the tests into separate test classes for them.