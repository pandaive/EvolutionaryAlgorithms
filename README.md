# Programming an Evolutionary Algorithm (EA)
Project for Sub-symbolic AI methods on NTNU (Norwegian University of Science and Technology)
The project is an implementation of an Evolutionary Algorithms in Java for following problems:
* The One-Max Problem
* The LOLZ Prefix Problem
* Surprising sequences

The One-Max Problem:
This is a search problem that is trivial for a human to solve but slightly more difficult for an EA.
The goal is simply to find a bit vector (of some pre-determined length) containing all 1's. So for
the 20-bit OneMax problem, the goal is to find the 20-bit vector containing all 1's. Since an EA
initially creates genotypes at random and then uses stochastic processes (selection, crossover and
mutation) to generate succeeding generations of genotypes, the EA cannot simply generate an
all-ones vector to solve the problem. It must generate random vectors for the initial population
and then assign them fitness based on their proximity to the goal (i.e. the all-ones vector). Those
with more ones will receive higher fitness and will reproduce more often, so the total number
of 1's in the population will gradually increase until an individual with all 1's emerges. So, for
example, the fitness function could just count the proportion of 1's in the genotype/phenotype
(they are essentially the same thing for this simple problem) and assign that fraction as the
fitness.

The LOLZ Prefix problem is to maximize the number of leading ones (LO) or leading zeros (LZ)
in a bit string. The fitness is the number of consecutive similar bits from the beginning of the
string, with the twist that the score for having leading zeros is capped at some threshold
value z. For the 6-bit problem, with z = 4, this is some examples and their fitness value:
[111111]=6, [000000]=4, [110101]=2, [001011]=2.

Surprising sequences are those that are completely free of repeating patterns. Defined formally:
A sequence is surprising if and only if, for every pair of symbols, A and B, and
any distance d, there is at most ONE instance in the sequence of AXdB, where Xd
is any subsequence of length d.
or not as formally:
A string over an alphabet is surprising if there are no two symbols A and B of
the alphabet such that there are two pairs of occurrences of these symbols where A
precedes B by the same distance.
Note that order matters in assessing patterns, so AXdB is not equal to BXdA. Also, the
criteria include the case where A=B.
As a simple example, the sequence ABCCBA is surprising, but AABCC is not, since AX2C
occurs twice. Similarly, ABBACCA is not surprising due to 2 occurrences of AX2A.
For this assignment, we will consider two types of surprising sequences:
1. Globally surprising sequences are those defined above: all values of distance d must be
considered.
2. Locally surprising sequences are those in which there are no repeat occurrences of AB
(i.e. AX0B) for any symbols A and B. That is, the only distance of relevance is d = 0.
Thus, AABCC is not globally surprising, but it is locally surprising. The same goes for
ABBACCA. A sequence that is not locally surprising is ABCBC due to the repeat of BC.
Your EA should search for the longest possible surprising sequence that can be constructed from
symbol sets of different lengths. For instance, when there are only 2 symbols, then the longest
globally surprising sequence is ABBA (or BAAB or AABA, etc.), while the largest locally-
surprising sequence is ABBAA (or BAABB or AABBA, etc.)
So, for each size (S) of symbol set, you should run your algorithm many times with different
lengths (L) to see if it can construct a sequence of length L from the symbols. If it can, you can
increase L and see if you can find an even longer one. You should handle S up to 40, for both
locally- and globally-surprising sequences.
To give you an idea of the size range: In general, the longest L for a given S is less than 3S.
For S = 10, the longest globally-surprising sequence is of L = 26. The longest locally-surprising
sequence for S=10 is above 100. Don't expect your EA to find the longest possible, at least not
consistently. For a given S and L, it is wise to run your EA between 5 to 10 times to see if it can
find a solution of length L.
How you represent this is in your program is up to you, but when displaying a solution you
should follow this format, so that we easy can verify that it's a correct solution. The output
should be using numbers for the symbols, separating them with a comma and a space. Here is
an example of a locally-surprising sequence S=4, L=17: 0, 0, 2, 3, 3, 0, 3, 1, 2, 1, 1, 3, 2, 2, 0,
1, 0