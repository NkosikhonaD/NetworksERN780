package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package queuingsystem;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * 
 */
public class SimulatorGuiController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Button handle_taskOneButtonClick;
    @FXML private Button goButton;
    @FXML private TextArea taskOneResultsDisplay;
    @FXML private TextField filePathField;
    @FXML private LineChart graph;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // setup a line chart
       // NumberAxis xAxis = new NumberAxis(0,2000,5);
        //xAxis.setLabel("Time in us");
        //NumberAxis yAxis = new NumberAxis(0,20,2);
        //yAxis.setLabel("Number Of Packets");
        
        graph.getXAxis().setLabel("Time in us");
        
        graph.getYAxis().setLabel("Number of Packets");
        
    }    
    
    @FXML
    private void handle_taskOneButtonClick( ActionEvent event )
    {
        TaskOneSimulator task1Sim = new TaskOneSimulator("/home/hltuser/networksLoad.txt");
        
        Results r = task1Sim.runSimulator(); 
        taskOneResultsDisplay.appendText( "Averate queuing detaly = " + r.averageQueueingDelay + "\n" );
        taskOneResultsDisplay.appendText("Arrival Rate = " + r.lambda + "\n");
        taskOneResultsDisplay.appendText( "Average service rate = " + r.mu );
    }
    
    @FXML
    private void handle_goButtonClick(ActionEvent event)
    {
        GraphData graphData = new GraphData(filePathField.getText());
        ArrayList<Point> points = graphData.getGraphData();
        
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Number of Packets in a queue");
        
        for( Point p : points )
        {
            series.getData().add(new XYChart.Data(p.x, p.y));
        }
        
        graph.getData().add(series);
    }
}
