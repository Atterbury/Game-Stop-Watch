/*
James Atterbury
9/23/19
this program is a stop watch to use for 8th grade tours to show something coded
and to auto calculate deductions
*/

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TourWatch extends Application{
    //height and width for stop watch parts (time and control btns)
    final int HEIGHT = 50;
    final int WIDTH = 100;
    
    //time
    int milliseconds = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    
    //count of penalties
    int commandPenalties = 0;
    int conePenalties = 0;
    int boundsPenalties = 0;
    
    //how much to increment for each penalty
    int commandTime = 5;
    int coneTime = 10;
    int boundsTime = 15;
    
    //penalty time
    int penaltyTimeSeconds = 0;
    int penaltyTimeMinutes = 0;
    
    boolean isCounting = false;
    
    public void start(Stage primaryStage){
      //setting up the interface
        HBox controls = new HBox(5); //hBox for the start, stop, and reset btn
        HBox time = new HBox();      //hBox for the time portion
        HBox deduction = new HBox(15); //hBox for the dedutions parts
        VBox dControls = new VBox(5); //vbox for add deduction btns
        VBox dCount = new VBox(5);   //vbox for deducition count lbls
        VBox dTotal = new VBox();    //vbox to hold lable of total penalties
        
        //creating buttons to start stop and reset stopwatch
        Button btnStart = new Button("Start");                  //create btn
        btnStart.setPrefSize(WIDTH, HEIGHT);                    //size btn
        btnStart.setStyle(String.format("-fx-font-size: %dpx;", //size text
                (int)(0.45 * HEIGHT)));
        
        Button btnStop = new Button("Stop");                   //create btn
        btnStop.setPrefSize(WIDTH, HEIGHT);                    //size btn
        btnStop.setStyle(String.format("-fx-font-size: %dpx;", //size text
                (int)(0.45 * HEIGHT)));
        
        Button btnReset = new Button("Reset");                  //create btn
        btnReset.setPrefSize(WIDTH, HEIGHT);                    //size btn
        btnReset.setStyle(String.format("-fx-font-size: %dpx;", //size text
                (int)(0.45 * HEIGHT)));
        
        //placing buttons in box
        controls.getChildren().addAll(btnStart, btnStop, btnReset);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5));
        
        //creating labels to display time
        Label milli = new Label("" + milliseconds);//Creating lbl
        milli.setPrefSize(5, HEIGHT);                            //sizing lbl
        milli.setStyle(String.format("-fx-font-size: %dpx",      //sizing text
                (int)(HEIGHT)));
        
        Label second = new Label(seconds + " .");               //Creating lbl
        second.setPrefSize(WIDTH, HEIGHT);                       //sizing lbl
        second.setStyle(String.format("-fx-font-size: %dpx",     //sizing text
                (int)(HEIGHT)));
        
        Label minute = new Label(minutes + " :");             //Creating lbl
        minute.setPrefSize(WIDTH, HEIGHT);                       //sizing lbl
        minute.setStyle(String.format("-fx-font-size: %dpx",     //sizing text
                (int)(HEIGHT)));
        
        Label hour = new Label(hours + " :");                       //Creating lbl
        hour.setPrefSize(WIDTH, HEIGHT);                       //sizing lbl
        hour.setStyle(String.format("-fx-font-size: %dpx",     //sizing text
                (int)(HEIGHT)));
        
        //putting labels in box
        time.getChildren().addAll(hour, minute, second, milli);
        time.setAlignment(Pos.CENTER);
        time.setPadding(new Insets(5));
        
        //adding deduction buttons
        Label lblAdd = new Label("Add Penality:");          //section title lbl
        Button btnCommand = new Button("Command (+5s)");    //adding cmd btn
        Button btnCone = new Button("Cones (+10s)");        //adding cone btn
        Button btnBounds = new Button("Boundries (+15s)");  //adding bounds btn
        
        //adding to vbox for penalty controls
        dControls.getChildren().addAll(lblAdd, btnCommand, btnCone, btnBounds);
        
        //creating labels for penalties count
        Label lblCount = new Label("Penality Count:");                //creating title
        Label lblCommand = new Label ("Command: " + commandPenalties);//cmd penalties
        lblCommand.setPrefSize(WIDTH*.75 , HEIGHT*.50);               //cmd resize
        
        Label lblCone = new Label ("Cones: " + conePenalties);         //cone penalties
        lblCone.setPrefSize(WIDTH*.75 , HEIGHT*.50);                  //cone resize
        
        Label lblBounds = new Label ("Boundries: " + boundsPenalties);//bound penalties
        lblBounds.setPrefSize(WIDTH*.75 , HEIGHT*.50);                //bound resize
        
        
        //adding to vbox for penalities count
        dCount.getChildren().addAll(lblCount, lblCommand, lblCone, lblBounds);
              
        //making total penalties label
        Label lblTotalTitle = new Label("Total Penalty Time:");             //title
        Label lblTotalTime = new Label("+ " + penaltyTimeMinutes + " : " +  //time added
                penaltyTimeSeconds);
        lblTotalTime.setPrefSize(WIDTH*.75 , HEIGHT*.50);           //sizing lbl
        lblTotalTime.setStyle(String.format("-fx-font-size: %dpx",  //sizing text
                (int)(HEIGHT* .4)));
        Button resetPenalties = new Button("Reset");                //penalty reset
        
        //adding total penalties to total box 
        dTotal.getChildren().addAll(lblTotalTitle, lblTotalTime, resetPenalties);
        
        //adding controls counts and total to penalties
        deduction.getChildren().addAll(dControls, dCount, dTotal);
        deduction.setPadding(new Insets(5));
        deduction.setAlignment(Pos.CENTER);
        
        //creating the pane
        BorderPane pane = new BorderPane();
        pane.setTop(controls); //adding buttons to top of pane
        pane.setCenter(time);  //adding time to middle of pane
        pane.setBottom(deduction);
        
        //setting scene and displaying stage
        Scene scene = new Scene(pane, 500, 300);
        primaryStage.setTitle("StopWatch");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
      //Making the clock function
        //when start is pressed
        btnStart.setOnAction(e -> {
            //saying that the clock is counting
            isCounting = true;
            
            
            //setting up thread to do counting
            Thread t = new Thread(){
                public void run(){
                    //run infinitly
                    for(;;){
                        //check if clock is counting
                        if(isCounting){
                            //if its counting try to sleep thread for a milli
                            try{
                                sleep(100);
                                
                               //adding a mill for every tick of the thread
                                milliseconds++;
                                
                                //if mill = 1 second convert
                                if(milliseconds > 9){
                                    milliseconds = 0;
                                    seconds++;
                                }
                                //if seconds = 1 miunte convert
                                if(seconds > 60){
                                    milliseconds = 0;
                                    seconds = 60;
                                    minutes++;
                                }
                                //if minutes = 1 hour convert
                                if(minutes > 60){
                                    milliseconds = 0;
                                    seconds = 0;
                                    minutes = 0;
                                    hours++;
                                }
                                                                
                                //updating the time on the ui
                                Platform.runLater(() -> {
                                    milli.setText("" + milliseconds);
                                    second.setText(seconds + " .");
                                    minute.setText(minutes + " :");
                                    hour.setText(hours + " :");
                                });
                                
                                
                            }catch(Exception e)   {
                                System.out.print(e);
                            }                             
                        }else{
                            //if clock is not counting break out of loop
                            break;
                        }
                    }
                }
            };
            
            //starting the thread
            t.start();
        });
        
        //whent the stop button is pushed
        btnStop.setOnAction(e -> {
            isCounting = false;
        });
        
        //when the reset button is pushed
        btnReset.setOnAction(e -> {
            isCounting = false;
            //reset the time vars
            milliseconds = 0;
            seconds = 0;
            minutes = 0;
            hours = 0;
            
            //update the time displayed
            milli.setText("" + milliseconds);
            second.setText(seconds + " .");
            minute.setText(minutes + " :");
            hour.setText(hours + " :");
        });
        
        //when the command penalty btn is pushed
        btnCommand.setOnAction(e -> {
            //adding to the command counter
            commandPenalties++;
            
            //adding time for penalty
            penaltyTimeSeconds += commandTime;
            
            //if seconds = 1 minute convert
            if(penaltyTimeSeconds > 60){
                penaltyTimeSeconds %= 60;
                penaltyTimeMinutes++;
            }
            //refreshing the labels
            lblCommand.setText("Command: " + commandPenalties);
            lblTotalTime.setText("+ " + penaltyTimeMinutes + " : " +  
                penaltyTimeSeconds);
        });
        
        //when the cone penalty btn pushed
        btnCone.setOnAction(e -> {
            //adding to the command counter
            conePenalties++;
            
            //adding time for penalty
            penaltyTimeSeconds += coneTime;
            
            //if seconds = 1 minute convert
            if(penaltyTimeSeconds > 60){
                penaltyTimeSeconds %= 60;
                penaltyTimeMinutes++;
            }
            //refreshing the labels
            lblCone.setText("Cones: " + conePenalties);
            lblTotalTime.setText("+ " + penaltyTimeMinutes + " : " +  
                penaltyTimeSeconds);
        });
        
        //when the bounds penalty btn pushed
        btnBounds.setOnAction(e -> {
            //adding to the command counter
            boundsPenalties++;
            
            //adding time for penalty
            penaltyTimeSeconds += coneTime;
            
            //if seconds = 1 minute convert
            if(penaltyTimeSeconds >= 60){
                penaltyTimeSeconds %= 60;
                penaltyTimeMinutes++;
            }
            //refreshing the labels
            lblBounds.setText("Boundries: " + boundsPenalties);
            lblTotalTime.setText("+ " + penaltyTimeMinutes + " : " +  
                penaltyTimeSeconds);
        });
        
        //when the penalty reset btn is pushed
        resetPenalties.setOnAction(e -> {
            //reseting all penalty vars to 0
            commandPenalties = 0;
            conePenalties = 0;
            boundsPenalties = 0;
            penaltyTimeSeconds = 0;
            
            //refreshing labels
            lblCommand.setText("Command: " + commandPenalties);
            lblCone.setText("Cones: " + conePenalties);
            lblBounds.setText("Boundries: " + boundsPenalties);
            lblTotalTime.setText("+ " + penaltyTimeMinutes + " : " +  
                penaltyTimeSeconds);
        });
        
    }
      
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
