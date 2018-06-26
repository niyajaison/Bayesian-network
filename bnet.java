
/**
 * @author Niya Jaison | UTA ID : 1001562701 | Net ID:nxj2701
 * References:	
 * The program that computes and prints out the probability of any combination of events given any other combination of events of the given Bayesian Network.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bnet {
	public static Map<String, Double> burglaryTT = new HashMap<String, Double>();/**Storing the values provided in Bayesian Network for Burglary*/
	public static Map<String, Double> earthquakeTT = new HashMap<String, Double>();/**Storing the values provided in Bayesian Network for Earthquake*/
	public static Map<String, Double> alarmTT = new HashMap<String, Double>();/**Storing the values provided in Bayesian Network for Alarm*/
	public static Map<String, Double> johnCallTT = new HashMap<String, Double>();/**Storing the values provided in Bayesian Network for JohnCall*/
	public static Map<String, Double> maryCallTT = new HashMap<String, Double>();/**Storing the values provided in Bayesian Network for MaryCall*/
	public static Map<Character, ArrayList<Character>> conditions,givenValues;/**Storing the values received for command line -as per the Bayes's rule*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length<1||args.length>6) {/**Validating the condition that number of arguments should be between 1 - 6*/
			System.out.println("The number of arguments should be between 1 - 6.\n!!!Exiting the Program!!!!");
			System.exit(0);
		}
		loadValues();/**A user defined function to load the values of the Bayesian network to the maps variable */
		int counter=0;

		givenValues=new HashMap<Character, ArrayList<Character>>();
		conditions=new HashMap<Character, ArrayList<Character>>();
		for(String inline:args) {
			ArrayList<Character> possibleBool=new ArrayList<Character>();
			if(inline.equals("given")) {
				counter=1;
				continue;
			}
			/**Splitting the arguments in to 2 map based on the argument 'given'(line 37-48)*/
			if(counter==0) {
				String temp=inline.toUpperCase();
				//System.out.println(inline);
				possibleBool.add(temp.charAt(1));
				conditions.put(temp.charAt(0), possibleBool);
			}
			else {
				String temp=inline.toUpperCase();
				//System.out.println(inline);
				possibleBool.add(temp.charAt(1));
				givenValues.put(temp.charAt(0), possibleBool);
			}
		}
		//System.out.println(conditions+"---------"+givenValues);
		if(conditions.keySet().size()< 1||conditions.keySet().size()>5) {/**validating the condition in the input argument set 'C1 given C2', C1 should be 1-5 arguments*/
			System.out.println("The number of conditions should be between 1 - 5.\n!!!Exiting the Program!!!!");
			System.exit(0);
		}
		if(counter==1&&givenValues.keySet().size()< 1||givenValues.keySet().size()>4) {/**Validating the condition 'the word "given" is followed by one to four arguments'*/
			System.out.println("The number of given statements should be between 1 - 4.\n!!!Exiting the Program!!!!");
			System.exit(0);
		}
		//System.out.println("here");
		conditions.putAll(givenValues);/**Creating numerator/conditions: when the given string is present, numerator=conditions before given  + conditions after given*/
		//generateRemainingPossibleValues();
		conditions.putAll(generateRemainingPossibleValues(conditions));
		givenValues.putAll(generateRemainingPossibleValues(givenValues));
		//System.out.println(conditions+"---------"+givenValues);
		double calcProb1=0.0,denomVal=0.0;
		/**Computing the numerator value/the value when no given is present.*/
		for(int b=0;b<conditions.get('B').size();b++) {
			for(int e=0;e<conditions.get('E').size();e++) {
				for(int a=0;a<conditions.get('A').size();a++) {
					for(int j=0;j<conditions.get('J').size();j++) {
						for(int m=0;m<conditions.get('M').size();m++) {
							//System.out.println(conditions.get('B').get(i)+"--"+conditions.get('E').get(j)+"--"+conditions.get('A').get(k)+"--"+conditions.get('J').get(l)+"--"+conditions.get('M').get(m));
							calcProb1+=computeProbability(conditions.get('B').get(b),conditions.get('E').get(e),conditions.get('A').get(a),conditions.get('J').get(j),conditions.get('M').get(m)); /**Calling the different possible combination of possible values of the variables B,E,A,J,M*/
						}
					}
				}
			}

		}
		//System.out.println(calcProb1);
		//System.out.println("-------------------------------");
		if (counter==1) {
			/**Given is present so calculating the  denominator*/
			for(int i=0;i<givenValues.get('B').size();i++) {
				for(int j=0;j<givenValues.get('E').size();j++) {
					for(int k=0;k<givenValues.get('A').size();k++) {
						for(int l=0;l<givenValues.get('J').size();l++) {
							for(int m=0;m<givenValues.get('M').size();m++) {
								//System.out.println(givenValues.get('B').get(i)+"--"+givenValues.get('E').get(j)+"--"+givenValues.get('A').get(k)+"--"+givenValues.get('J').get(l)+"--"+givenValues.get('M').get(m));
								denomVal+=computeProbability(givenValues.get('B').get(i),givenValues.get('E').get(j),givenValues.get('A').get(k),givenValues.get('J').get(l),givenValues.get('M').get(m));/**Calling the different possible combination of possible values of the variables B,E,A,J,M*/
							}
						}
					}
				}
			}
			//System.out.println(denomVal);
			calcProb1=calcProb1/denomVal;/**Calculating the probability when given is present*/

		}

		System.out.print("Probability of the statement ' ");
		for(int s=0;s<args.length;s++) {
			System.out.print(args[s]+" ");
		}
		//System.out.printf("' = "+calcProb1);
		System.out.printf("' = %.20f\n", calcProb1);
	//	System.out.println(computeProbability('F', 'F', 'F', 'F', 'F'));
	}

	/**
	 * @author Niya Jaison | UTA ID : 1001562701 
	 * Input :  Character - The T/F value for each of the variable 
	 * Output :  Double - The computed probability depending upon the input arguments
	 * Function: Calculate the probability based on the values of B,E,A,J,M.
	 * */

	public static double computeProbability(char burglary,char earthquake,char alarm,char johnCall,char maryCall) {
		double computedProbability=0.0; 
		double burglaryVal=0.0,earthquakeVal=0.0,alarmVal=0.0,johnCallVal=0.0,maryCallVal=0.0;
		/**Get the probability values from the Bayesian network for B=T,E=T,A=T | arguent burglary, argument earthquake ; J=T | argument alarm ; M=T |argument alarm*/
		burglaryVal=burglaryTT.get("B_T");
		earthquakeVal=earthquakeTT.get("E_T");
		alarmVal=alarmTT.get("A_T|B_"+burglary+",E_"+earthquake);
		johnCallVal=johnCallTT.get("J_T|A_"+alarm);
		maryCallVal=maryCallTT.get("M_T|A_"+alarm);
		if(burglary=='F') burglaryVal=1.00-burglaryVal;/**If burglary =f then we do 1 - P(B=t)*/
		if(earthquake=='F') earthquakeVal=1.00-earthquakeVal;/**If earthquake =f then we do 1 - P(E=t)*/
		if(alarm=='F') alarmVal=1.00-alarmVal;	/**If alarm =f then we do 1 - P(A=t)*/	
		if(johnCall=='F') johnCallVal=1.00-johnCallVal;/**If johnCall =f then we do 1 - P(J=t)*/
		if(maryCall=='F') maryCallVal=1.00-maryCallVal;/**If maryCall =f then we do 1 - P(M=t)*/
		computedProbability=burglaryVal * earthquakeVal* alarmVal *johnCallVal *maryCallVal;/**compute the probability as the product of each terms probability.*/
		return computedProbability;
	}


	/**
	 * @author Niya Jaison | UTA ID : 1001562701 
	 * Input :  Map<Character, ArrayList<Character>> : The map of numerator or denominator
	 * Output :  Map<Character, ArrayList<Character>> : The map containing the missing values and its corresponding truth value. 
	 * Function: Generating the missing variables for the statement validation.
	 * */
	public static Map<Character, ArrayList<Character>> generateRemainingPossibleValues(Map<Character, ArrayList<Character>> passedMap){
		Map<Character, ArrayList<Character>> possibleVal=new HashMap<Character, ArrayList<Character>>();
		ArrayList<Character> possibleBool;
		if(!(passedMap.containsKey('B'))){/**Check if the input does not a key 'B' (ie, no Bt or Bf was provided as input) */
			possibleBool= new ArrayList<Character>();
			possibleBool.add('T');
			possibleBool.add('F');
			possibleVal.put('B', possibleBool);/**If so add B to the Map with truth values set as T and F */
		}
		if(!(passedMap.containsKey('E'))){/**Check if the input does not a key 'E' (ie, no Ef or Et was provided as input) */
			possibleBool= new ArrayList<Character>();
			possibleBool.add('T');
			possibleBool.add('F');
			possibleVal.put('E', possibleBool);/**If so add E to the Map with truth values set as T and F */
		}
		if(!(passedMap.containsKey('A'))){/**Check if the input does not a key 'A' (ie, no At or Af was provided as input) */
			possibleBool= new ArrayList<Character>();
			possibleBool.add('T');
			possibleBool.add('F');
			possibleVal.put('A', possibleBool);/**If so add A to the Map with truth values set as T and F */
		}
		if(!(passedMap.containsKey('J'))){/**Check if the input does not a key 'J' (ie, no Jt or Jf was provided as input) */
			possibleBool= new ArrayList<Character>();
			possibleBool.add('T');
			possibleBool.add('F');
			possibleVal.put('J', possibleBool);/**If so add J to the Map with truth values set as T and F */
		}
		if(!(passedMap.containsKey('M'))){/**Check if the input does not a key 'M' (ie, no Mt or Mf was provided as input) */
			possibleBool= new ArrayList<Character>();
			possibleBool.add('T');
			possibleBool.add('F');
			possibleVal.put('M', possibleBool);/**If so add M to the Map with truth values set as T and F */
		}
		return possibleVal;

	}
	/**
	 * @author Niya Jaison | UTA ID : 1001562701 
	 * Input :  void
	 * Output :  void 
	 * Function: Populating the Map with the values given in the Bayesian network diagram.
	 * */

	public static void loadValues() {
		burglaryTT.put("B_T", 0.001);
		earthquakeTT.put("E_T", .002);
		/**TT values of Alarm*/
		alarmTT.put("A_T|B_T,E_T", 0.95);
		alarmTT.put("A_T|B_T,E_F", 0.94);
		alarmTT.put("A_T|B_F,E_T", 0.29);
		alarmTT.put("A_T|B_F,E_F", 0.001);
		/**TT values of JohnCalls*/
		johnCallTT.put("J_T|A_T", 0.90);		
		johnCallTT.put("J_T|A_F", 0.05);
		/**TT values of MaryCall*/
		maryCallTT.put("M_T|A_T", 0.70);		
		maryCallTT.put("M_T|A_F", 0.01);

	}
}
