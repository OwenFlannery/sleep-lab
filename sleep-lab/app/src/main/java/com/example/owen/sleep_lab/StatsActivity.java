package com.example.owen.sleep_lab;

		import android.support.v7.app.AppCompatActivity;
		import android.os.Bundle;
		import android.util.Log;
		import android.widget.TextView;

		import com.jjoe64.graphview.GraphView;
		import com.jjoe64.graphview.series.DataPoint;
		import com.jjoe64.graphview.series.LineGraphSeries;

		import java.io.File;
		import java.io.FileInputStream;
		import java.io.FileNotFoundException;
		import java.io.IOException;
		import java.io.InputStream;
		import java.util.ArrayList;
		import java.util.Date;
		import java.util.Scanner;
		import java.util.function.DoublePredicate;

		/* jar file needs to be imported for accessing values from excell files http://www.java2s.com/Code/Jar/p/Downloadpoi39jar.htm
		import org.apache.poi.ss.usermodel.Cell;
		import org.apache.poi.ss.usermodel.CellValue;
		import org.apache.poi.ss.usermodel.FormulaEvaluator;
		import org.apache.poi.ss.usermodel.Row;
		import org.apache.poi.xssf.usermodel.*;
		*//////////////////////

public class StatsActivity extends AppCompatActivity
{
	private String TAG = "StatsActivity";
	GraphView graph1, graph2, graph3;
	TextView textView;
	int x, y, i;
	private String[] path;
	private String[] fileName;
	private File[] listFile;
	ArrayList<graph> uploadData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Scanner scanner = new Scanner(System.in);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		graph1 = (GraphView) findViewById(R.id.graph1);
		textView = (TextView) findViewById(R.id.textView);

		LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

		//path ="C:\\Users\\owen\\Desktop\\sleep-lab-master\\sleep-lab\\app\\src\\main\\sampledata\\";
		//readExcel(path);
	}

	/*private void readExcel(String filepath)
	{
		File inputFile = new File(filepath);

		try
		{
			InputStream input = new FileInputStream(inputFile);
			XSSFWorkbook workBook = new XSSFWorkbook(input);
			XSSFSheet sheet = workBook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			FormulaEvaluator formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < rows; i++)
			{
				Row row = sheet.getRow(i);
				int cellCount = row.getPhysicalNumberOfCells();

				int j = 25;
				String value = getCellAssString(row, j, formulaEvaluator);
				sb.append(value + ", ");

			}

		}
		catch(FileNotFoundException e)
		{
			Log.d(TAG, "File not Found");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private String getCellAssString(Row row, int j, FormulaEvaluator formulaEvaluator)
	{
		String value = " ";
		try
		{
			Cell cell = row.getCell(j);
			CellValue cellValue = formulaEvaluator.evaluate(cell);
			double numericValue = cellValue.getNumberValue();

			value = " " + numericValue;
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();

		}
		return value;
	}*/

	private void parseStringBuilder(StringBuilder sb)
	{

		String[] rows = sb.toString().split(":");

		for(int d = 0; d < rows.length; d++)
		{
			String[] columns = rows[d].split(",");

			try
			{
				double x = Double.parseDouble(columns[0]);
				double y = Double.parseDouble(columns[1]);

				uploadData.add(new graph(x, y));
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
			}
		}

	}
}
