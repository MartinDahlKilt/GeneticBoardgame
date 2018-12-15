package org.mdk.BoardGame.Backgammon.Player;

import java.util.Arrays;
import java.util.List;

import org.mdk.BoardGame.Board;
import org.mdk.BoardGame.Backgammon.BackgammonBoard;
import org.mdk.BoardGame.Backgammon.BackgammonRoll;

public class MDK2PlyEvalPlayer extends BackgammonPlayer {
	private double wr[] = { -2.32740071445563,-2.8893526607090703,-3.037017095514873,-0.2854842293711314,-2.7456986021289014,-2.0402114083105944,-6.066721128429623,-4.134061127232825,-2.9043385315730315,-4.024104280626276,
			-0.06933595545635017,-4.395123240732431,-4.323504778226347,-0.7698342475173394,-8.252177890062413,-5.7535148441675155,-7.612445609025603,-9.170098051016055,-6.571863092429562,-0.07441332918029483,
			-12.246951097208928,-8.598787795849208,-2.7238113298833273,-4.992964364076996,-6.421746832429183,-2.303826314289761,-5.796452720582366,-5.000958096237728,-0.6474956369724775,-9.870698340974874,
			-4.680219042279369,-1.9594481604885072,-2.6429790901364303,-0.3190043414070676,-4.86428481641325,-0.8322788848663576,-9.494037877014986,-3.137570274584796,-2.0580351480089942,-4.127421881487584,
			-4.279638978192194,-2.6280355711895313,-4.087468081610747,-1.1471808663442893,-2.902084586391767,-5.292075747461835,-2.591596365906569,-3.5697990336826915,-1.3002019095364068,-3.220955350183786,
			-4.222219374082296,-4.889037396221964,-8.602874326561578,-0.3275936216874012,-0.25209710464217644,-1.9889738601093059,-6.779360870552813,-0.27200177978166995,-9.164062771824517,-5.85294005405833,
			-2.230641501081971,-0.10169361915502924,-7.984462801941921,-4.675992453751281,-3.2492224532549385,-3.76776832895741,-4.1241384961993415,-4.850097340138329,-3.503529399062235,-5.497565159315656,
			-5.7082529698003945,-4.071168408934673,-5.201190094607422,-5.9084493769625315,-0.9527896449916632,-6.296797297151709,-8.109253010283107,-3.071622399013771,-4.951683979114432,-1.2095719976532444,
			-0.8001706884862428,-4.390235025729549,-3.134924111031892,-2.6231952078216163,-7.738087137093292,-2.93792160880683,-6.53348395850303,-3.490825462901349,-5.920851972497577,-1.4130649194410614,
			-3.099186910953155,-3.316937515953284,-3.6315920895798546,-5.543564137975889,-3.6601874368983918,-0.38387630762800523,-1.2927519686564868,-1.9694728443755978,-0.10916729342365247,-0.5101774396856174,
			-4.956379056711452,-1.0431645719551599,-6.0158646481220455,-1.5155102853572493,-0.1474137402271294,-5.957952044890135,-2.329479700388965,-3.4301586198450544,-6.72953726924667,-3.632696732683793,
			-4.065785629984903,-1.9578007843999388,-6.675957881728573,-5.823430366517912,-5.214988735315706,-2.071795964816077,-5.19334251891822,-3.034292446206176,-0.24623719052963638,-9.046527783926543,
			-14.95020265332741,-0.5708537094943686};
	private double wc[] = {-1.6235146087119046,-1.9933264658638965,-3.8653627838066624,-3.054392350712612,-11.842961166525733,-5.868879920128548,-2.377920808889331,-6.396685625316291,-0.6722346474353743,-2.439580310955011,-7.4806381475536545,-1.9839660787625237,-1.7425729154653808,-1.3980637735538712,-1.2962139848588965,-4.061300360623064,-1.7080943160872413,-0.11838069791860525,-1.314113749991921,-7.0721834722559365,-5.314317164509888,-2.649522573237325,-0.5287042715363892,-2.340613485813654,-5.796155421667712,-3.03612919193069,-2.1939639787508165,-2.2564127536799323,-7.59683244210827,-5.0906682264471765,-5.96805495873534,-2.2015011585641275,-0.920898915292312,-1.9455002789932287,-2.4860922796523997,-6.896131746606035,-1.6922703824688659,-0.20937319167016144,-3.4594569905592856,-0.041621931802694155,-6.994660437790982,-1.7891831909712026,-1.3295708897355931,-3.5830867708402514,-6.1168301339778495,-6.452867600838028,-1.5746402739772005,-1.1566479074928897,-9.489701322278467,-2.6666481012054937,-5.227840347818759,-2.0660020176094895,-0.632810394310289,-3.878509481373318,-8.000135651030291,-6.090262356726776,-1.954947023795359,-1.4041801430137548,-0.5986896221219286,-5.687188409801313,-6.818407934689431,-1.6043997336116105,-6.471215041086672,-0.3475755139399433,-1.554552775351541,-6.214223578279626,-1.9175687490267526,-1.8144365941145715,-0.6144867297848261,-6.952547248997016,-6.563799083446946,-2.0569824673273516,-1.878565971350479,-2.279600528305823,-2.4451801395797133,-11.320753298488414,-2.4881379583758583,-2.4908486079185246,-3.9020392044498373,-6.77654955305706,-8.266026922717863,-2.9156818576517005,-2.384982945328969,-0.7849114909298998,-5.6207941995246395,-4.901699981359307,-2.8599108622395777,-0.9897486824404368,-1.8680133007166313,-7.081815600512327,-3.6554149256292208,-8.37037236879555,-0.134181940872436,-0.780019761430002,-6.130144089287992,-10.01960480791101,-8.750078556402283,-0.607355244523617,-0.9055709529640726,-6.259543491052976,-10.300904851692938,-8.805760972575062,-0.09743515470100364,-2.623884558109559,-7.026451868055684,-4.919143226843773,-4.434014632656107,-0.06659976856840003,-2.713996175928343,-12.078932224536597,-3.540901581254876,-5.194268771626645,-2.3050062205135355,-3.5934401902811506,-14.00109549755792,-6.39920963098546,-8.489979023061462,-5.185581892688497,-4.607475777315428,-15.472828905145699,-0.03239250614753956,-9.48780939211547}; 
	
