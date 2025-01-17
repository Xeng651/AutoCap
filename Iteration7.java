import java.awt.Color;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hslf.usermodel.HSLFLine;
import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTableCell;
import org.apache.poi.hslf.usermodel.HSLFTextBox;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.sl.usermodel.TableCell.BorderEdge;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.VerticalAlignment;

public class Iteration7 {
	
	/*
	Notes for default table size of 12x16:
		Good table size:
		row_width = 30
		row_height = 30
		place in middle of power-point coordinates:
		tableMoveX = 140
		tableMoveY = 130
	*/
	
	//side_label_width should stay at 30 at all times in order to not overlap with table
	//or else table X and Y coordinates will have to be adjusted.
	private final static int tableMoveX = 115; 
	private final static int tableMoveY = 105; 
	private final static int table_width = 30;
	private final static int table_height = 30;
	private final static int side_label_width = 30;
	
	private final static double table_fontSize = 15.0;
	private final static String table_font = "Arial";
	private final static String content_font = "Arial";
	
	private int startID = callStartID();
	private int endID = callEndID();
	
	public int getStartID() {
		return startID;
	}
	
	public int getEndID() {
		return endID;
	}
	
	public int callStartID() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the starting ID: ");
		return scan.nextInt();
	}
	
	public int callEndID() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the ending ID: ");
		return scan.nextInt();
	}
	
	public static void setBorders(HSLFTableCell cell) {
		cell.setBorderColor(BorderEdge.bottom, Color.black);
		cell.setBorderColor(BorderEdge.top, Color.black);
		cell.setBorderColor(BorderEdge.right, Color.black);
		cell.setBorderColor(BorderEdge.left, Color.black);
	}
	
	/*
	public static void changeBackground(HSLFSlideShow ppt, HSLFSlide slide) throws IOException {
		slide.setFollowMasterBackground(false);
		HSLFFill fill = slide.getBackground().getFill();
		HSLFPictureData pd = ppt.addPicture(new File("white.png"), HSLFPictureData.PictureType.PNG);
		fill.setFillType(2);
		fill.setPictureData(pd);
	}
	*/
	
	/*
	public static void createFooter(HSLFSlide slide, String name) {
		HSLFTextBox footer = slide.createTextBox();
		HSLFTextParagraph p = footer.getTextParagraphs().get(0);
		p.setTextAlign(TextAlign.CENTER);
		footer.setFillColor(Color.green);
		HSLFTextRun r = p.getTextRuns().get(0);
		r.setText("www.telugupuzzles.com\nusername: " + name);
		r.setFontFamily(content_font);
		r.setFontSize(18.);
		footer.setAnchor(new Rectangle(0,490,720,50));
	}
	*/
	
	public static void createTitle(HSLFSlide slide, String title_name, int width, int height) {
		HSLFTextBox title = slide.createTextBox();
		HSLFTextParagraph p = title.getTextParagraphs().get(0);
		p.setTextAlign(TextAlign.CENTER);
		HSLFTextRun r = p.getTextRuns().get(0);
		r.setBold(true);
		r.setFontColor(Color.black);
		r.setText(title_name);
		r.setFontFamily(content_font);
		r.setFontSize(35.);
		title.setAnchor(new Rectangle(270,5,width,height));
	}
	
	public static void createSlideNum(HSLFSlide slide, int slide_num, int width, int height) {
		HSLFTextBox slide_number = slide.createTextBox();
		HSLFTextParagraph p = slide_number.getTextParagraphs().get(0);
		p.setTextAlign(TextAlign.CENTER);
		slide_number.setFillColor(Color.green);
		HSLFTextRun r = p.getTextRuns().get(0);
		r.setText("" + slide_num + "");
		r.setFontFamily(content_font);
		r.setFontSize(30.);
		slide_number.setAnchor(new Rectangle(221,6,width,height));
	}
	
	public static void createLine(HSLFSlide slide, int x, int y, int width, int height) {
		HSLFLine line = new HSLFLine();
		line.setAnchor(new Rectangle(x,y,width,height));
		line.setLineColor(Color.black);
		slide.addShape(line);
	}
	
	public static void createPic(HSLFSlideShow ppt, HSLFSlide slide) throws IOException {
		byte[] picture = IOUtils.toByteArray(new FileInputStream(new File("logo.png")));
		HSLFPictureData pd = ppt.addPicture(picture, HSLFPictureData.PictureType.PNG);
		HSLFPictureShape pic_shape = slide.createPicture(pd); 
		pic_shape.setAnchor(new Rectangle(0, 0, 174, 65));
	}
	
	public static void getLabels(HSLFSlide slide, int num_row, int num_column) {
		String[] top_label = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		String[] side_label = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "25", "26" };
		
		//create labels for 1st slide
		HSLFTable top_row = slide.createTable(1, num_column);
		HSLFTable side_row = slide.createTable(num_row, 1);
		
		for (int i = 0; i < num_row; i++) {
			//side column labels for slide 1
			HSLFTableCell side_cell = side_row.getCell(i, 0);
			side_cell.setText(side_label[i]);
			setBorders(side_cell);
			HSLFTextRun rts1 = side_cell.getTextParagraphs().get(0).getTextRuns().get(0);
			rts1.setFontFamily(table_font);
			rts1.setFontSize(table_fontSize - 5); //labels' font size are 5 less than table font size
			rts1.setBold(true);
			side_cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			side_cell.setHorizontalCentered(true);
			
			for (int j = 0; j < num_column; j++) {
				//top row labels for slide 1
				HSLFTableCell top_cell = top_row.getCell(0, j);
				top_cell.setText(top_label[j]);
				setBorders(top_cell);
				HSLFTextRun rt2s1 = top_cell.getTextParagraphs().get(0).getTextRuns().get(0);
				rt2s1.setFontFamily(table_font);
				rt2s1.setFontSize(table_fontSize - 5);
				rt2s1.setBold(true);
				top_cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
				top_cell.setHorizontalCentered(true);
			}
		}
		
		for (int i = 0; i < num_column; i++) {
			top_row.setColumnWidth(i, table_width);
			side_row.setColumnWidth(0, side_label_width);
		}
		
		for (int i = 0; i < num_row; i++) {
			side_row.setRowHeight(i, table_height);
			top_row.setRowHeight(0, 10);
		}
		
		top_row.moveTo(tableMoveX, tableMoveY - 20); // y - 20 to match table
		side_row.moveTo(tableMoveX - 30, tableMoveY); // x - 30 to match table
	}
	
	public static char[][] genGrid(HSLFTable table, int num_row, int num_column, char[] phraseChars1, boolean solution, String filler) {
		Random rand = new Random();
		
		//String values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		char[][] grid = new char[num_row][num_column];
		
		int size = filler.length();
		
		for (int i = 0; i < num_row; i++) {		
		    for (int j = 0; j < num_column; j++) {
		    	grid[i][j] = filler.charAt(rand.nextInt(size));
		    }
		}
		
		char[] phraseChars = phraseChars1; 
		int[][] locations = chooseLocations(num_row, num_column, phraseChars);
		
		for (int i = 0; i < phraseChars.length; i++) {
			int x = locations[i][0]; 
			int y = locations[i][1];
			grid[x][y] = phraseChars[i];
			String char_string = String.valueOf(grid[x][y]);
			HSLFTableCell cell = table.getCell(x, y);
			cell.setText(char_string);
			if (solution == true) {
				cell.setFillColor(Color.green); //set solution to color green 
			}
		}
		
		return grid;
	}
	
	public static int[][] chooseLocations(int num_row, int num_column, char[] phraseChars) {
		boolean legitimatePlacement = false;
		boolean startOver = true;
		Random rand = new Random();
		int[][] locations = new int[1][2];

		//continue until a full set of legitimate placements have been found
		while (startOver) {
			startOver = false;

			locations[0][0] = (int)Math.floor(rand.nextDouble() * num_row);
			locations[0][1] = (int)Math.floor(rand.nextDouble() * num_column);

			//choose random locations for the other characters such that no character touches an earlier character
			for (int i = 1; i < phraseChars.length; i++) { 
				int[] newLocation = {};
				int placementOptions = 8;
				ArrayList<Object> badPlacements = new ArrayList<Object>();

				while (!legitimatePlacement && !startOver) {
					//pick a random location for the next character
					int placement = (int)Math.floor(rand.nextDouble() * placementOptions);

					//adjust placement based on which placements have been determined to be bad
					for (int j = 0; j < badPlacements.size(); j++) {
						if (j <= placement) {
							if (Boolean.parseBoolean(badPlacements.get(j).toString()) == true) {
								placement += 1;
							}
						}
					}

					//the location in vertical and horizontal coordinates of the grid
					newLocation = new int[2];
					if (placement < 8) {
						
						switch (placement) {
							case 0:
								newLocation[0] = locations[i - 1][0] - 1;
								newLocation[1] = locations[i - 1][1] - 1;
								break;
							case 1:
								newLocation[0] = locations[i - 1][0] - 1;
								newLocation[1] = locations[i - 1][1];
								break;
							case 2:
								newLocation[0] = locations[i - 1][0] - 1;
								newLocation[1] = locations[i - 1][1] + 1;
								break;
							case 3:
								newLocation[0] = locations[i - 1][0];
								newLocation[1] = locations[i - 1][1] + 1;
								break;
							case 4:
								newLocation[0] = locations[i - 1][0] + 1;
								newLocation[1] = locations[i - 1][1] + 1;
								break;
							case 5:
								newLocation[0] = locations[i - 1][0] + 1;
								newLocation[1] = locations[i - 1][1];
								break;
							case 6:
								newLocation[0] = locations[i - 1][0] + 1;
								newLocation[1] = locations[i - 1][1] - 1;
								break;
							case 7:
								newLocation[0] = locations[i - 1][0];
								newLocation[1] = locations[i - 1][1] - 1;
								break;
						}
						//check if the location chosen is legitimate (is not outside the grid or touching a previous character in the phrase)
						legitimatePlacement = legitimate(locations, newLocation, num_row, num_column);
					}
					else {
						startOver = true;
					}

					//if the placement is not legitimate, record it as bad
					if (!legitimatePlacement) {
						if (badPlacements.size() < (placement + 1)) {
							int n = (placement + 1) - badPlacements.size();
							for (int r = 0; r < n + 1; r++) {
								badPlacements.add("false");
							}
						}

						badPlacements.set(placement, true);
						placementOptions -= 1;
					}
				}

				//if all possible locations have been checked and none of them are legitimate, start over
				if (startOver) {
					locations = new int[1][2];
					break;
				}

				//when a legitimate placement is found, add it to the locations list
				if (locations.length <= i) {
					 locations = ResizeArray(locations);
				}

				locations[i][0] = newLocation[0];
				locations[i][1] = newLocation[1];
				legitimatePlacement = false;
			}
		}

		return locations;
	}
	
	
	public static boolean legitimate(int[][] locations, int[] newLocation, int num_row, int num_column) {
		//check if new location out of bounds
		if (newLocation[0] < 0 || newLocation[0] >= num_row || newLocation[1] < 0 || newLocation[1] >= num_column) {
			return false;
		}

		//check if new location touches an old location
		for (int i = 0; i < locations.length - 1; i++) {
			//the new location touches an old location if it is within the 3x3 square surrounding the old location
			if (newLocation[0] <= (locations[i][0] + 1) && newLocation[0] >= (locations[i][0] - 1) && newLocation[1] <= (locations[i][1] + 1) && newLocation[1] >= (locations[i][1] - 1)) {
				return false;
			}
		}

		//otherwise, placement is legitimate
		return true;
	}
	
	public static int[][] ResizeArray(int[][] location) {
		int[][] new_loc = new int[location.length + 1][2];

		for (int i = 0; i < new_loc.length; i++) {
			new_loc[i][0] = 0;
			new_loc[i][1] = 0;
		}

		for (int j = 0; j < location.length; j++) {
			new_loc[j][0] = location[j][0];
			new_loc[j][1] = location[j][1];
		}
		return new_loc;
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		File f = new File("Puzzle.ppt");
		
		ArrayList<String> list_quotes = quotes_DB.connect_to_db();
		
		API api = new API();
		
		ArrayList<String> list_quotes1 = api.parseLogicalChars(list_quotes);
		String[] quote_array = list_quotes1.toArray(new String[0]); //turn ArrayList into Array of all the strings
		
		ArrayList<String> filler_array1 = api.getFillers(list_quotes);
		String[] filler_array = filler_array1.toArray(new String[0]);
		
		int no_solution_slide = 1;
		int solution_slide = 1;
		
		int num_column = 18; //width of 18
		int num_row = 14; //height of 14
		
		ArrayList<char[][]> puzzles = new ArrayList<char[][]>();
		
		//create power-point
		HSLFSlideShow ppt = new HSLFSlideShow();
		
		//mass generate slides for a quote starting at index 0
		for (int index = 0; index < quote_array.length; index++) {
			
			HSLFSlide slide = ppt.createSlide();
			String title_name = "Puzzle Solution";
			createTitle(slide, title_name, 320, 60); //create template for slide1: puzzle no solution
			//createFooter(slide, name);
			createPic(ppt, slide); //add logo picture
		
			HSLFTable table = slide.createTable(num_row, num_column); //create a table of 12 rows and 16 columns
			getLabels(slide, num_row, num_column); //create labels for slide1
			
			char[] phraseChars = quote_array[index].toCharArray(); 
			char grid[][] = genGrid(table, num_row, num_column, phraseChars, true, filler_array[index]); //call method to generate Grid
		
			for(int i = 0; i < num_column; i++) {
				for(int j = 0; j < num_row; j++) {
					//writes values from puzzle into tables
					String char_string = String.valueOf(grid[j][i]);
					HSLFTableCell cell1 = table.getCell(j, i);
					cell1.setText(char_string);
				
					//formats each cell on slide 1
					setBorders(cell1);
					HSLFTextRun rt1 = cell1.getTextParagraphs().get(0).getTextRuns().get(0);
					rt1.setFontFamily("Arial");
					rt1.setFontSize(10.);
					cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
					cell1.setHorizontalCentered(true);
				}
			}
		
			for (int i = 0; i < num_column; i++) {
				table.setColumnWidth(i, table_width);
			}
		
			for (int i = 0; i < num_row; i++) {
				table.setRowHeight(i, table_height);
			}
		
			table.moveTo(tableMoveX, tableMoveY);
			
			if (no_solution_slide >= 100) {
				createSlideNum(slide, no_solution_slide, 65, 49);
				
				//create text box lines   
				createLine(slide, 220, 5, 65, 0); //top line
				createLine(slide, 285, 5, 0, 50); //right line
				createLine(slide, 220, 55, 65, 0); //bottom line
				createLine(slide, 220, 5, 0, 50); //left line
			}
			else {
				createSlideNum(slide, no_solution_slide, 49, 49);
				
				//create text box lines   
				createLine(slide, 220, 5, 50, 0); //top line
				createLine(slide, 270, 5, 0, 50); //right line
				createLine(slide, 220, 55, 50, 0); //bottom line
				createLine(slide, 220, 5, 0, 50); //left line
			}
			
			no_solution_slide = no_solution_slide + 1;
			puzzles.add(grid);
		
		}
		
		for (int index = 0; index < quote_array.length; index++) {
			
			HSLFSlide slide2 = ppt.createSlide();
			String title_name = "Puzzle";
			createTitle(slide2, title_name, 200, 60); //create template for slide2: puzzle solution
			//createFooter(slide, name);
			createPic(ppt, slide2); //add logo picture

			HSLFTable table2 = slide2.createTable(num_row, num_column); //create a table of n rows and n columns
			getLabels(slide2, num_row, num_column); //create labels for slide2
			
			char grid[][] = puzzles.get(index);
		
			for(int i = 0; i < num_column; i++) {
				for(int j = 0; j < num_row; j++) {
					//writes values from puzzle into tables
					String char_string = String.valueOf(grid[j][i]);
					HSLFTableCell cell1 = table2.getCell(j, i);
					cell1.setText(char_string);
				
					//formats each cell on slide 2
					setBorders(cell1);
					HSLFTextRun rt1 = cell1.getTextParagraphs().get(0).getTextRuns().get(0);
					rt1.setFontFamily("Arial");
					rt1.setFontSize(10.);
					cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
					cell1.setHorizontalCentered(true);
				}
			}
		
			for (int i = 0; i < num_column; i++) {
				table2.setColumnWidth(i, table_width);
			}
	
			for (int i = 0; i < num_row; i++) {
				table2.setRowHeight(i, table_height);
			}
			
			if (solution_slide >= 100) {
				createSlideNum(slide2, solution_slide, 65, 49);
				
				//create text box lines   
				createLine(slide2, 220, 5, 65, 0); //top line
				createLine(slide2, 285, 5, 0, 50); //right line
				createLine(slide2, 220, 55, 65, 0); //bottom line
				createLine(slide2, 220, 5, 0, 50); //left line
			}
			else {
				createSlideNum(slide2, solution_slide, 49, 49);
				
				//create text box lines   
				createLine(slide2, 220, 5, 50, 0); //top line
				createLine(slide2, 270, 5, 0, 50); //right line
				createLine(slide2, 220, 55, 50, 0); //bottom line
				createLine(slide2, 220, 5, 0, 50); //left line
			}
			
			table2.moveTo(tableMoveX, tableMoveY);
			solution_slide = solution_slide + 1; 
			
		}
		
		//reorder slides so solution slides goes on the bottom half
		int size = quote_array.length;
		int o = 1;
		int n = quote_array.length + 1;
		for (int index = 0; index < size; index++) {
			ppt.reorderSlide(o, n);
			o++;
			n++;
		}
	
		FileOutputStream out = new FileOutputStream(f);
		ppt.write(out);
		out.close();
		
		System.out.println("Puzzle is created: " + f);
		
		Desktop.getDesktop().browse(f.toURI());
		ppt.close();
	}
}



