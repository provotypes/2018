//Auto 1
var DISTANCE_TO_AUTOLINE = 120; //inches
var AUTO_SPEED = 0.2;

function init(){};

function periodic(){
	if (drivetrain.encoder.getDistance() < DISTANCE_TO_AUTOLINE){
		drivetrain.tankDrive(AUTO_SPEED, AUTO_SPEED);
	} else {
		drivetrain.tankDrive(0, 0);
	}
}