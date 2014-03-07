import choco.*;
/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


import choco.ContradictionException;
import choco.Problem;
import choco.integer.IntVar;
import choco.integer.IntDomainVar;

/**
 * Chokodoku
 *
 * This program use to be an example of one usage of the choco-solver. This
 * application aims at easily solving any sudoku problem. All sudoku problem
 * hasn't been tested. If many solutions are possible, this program will not
 * be really accurate.
 *
 * Based on Choco 1.0b003
 *
 * @author Adjanakis (http://code.adjanakis.org/)
 * @version 0.1.0
 */
public class Sodoco {

  /**
   * Number of numbers on one side of the internal square. 3 is the usual value.
   */
  private static int CHOKODOKU_SIZE = 3;

    /**
     * Here is the description of the sudoku problem. Each number represents a value to
     * find or a value to get. The values to find are represented by a 0.
     */
  private static int[] pbDescr = new int[]{
            8,0,4,0,0,0,2,0,9,
            0,0,9,0,0,0,1,0,0,
            1,0,0,3,0,2,0,0,7,
            0,5,0,1,0,4,0,8,0,
            0,0,0,0,3,0,0,0,0,
            0,1,0,7,0,9,0,2,0,
            5,0,0,4,0,3,0,0,8,
            0,0,3,0,0,0,4,0,0,
            4,0,6,0,0,0,3,0,1
    };


    /**
     * The object used to represent the problem. It will store the constraint a the
     * possible value for each value of the sudoku.
     */
  private static Problem myPb = new Problem();

  /**
   * Basic method to launch the program
   * @param args parameters are not used
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * Method used to resolve a sudoku problem. This problem is hard coded into this method.
   */
  public static void launch(){

    //The variables will be stored into a vector
    IntVar[] myPbVars = new IntVar[CHOKODOKU_SIZE*CHOKODOKU_SIZE*CHOKODOKU_SIZE*CHOKODOKU_SIZE];
    System.out.println("size"+CHOKODOKU_SIZE);
    //We attribute the possible value for each value of the problem into our vector.
    for( int i = 0; i < CHOKODOKU_SIZE*CHOKODOKU_SIZE*CHOKODOKU_SIZE*CHOKODOKU_SIZE; i++ ){
      if( pbDescr[i] != 0 ){
          //The value defined by the problem
        myPbVars[i] = myPb.makeEnumIntVar(null,pbDescr[i],pbDescr[i]);
      } else {
          //The value is undefined. It may be on from 1 to the max value
        myPbVars[i] = myPb.makeEnumIntVar(null,1,CHOKODOKU_SIZE*CHOKODOKU_SIZE);
      }
    }

    //Creation of the sudoku constraint
    for( int i = 0; i < 9; i++){
      IntVar[] bufIntVarsRow            = new IntVar[CHOKODOKU_SIZE*CHOKODOKU_SIZE];
      IntVar[] bufIntVarsColumn         = new IntVar[CHOKODOKU_SIZE*CHOKODOKU_SIZE];
      IntVar[] bufIntVarsInternalSquare = new IntVar[CHOKODOKU_SIZE*CHOKODOKU_SIZE];
      for( int j = 0; j < 9; j++){
        bufIntVarsRow[j]   = myPbVars[ i*CHOKODOKU_SIZE*CHOKODOKU_SIZE + j ];
        bufIntVarsColumn[j] = myPbVars[ j*CHOKODOKU_SIZE*CHOKODOKU_SIZE + i ];
        bufIntVarsInternalSquare[j]   = myPbVars[ (i%CHOKODOKU_SIZE)*CHOKODOKU_SIZE +
                                          j%CHOKODOKU_SIZE +
                                          (i/CHOKODOKU_SIZE)*CHOKODOKU_SIZE*CHOKODOKU_SIZE*CHOKODOKU_SIZE +
                                          (j/CHOKODOKU_SIZE)*CHOKODOKU_SIZE*CHOKODOKU_SIZE];
      }
      //All values of a line must be different
      myPb.post(myPb.allDifferent(bufIntVarsRow));

      //All value of a column must be different
      myPb.post(myPb.allDifferent(bufIntVarsColumn));

      //All value of an internal square must be different
      myPb.post(myPb.allDifferent(bufIntVarsInternalSquare));
    }

    try {
        //Have the unique solution of the problem if it exists, or an approximation
      myPb.propagate();
    }
    catch (ContradictionException e) {
        //Solving the problem is not possible
      System.err.println("This problem is over-constrained");
    }
    System.out.println("solve");

    //Display the result found
    for( int i = 0; i < 9; i++){
      for( int j = 0; j < 9; j++){
        System.out.print(" " + ((IntDomainVar) myPbVars[i*9+j]).getVal());
      }
      System.out.println();
    }
  }
}