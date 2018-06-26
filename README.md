# Bayesian-network
Computes the probability of any combination of events given any other combination of events in a given Bayesian network with events - Burglary, Earthquake, Alarm, JohnCalls, and MaryCalls.

Example invocations of the program:
1. To print out the probability P(Burglary=true and Alarm=false | MaryCalls=false).
         bnet Bt Af given Mf
2. To print out the probability P(Alarm=false and Earthquake=true).
    bnet Af Et
3. To print out the probability P(JohnCalls=true and Alarm=false | Burglary=true and Earthquake=false).
     bnet Jt Af given Bt Ef
4. To print out the probability P(Burglary=true and Alarm=false and MaryCalls=false and JohnCalls=true and Earthquake=true).
     bnet Bt Af Mf Jt Et
     
bnet takes 1 to 6(no more, no fewer) command line arguments, as follows:
a) First, there are one to five arguments, each argument specifying a variable among Burglary, Earthquake, Alarm, JohnCalls, and MaryCalls and a value equal to true or false. Each of these arguments is a string with two letters. The first letter is B (for Burglary), E (for Earthquake), A (for Alarm), J (for JohnCalls) or M (for MaryCalls). The second letter is t (for true) or f (for false). These arguments specify a combination C1 of events whose probability we want to compute. 

b) Then, optionally, the word "given" follows, followed by one to four arguments. Each of these one to four arguments is again a string with two letters, where, as before the first letter is B (for Burglary), E (for Earthquake), A (for Alarm), J (for JohnCalls) or M (for MaryCalls). The second letter is t (for true) or f (for false). These last arguments specify a combination of events C2 such that we need to compute the probability of C1 given C2. 

**The main functions used in the bnet class are:**
a. computeProbability(char, char, char, char, char): Calculate the probability based on the values of B,E,A,J,M.
b. generateRemainingPossibleValues(Map<Character, ArrayList<Character>> ) : Generating the missing variables for the statement validation.
c. loadValues(): Populating the Map with the values given in the Bayesian network diagram.(The values are hard coded here.)
  
  **Assumption**
  The programs works under the assumption that two values will not be provided for a same variable in a single statement.
  ie.,a statement like the below is not a valid input:
  bnet Jt Bf given Bt 