	private double x[];
	public MDK2PlyEvalPlayer() {
		x = new double[122];
	}
	
	public MDK2PlyEvalPlayer(double[] weights) {
		x = new double[122];
		for(int idx=0; idx < 122; idx++) {
			wr[idx]=weights[idx];
		}
		for(int idx=122; idx < 244; idx++) {
			wc[idx-122]=weights[idx];
		}
	}
	
	private double pubeval(BackgammonBoard b) {
	        /* Backgammon move-selection evaluation function
	           for benchmark comparisons.  Computes a linear
	           evaluation function:  Score = W * X, where X is
	           an input vector encoding the board state (using
	           a raw encoding of the number of men at each location),
	           and W is a weight vector.  Separate weight vectors
	           are used for racing positions and contact positions.
	           Makes lots of obvious mistakes, but provides a
	           decent level of play for benchmarking purposes. */

	        /* Provided as a public service to the backgammon
	           programming community by Gerry Tesauro, IBM Research.
	           (e-mail: tesauro@watson.ibm.com)                     */

	        /* The following inputs are needed for this routine:

	           race   is an integer variable which should be set
	           based on the INITIAL position BEFORE the move.
	           Set race=1 if the position is a race (i.e. no contact)
	           and 0 if the position is a contact position.

	           pos[]  is an integer array of dimension 28 which
	           should represent a legal final board state after
	           the move. Elements 1-24 correspond to board locations
	           1-24 from computer's point of view, i.e. computer's
	           men move in the negative direction from 24 to 1, and
	           opponent's men move in the positive direction from
	           1 to 24. Computer's men are represented by positive
	           integers, and opponent's men are represented by negative
	           integers. Element 25 represents computer's men on the
	           bar (positive integer), and element 0 represents opponent's
	           men on the bar (negative integer). Element 26 represents
	           computer's men off the board (positive integer), and
	           element 27 represents opponent's men off the board
	           (negative integer).                                  */

	        /* Also, be sure to call rdwts() at the start of your
	           program to read in the weight values. Happy hacking] */

	        int i;
	        double score;

	        if(b.getNumMenOff(Board.Player.PLAYER)==15) return(99999999.);
	        /* all men off, best possible move */

	        setx(b); /* sets input array x[] */
	        score = 0.0;
	        if(b.isRace()) {  /* use race weights */
	            for(i=0;i<122;++i) score += wr[i]*x[i];
	        } else {  /* use contact weights */
	            for(i=0;i<122;++i) score += wc[i]*x[i];
	        }
	        return(score);
	}
	private void setx(BackgammonBoard b) {
	        /* sets input vector x[] given board position pos[] */
	        int j, n;
	        /* initialize */
	        Arrays.fill(x, 0);

	        /* first encode board locations 24-1 */
	        for(j=1;j<=24;++j) {
	        	int jm1 = j - 1;
	            n = b.get(25-j);
	            if(n!=0) {
	                if(n==-1) x[5*jm1+0] = 1.0;
	                if(n==1) x[5*jm1+1] = 1.0;
	                if(n>=2) x[5*jm1+2] = 1.0;
	                if(n==3) x[5*jm1+3] = 1.0;
	                if(n>=4) x[5*jm1+4] = (float)(n-3)/2.0;
	            }
	        }
	        /* encode opponent barmen */
	        x[120] = -(float)(b.getNumBarMen(Board.Player.OPPONENT))/2.0;
	        /* encode computer's menoff */
	        x[121] = (float)(b.getNumMenOff(Board.Player.PLAYER))/15.0;
	}

