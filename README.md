# Refactoring by Martin

These are the rules that I followed for this refactoring.

0. Change as little as possible.
0. Keep original interfaces if at all possible.
0. Don't use double for money.
0. Don't use strings for state because you could misspell them. 
0. Don't compare strings using the == operator (in Java).
0. Keep methods small and readable.
0. Try to make things testable. In this refactoring this advice may not be strictly adhered to: some methods that may have benefitted from unit testing are private.
0. Don't overdo it.
0. I like good comments but not as a substitute for good code.

The StringBuilder for String substitution is not required for this small project, it just smelled a bit.



# Refactoring Java

The code creates an information slip about movie rentals.
Rewrite and improve the code after your own liking.

Think: you are responsible for the solution, this is a solution you will have to put your name on.


## Handing in the assignment

Reason how you have been thinking and the decisions you took. 
You can hand in the result any way you feel (git patch, pull-request or ZIP-file).
Note: the Git history must be included.


## To run the test:

```
javac src/*.java
java -cp src Main
```
