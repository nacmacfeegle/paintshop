
This is the solution to a really interesting technical challenge I was once set. The problem description is as follows:



You run a paint shop, and there are a few different colors of paint you can prepare.  Each color can be either "glossy" or "matte".

You have a number of customers, and each have some colors they like, in either glossy or matte.  No customer will like more than one color in matte.

You want to mix the colors, so that:
   * There is just one batch for each color, and it's either glossy or matte.
   * For each customer, there is at least one color they like.
   * You make as few mattes as possible (because they are more expensive).

Your program should accept an input file, and print a result.  An example input file is:

5
1 M 3 G 5 G
2 G 3 M 4 G
5 M

The first line specifies how many colors there are.

Each subsequent line describes a customer.  For example, the first customer like color 1 in matte, color 3 in glossy and color 5 in glossy.

Your program should read an input file like this, and print out either than it is impossible to satisfy all the customer, or describe, for each of the colors, whether it should be made glossy or matte.

The output for the above file should be:

G G G G M

...because all customers can be made happy by every paint being prepared as glossy except number 5.

An example of a file which has no solution is:

1
1 G
1 M

Your program should print

No solution exists