	private double minimax(BackgammonBoard board, Board.Player player, int targetDepth, int depth) {
		if(depth==targetDepth) {
			int count = 0;
			double sum = 0;
			for(int d1 = 1; d1 <=6; d1++) {
				for(int d2 = d1; d2 <=6; d2++) {
					BackgammonRoll r = new BackgammonRoll(d1,d2);
					List<BackgammonBoard> configs = board.getBoards(r);
					count++;					
					for(BackgammonBoard b: configs) {
						sum += pubeval(b);
					}
				}
			}
			double avg = sum / (double)count; 
//			System.out.println("Avg:"+avg+" "+player.name());
			return avg;
		}
		//double score = player==Board.Player.PLAYER?Double.POSITIVE_INFINITY:Double.NEGATIVE_INFINITY;
		double sum = 0;
		int count = 0;
		for(int d1 = 1; d1 <=6; d1++) {
			for(int d2 = d1; d2 <=6; d2++) {
				BackgammonRoll r = new BackgammonRoll(d1,d2);
				List<BackgammonBoard> configs = board.getBoards(r);
				BackgammonBoard m = board;
				double best = Double.NEGATIVE_INFINITY;
				for(BackgammonBoard c: configs) {
					double val = pubeval(c);
					if(val > best) {
						best = val;
						m = c;
					}
				}
				if(player==Board.Player.PLAYER) {
					sum += minimax(m.flip(), Board.Player.OPPONENT, targetDepth, depth+1);
					count++;
//					score = Math.min(score, minimax(m.flip(), Board.Player.OPPONENT, targetDepth, depth+1));
				} else {
					sum += minimax(m.flip(), Board.Player.PLAYER, targetDepth, depth+1);
					count++;
//					score = Math.max(score, minimax(m.flip(), Board.Player.PLAYER, targetDepth, depth+1));
				}
			}
		}
		double avg = sum / count;
//		System.out.println(player.name()+": avg:"+avg);
		return avg;
	}
		
	@Override
	public BackgammonBoard move(BackgammonBoard board, BackgammonRoll roll) {
		BackgammonBoard m = board;
		List<BackgammonBoard> moves = board.getBoards(roll);
		int size = moves.size();
		if(size==0) {
			return board;
		} else if(size==1) {
			return moves.get(0);
		}
		
		double bestScore = Double.POSITIVE_INFINITY;
		for(BackgammonBoard b: moves) {
			double newScore = minimax(b.flip(), Board.Player.OPPONENT, 1, 1);
			if(newScore<bestScore) {
				m = b;
				bestScore = newScore;
			}
		}
//		System.out.println("Worst opponent score: "+bestScore+" moves:"+moves.size());
		return m;
	}
	
	public void setContactWeights(double[] weights) {
		for(int idx = 0; idx < wc.length; idx++) {
			wc[idx]=weights[idx];
		}
	}
	
	public void setRaceWeights(double[] weights) {
		for(int idx = 0; idx < wr.length; idx++) {
			wr[idx]=weights[idx];
		}
	}

	@Override
	public String getName() {
		return "MDKPlayer";
	}
}
