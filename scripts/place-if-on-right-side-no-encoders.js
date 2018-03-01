var COUNTER_TICKS_HIGH_SPEED = 20;//DISTANCE_HIGH_SPEED = 40; //inches
var COUNTER_TICKS_TO_AUTOLINE = 70;//DISTANCE_TO_AUTOLINE = 120; //inches
var HIGH_SPEED = -.8;
var LOW_SPEED = -.3;

var counter = 0;

function init() {
	//drivetrain.resetEncoders();
};

function periodic() {
	arm.middle();

	if (counter < COUNTER_TICKS_HIGH_SPEED) {
		drivetrain.tankDrive(HIGH_SPEED, HIGH_SPEED);
	} else if (counter < COUNTER_TICKS_TO_AUTOLINE){
		drivetrain.tankDrive(LOW_SPEED, LOW_SPEED);
	} else {
		drivetrain.tankDrive(0, 0);

		if (gameData.length == 3 && gameData.charAt(0) == 'R'){
			intake.shoot();
		}
	}

	counter += 1;
}