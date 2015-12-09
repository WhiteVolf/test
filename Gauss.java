package ua.javastart.module4;

import java.io.FileNotFoundException;
import java.io.*;

/**
 * Gaussian algoritm for sloving systems of linear equatons
 * 
 * @author Aleksandr Gorkusha
 *
 */
public class Gauss {

	// Need change to file name on destination PC
	private static final String FILE_NAME = "D:\\eclipse_java\\JavaStart\\src\\ua\\javastart\\module4\\equation2.txt";

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		//Read linear equations system from file 
		String textFromFile = read(FILE_NAME);
		
		//Resolve linear equation system
		parseAndResolve(textFromFile);
	}

	public static String read(String fileName) throws FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		exists(fileName);

		try {
			BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			try {
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append('\n');
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return sb.toString();
	}

	private static void exists(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getName());
		}
	}

	private static String removeSpaces(String str) {
		while (str.contains(" ")) {
			String replace = str.replace(" ", "");
			str = replace;
		}

		return str;
	}

	private static void parseAndResolve(String str) {
		
		//Print linear system
		System.out.println("Linear equation system:\n"+ str);
		
		String text = removeSpaces(str);
		
		String[] params = { "x", "y", "z" };
		String[] sep = text.split("\n");
		int rows = sep.length;
		int[][] matrix = new int[3][4];

		for (int i = 0; i < rows; i++) {
			String[] eq = sep[i].split("=");
			matrix[i][3] = Integer.parseInt(eq[1]);
			for (int g = 0; g < params.length; g++) {
				if (eq[0].contains(params[g])) {
					int pos = eq[0].indexOf(params[g]);
					if (pos == 0) {
						matrix[i][g] = 1;
					} else {
						int coef = 1;
						String num = "";
						for (int t = pos - 1; t >= 0; t--) {
							switch (eq[0].charAt(t)) {
							case '+':
								coef = 1;
								t = -1;
								break;
							case '-':
								coef = -1;
								t = -1;
								break;
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								num = eq[0].charAt(t) + num;
								break;
							}
							int nn;
							if (num.equals("")) {
								nn = 1;
							} else {
								nn = Integer.parseInt(num);
							}
							matrix[i][g] = coef * nn;
						}
					}
				} else {
					matrix[i][g] = 0;
				}
			}
		}
		
		System.out.println("Matrix: ");
		printMatrix(matrix);

		double[][] next = new double[3][4];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				next[i][j] = (double) matrix[i][j];
			}
		}

		for (int i = 1; i < 3; i++) {
			double co = -(next[0][0] / next[i][0]);
			for (int j = 0; j < 4; j++) {
				next[i][j] *= co;
				next[i][j] += next[0][j];
			}
		}


		double co = -(next[1][1] / next[2][1]);
		for (int j = 0; j < 4; j++) {
			next[2][j] *= co;
			next[2][j] += next[1][j];
		}

		//Show triangular matrix
		System.out.println("Triangular matrix: ");
		printMatrix(next);
		
		double z = next[2][3] / next[2][2];
		double y = (next[1][3] - next[1][2] * z) / next[1][1];
		double x = (next[0][3] - next[0][2] * z - next[0][1] * y) / next[0][0];
		
		System.out.println("Solution: ");
		System.out.println("x = " + x + ", y = " + y + ", z = " + z);

	}

	private static void printMatrix(int[][] m) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void printMatrix(double[][] m) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
