package app1.com;

import java.util.Random;

public class Computation {
	
	
	public static int [][] matrice(int n, int m){
		Random r = new Random();
		int c, d, e, f, g, sum = 0;
		
		
		  int first[][] = new int [n][m];
	        for(c = 0; c < n; c++)
	        	for(d = 0; d < m; d++)
	        		first[c][d] = r.nextInt();

	        int second[][] = new int [n][m];
	        int multiply[][] = new int [n][m];
	        for(e = 0; e < n; e++)
	        	for(f = 0; f < m; f++)
	        		second[e][f] = r.nextInt(); 
	        
	        for ( c = 0 ; c < n ; c++ )
	         {
	            for ( d = 0 ; d < m ; d++ )
	            {   
	               for ( g = 0 ; g < n ; g++ )
	               {
	                  sum = sum + first[c][g]*second[g][d];
	               }
	              
	               multiply[c][d] = sum;
	               sum = 0;
	            }
	         }
	        return multiply;
	}
	
	public static String toString(int [][] M)
	{
		 String separator = ", ";
		    StringBuffer result = new StringBuffer();

		    // iterate over the first dimension
		    for (int i = 0; i < M.length; i++) {
		        // iterate over the second dimension
		        for(int j = 0; j < M[i].length; j++){
		            result.append(M[i][j]);
		            result.append(separator);
		        }
		        // remove the last separator
		        result.setLength(result.length() - separator.length());
		        // add a line break.
		        result.append("\n");
		    }
		    return result.toString();
	}  
}
